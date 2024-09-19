package com.drtaa.core_data.repository

import com.drtaa.core_model.map.ResponseGPS
import kotlinx.coroutines.flow.Flow

interface GPSRepository {
    fun observeMqttMessages(): Flow<ResponseGPS>
    suspend fun setupMqttConnection()
    fun publishGpsData(data: String)
    fun disconnectMqtt()
}