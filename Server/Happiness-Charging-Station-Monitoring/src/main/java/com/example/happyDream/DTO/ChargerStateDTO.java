package com.example.happyDream.DTO;

import com.example.happyDream.Entity.ChargerEntity;
import com.example.happyDream.Entity.ChargerStateEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChargerStateDTO {
    // 충전 상태 기본 데이터
    private Integer id;
    private Integer chargerId;
    private Boolean usingYn;
    private Boolean brokenYn;

    // 관리 목적 데이터
    private LocalDateTime usingAt;
    private LocalDateTime brokenAt;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public ChargerStateEntity toEntity() {
        ChargerEntity chargerEntity = ChargerEntity.builder().id(chargerId).build();
        return ChargerStateEntity.builder()
                .id(id)
                .charger(chargerEntity)
                .usingYn(usingYn)
                .brokenYn(brokenYn)
                .usingAt(usingAt)
                .brokenAt(brokenAt)
                .createdAt(createdAt)
                .modifiedAt(modifiedAt)
                .build();
    }
}