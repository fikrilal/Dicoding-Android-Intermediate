package com.fikrilal.narate_mobile_apps.homepage.viewmodel

import com.fikrilal.narate_mobile_apps._core.data.model.stories.Story

object DataDummy {

    fun generateDummyStoryItems(): List<Story> {
        val items: MutableList<Story> = arrayListOf()
        for (i in 0..100) {
            val story = Story(
                id = i.toString(),
                name = "author $i",
                description = "quote $i",
                photoUrl = "https://example.com/photo$i.jpg",
                createdAt = "2023-10-01T00:00:00Z",
                lat = 0.0,
                lon = 0.0
            )
            items.add(story)
        }
        return items
    }
}