package com.mobilemonkeysoftware.testapplication.ui

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class BaseAdapter<in E, VH : BaseViewHolder<E>>(
    private val items: List<E>
) : RecyclerView.Adapter<VH>() {

    override fun getItemCount(): Int = items.size

    fun inflate(
        @LayoutRes layoutResId: Int,
        parent: ViewGroup
    ): View = LayoutInflater
        .from(parent.context).inflate(layoutResId, parent, false)
}

open class BaseViewHolder<in E>(itemView: View) : RecyclerView.ViewHolder(itemView) {

    open fun bind() {}
}
