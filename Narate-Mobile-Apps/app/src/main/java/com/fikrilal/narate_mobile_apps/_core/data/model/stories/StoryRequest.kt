package com.fikrilal.narate_mobile_apps._core.data.model.stories

import com.google.gson.annotations.SerializedName

data class StoryRequest(
    @SerializedName("description")
    val description: String,
    @SerializedName("photo")
    val photo: String,
    @SerializedName("lat")
    val lat: Double? = null,
    @SerializedName("lon")
    val lon: Double? = null
)