package com.example.happyDream.Repository;

import com.example.happyDream.Entity.ReviewEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.Optional;

public class ReviewRepositoryImpl implements ReviewRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<ReviewEntity> findByChargerIdAndUserId(Integer chargerId, Integer userId) {
        String jpql = "SELECT r FROM ReviewEntity r WHERE r.chargerId.id = :chargerId AND r.userId.id = :userId";

        TypedQuery<ReviewEntity> query = entityManager.createQuery(jpql, ReviewEntity.class);
        query.setParameter("chargerId", chargerId);
        query.setParameter("userId", userId);

        return query.getResultList().stream().findFirst();
    }
}
