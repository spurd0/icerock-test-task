package com.icerockdev.babenko.applicaiton.dagger.modules

import com.google.gson.FieldNamingPolicy
import com.google.gson.FieldNamingStrategy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Field
import javax.inject.Singleton

/**
 * Created by Roman Babenko on 04/06/17.
 */
@Module
class RetrofitModule {

    @Provides
    @Singleton
    internal fun provideRetrofit(builder: Retrofit.Builder): Retrofit {
        return builder.baseUrl("https://api.github.com").build()
    }

    @Provides
    @Singleton
    internal fun provideRetrofitBuilder(factory: Converter.Factory): Retrofit.Builder {
        return Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(factory)

    }

    @Provides
    @Singleton
    fun provideConverterFactory(gson: Gson): Converter.Factory {
        return GsonConverterFactory.create(gson)
    }

    @Provides
    @Singleton
    internal fun provideGson(): Gson {
        return GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
            .setFieldNamingStrategy(CustomFieldNamingPolicy())
            .setPrettyPrinting()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            .serializeNulls()
            .create()
    }

    private class CustomFieldNamingPolicy : FieldNamingStrategy {
        override fun translateName(field: Field): String {
            var name = FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES.translateName(field)
            if (name == "value")
                return "default_value"
            if (name == "album_id")
                return "albumId"
            return if (name == "thumbnail_url") "thumbnailUrl" else name
        }
    }
}
