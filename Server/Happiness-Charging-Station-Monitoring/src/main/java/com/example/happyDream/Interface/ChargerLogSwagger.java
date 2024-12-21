package com.example.happyDream.Interface;

import com.example.happyDream.DTO.ChargerLogDTO;
import com.example.happyDream.DTO.ResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "ChargerLog", description = "충전기 로그 API")
public interface ChargerLogSwagger {
    @Operation(summary = "전체 충전 로그 조회", description = "전체 충전기의 충전 로그를 조회합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = String.class, hidden = true))),
                    @ApiResponse(responseCode = "204", description = "조회는 성공했지만, 데이터가 없음", content = @Content(schema = @Schema(implementation = String.class, hidden = true)))
            })
    public ResponseDTO getAllChargerLog();

    @Operation(summary = "전체 충전 로그 삭제", description = "전체 충전기의 충전 로그를 삭제합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "삭제 성공", content = @Content(schema = @Schema(implementation = ResponseDTO.class, hidden = true)))
            })
    public ResponseDTO deleteAllChargerLog();

    @Operation(summary = "특정 충전기의 전체 충전 로그 조회", description = "충전기 id를 입력받아 특정 충전기의 전체 충전 로그를 조회합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = ResponseDTO.class, hidden = true)))
            },
            parameters = {
                    @Parameter(description = "충전기 id", required = true, in = ParameterIn.PATH, example = "1")
            }
    )
    public ResponseDTO getChargerLog(Integer chargerId);

    @Operation(summary = "특정 충전기의 충전 로그 추가", description = "충전기 id를 입력받아 특정 충전기의 충전 로그를 추가합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "충전 로그 추가 성공", content = @Content(schema = @Schema(implementation = ResponseDTO.class, hidden = true))),
                    @ApiResponse(responseCode = "400", description = "충전 로그 추가 실패: ampere is null(파라미터 오류)", content = @Content(schema = @Schema(implementation = ResponseDTO.class, hidden = true))),
                    @ApiResponse(responseCode = "404", description = "충전 로그 추가 실패: 존재하지 않는 충전기에 대한 작업 시도", content = @Content(schema = @Schema(implementation = ResponseDTO.class, hidden = true))),
                    @ApiResponse(responseCode = "500", description = "충전 로그 추가 실패: 알 수 없는 오류", content = @Content(schema = @Schema(implementation = ResponseDTO.class, hidden = true)))},
            parameters = {
                    @Parameter(name = "id", description = "충전기 id", required = true, in = ParameterIn.PATH, example = "1")
            }
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(
            examples = {
                    @ExampleObject(
                            name = "Correct Request: Response Code 200",
                            description = "정상적인 요청",
                            value = "{ \"ampere\": 53.941 }"),
                    @ExampleObject(
                            name = "Wrong Request: Response Code 400",
                            description = "잘못된 요청(null)",
                            value = "{ }")
            }
    ))
    public ResponseDTO createChargerLog(Integer chargerId, ChargerLogDTO _chargerLogDto);

    public ResponseDTO getChargerLogSetting();

    public ResponseDTO updateChargerLogSetting();
}
