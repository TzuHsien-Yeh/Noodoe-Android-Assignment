package com.tzuhsien.noodoeassignment.di

import com.tzuhsien.noodoeassignment.BuildConfig
import com.tzuhsien.noodoeassignment.Constants
import com.tzuhsien.noodoeassignment.data.source.DataSource
import com.tzuhsien.noodoeassignment.data.source.DefaultRepository
import com.tzuhsien.noodoeassignment.data.source.Repository
import com.tzuhsien.noodoeassignment.data.source.remote.RemoteDataSource
import com.tzuhsien.noodoeassignment.network.NoodoeApiService
import com.tzuhsien.noodoeassignment.network.ParkingApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Named
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
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder()
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
    @Named("noodoeApiService")
    fun provideRetrofit1(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(Constants.NOODOE_BASE_URL)
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideNoodoeApiService(@Named("noodoeApiService") retrofit: Retrofit): NoodoeApiService =
        retrofit.create(NoodoeApiService::class.java)

    @Singleton
    @Provides
    @Named("parkingApiService")
    fun provideRetrofit2(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(Constants.PARKING_BASE_URL)
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideParkingApiService(@Named("parkingApiService") retrofit: Retrofit): ParkingApiService =
        retrofit.create(ParkingApiService::class.java)

    @Provides
    @Singleton
    fun provideRemoteDataSource(
        noodoeApiService: NoodoeApiService,
        parkingApiService: ParkingApiService
    ): DataSource = RemoteDataSource(noodoeApiService, parkingApiService)

    @Provides
    @Singleton // make sure only one instance provided throughout the whole app life
    fun provideRepository(
        remoteDataSource: DataSource,
    ): Repository = DefaultRepository(remoteDataSource)

}