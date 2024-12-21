package com.example.happyDream.DTO;

import com.example.happyDream.Entity.ChargerEntity;
import com.example.happyDream.Entity.ChargerStateEntity;
import com.example.happyDream.Entity.ChargerStatisticEntity;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChargerStatisticDTO {
    private Long id;
    private Integer chargerId;
    private LocalDateTime startedAt;
    private LocalDateTime finishedAt;
    private Integer usingSecond;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public ChargerStatisticEntity toEntity() {
        ChargerEntity chargerEntity = ChargerEntity.builder().id(chargerId).build();
        return ChargerStatisticEntity.builder()
                .id(id)
                .charger(chargerEntity)
                .startedAt(startedAt)
                .finishedAt(finishedAt)
                .usingSecond(usingSecond)
                .createdAt(createdAt)
                .modifiedAt(modifiedAt)
                .build();
    }
}