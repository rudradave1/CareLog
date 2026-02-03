package com.rudra.network

import android.content.Context
import com.rudra.network.api.TaskSyncApi
import com.rudra.network.fake.FakeTaskSyncApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object NetworkProvider {

    fun provideTaskSyncApi(
        context: Context
    ): TaskSyncApi {
        // Use a local fake server in debug so we can test full sync
        // semantics without weakening offline-first behavior.
        return if (BuildConfig.DEBUG) {
            FakeTaskSyncApi(context)
        } else {
            createRetrofitApi()
        }
    }

    private fun createRetrofitApi(): TaskSyncApi {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val logging = HttpLoggingInterceptor().apply {
            // Avoid logging in release builds to protect privacy.
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BASIC
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }

        val client = OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.CARELOG_SYNC_BASE_URL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        return retrofit.create(TaskSyncApi::class.java)
    }
}
