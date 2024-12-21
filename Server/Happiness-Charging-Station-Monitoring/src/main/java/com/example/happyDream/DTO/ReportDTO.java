package com.example.happyDream.DTO;

import com.example.happyDream.Entity.ChargerEntity;
import com.example.happyDream.Entity.ReportEntity;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
public class ReportDTO {
    private Integer id; // 고장 보고 식별자
    private Integer chargerId; // 충전소 ID
    private String reportContent; // 고장 내용
    private LocalDateTime createdAt; // 데이터 생성 시각
    private Boolean checkedReport;
    private Boolean checkedRepair;

    // 빌더 패턴을 사용하여 객체 생성
    @Builder
    public ReportDTO(Integer id, Integer chargerId, String reportContent, LocalDateTime createdAt, Boolean checkedReport, Boolean checkedRepair) {
        this.id = id;
        this.chargerId = chargerId;
        this.reportContent = reportContent;
        this.createdAt = createdAt;
        this.checkedReport = checkedReport;
        this.checkedRepair = checkedRepair;
    }

    // ReportDTO를 Report 엔티티로 변환
    public ReportEntity toEntity() {
        ChargerEntity chargerEntity = ChargerEntity.builder().id(chargerId).build();
        return ReportEntity.builder()
                .id(id)
                .chargerId(chargerEntity)
                .reportContent(reportContent)
                .createdAt(createdAt)
                .checkedReport(checkedReport)
                .checkedRepair(checkedRepair)
                .build();
    }
}
