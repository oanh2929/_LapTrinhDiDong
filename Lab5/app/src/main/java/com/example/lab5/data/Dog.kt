package com.example.lab5.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.lab5.R

data class Dog(
    @DrawableRes val imageResourceId: Int,
    @StringRes val name: Int,
    val age: Int,
    @StringRes val hobbies: Int
)

val dogs = listOf(
    Dog(R.drawable.koda, R.string.dog_name_koda, 2, R.string.dog_description_koda),
    Dog(R.drawable.lola, R.string.dog_name_lola, 16, R.string.dog_description_lola),
    Dog(R.drawable.frankie, R.string.dog_name_frankie, 2, R.string.dog_description_frankie),
    Dog(R.drawable.nox, R.string.dog_name_nox, 8, R.string.dog_description_nox),
    Dog(R.drawable.faye, R.string.dog_name_faye, 8, R.string.dog_description_faye),
    Dog(R.drawable.bella, R.string.dog_name_bella, 14, R.string.dog_description_bella),
    Dog(R.drawable.moana, R.string.dog_name_moana, 2, R.string.dog_description_moana),
    Dog(R.drawable.tzeitel, R.string.dog_name_tzeitel, 7, R.string.dog_description_tzeitel)
)