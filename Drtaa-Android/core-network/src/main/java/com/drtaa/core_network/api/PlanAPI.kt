package com.drtaa.core_network.api

import com.drtaa.core_model.plan.LastPlan
import com.drtaa.core_model.plan.Plan
import com.drtaa.core_model.plan.PlanItem
import com.drtaa.core_model.plan.PlanSimple
import com.drtaa.core_model.plan.RequestPlanName
import com.drtaa.core_model.plan.ResponsePutPlan
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PlanAPI {
    @PUT("travel")
    suspend fun putPlan(
        @Body plan: Plan
    ): ResponsePutPlan

    @GET("travel")
    suspend fun getPlanList(): List<PlanSimple>

    @GET("travel/{travelId}")
    suspend fun getPlanDetail(
        @Path("travelId") travelId: Int
    ): Plan

    @PATCH("travel/name")
    suspend fun updatePlanName(
        @Body planName: RequestPlanName
    ): String

    @GET("travel/today")
    suspend fun getTodayPlanList(): List<PlanItem>

    @POST("travel")
    suspend fun addPlanAtLast(
        @Body plan: LastPlan
    ): String
}