package com.rudra.network.api

import com.rudra.network.model.SyncRequest
import com.rudra.network.model.SyncResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface TaskSyncApi {

    @POST("tasks/sync")
    suspend fun syncTasks(
        @Body request: SyncRequest
    ): SyncResponse
}
