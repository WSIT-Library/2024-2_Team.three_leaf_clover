package com.example.happyDream.Interface;

import com.example.happyDream.DTO.ResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Charger", description = "충전기 정보 API")
public interface ChargerSwagger {
    @Operation(summary = "전체 충전기 조회", description = "전체 충전기를 조회합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = ResponseDTO.class, hidden = true))),
                    @ApiResponse(responseCode = "204", description = "조회는 성공했지만, 데이터가 없음", content = @Content(schema = @Schema(implementation = ResponseDTO.class, hidden = true)))
            })
    public ResponseDTO getAllChargers();

    @Operation(summary = "충전기 추가(공공데이터)", description = "공공데이터포털의 충전소 정보가 담긴 JSON을 입력받아 데이터를 추가합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "충전기 추가 성공", content = @Content(schema = @Schema(implementation = ResponseDTO.class, hidden = true))),
                    @ApiResponse(responseCode = "500", description = "충전기 추가 실패(공공데이터-ChargerDTO 간 필드 매핑에 필요한 JSON 파일을 불러오지 못함)", content = @Content(schema = @Schema(implementation = ResponseDTO.class, hidden = true)))
            })
    public ResponseDTO createCharger(Boolean initYn, String requestJson);
}
