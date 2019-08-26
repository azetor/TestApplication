package com.mobilemonkeysoftware.testapplication.ui.list


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding2.view.clicks
import com.mobilemonkeysoftware.testapplication.R
import com.mobilemonkeysoftware.testapplication.core.Photo
import com.mobilemonkeysoftware.testapplication.tools.ImageLoader
import com.mobilemonkeysoftware.testapplication.ui.BaseAdapter
import com.mobilemonkeysoftware.testapplication.ui.BaseViewHolder
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import kotlinx.android.synthetic.main.list_item.view.*

class Adapter(
    private val items: MutableList<Photo>
) : BaseAdapter<Photo, BaseViewHolder<Photo>>(items) {

    val subject: Subject<Photo> = PublishSubject.create()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<Photo> = ViewHolder(
        LayoutInflater
            .from(parent.context).inflate(R.layout.list_item, parent, false)
    )

    override fun onBindViewHolder(holder: BaseViewHolder<Photo>, position: Int) {
        if (holder is ViewHolder) {
            holder.bind(items[position], subject)
        }
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(itemView: View) : BaseViewHolder<Photo>(itemView) {

        fun bind(item: Photo, subject: Subject<Photo>) {

            itemView
                .clicks()
                .throttleFirst(1, java.util.concurrent.TimeUnit.SECONDS)
                .map { item }
                .subscribe(subject)

            ImageLoader
                .load(itemView.context, item.downloadUrl, itemView.photo)
        }
    }

    fun add(photo: Photo) {

        items.add(photo)
        notifyItemInserted(itemCount)
    }
}
