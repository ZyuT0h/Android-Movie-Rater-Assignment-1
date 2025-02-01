package com.it2161.dit99999x.assignment1.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun fromGenreList(genres: List<Genre>): String {
        return Gson().toJson(genres)
    }

    @TypeConverter
    fun toGenreList(json: String): List<Genre> {
        val type = object : TypeToken<List<Genre>>() {}.type
        return Gson().fromJson(json, type)
    }
}