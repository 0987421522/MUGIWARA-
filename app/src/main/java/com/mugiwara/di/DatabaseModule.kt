package com.mugiwara.di

import android.content.Context
import androidx.room.Room
import com.mugiwara.data.local.database.MugiwaraDatabase
import com.mugiwara.data.local.database.Converters
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
        converters: Converters // إذا كنت تستخدم حقن التبعيات للـ TypeConverters
    ): MugiwaraDatabase {
        return Room.databaseBuilder(
            context,
            MugiwaraDatabase::class.java,
            "mugiwara_database" // اسم قاعدة البيانات الموحد
        )
            .addTypeConverter(converters)
            .fallbackToDestructiveMigration() // مؤقتاً أثناء التطوير لتجنب كراش الميغريشن
            .build()
    }

    @Provides
    fun provideSymbolDao(db: MugiwaraDatabase) = db.symbolDao()

    @Provides
    fun provideTickDao(db: MugiwaraDatabase) = db.tickDao()

    @Provides
    fun provideCandleDao(db: MugiwaraDatabase) = db.candleDao()

    // ... باقي تزويد الـ DAOs
}
