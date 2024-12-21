package com.example.happyDream.Service;

import com.example.happyDream.DTO.ChargerDTO;
import com.example.happyDream.DTO.ReviewDTO;
import com.example.happyDream.Entity.ChargerEntity;
import com.example.happyDream.Entity.ReviewEntity;
import com.example.happyDream.Entity.UserEntity;
import com.example.happyDream.Repository.ReviewRepository;
import com.example.happyDream.Util.Converter;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
public class ReviewService {
    private final UserService userService;
    private final ChargerService chargerService;
    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(UserService userService, ChargerService chargerService, ReviewRepository reviewRepository) {
        this.userService = userService;
        this.chargerService = chargerService;
        this.reviewRepository = reviewRepository;
    }

    public List<ReviewDTO> reviewSelectAll() {
        List<ReviewEntity> entityList = this.reviewRepository.findAll();
        return Converter.EntityListToDtoList(entityList, ReviewEntity::toDTO);
    }

    public void reviewDeleteAll() {
        this.reviewRepository.deleteAll();
    }

    public void reviewInsert(ReviewDTO review) {
        this.reviewRepository.save(review.toEntity());
    }

    public ReviewDTO reviewSelect(Integer id) {
        Optional<ReviewEntity> entity = this.reviewRepository.findById(id);
        if(entity.isEmpty()){
            throw new EntityNotFoundException();
        }
        return entity.get().toDTO();
    }

    public List<ReviewDTO> reviewSelectByChargerId(Integer chargerId) {
        List<ReviewEntity> reviewEntities = reviewRepository.findByChargerId(chargerId);
        return reviewEntities.stream()
                .map(ReviewEntity::toDTO)
                .collect(Collectors.toList());
    }

    public List<ReviewDTO> reviewSelectAsSearch(String city, String district, String keyword) {
        String normalizeCity = reverseNormalizeProvinceName(city);
        if (city == null && district == null && keyword == null) {
            return Converter.EntityListToDtoList(this.reviewRepository.findAll(), ReviewEntity::toDTO);
        }
        if (city == null && district == null) {
            return Converter.EntityListToDtoList(this.reviewRepository.findBySubject(keyword), ReviewEntity::toDTO);
        }
        List<ChargerEntity> chargerDTOList = this.chargerService.selectChargerCityAnddistrict(city, normalizeCity, district);
        if (keyword == null || keyword.isEmpty()) {
            return Converter.EntityListToDtoList(this.reviewRepository.findByAddress(chargerDTOList), ReviewEntity::toDTO);
        }
        return Converter.EntityListToDtoList(this.reviewRepository.findByAddressAndSubject(chargerDTOList, keyword),
                ReviewEntity::toDTO);
    }

    public List<ReviewDTO> reviewSelectByRatingFive(){
        List<ReviewEntity> entityList = this.reviewRepository.findByRatingFive();
        return Converter.EntityListToDtoList(entityList, ReviewEntity::toDTO);
    }

    public List<ReviewDTO> reviewSelectByRatingFour(){
        List<ReviewEntity> entityList = this.reviewRepository.findByRatingFour();
        return Converter.EntityListToDtoList(entityList, ReviewEntity::toDTO);
    }

    public List<ReviewDTO> reviewSelectByRatingThree(){
        List<ReviewEntity> entityList = this.reviewRepository.findByRatingThree();
        return Converter.EntityListToDtoList(entityList, ReviewEntity::toDTO);
    }

    public List<ReviewDTO> reviewSelectByRatingTwo(){
        List<ReviewEntity> entityList = this.reviewRepository.findByRatingTwo();
        return Converter.EntityListToDtoList(entityList, ReviewEntity::toDTO);
    }

    public List<ReviewDTO> reviewSelectByRatingOne(){
        List<ReviewEntity> entityList = this.reviewRepository.findByRatingOne();
        return Converter.EntityListToDtoList(entityList, ReviewEntity::toDTO);
    }

    @Transactional
    public void reviewUpdate(ReviewDTO reviewDTO) {
        ReviewEntity reviewEntity = reviewRepository.findByChargerIdAndUserId(
                reviewDTO.getChargerId(), reviewDTO.getUserId()
        ).orElseThrow(() -> new IllegalArgumentException("해당 리뷰가 존재하지 않습니다."));
        /*
        reviewEntity.setReviewContent(reviewDTO.getReviewContent());
        reviewEntity.setRating(reviewDTO.getRating());
        reviewEntity.setModifiedAt(LocalDateTime.now());
         */
        // 엔티티에 Setter 미사용시 수정 로직을 어떻게 처리할 것인지 논의 필요
    }

    public void reviewDelete(Integer id) {
        this.reviewRepository.deleteById(id);
    }

    public Map<String, List<String>> reviewAddress() {
        return createUniqueCities(this.chargerService.selectChargerAddress());
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
