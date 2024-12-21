package com.example.happyDream.RestController;

import com.example.happyDream.DTO.ChargerDTO;
import com.example.happyDream.DTO.ChargerLogDTO;
import com.example.happyDream.DTO.ResponseDTO;
import com.example.happyDream.Interface.ChargerLogSwagger;
import com.example.happyDream.Service.ChargerLogService;
import com.example.happyDream.Service.ChargerServiceFacade;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1", produces = "application/json")
public class ChargerLogRestController implements ChargerLogSwagger {
    private final ChargerServiceFacade chargerServiceFacade;

    @Autowired
    public ChargerLogRestController(ChargerServiceFacade chargerServiceFacade, ChargerLogService chargerLogService) {
        this.chargerServiceFacade = chargerServiceFacade;
    }
    
    @GetMapping("/chargers/logs")
    public ResponseDTO getAllChargerLog() {
        ResponseDTO responseDto;
        try {
            List<ChargerLogDTO> chargerLogDtoList = this.chargerServiceFacade.getAllChargerLog(false, true);

            if (chargerLogDtoList.isEmpty()) {
                responseDto = ResponseDTO.success("v1", HttpServletResponse.SC_NO_CONTENT, Collections.unmodifiableList(chargerLogDtoList));
            }
            else {
                responseDto = ResponseDTO.success("v1", HttpServletResponse.SC_OK, Collections.unmodifiableList(chargerLogDtoList));
            }
        } catch (Exception e) {
            log.error("알 수 없는 오류: {} ", (Object) e.getStackTrace());
            responseDto = ResponseDTO.error("v1", HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "알 수 없는 오류");
        }

        return responseDto;
    }
    
    // TODO - 권한 검증 추가
    @DeleteMapping("/chargers/logs")
    public ResponseDTO deleteAllChargerLog() {
        Long count = this.chargerServiceFacade.deleteAllChargerLog();

        return ResponseDTO.success("v1", HttpServletResponse.SC_OK, Collections.singletonList(count.toString()));
    }

    @GetMapping("/chargers/{id}/logs")
    public ResponseDTO getChargerLog(@PathVariable("id") Integer chargerId) {
        ResponseDTO responseDto;

        ChargerDTO chargerDto = chargerServiceFacade.chargerSelect(chargerId);
        List<ChargerLogDTO> chargerLogDtoList = chargerServiceFacade.getAllTargetChargerLog(chargerId, true);
        if (chargerLogDtoList.isEmpty()) {
            responseDto = ResponseDTO.success("v1", HttpServletResponse.SC_NO_CONTENT, Collections.unmodifiableList(chargerLogDtoList));
        }
        else {
            responseDto = ResponseDTO.success("v1", HttpServletResponse.SC_OK, Collections.unmodifiableList(chargerLogDtoList));
        }

        return responseDto;
    }

    @PostMapping("/chargers/{id}/logs")
    public ResponseDTO createChargerLog(@PathVariable("id") Integer chargerId,
                                   @RequestBody ChargerLogDTO _chargerLogDto) {
        log.info(_chargerLogDto.toString());
        ResponseDTO responseDto;

        try {
            Float ampere = _chargerLogDto.getAmpere();
            if (ampere == null) {
                throw new NullPointerException("ampere is null");
            }
            ChargerDTO chargerDto = chargerServiceFacade.chargerSelect(chargerId);

            ChargerLogDTO chargerLogDto = ChargerLogDTO.builder()
                    .chargerId(chargerDto.getId())
                    .ampere(ampere)
                    .requestAt(_chargerLogDto.getRequestAt())
                    .build();

            chargerServiceFacade.createTargetChargerLog(chargerLogDto);
            responseDto = ResponseDTO.success("v1", HttpServletResponse.SC_OK);
        } catch (EntityNotFoundException ignored) {
            log.warn("존재하지 않는 충전기 id: {}", chargerId);
            responseDto = ResponseDTO.error("v1", HttpServletResponse.SC_NOT_FOUND, "충전기가 존재하지 않음");
        } catch (NullPointerException e) {
            log.error("{} - 요청한 충전기 id: {}\n{}", e.getMessage(), chargerId, e.getStackTrace());
            responseDto = ResponseDTO.error("v1", HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            log.error("알 수 없는 오류 - 요청한 충전기 id: {}\n{}", chargerId, e.getStackTrace());
            responseDto = ResponseDTO.error("v1", HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "알 수 없는 오류");
        }

        return responseDto;
    }

    // 충전기 하드웨어 설정값 조회
    @GetMapping("/chargers/logs/upgrade")
    public ResponseDTO getChargerLogSetting() {
        return ResponseDTO.error("v1", HttpServletResponse.SC_NOT_IMPLEMENTED, "미구현");
    }

    // 충전기 하드웨어 설정값 변경
    @PutMapping("/chargers/logs/upgrade")
    public ResponseDTO updateChargerLogSetting() {
        return ResponseDTO.error("v1", HttpServletResponse.SC_NOT_IMPLEMENTED, "미구현");
    }
}