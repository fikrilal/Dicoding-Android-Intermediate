package com.fikrilal.narate_mobile_apps._core.data.model.stories

import com.google.gson.annotations.SerializedName

data class StoryDetailResponse(
    @SerializedName("error")
    val error: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("story")
    val story: Story
)