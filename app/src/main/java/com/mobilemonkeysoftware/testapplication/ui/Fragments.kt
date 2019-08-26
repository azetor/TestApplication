package com.mobilemonkeysoftware.testapplication.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import com.mobilemonkeysoftware.testapplication.R
import com.mobilemonkeysoftware.testapplication.ui.detail.DetailFragment
import com.mobilemonkeysoftware.testapplication.ui.list.ListFragment

const val STACK_NAME = "FRAGMENTS"

enum class NavigationDirection(val id: String) {
    LIST(ListFragment::class.java.name),
    DETAIL(DetailFragment::class.java.name)
}

fun navigateTo(
    activity: FragmentActivity?,
    direction: NavigationDirection,
    bundle: Bundle = Bundle.EMPTY
) {

    activity
        ?.supportFragmentManager
        ?.beginTransaction()
        ?.addToBackStack(STACK_NAME)
        ?.replace(
            R.id.fragments,
            Fragment.instantiate(
                activity,
                direction.id,
                bundle
            )
        )
        ?.commit()
}

fun FragmentActivity.manageStack() {

    if (supportFragmentManager.backStackEntryCount == 1) {
        finish()
    } else {
        supportFragmentManager.popBackStackImmediate()
    }
}