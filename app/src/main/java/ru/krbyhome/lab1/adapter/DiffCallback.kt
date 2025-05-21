package ru.krbyhome.lab1.adapter

import androidx.recyclerview.widget.DiffUtil
import ru.krbyhome.lab1.model.Post

class DiffCallback  : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(old: Post, new: Post) = old.id == new.id
    override fun areContentsTheSame(old: Post, new: Post) = old == new
}