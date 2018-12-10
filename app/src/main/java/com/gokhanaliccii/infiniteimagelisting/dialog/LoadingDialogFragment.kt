package com.gokhanaliccii.infiniteimagelisting.dialog

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gokhanaliccii.infiniteimagelisting.R

class LoadingDialogFragment : DialogFragment() {

    companion object {
        val TAG: String = "LoadingDialogFragment"
        fun newInstance(): LoadingDialogFragment = LoadingDialogFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_loading, container, false)
    }

}