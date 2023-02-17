package com.tzuhsien.noodoeassignment.di

import com.tzuhsien.noodoeassignment.BuildConfig
import com.tzuhsien.noodoeassignment.Constants
import com.tzuhsien.noodoeassignment.data.source.DataSource
import com.tzuhsien.noodoeassignment.data.source.DefaultRepository
import com.tzuhsien.noodoeassignment.data.source.Repository
import com.tzuhsien.noodoeassignment.data.source.remote.RemoteDataSource
import com.tzuhsien.noodoeassignment.network.NoodoeApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) // The dependency will live as long as the app
object AppModule {

    @Provides
    @Singleton
    fun provideInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().setLevel(
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
        )

    @Singleton
    @Provides
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor { chain ->
            val url = chain
                .request()
                .url
                .newBuilder()
                .build()
            chain.proceed(chain.request().newBuilder().url(url).build())
        }
        .build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(Constants.NOODOE_BASE_URL)
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideNoodoeApiService(retrofit: Retrofit): NoodoeApiService = retrofit.create(NoodoeApiService::class.java)

//    val loggingInterceptor =
//        HttpLoggingInterceptor().setLevel(
//            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
//            else HttpLoggingInterceptor.Level.NONE
//        )
//
//    val client = OkHttpClient.Builder()
//        .addInterceptor(loggingInterceptor)
//        .build()
//
//    @Provides
//    @Singleton // make sure only one instance of Api provided throughout the whole app life
//    fun provideNoodoeApi(): NoodoeApiService {
//        return Retrofit.Builder()
//            .addConverterFactory(GsonConverterFactory.create())
//            .baseUrl(NOODOE_BASE_URL)
//            .client(client)
//            .build()
//            .create(NoodoeApiService::class.java)
//    }

    @Provides
    @Singleton
    fun provideRemoteDataSource(
        noodoeApiService: NoodoeApiService
    ): DataSource = RemoteDataSource(noodoeApiService)

    @Provides
    @Singleton // make sure only one instance provided throughout the whole app life
    fun provideRepository(
        remoteDataSource: DataSource
    ): Repository = DefaultRepository(remoteDataSource)

}