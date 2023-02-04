package com.example.fintech2023chupin.data.database

import androidx.room.TypeConverter
import com.example.fintech2023chupin.data.model.Genre
import com.google.gson.Gson

class Converters {

    @TypeConverter
    fun listToJson(value: List<Genre>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<Genre>::class.java).toList()
}