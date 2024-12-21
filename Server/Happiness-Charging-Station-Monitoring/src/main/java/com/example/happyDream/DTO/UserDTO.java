package com.example.happyDream.DTO;

import com.example.happyDream.Entity.UserEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@Schema(description = "유저 DTO")
public class UserDTO {
    //사용자 데이터
    @Schema(description = "유저 식별자")
    private Integer id;

    @Schema(description = "아이디")
    private String username;

    @Schema(description = "비밀번호")
    private String password;

    @Schema(description = "유저 타입")
    private Byte userType;

    //관리 목적 데이터
    @Schema(description = "데이터 생성 시각")
    private LocalDateTime createdAt;

    @Schema(description = "데이터 수정 시각")
    private LocalDateTime modifiedAt;

    @Schema(description = "삭제 여부")
    private Boolean deletedYn;

    @Schema(description = "데이터 삭제 시각")
    private LocalDateTime deletedAt;

    @Builder
    public UserDTO(Integer id, String username, String password, Byte userType, LocalDateTime createdAt, LocalDateTime modifiedAt, Boolean deletedYn, LocalDateTime deletedAt) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.userType = userType;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.deletedYn = deletedYn;
        this.deletedAt = deletedAt;
    }
    
    public UserEntity toEntity() {
        return UserEntity.builder()
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
