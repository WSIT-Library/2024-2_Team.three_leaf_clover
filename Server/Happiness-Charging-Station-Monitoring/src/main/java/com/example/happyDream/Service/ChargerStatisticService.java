package com.example.happyDream.Service;

import com.example.happyDream.DTO.ChargerLogDTO;
import com.example.happyDream.DTO.ChargerStatisticDTO;
import com.example.happyDream.Entity.ChargerLogEntity;
import com.example.happyDream.Entity.ChargerStatisticEntity;
import com.example.happyDream.Repository.ChargerStatisticRepository;
import com.example.happyDream.Util.Converter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ChargerStatisticService {
    private final ChargerStatisticRepository chargerStatisticRepository;

    @Autowired
    public ChargerStatisticService(ChargerStatisticRepository chargerStatisticRepository) {
        this.chargerStatisticRepository = chargerStatisticRepository;
    }

    // 전체 충전기 이용 내역 조회
    public List<ChargerStatisticDTO> getAllChargerStatistic() {
        return Converter.EntityListToDtoList(this.chargerStatisticRepository.findAll(), ChargerStatisticEntity::toDTO);
    }

    // 전체 충전기 이용 내역 삭제
    public void deleteAllChargerStatistic() {
        this.chargerStatisticRepository.deleteAll();
    }

    // 특정 충전기의 이용 내역 조회
    public List<ChargerStatisticDTO> getTargetChargerStatistic(Integer chargerId, Boolean descYn) {
        List<ChargerStatisticDTO> dtoList;
        if (descYn) {
            dtoList = Converter.EntityListToDtoList(this.chargerStatisticRepository.findAllByChargerIdOrderByDesc(chargerId), ChargerStatisticEntity::toDTO);
        }
        else {
            dtoList = Converter.EntityListToDtoList(this.chargerStatisticRepository.findAllByChargerId(chargerId), ChargerStatisticEntity::toDTO);
        }
        return dtoList;
    }

    // 특정 충전기의 이용 시작 추가


    // 특정 충전기의 이용 종료 추가


}
