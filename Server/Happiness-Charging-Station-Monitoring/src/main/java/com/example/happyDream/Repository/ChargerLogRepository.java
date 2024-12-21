package com.example.happyDream.Repository;

import com.example.happyDream.Entity.ChargerLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ChargerLogRepository extends JpaRepository<ChargerLogEntity, Long> {
    List<ChargerLogEntity> findAllByOrderByIdDesc();

    @Query("SELECT cl FROM ChargerLogEntity cl WHERE cl.charger.id = :chargerId")
    List<ChargerLogEntity> findAllByChargerId(@Param("chargerId") Integer chargerId);

    @Query("SELECT cl FROM ChargerLogEntity cl WHERE cl.charger.id = :chargerId ORDER BY cl.id DESC")
    List<ChargerLogEntity> findAllByChargerIdOrderByDesc(@Param("chargerId") Integer chargerId);
}
