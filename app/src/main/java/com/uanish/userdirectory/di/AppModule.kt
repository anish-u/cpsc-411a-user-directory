package com.uanish.userdirectory.di

import android.content.Context
import androidx.room.Room
import com.uanish.userdirectory.data.local.AppDatabase
import com.uanish.userdirectory.data.remote.UserApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AppModule {

    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

    fun provideUserApi(): UserApi {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserApi::class.java)
    }

    fun provideDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "user_directory_db"
        ).build()
    }
}
