package com.mugiwara.data.local.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Type converters for Room Database
 * Handles conversion of complex types to/from primitives
 */
class Converters {
    
    private val gson = Gson()
    
    @TypeConverter
    fun fromStringList(value: String?): List<String>? {
        if (value == null) return null
        val listType = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, listType)
    }
    
    @TypeConverter
    fun toStringList(list: List<String>?): String? {
        return if (list == null) null else gson.toJson(list)
    }
    
    @TypeConverter
    fun fromDoubleMap(value: String?): Map<String, Double>? {
        if (value == null) return null
        val mapType = object : TypeToken<Map<String, Double>>() {}.type
        return gson.fromJson(value, mapType)
    }
    
    @TypeConverter
    fun toDoubleMap(map: Map<String, Double>?): String? {
        return if (map == null) null else gson.toJson(map)
    }
    
    @TypeConverter
    fun fromStringMap(value: String?): Map<String, String>? {
        if (value == null) return null
        val mapType = object : TypeToken<Map<String, String>>() {}.type
        return gson.fromJson(value, mapType)
    }
    
    @TypeConverter
    fun toStringMap(map: Map<String, String>?): String? {
        return if (map == null) null else gson.toJson(map)
    }
    
    @TypeConverter
    fun fromLong(value: Long?): Long? = value
    
    @TypeConverter
    fun toLong(value: Long?): Long? = value
}
