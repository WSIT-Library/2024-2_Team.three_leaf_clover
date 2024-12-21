package com.example.happyDream.Entity;

import com.example.happyDream.DTO.ChargerLogDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "charger_log")
@Getter //Setter 미사용
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED) //생성자 외부 접근 차단
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class) //Auditing 사용 명시
public class ChargerLogEntity {
    //충전 로그 데이터
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) //충전기 1대 당 로그 여러개
    @JoinColumn(name = "charger_id") //키 매핑 에러 시 고려
    private ChargerEntity charger;

    @NotNull
    @Column
    private Float ampere;

    @Column
    private LocalDateTime requestAt;

    //관리 목적 데이터
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column
    private LocalDateTime modifiedAt;

    public ChargerLogDTO toDTO() {
        return ChargerLogDTO.builder()
                .id(id)
                .chargerId(charger.getId())
                .ampere(ampere)
                .requestAt(requestAt)
                .createdAt(createdAt)
                .modifiedAt(modifiedAt)
                .build();
    }
}
