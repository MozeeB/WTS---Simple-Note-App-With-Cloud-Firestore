package com.cikup.noteappfirestore.helper.navigation

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.cikup.noteappfirestore.R.anim.*
import com.cikup.noteappfirestore.ui.AddActivity
import com.cikup.noteappfirestore.ui.DetailActivity
import com.cikup.noteappfirestore.ui.MainActivity

fun navigateToAddActivity(context: Context) {
    if (context != null && context is Activity) {
        val activity = context
        val flags = context.flags(Intent.FLAG_ACTIVITY_NEW_TASK, Intent.FLAG_ACTIVITY_CLEAR_TOP)
        activity.start<AddActivity>(flags, right_in, left_out)
    }
}

fun navigateToDetailActivity(context: Context, bundle: Bundle) {
    if (context != null && context is Activity) {
        val activity = context
        val flags = context.flags(Intent.FLAG_ACTIVITY_NEW_TASK, Intent.FLAG_ACTIVITY_CLEAR_TOP)
        activity.start<DetailActivity>(bundle, flags, right_in, left_out)
    }
}

fun backToMainActivity(context: Context) {
    if (context != null && context is Activity) {
        val activity = context
        val flags = context.flags(Intent.FLAG_ACTIVITY_NEW_TASK, Intent.FLAG_ACTIVITY_CLEAR_TOP)
        activity.start<MainActivity>(flags, left_in, right_out)
    }
}

