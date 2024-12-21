package com.example.happyDream.Entity;

import com.example.happyDream.DTO.UserDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "user") //MYSQL 외 타 DB는 문제 발생할 수 있음(users, member 등 대응)
@Schema(description = "유저 Entity")
@Getter //Setter 미사용
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED) //생성자 외부 접근 차단
@EntityListeners(AuditingEntityListener.class) //Auditing 사용 명시
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "유저 식별자")
    private Integer id;

    @NotNull
    @Column(length = 32)
    @Schema(description = "아이디")
    private String username;

    @NotNull
    @Column(length = 255)
    @Schema(description = "비밀번호")
    private String password;

    //TODO - 추후 Enum 전환하고, 컨버터 추가
    @NotNull
    @Column(columnDefinition = "TINYINT UNSIGNED")
    @Schema(description = "유저 타입")
    private Byte userType;

    @CreatedDate
    @Column(updatable = false)
    @Schema(description = "데이터 생성 시각")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column
    @Schema(description = "데이터 수정 시각")
    private LocalDateTime modifiedAt;

    @NotNull
    @Column(columnDefinition = "TINYINT(1) UNSIGNED DEFAULT 0")
    @Schema(description = "삭제 여부")
    private Boolean deletedYn;

    @Column(insertable = false)
    @Schema(description = "데이터 삭제 시각")
    private LocalDateTime deletedAt;

    @Builder
    public UserEntity(Integer id, String username, String password, Byte userType, LocalDateTime createdAt, LocalDateTime modifiedAt, Boolean deletedYn, LocalDateTime deletedAt) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.userType = userType;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.deletedYn = deletedYn;
        this.deletedAt = deletedAt;
    }

    public UserDTO toDTO() {
        return UserDTO.builder()
                .id(id)
                .username(username)
                .password(password)
                .userType(userType)
                .createdAt(createdAt)
                .modifiedAt(modifiedAt)
                .deletedYn(deletedYn)
                .deletedAt(deletedAt)
                .build();
    }
}
