package com.example.happyDream.Entity;

import com.example.happyDream.DTO.ReviewDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "review")
@Getter //Setter 미사용
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED) //생성자 외부 접근 차단
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class) //Auditing 사용 명시
public class ReviewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; //리뷰 식별자

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "charger_id")
    private ChargerEntity chargerId; //충전기 식별자

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userId; //유저 식별자

    @Column(length = 1024)
    private String reviewContent; //리뷰 내용

    @NotNull
    @Column
    private Byte rating; //별점(1~5)

    @CreatedDate
    @Column
    private LocalDateTime createdAt; //데이터 생성 시각

    @LastModifiedDate
    @Column
    private LocalDateTime modifiedAt; //데이터 수정 시각

    @NotNull
    @Column(columnDefinition = "TINYINT(1) UNSIGNED DEFAULT 0")
    private Boolean deletedYn; //리뷰 삭제 여부

    @Column(insertable = false)
    private LocalDateTime deletedAt; //리뷰 삭제 시각

    @PrePersist
    public void checkYnNull() {
        if (this.deletedYn == null) {
            this.deletedYn = false;
        }
    }

    public ReviewDTO toDTO() {
        return ReviewDTO.builder()
                .id(id)
                .chargerId(chargerId.getId())
                .userId(userId.getId())
                .reviewContent(reviewContent)
                .rating(rating)
                .createdAt(createdAt)
                .modifiedAt(modifiedAt)
                .deletedYn(deletedYn)
                .deletedAt(deletedAt)
                .build();
    }
}
