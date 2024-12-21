package com.example.happyDream.DTO;

import com.example.happyDream.Entity.ChargerEntity;
import com.example.happyDream.Entity.ChargerLogEntity;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChargerLogDTO {
    //충전 로그 데이터
    private Long id;
    private Integer chargerId;
    private Float ampere;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") // LocalDateTime 직렬화 설정
    private LocalDateTime requestAt;

    //관리 목적 데이터
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public ChargerLogEntity toEntity() {
        ChargerEntity chargerEntity = ChargerEntity.builder().id(chargerId).build();
        return ChargerLogEntity.builder()
                .id(id)
                .charger(chargerEntity)
                .ampere(ampere)
                .requestAt(requestAt)
                .createdAt(createdAt)
                .modifiedAt(modifiedAt)
                .build();
    }
}
