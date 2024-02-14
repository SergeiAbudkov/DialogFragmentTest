package com.example.dialogfragmenttest.level1

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.example.dialogfragmenttest.R
import com.example.dialogfragmenttest.showToast

class SimpleDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val listener = DialogInterface.OnClickListener { dialog, which ->
            parentFragmentManager.setFragmentResult(REQUEST_KEY, bundleOf(RESULT_KEY to which))
        }
        return AlertDialog.Builder(requireContext())
            .setIcon(R.mipmap.ic_launcher_round)
            .setTitle(R.string.default_alert_title)
            .setMessage(R.string.default_alert_message)
            .setPositiveButton(R.string.action_yes, listener)
            .setNegativeButton(R.string.action_no, listener)
            .setNeutralButton(R.string.action_ignore, listener)

            .create()
    }

    override fun onCancel(dialog: DialogInterface) {
        showToast(R.string.dialog_cancelled)
        super.onCancel(dialog)
    }

    companion object {
        const val TAG = "SimpleDialogFragment"
        const val REQUEST_KEY = "REQUEST_KEY1"
        const val RESULT_KEY = "RESULT_KEY1"
    }
}