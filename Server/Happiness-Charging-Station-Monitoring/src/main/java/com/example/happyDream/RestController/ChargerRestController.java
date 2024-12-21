package com.example.happyDream.RestController;

import com.example.happyDream.DTO.ChargerDTO;
import com.example.happyDream.DTO.ChargerDetailDTO;
import com.example.happyDream.DTO.ResponseDTO;
import com.example.happyDream.Interface.ChargerSwagger;
import com.example.happyDream.Service.ChargerServiceFacade;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Slf4j
@RestController //ResponseBody + Controller 어노테이션
@RequestMapping(path = "/api/v1", produces = "application/json")
public class ChargerRestController implements ChargerSwagger {
    private final ChargerServiceFacade chargerServiceFacade;

    @Autowired
    public ChargerRestController(ChargerServiceFacade chargerServiceFacade) {
        this.chargerServiceFacade = chargerServiceFacade;
    }

    // 전체 충전기 조회
    @GetMapping("/chargers")
    public ResponseDTO getAllChargers() {
        List<ChargerDTO> chargerDtoList = this.chargerServiceFacade.chargerSelectAll();

        ResponseDTO responseDto;
        if (chargerDtoList.isEmpty()) {
            responseDto = ResponseDTO.success("v1", HttpServletResponse.SC_NO_CONTENT, Collections.unmodifiableList(chargerDtoList));
        }
        else {
            responseDto = ResponseDTO.success("v1", HttpServletResponse.SC_OK, Collections.unmodifiableList(chargerDtoList));
        }
        return responseDto;
    }

    // 전체 충전기 조회(상세)
    @GetMapping("/chargers/detail")
    public ResponseDTO getAllChargersDetail() {
        List<ChargerDetailDTO> chargerDetailDtoList = this.chargerServiceFacade.chargerSelectAllDetail();

        ResponseDTO responseDto;
        if (chargerDetailDtoList.isEmpty()) {
            responseDto = ResponseDTO.success("v1", HttpServletResponse.SC_NO_CONTENT, Collections.unmodifiableList(chargerDetailDtoList));
        }
        else {
            responseDto = ResponseDTO.success("v1", HttpServletResponse.SC_OK, Collections.unmodifiableList(chargerDetailDtoList));
        }
        return responseDto;
    }

    // 충전기 검색
    @GetMapping("/chargers/search")
    public ResponseDTO getAllSearchedCharger(@RequestParam(required = false) Boolean usingYn,
                                             @RequestParam(required = false) Boolean brokenYn) {
        if (usingYn == null && brokenYn == null) {
            log.info("요청에 파라미터가 모두 누락됨");
            return this.getAllChargers();
        }

        ResponseDTO responseDto;

        if (usingYn != null) {
            List<ChargerDetailDTO> ChargerDetailDtoList = this.chargerServiceFacade.chargerSelectDetailByUsingYn(usingYn);
            responseDto = ResponseDTO.success("v1", HttpServletResponse.SC_OK, ChargerDetailDtoList);
            return responseDto;
        }
        else if (brokenYn != null) {
            List<ChargerDetailDTO> chargerDtoList = this.chargerServiceFacade.chargerSelectDetailByBrokenYn(brokenYn);
            responseDto = ResponseDTO.success("v1", HttpServletResponse.SC_OK, chargerDtoList);
            return responseDto;
        }

        responseDto = ResponseDTO.error("v1", HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "처리되지 않음");
        return responseDto;
    }

    // 충전기 생성
    @PostMapping("/chargers")
    public ResponseDTO createCharger(@RequestParam(required = false) Boolean initYn,
                                     @RequestBody String requestJson) {
        return this.chargerServiceFacade.createChargerFromJson(initYn, requestJson);
    }

    @GetMapping("/chargers/test")
    public ResponseDTO getAllChargerWithoutLogInPeriod() {
        List<ChargerDTO> chargerDtoList = this.chargerServiceFacade.chargerSelectWithoutLogInPeriod(30);

        ResponseDTO responseDto;
        if (chargerDtoList.isEmpty()) {
            responseDto = ResponseDTO.success("v1", HttpServletResponse.SC_NO_CONTENT, Collections.unmodifiableList(chargerDtoList));
        }
        else {
            responseDto = ResponseDTO.success("v1", HttpServletResponse.SC_OK, Collections.unmodifiableList(chargerDtoList));
        }
        return responseDto;
    }
}