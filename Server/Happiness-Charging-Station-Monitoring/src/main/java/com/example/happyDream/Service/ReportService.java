package com.example.happyDream.Service;

import com.example.happyDream.DTO.ReportDTO;
import com.example.happyDream.DTO.ReviewDTO;
import com.example.happyDream.Entity.ChargerEntity;
import com.example.happyDream.Entity.ReportEntity;
import com.example.happyDream.Entity.ReviewEntity;
import com.example.happyDream.Repository.ReportRepository;
import com.example.happyDream.Repository.ReportSpecification;
import com.example.happyDream.Repository.ReviewRepository;
import com.example.happyDream.Util.Converter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class ReportService {
    private final ChargerService chargerService;
    private final ReportRepository reportRepository;

    @Autowired
    public ReportService(ChargerService chargerService, ReportRepository reportRepository) {
        this.chargerService = chargerService;
        this.reportRepository = reportRepository;
    }

    public void insertReport(ReportDTO reportDTO) {
        ReportEntity reportEntity = reportDTO.toEntity();
        reportRepository.save(reportEntity);
    }

    public List<ReportDTO> reportSelectAll() {
        List<ReportEntity> entityList = this.reportRepository.findAllOrdered();
        return Converter.EntityListToDtoList(entityList, ReportEntity::toDTO);
    }

    public List<ReportDTO> reportSelectByNotCheckedReport(){
        List<ReportEntity> entityList = this.reportRepository.findByNotCheckedReport();
        return Converter.EntityListToDtoList(entityList, ReportEntity::toDTO);
    }

    public List<ReportDTO> reportSelectByCheckedReport(){
        List<ReportEntity> entityList = this.reportRepository.findByCheckedReport();
        return Converter.EntityListToDtoList(entityList, ReportEntity::toDTO);
    }

    public List<ReportDTO> reportSelectByCheckedRepair(){
        List<ReportEntity> entityList = this.reportRepository.findByCheckedRepair();
        return Converter.EntityListToDtoList(entityList, ReportEntity::toDTO);
    }

    public void reportCheck(Integer reportId) {
        ReportEntity report = this.reportRepository.findById(reportId)
                .orElseThrow(() -> new IllegalArgumentException("신고를 찾을 수 없습니다."));
        report.updateCheckedReport(true);
        this.reportRepository.save(report);
    }

    public void reportRepair(Integer reportId) {
        ReportEntity report = this.reportRepository.findById(reportId)
                .orElseThrow(() -> new IllegalArgumentException("신고를 찾을 수 없습니다."));
        report.updateCheckedRepair(true);
        this.reportRepository.save(report);
    }

    public Map<String, List<String>> reportAddress() {
        return createUniqueCities(this.chargerService.selectChargerAddress());
    }

    public List<ReportDTO> reportSelectAsSearch(String city, String district, Integer chargerId,
                                                LocalDateTime startDate, LocalDateTime endDate) {
        String normalizeCity = reverseNormalizeProvinceName(city);
        List<ChargerEntity> chargerIdList = this.chargerService.selectChargerCityAnddistrict(city, normalizeCity, district);
        Specification<ReportEntity> spec = ReportSpecification.combineConditions(chargerIdList, chargerId, startDate, endDate);
        return Converter.EntityListToDtoList(reportRepository.findAll(spec), ReportEntity::toDTO);
    }

    private Map<String, List<String>> createUniqueCities (List<Object[]> cities){
        Map<String, List<String>> uniqueCities = cities.stream()
                .filter(city -> city.length > 1 && city[0] instanceof String && city[1] instanceof String)
                .collect(Collectors.toMap(
                        city -> normalizeProvinceName((String) city[0]),
                        city -> new ArrayList<>(List.of((String) city[1])), // 값을 List로 저장
                        (existing, replacement) -> { // 중복된 key가 있을 때, 기존 리스트에 추가
                            existing.addAll(replacement);
                            return existing;
                        }
                ));
        return uniqueCities;
    }

    // 도시 이름을 정규화하는 메서드
    private String normalizeProvinceName(String provinceName) {
        // 전북특별자치도 -> 전라북도, 강원특별자치도 -> 강원도
        if ("전북특별자치도".equals(provinceName)) {
            return "전라북도";
        }
        if ("강원특별자치도".equals(provinceName)) {
            return "강원도";
        }
        return provinceName; // 그대로 반환
    }

    // 도시 이름을 역정규화하는 메서드
    private String reverseNormalizeProvinceName(String provinceName) {
        // 전북특별자치도 -> 전라북도, 강원특별자치도 -> 강원도
        if ("전라북도".equals(provinceName)) {
            return "전북특별자치도";
        }
        if ("강원도".equals(provinceName)) {
            return "강원특별자치도";
        }
        return provinceName; // 그대로 반환
    }
}
