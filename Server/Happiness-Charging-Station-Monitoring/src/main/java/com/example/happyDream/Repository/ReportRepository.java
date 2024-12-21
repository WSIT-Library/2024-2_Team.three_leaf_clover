package com.example.happyDream.Repository;

import com.example.happyDream.Entity.ChargerEntity;
import com.example.happyDream.Entity.ReportEntity;

import com.example.happyDream.Entity.ReviewEntity;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReportRepository extends JpaRepository<ReportEntity, Integer> {
    List<ReportEntity> findAll(Specification<ReportEntity> spec);

    @Query("SELECT r FROM ReportEntity r ORDER BY r.checkedReport ASC, r.checkedRepair ASC")
    List<ReportEntity> findAllOrdered();

    @Query("SELECT r FROM ReportEntity r WHERE r.checkedReport = false AND r.checkedRepair = false")
    List<ReportEntity> findByNotCheckedReport();

    @Query("SELECT r FROM ReportEntity r WHERE r.checkedReport = true AND r.checkedRepair = false")
    List<ReportEntity> findByCheckedReport();

    @Query("SELECT r FROM ReportEntity r WHERE r.checkedReport = true AND r.checkedRepair = true")
    List<ReportEntity> findByCheckedRepair();
}
