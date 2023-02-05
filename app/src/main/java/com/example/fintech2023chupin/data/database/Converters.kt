package com.example.fintech2023chupin.data.database

import androidx.room.TypeConverter
import com.example.fintech2023chupin.data.model.Country
import com.example.fintech2023chupin.data.model.Genre
import com.google.gson.Gson

class Converters {

    @TypeConverter
    fun listGenreToJson(value: List<Genre>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToListGenre(value: String) = Gson().fromJson(value, Array<Genre>::class.java).toList()

    @TypeConverter
    fun listCountyToJson(value: List<Country>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToListCountry(value: String) =
        Gson().fromJson(value, Array<Country>::class.java).toList()
}