package com.mugiwara.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mugiwara.data.local.dao.CandleDao
import com.mugiwara.data.local.dao.MarketDataDao
import com.mugiwara.data.local.dao.SymbolDao
// قم بإضافة باقي الـ DAOs المفردة هنا إذا كانت موجودة فعلاً في مشروعك
// import com.mugiwara.data.local.dao.OrderDao

import com.mugiwara.data.local.entity.CandleEntity
import com.mugiwara.data.local.entity.MarketDataEntity
import com.mugiwara.data.local.entity.SymbolEntity
import com.mugiwara.data.local.entity.TickEntity
// قم بإضافة باقي الـ Entities هنا إذا كانت موجودة فعلاً في مشروعك
// import com.mugiwara.data.local.entity.OrderEntity

@Database(
    entities = [
        // فقط الـ Entities التي تأكدنا من وجودها وتصحيحها
        SymbolEntity::class,
        TickEntity::class,
        CandleEntity::class,
        MarketDataEntity::class
        
        // إذا كانت هناك Entities أخرى موجودة فعلاً ومشفرة بشكل صحيح أضفها هنا
        // مثل: UserAccountEntity::class, OrderEntity::class ... إلخ
        // ولكن إذا لم تكن متأكاً من وجود الملف، لا تضعه هنا لكي لا يفشل البناء
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class MugiwaraDatabase : RoomDatabase() {

    // فقط الـ DAOs التي تأكدنا من وجودها وتصحيحها
    abstract fun symbolDao(): SymbolDao
    abstract fun candleDao(): CandleDao
    abstract fun marketDataDao(): MarketDataDao

    // أضف باقي الـ DAOs هنا فقط إذا كانت ملفاتها موجودة كـ Single Dao
    // abstract fun orderDao(): OrderDao
}
