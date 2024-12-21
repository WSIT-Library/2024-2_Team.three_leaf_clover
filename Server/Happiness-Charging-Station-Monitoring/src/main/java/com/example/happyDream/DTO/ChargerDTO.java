package com.example.happyDream.DTO;
import com.example.happyDream.Entity.ChargerEntity;
import com.example.happyDream.Entity.ChargerStateEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class ChargerDTO {
    // 츙전기 기본 데이터
    private Integer id;
    @JsonIgnore
    private ChargerStateDTO chargerState;
    private String name;
    private String city1;
    private String city2;
    private Integer city2Code;
    private String addressNew;
    private String addressOld;
    private String addressDetail;
    private Double latitude;
    private Double longitude;

    // 츙전기 운영 관련 데이터
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

    // 관리 목적 데이터
    private LocalDate updatedDate;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Boolean deletedYn;
    private LocalDateTime deletedAt;

    public ChargerEntity toEntity() {
        return ChargerEntity.builder()
                .id(id)
                .name(name)
                .city1(city1)
                .city2(city2)
                .city2Code(city2Code)
                .addressNew(addressNew)
                .addressOld(addressOld)
                .addressDetail(addressDetail)
                .latitude(latitude)
                .longitude(longitude)
                .weekdayOpen(weekdayOpen)
                .saturdayOpen(saturdayOpen)
                .holidayOpen(holidayOpen)
                .weekdayClose(weekdayClose)
                .saturdayClose(saturdayClose)
                .holidayClose(holidayClose)
                .chargerCount(chargerCount)
                .chargeAirYn(chargeAirYn)
                .chargePhoneYn(chargePhoneYn)
                .callNumber(callNumber)
                .updatedDate(updatedDate)
                .createdAt(createdAt)
                .modifiedAt(modifiedAt)
                .deletedYn(deletedYn)
                .deletedAt(deletedAt)
                .build();
    }
}
