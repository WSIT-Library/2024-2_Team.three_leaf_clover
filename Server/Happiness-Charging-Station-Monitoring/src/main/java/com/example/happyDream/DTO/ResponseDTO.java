package com.example.happyDream.DTO;

import com.google.gson.annotations.SerializedName;
import lombok.*;
import org.springframework.context.annotation.Description;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDTO {
    @SerializedName("api_version")
    private String apiVersion;
    private String status;

    @SerializedName("response_code")
    private Integer responseCode;

    @Builder.Default
    private LocalDateTime time = LocalDateTime.now();

    private String message;

    @Builder.Default
    private Integer count = 0; // 기본값 0 설정

    @Builder.Default
    private List<?> data = Collections.unmodifiableList(new ArrayList<>()); // 기본값 빈 리스트 설정

    public static ResponseDTOBuilder builder() {
        return new ResponseDTOBuilder(); // Builder 어노테이션이 생성한 빌더
    }

    @Description("test")
    public static ResponseDTO success(String apiVersion, int responseCode) {
        // TODO - 추후 responseCode에 맞춰 정의
        return builder()
                .apiVersion(apiVersion)
                .status("success")
                .responseCode(responseCode)
                .build();
    }

    public static ResponseDTO success(String apiVersion, int responseCode, List<?> data) {
        return builder()
                .apiVersion(apiVersion)
                .status("success")
                .responseCode(responseCode)
                .count(data.size())
                .data(data)
                .build();
    }

    public static ResponseDTO success(String apiVersion, int responseCode, String jwt) {
        return builder()
                .apiVersion(apiVersion)
                .status("success")
                .responseCode(responseCode)
                .message(jwt)
                .build();
    }

    public static ResponseDTO error(String apiVersion, int responseCode, String message) {
        return builder()
                .apiVersion(apiVersion)
                .status("error")
                .responseCode(responseCode)
                .message(message)
                .build();
    }
}
