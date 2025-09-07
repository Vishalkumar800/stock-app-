package com.rach.stockapp

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.rach.stockapp.data.network.ApiResponse
import com.rach.stockapp.data.repositoryImp.CompanyInfoRepositoryImp
import com.rach.stockapp.data.repositoryImp.GainerImp
import com.rach.stockapp.data.repositoryImp.SearchRepositoryImp
import com.rach.stockapp.data.repositoryImp.WatchListStockRepoImp
import com.rach.stockapp.data.roomdb.AppDatabase
import com.rach.stockapp.data.roomdb.SavedStockDao
import com.rach.stockapp.data.roomdb.WatchList
import com.rach.stockapp.data.roomdb.WatchListDao
import com.rach.stockapp.domain.repository.Gainers
import com.rach.stockapp.domain.repository.SearchRepository
import com.rach.stockapp.domain.repository.StockDetailsRepository
import com.rach.stockapp.domain.repository.WatchListStockRepository
import com.rach.stockapp.utils.K
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideBaseUrl(): String {
        return K.BASE_URL
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder().setLenient().create()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY // Log full request and response
            })
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        baseUrl: String,
        okHttpClient: OkHttpClient,
        gson: Gson
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    fun provideApiResponse(retrofit: Retrofit): ApiResponse {
        return retrofit.create(ApiResponse::class.java)
    }

    @Provides
    @Singleton
    fun provideGainerRepository(apiResponse: ApiResponse): Gainers {
        return GainerImp(apiResponse = apiResponse)
    }

    @Provides
    @Singleton
    fun provideCompanyOverviewRespository(apiResponse: ApiResponse): StockDetailsRepository {
        return CompanyInfoRepositoryImp(apiResponse = apiResponse)
    }

    ///Rooom

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context = context,
            AppDatabase::class.java,
            "stock.db",
        ).addCallback(object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                CoroutineScope(Dispatchers.IO).launch {
                    val dao = Room.databaseBuilder(
                        context,
                        AppDatabase::class.java,
                        "app_db"
                    ).build().watchListDao()

                    dao.addWatchList(WatchList(title = "WatchList 1"))
                    dao.addWatchList(WatchList(title = "WatchList 2"))

                }
            }
        }).build()
    }

    @Provides
    @Singleton
    fun provideWatchListDao(appDatabase: AppDatabase): WatchListDao = appDatabase.watchListDao()

    @Provides
    @Singleton
    fun provideSavedStockDao(appDatabase: AppDatabase): SavedStockDao = appDatabase.stockInfoDao()

    @Provides
    @Singleton
    fun provideWatchListRepository(
        watchListDao: WatchListDao,
        savedStockDao: SavedStockDao
    ): WatchListStockRepository{
        return WatchListStockRepoImp(
            watchListDao = watchListDao,
            savedStockDao = savedStockDao
        )
    }

    @Provides
    @Singleton
    fun provideSearchRepo(apiResponse: ApiResponse): SearchRepository{
        return SearchRepositoryImp(apiResponse)
    }
}