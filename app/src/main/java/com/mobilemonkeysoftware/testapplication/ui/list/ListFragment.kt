package com.mobilemonkeysoftware.testapplication.ui.list

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mobilemonkeysoftware.testapplication.R
import com.mobilemonkeysoftware.testapplication.core.Photo
import com.mobilemonkeysoftware.testapplication.core.PicsumPhotosApi
import com.mobilemonkeysoftware.testapplication.tools.RxSchedulers
import com.mobilemonkeysoftware.testapplication.ui.EXTRA_PHOTO
import com.mobilemonkeysoftware.testapplication.ui.STACK_NAME
import com.mobilemonkeysoftware.testapplication.ui.detail.DetailFragment
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.fragment_list.*

internal fun View.hide() {
    this.visibility = View.GONE
}

internal fun View.show() {
    this.visibility = View.VISIBLE
}

class ListFragment : Fragment() {

    private val adapter: Adapter = Adapter(mutableListOf())
    private var clickDisposable: Disposable? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater
        .inflate(R.layout.fragment_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setup()
        load()
    }

    private fun setup() {

        recycler.layoutManager = GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)
        recycler.adapter = adapter
    }

    private fun showDetail(photo: Photo) {

        activity
            ?.supportFragmentManager
            ?.beginTransaction()
            ?.addToBackStack(STACK_NAME)
            ?.add(
                R.id.fragments,
                Fragment.instantiate(
                    activity,
                    DetailFragment::class.java.name,
                    Bundle().apply { putSerializable(EXTRA_PHOTO, photo) })
            )
            ?.commit()
    }

    private fun load() {

        PicsumPhotosApi
            .flattenedList()
            .observeOn(RxSchedulers.mainThread())
            .subscribeBy {
                adapter.add(it)
                hideProgress()
            }
    }

    private fun hideProgress() {

        if (adapter.itemCount > 0) {
            progress.hide()
        }
    }

    override fun onResume() {
        super.onResume()

        clickDisposable = adapter
            .subject
            .subscribeBy { showDetail(it) }
    }

    override fun onPause() {
        super.onPause()

        clickDisposable?.dispose()
        clickDisposable = null
    }
}