package rocks.ivski.bbc.di

import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import rocks.ivski.bbc.data.api.API
import rocks.ivski.bbc.data.api.ApiService
import rocks.ivski.bbc.utils.BASE_URL
import rocks.ivski.bbc.utils.NetworkUtil

val appModule = module {
    single { provideOkHttpClient() }
    single { provideRetrofit(get(), BASE_URL) }
    single { provideApi(get()) }
    single { provideNetworkUtil(androidContext()) }

    single {
        return@single ApiService(get())
    }
}

private fun provideNetworkUtil(context: Context) = NetworkUtil(context)

private fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
    val loggingInterceptor = HttpLoggingInterceptor()
    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
    OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()
} else OkHttpClient
    .Builder()
    .build()

private fun provideRetrofit(
    okHttpClient: OkHttpClient,
    BASE_URL: String
): Retrofit =
    Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

private fun provideApi(retrofit: Retrofit): API =
    retrofit.create(API::class.java)

private fun provideApiService(api: ApiService): ApiService = api