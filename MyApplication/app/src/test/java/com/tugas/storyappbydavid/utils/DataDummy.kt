package com.tugas.storyappbydavid.utils

import com.tugas.storyappbydavid.apiresponse.ListStoryItem

object DataDummy {
    fun generateDummyStoryEntity(): List<ListStoryItem> {
        val storyList : MutableList<ListStoryItem> = arrayListOf()
        for (i in 0..100) {
            val story = ListStoryItem(
                "https://story-api.dicoding.dev/images/stories/photos-1641623658595_dummy-pic.png",
                "2022-01-08T06:34:18.598Z",
                "Dimas",
                "Lorem Ipsum",
                "-10.212",
                "story-FvU4u0Vp2S3PMsFg",
                "-16.002",
            )
            storyList.add(story)
        }
        return storyList
    }
}