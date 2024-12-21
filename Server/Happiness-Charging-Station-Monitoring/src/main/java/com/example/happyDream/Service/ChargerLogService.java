package com.example.happyDream.Service;

import com.example.happyDream.DTO.ChargerLogDTO;
import com.example.happyDream.Entity.ChargerLogEntity;
import com.example.happyDream.Repository.ChargerLogRepository;
import com.example.happyDream.Util.Converter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class ChargerLogService {
    private final ChargerLogRepository chargerLogRepository;

    @Autowired
    public ChargerLogService(ChargerLogRepository chargerLogRepository) {
        this.chargerLogRepository = chargerLogRepository;
    }

    // 전체 충전 로그 조회
    public List<ChargerLogDTO> getAllChargerLog(Boolean join, Boolean descYn) {
        List<ChargerLogDTO> dtoList;
//        if (join == false) {
//            for (ChargerLogDTO dto : dtoList) {
//                ChargerDTO chargerDto = ChargerDTO.builder().id(dto.getChargerId()).build();
//                dto.setChargerId(chargerDto);
//            }
//        }

        if (descYn) {
            dtoList = Converter.EntityListToDtoList(this.chargerLogRepository.findAllByOrderByIdDesc(), ChargerLogEntity::toDTO);
        }
        else {
            dtoList = Converter.EntityListToDtoList(this.chargerLogRepository.findAll(), ChargerLogEntity::toDTO);
        }
        return dtoList;
    }

    // 전체 충전 로그 삭제
    public Long deleteAllChargerLog() {
        long count = chargerLogRepository.count();
        this.chargerLogRepository.deleteAll();
        count = count - chargerLogRepository.count();
        log.warn("전체 충전 로그 {}개가 삭제됨", count);
        return count;
    }

    // 특정 충전기의 전체 충전 로그 조회
    public List<ChargerLogDTO> getAllTargetChargerLog(Integer chargerId, Boolean descYn) {
        List<ChargerLogDTO> dtoList;
        if (descYn) {
            dtoList = Converter.EntityListToDtoList(this.chargerLogRepository.findAllByChargerIdOrderByDesc(chargerId), ChargerLogEntity::toDTO);
        }
        else {
            dtoList = Converter.EntityListToDtoList(this.chargerLogRepository.findAllByChargerId(chargerId), ChargerLogEntity::toDTO);
        }
        return dtoList;
    }

    // 특정 충전기 충전 로그 추가
    public void createTargetChargerLog(ChargerLogDTO chargerLogDto) {
        this.chargerLogRepository.save(chargerLogDto.toEntity());
    }
    // TODO - 충전기 하드웨어 설정 값 조회
    // TODO - 충전기 하드웨어 설정 값 변경
}
