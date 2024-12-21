package com.example.happyDream.Service;

import com.example.happyDream.DTO.ChargerDTO;
import com.example.happyDream.DTO.ChargerDetailDTO;
import com.example.happyDream.Entity.ChargerEntity;
import com.example.happyDream.Repository.ChargerRepository;
import com.example.happyDream.Util.Converter;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ChargerService {
    private final ChargerRepository chargerRepository;

    @Autowired
    public ChargerService(ChargerRepository chargerRepository) {
        this.chargerRepository = chargerRepository;
    }

    // 충전기 추가(단일)
    public ChargerDTO createCharger(ChargerDTO chargerDto) {
        return this.chargerRepository.save(chargerDto.toEntity()).toDTO();
    }

    // 충전기 추가(단일) [레거시]
    public ChargerEntity createChargerLegacy(ChargerEntity chargerEntity) {
        return this.chargerRepository.save(chargerEntity);
    }

    // 충전기 추가(리스트)
    public void createCharger(List<ChargerDTO> chargerDtoList) {
        List<ChargerEntity> chargerEntityList = Converter.DtoListToEntityList(chargerDtoList, ChargerDTO::toEntity);
        this.chargerRepository.saveAll(chargerEntityList);
    }

    // 전체 충전기 조회
    public List<ChargerDTO> chargerSelectAll(){
        List<ChargerEntity> entityList = this.chargerRepository.findAll();
        List<ChargerDTO> dtoList = Converter.EntityListToDtoList(entityList, ChargerEntity::toDTO);
        System.out.println("가져온 충전기 수: " + dtoList.size());
        return dtoList;
    }

    // 충전중인 충전기 조회 - ChargerDTO
    public List<ChargerDTO> chargerSelectByUsingYn(Boolean usingYn) {
        List<ChargerEntity> entityList = this.chargerRepository.findAllChargerByUsingYn(usingYn);
        return Converter.EntityListToDtoList(entityList, ChargerEntity::toDTO);
    }

    // 고장난 충전기 조회 - ChargerDTO
    public List<ChargerDTO> chargerSelectByBrokenYn(Boolean brokenYn) {
        List<ChargerEntity> entityList = this.chargerRepository.findAllChargerByBrokenYn(brokenYn);
        return Converter.EntityListToDtoList(entityList, ChargerEntity::toDTO);
    }

    // 전체 충전기 상세 정보 조회(State 포함 정보)
    public List<ChargerDetailDTO> chargerSelectAllDetail(){
        List<ChargerDetailDTO> dtoList = this.chargerRepository.findAllChargerDetail();
        System.out.println("가져온 충전기 수: " + dtoList.size());
        return dtoList;
    }

    // 충전중인 충전기 조회 - ChargerDetailDTO
    public List<ChargerDetailDTO> chargerSelectDetailByUsingYn(Boolean usingYn) {
        List<ChargerDetailDTO> dtoList = this.chargerRepository.findAllChargerDetailByUsingYn(usingYn);
        return dtoList;
    }

    // 고장난 충전기 조회 - ChargerDetailDTO
    public List<ChargerDetailDTO> chargerSelectDetailByBrokenYn(Boolean brokenYn) {
        List<ChargerDetailDTO> dtoList = this.chargerRepository.findAllChargerDetailByBrokenYn(brokenYn);
        return dtoList;
    }

    // 전체 충전기 삭제
    public void chargerDeleteAll(){
        this.chargerRepository.deleteAll();
    }

    // 특정 충전기 조회
    public ChargerDTO chargerSelect(Integer id){
        Optional<ChargerEntity> entity = this.chargerRepository.findById(id);
        if(entity.isPresent()) {
            return entity.get().toDTO();
        } else {
            throw new EntityNotFoundException();
        }
    }

    // 특정 충전기 조회(주소)
    public List<ChargerDTO> chargerSelectByAddress(String address){
        Set<ChargerEntity> chargers = new HashSet<>();
        List<ChargerDTO> chargersDTO = new ArrayList<>();
        String[] addressList = address.split(" ");
        try{
            for (int i = 0; i<addressList.length; i++){
                if(i==0){
                    chargers.addAll(this.chargerRepository.findChargersByAddress(addressList[i]));
                }else{
                    chargers.retainAll(this.chargerRepository.findChargersByAddress(addressList[i]));
                }
            }
            // chargers 리스트가 비어 있는지 체크하고, 비어 있지 않다면 내용을 출력
            if (chargers.isEmpty()) {
                System.out.println("충전소 데이터가 없습니다.");
                throw new EntityNotFoundException();
            } else {
                for(ChargerEntity chargerEntitys : chargers) {
                    chargersDTO.add(chargerEntitys.toDTO());
                }
                return chargersDTO;
            }
        }catch (Exception exception){
            throw new EntityNotFoundException();
        }
    }

    // 특정 충전기 삭제
    public void chargerDelete(Integer id){
        this.chargerRepository.deleteById(id);
    }

    // 주변 충전기 조회
    public List<ChargerDTO> chargerSelectNear(Double latitude, Double longitude){
        List<ChargerEntity> chargers = this.chargerRepository.findChargersByNear(latitude, longitude);
        if(chargers.isEmpty()) {
            throw new EntityNotFoundException();
        }
        return Converter.EntityListToDtoList(chargers, ChargerEntity::toDTO);
    }

    public List<ChargerDTO> chargerSelectWithoutLogInPeriod(LocalDateTime fromAt, LocalDateTime toAt) {
        List<ChargerEntity> chargers = this.chargerRepository.findAllChargerWithoutLogInPeriod(fromAt, toAt);
        return Converter.EntityListToDtoList(chargers, ChargerEntity::toDTO);
    }

    // 충전기 주소 불러오기
    public List<Object[]> selectChargerAddress() {
        return this.chargerRepository.findAddress();
    }

    //
    public List<ChargerEntity> selectChargerCityAnddistrict(String city, String normalizeCity, String district) {
        try{
            if(district == null){
                return this.chargerRepository.findByCity(city, normalizeCity);
            }
            return this.chargerRepository.findByCityAndDistrict(city, normalizeCity, district);
        }catch (Exception exception){
            throw new EntityNotFoundException();
        }
    }
}
