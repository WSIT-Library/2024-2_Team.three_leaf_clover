package com.example.happyDream.Repository;

import com.example.happyDream.Entity.ChargerEntity;
import com.example.happyDream.Entity.ChargerStateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChargerStateRepository extends JpaRepository<ChargerStateEntity, Integer> {
    public Optional<ChargerStateEntity> findByCharger(ChargerEntity chargerEntity);
}
