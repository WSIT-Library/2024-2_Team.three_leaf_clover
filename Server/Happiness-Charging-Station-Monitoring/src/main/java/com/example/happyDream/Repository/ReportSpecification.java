package com.example.happyDream.Repository;

import com.example.happyDream.Entity.ChargerEntity;
import com.example.happyDream.Entity.ReportEntity;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.List;

public class ReportSpecification {

    public static Specification<ReportEntity> withChargerIds(List<ChargerEntity> chargerIdList) {
        return (root, query, criteriaBuilder) -> {
            if (chargerIdList == null || chargerIdList.isEmpty()) {
                return criteriaBuilder.conjunction(); // 조건 없음
            }
            return root.join("chargerId").in(chargerIdList);
        };
    }

    public static Specification<ReportEntity> withChargerId(Integer chargerId) {
        return (root, query, criteriaBuilder) -> {
            if (chargerId == null) {
                return criteriaBuilder.conjunction(); // 조건 없음
            }
            return criteriaBuilder.equal(root.get("chargerId").get("id"), chargerId);
        };
    }

    public static Specification<ReportEntity> withDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return (root, query, criteriaBuilder) -> {
            if (startDate == null || endDate == null) {
                return criteriaBuilder.conjunction(); // 조건 없음
            }
            return criteriaBuilder.between(root.get("createdAt"), startDate, endDate);
        };
    }

    public static Specification<ReportEntity> combineConditions(List<ChargerEntity> chargerIdList, Integer chargerId,
                                                                LocalDateTime startDate, LocalDateTime endDate) {
        Specification<ReportEntity> spec = Specification.where(null);

        if (chargerIdList != null && !chargerIdList.isEmpty()) {
            spec = spec.and(withChargerIds(chargerIdList));
        }

        if (chargerId != null) {
            spec = spec.and(withChargerId(chargerId));
        }

        if (startDate != null && endDate != null) {
            spec = spec.and(withDateRange(startDate, endDate));
        }

        return spec;
    }
}
