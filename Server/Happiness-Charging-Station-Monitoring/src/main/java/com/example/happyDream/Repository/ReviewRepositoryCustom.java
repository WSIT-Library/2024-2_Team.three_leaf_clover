package com.example.happyDream.Repository;

import com.example.happyDream.Entity.ReviewEntity;
import java.util.Optional;

public interface ReviewRepositoryCustom {
    Optional<ReviewEntity> findByChargerIdAndUserId(Integer chargerId, Integer userId);
}
