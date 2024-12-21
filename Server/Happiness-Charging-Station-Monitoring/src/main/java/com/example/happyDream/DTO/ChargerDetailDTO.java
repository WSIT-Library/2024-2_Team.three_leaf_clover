package com.example.happyDream.DTO;

import com.example.happyDream.Entity.ChargerStateEntity;
import lombok.*;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChargerDetailDTO {
    // 츙전기 기본 데이터(Charger)
    private Integer chargerId;

    //private ChargerStateEntity chargerState;
    private String name;
    private String city1;
    private String city2;
    private Integer city2Code;
    private String addressNew;
    private String addressOld;
    private String addressDetail;
    private Double latitude;
    private Double longitude;

    // 츙전기 운영 관련 데이터(Charger)
    private Time weekdayOpen;
    private Time saturdayOpen;
    private Time holidayOpen;
    private Time weekdayClose;
    private Time saturdayClose;
    private Time holidayClose;
    private Integer chargerCount;
    private Boolean chargeAirYn;
    private Boolean chargePhoneYn;
    private String callNumber;

    // 충전 상태 기본 데이터 (ChargerState)
    private Integer chargerStateId;
    private Boolean usingYn;
    private Boolean brokenYn;

    // 관리 목적 데이터 (Charger)
    private LocalDate updatedDate;
    private LocalDateTime chargerCreatedAt;
    private LocalDateTime chargerModifiedAt;
    private Boolean deletedYn;
    private LocalDateTime deletedAt;

    // 관리 목적 데이터 (ChargerState)
    private LocalDateTime usingAt;
    private LocalDateTime brokenAt;
    private LocalDateTime chargerStateCreatedAt;
    private LocalDateTime chargerStateModifiedAt;

    // 충전기 리뷰 관련 데이터 (Review)
    private Double rating;
}