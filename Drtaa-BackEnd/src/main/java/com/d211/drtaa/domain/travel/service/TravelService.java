package com.d211.drtaa.domain.travel.service;

import com.d211.drtaa.domain.travel.dto.response.TravelDetailResponseDTO;

import java.util.List;

public interface TravelService {
    // travelId의 해당하는 여행 일정, 장소 상세 조회
    TravelDetailResponseDTO getTravel(Long travelId);
}
