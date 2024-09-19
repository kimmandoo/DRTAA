package com.d211.drtaa.domain.rent.repository.car;

import com.d211.drtaa.domain.rent.entity.car.RentCar;
import com.d211.drtaa.domain.rent.entity.car.RentCarSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface RentCarScheduleRepository extends JpaRepository<RentCarSchedule, Long> {
    List<RentCarSchedule> findByRentCar(RentCar car);
}
