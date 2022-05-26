package com.example.myfirstapplication.data

import com.google.gson.annotations.SerializedName

data class Cast (
    @SerializedName("name") val name: String
) {
}