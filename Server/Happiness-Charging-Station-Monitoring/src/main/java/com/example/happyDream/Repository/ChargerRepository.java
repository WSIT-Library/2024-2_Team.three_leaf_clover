package com.example.happyDream.Repository;

import com.example.happyDream.DTO.ChargerDetailDTO;
import com.example.happyDream.Entity.ChargerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ChargerRepository extends JpaRepository<ChargerEntity, Integer> {
    String CHARGER_DETAIL_JOIN_QUERY = "SELECT new com.example.happyDream.DTO.ChargerDetailDTO(" +
            "c.id as chargerId, c.name as name, c.city1 as city1, c.city2 as city2, c.city2Code as city2Code, " +
            "c.addressNew as addressNew, c.addressOld as addressOld, c.addressDetail as addressDetail, " +
            "c.latitude as latitude, c.longitude as longitude, " +
            "c.weekdayOpen as weekdayOpen, c.saturdayOpen as saturdayOpen, c.holidayOpen as holidayOpen, " +
            "c.weekdayClose as weekdayClose, c.saturdayClose as saturdayClose, c.holidayClose as holidayClose, " +
            "c.chargerCount as chargerCount, c.chargeAirYn as chargeAirYn, c.chargePhoneYn as chargePhoneYn, " +
            "c.callNumber as callNumber, " +
            "c.updatedDate as updatedDate, c.createdAt as chargerCreatedAt, c.modifiedAt as chargerModifiedAt, " +
            "c.deletedYn as deletedYn, c.deletedAt as deletedAt, " +
            "cs.id as chargerStateId, cs.usingYn as usingYn, cs.brokenYn as brokenYn, " +
            "cs.usingAt as usingAt, cs.brokenAt as brokenAt, " +
            "cs.createdAt as chargerStateCreatedAt, cs.modifiedAt as chargerStateModifiedAt) " +
            "FROM ChargerEntity c " +
            "JOIN c.chargerState cs";

    String CHARGER_DETAIL_JOIN_QUERY_WITH_RATING = "SELECT new com.example.happyDream.DTO.ChargerDetailDTO(" +
            "c.id as chargerId, c.name as name, c.city1 as city1, c.city2 as city2, c.city2Code as city2Code, " +
            "c.addressNew as addressNew, c.addressOld as addressOld, c.addressDetail as addressDetail, " +
            "c.latitude as latitude, c.longitude as longitude, " +
            "c.weekdayOpen as weekdayOpen, c.saturdayOpen as saturdayOpen, c.holidayOpen as holidayOpen, " +
            "c.weekdayClose as weekdayClose, c.saturdayClose as saturdayClose, c.holidayClose as holidayClose, " +
            "c.chargerCount as chargerCount, c.chargeAirYn as chargeAirYn, c.chargePhoneYn as chargePhoneYn, " +
            "c.callNumber as callNumber, " +
            "c.updatedDate as updatedDate, c.createdAt as chargerCreatedAt, c.modifiedAt as chargerModifiedAt, " +
            "c.deletedYn as deletedYn, c.deletedAt as deletedAt, " +
            "cs.id as chargerStateId, cs.usingYn as usingYn, cs.brokenYn as brokenYn, " +
            "cs.usingAt as usingAt, cs.brokenAt as brokenAt, " +
            "cs.createdAt as chargerStateCreatedAt, cs.modifiedAt as chargerStateModifiedAt, " +
            "(SELECT AVG(r.rating) FROM ReviewEntity r WHERE r.chargerId.id = c.id) as rating) " + // 별점 추가
            "FROM ChargerEntity c " +
            "JOIN c.chargerState cs";


    @Query("SELECT c " +
            "FROM ChargerEntity c " +
            "WHERE c.addressOld LIKE %:address% OR c.addressNew LIKE %:address% OR c.name LIKE %:address%")
    List<ChargerEntity> findChargersByAddress(@Param("address") String address);

    @Query("SELECT c " +
            "FROM ChargerEntity c " +
            "WHERE (6371*acos(cos(radians(:userLatitude))*cos(radians(c.latitude))*cos(radians(c.longitude) " +
            "-radians(:userLongitude))+sin(radians(:userLatitude))*sin(radians(c.latitude)))) <= 3")
    List<ChargerEntity> findChargersByNear(@Param("userLatitude") Double userLatitude, @Param("userLongitude") Double userLongitude);

    @Query("SELECT c FROM ChargerEntity c JOIN c.chargerState cs WHERE cs.usingYn = :usingYn")
    List<ChargerEntity> findAllChargerByUsingYn(Boolean usingYn);

    @Query("SELECT c FROM ChargerEntity c JOIN c.chargerState cs WHERE cs.brokenYn = :brokenYn")
    List<ChargerEntity> findAllChargerByBrokenYn(Boolean brokenYn);

    // 단순 조회용 ChargerDetailDTO
    @Query(CHARGER_DETAIL_JOIN_QUERY_WITH_RATING)
    List<ChargerDetailDTO> findAllChargerDetail();

    @Query(CHARGER_DETAIL_JOIN_QUERY + " WHERE cs.usingYn = :usingYn")
    List<ChargerDetailDTO> findAllChargerDetailByUsingYn(Boolean usingYn);

    @Query(CHARGER_DETAIL_JOIN_QUERY + " WHERE cs.brokenYn = :brokenYn")
    List<ChargerDetailDTO> findAllChargerDetailByBrokenYn(Boolean brokenYn);

    @Query("SELECT c FROM ChargerEntity c LEFT JOIN ChargerLogEntity cl ON c.id = cl.charger.id " +
            "WHERE c.id NOT IN " +
            "(SELECT cl2.charger.id FROM ChargerLogEntity cl2 " +
            "WHERE cl2.requestAt BETWEEN :fromAt AND :toAt)")
    List<ChargerEntity> findAllChargerWithoutLogInPeriod(LocalDateTime fromAt, LocalDateTime toAt);

    @Query("SELECT DISTINCT c.city1, c.city2 FROM ChargerEntity c")
    List<Object[]> findAddress();

    @Query("SELECT c FROM ChargerEntity c WHERE c.city1 = :city OR c.city1 = :normalizeCity")
    List<ChargerEntity> findByCity(@Param("city") String city, @Param("normalizeCity") String normalizeCity);

    @Query("SELECT c FROM ChargerEntity c WHERE c.city1 = :city OR c.city1 = :normalizeCity AND c.city2 = :district")
    List<ChargerEntity> findByCityAndDistrict(@Param("city") String city, @Param("normalizeCity") String normalizeCity, @Param("district") String district);
}
