package com.fikrilal.narate_mobile_apps.homepage.utils

import androidx.recyclerview.widget.DiffUtil
import com.fikrilal.narate_mobile_apps._core.data.model.stories.Story

class StoryDiffCallback : DiffUtil.ItemCallback<Story>() {
    override fun areItemsTheSame(oldItem: Story, newItem: Story): Boolean {
        // Compare unique identifier of each item, assuming Story has an 'id' property
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Story, newItem: Story): Boolean {
        // Compare all properties that affect UI to determine if the item's representation is the same
        return oldItem == newItem
    }
}
