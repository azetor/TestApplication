package com.mobilemonkeysoftware.testapplication.ui

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
