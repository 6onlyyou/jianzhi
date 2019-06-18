package com.heixiu.errand.net

import android.content.Context
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import okhttp3.Cache
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

/**
 * Created by YuanGang on 2018/3/19.
 */

object OkHttpFactory {
    private val DEFAULT_TIME_OUT: Long = 40
    private lateinit var okHttpClient: OkHttpClient
    private val cookie:SetCookieCache = SetCookieCache()

    fun OkHttpClientManager() {
    }

    fun init(context: Context) {
        val cacheSize = 5242880
        val cache = Cache(context.cacheDir, cacheSize.toLong())
        val cookieJar = PersistentCookieJar(getCookie(), SharedPrefsCookiePersistor(context))

        val okHttpClientBuilder = OkHttpClient.Builder()
                .cache(cache)
                .cookieJar(cookieJar)
                .addInterceptor(RequestInterceptor(context))
                .retryOnConnectionFailure(true)
                .connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
                .hostnameVerifier { p0, p1 -> true }
                .writeTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
        okHttpClient = okHttpClientBuilder.build()
    }

    fun getOkHttpClient(): OkHttpClient {
        return okHttpClient
    }

    fun getCookie():SetCookieCache{
        return cookie
    }
    fun getLongTimeOutOkHttpClientClone(): OkHttpClient {
        return getOkHttpClient().newBuilder().build()
    }




}
