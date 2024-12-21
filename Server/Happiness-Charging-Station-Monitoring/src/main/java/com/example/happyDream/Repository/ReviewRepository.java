package com.example.happyDream.Repository;

import com.example.happyDream.Entity.ChargerEntity;
import com.example.happyDream.Entity.ReportEntity;
import com.example.happyDream.Entity.ReviewEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Integer>, ReviewRepositoryCustom {
    @Query("SELECT r FROM ReviewEntity r WHERE r.chargerId.id = :chargerId")
    List<ReviewEntity> findByChargerId(@Param("chargerId") Integer chargerId);

    @Query("SELECT r FROM ReviewEntity r WHERE r.rating = 5")
    List<ReviewEntity> findByRatingFive();

    @Query("SELECT r FROM ReviewEntity r WHERE r.rating = 4")
    List<ReviewEntity> findByRatingFour();

    @Query("SELECT r FROM ReviewEntity r WHERE r.rating = 3")
    List<ReviewEntity> findByRatingThree();

    @Query("SELECT r FROM ReviewEntity r WHERE r.rating = 2")
    List<ReviewEntity> findByRatingTwo();

    @Query("SELECT r FROM ReviewEntity r WHERE r.rating = 1")
    List<ReviewEntity> findByRatingOne();

    @Query("SELECT r FROM ReviewEntity r JOIN r.chargerId c WHERE c IN :chargerIdList")
    List<ReviewEntity> findByAddress(@Param("chargerIdList") List<ChargerEntity> chargerIdList);

    @Query("SELECT r FROM ReviewEntity r WHERE r.reviewContent LIKE %:keyword%")
    List<ReviewEntity> findBySubject(@Param("keyword") String keyword);

    @Query("SELECT r FROM ReviewEntity r JOIN r.chargerId c WHERE c IN :chargerIdList AND r.reviewContent LIKE %:keyword%")
    List<ReviewEntity> findByAddressAndSubject(@Param("chargerIdList") List<ChargerEntity> chargerIdList, @Param("keyword") String keyword);
}
