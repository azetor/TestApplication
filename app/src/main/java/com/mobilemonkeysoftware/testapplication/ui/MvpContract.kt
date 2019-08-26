package com.mobilemonkeysoftware.testapplication.ui

const val EXTRA_PHOTO = "extra_photo"

interface MvpContract {
    interface Model

    interface Presenter<V : View> {
        var view: V?

        fun initView()

        fun setupView(view: V) {
            this.view = view
        }
    }

    interface View
}
