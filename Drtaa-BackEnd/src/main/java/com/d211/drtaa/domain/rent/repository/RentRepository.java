package com.d211.drtaa.domain.rent.repository;

import com.d211.drtaa.domain.rent.entity.Rent;
import com.d211.drtaa.domain.rent.entity.RentStatus;
import com.d211.drtaa.domain.travel.entity.Travel;
import com.d211.drtaa.domain.user.entity.User;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RentRepository extends JpaRepository<Rent, Long> {
    // find
    Optional<Rent> findByRentId(Long rentId);
    Optional<Rent> findByTravel(Travel travel);
    @Query("SELECT r FROM Rent r " +
            "JOIN FETCH r.rentCar rc " +
            "WHERE r.rentStatus = 'in_progress' " +
            "AND r.user.userProviderId = :userProviderId " +
            "AND r.rentStartTime <= CURRENT_TIMESTAMP " +
            "AND r.rentEndTime > CURRENT_TIMESTAMP")
    Optional<Rent> findCurrentRentByUserProviderId(String userProviderId);
    Optional<Rent> findFirstByRentCar_RentCarIdAndRentStatusAndRentStartTimeLessThanEqualAndRentEndTimeGreaterThanEqual(
            Long rentCarId,
            RentStatus rentStatus,
            LocalDateTime rentStartTime,
            LocalDateTime rentEndTime
    );
    Optional<Rent> findByUserAndRentStatusIn(User user, List<RentStatus> rentStatuses);
    Optional<Rent> findByUserAndRentStartTimeBetween(User user, LocalDateTime startDate, LocalDateTime endDate);
    @Query("SELECT r FROM Rent r WHERE r.user = :user " +
            "AND r.rentStatus IN (:statuses) " +
            "AND :today BETWEEN r.rentStartTime AND r.rentEndTime")
    Optional<Rent> findRentByUserAndStatusAndToday(@Param("user") User user,
                                                   @Param("today") LocalDateTime today,
                                                   @Param("statuses") List<RentStatus> statuses);
    Optional<Rent> findByUserAndRentStartTimeLessThanEqualAndRentEndTimeGreaterThanEqual(User user, LocalDateTime now, LocalDateTime now1);
    @Query("SELECT r FROM Rent r WHERE r.user = :user AND r.rentStatus IN :statuses AND " +
            "(r.rentStartTime BETWEEN :startOfDay AND :endOfDay OR r.rentEndTime BETWEEN :startOfDay AND :endOfDay)")
    Optional<Rent> findRentByUserAndStatusAndDateRange(@Param("user") User user,
                                                       @Param("startOfDay") LocalDateTime startOfDay,
                                                       @Param("endOfDay") LocalDateTime endOfDay,
                                                       @Param("statuses") List<RentStatus> statuses);



    List<Rent> findByUser(User user);
    List<Rent> findByUserAndRentStatusInOrderByRentStatusDesc(User user, List<RentStatus> rentStatuses);
    List<Rent> findByRentStartTimeBetween(LocalDateTime startDate, LocalDateTime endDate);
    @Query("SELECT r FROM Rent r " +
            "WHERE r.user = :user AND r.rentStatus = 'completed'")
    List<Rent> findByUserAndRentStatusCompleted(User user);
    List<Rent> findByRentEndTimeBetween(LocalDateTime startDate, LocalDateTime endDate);
    List<Rent> findByUserAndRentStatus(User user, RentStatus rentStatus);
    List<Rent> findAllByRentEndTimeBeforeAndRentStatusIn(LocalDateTime now, List<RentStatus> list);

    // exists
    boolean existsByUserAndRentStatusAndRentStartTimeBetweenAndRentEndTimeBetween(
            User user,
            RentStatus rentStatus,
            LocalDateTime rentStartTimeStart,
            LocalDateTime rentStartTimeEnd,
            LocalDateTime rentEndTimeStart,
            LocalDateTime rentEndTimeEnd
    );

}
