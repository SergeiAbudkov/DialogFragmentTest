package com.example.dialogfragmenttest.level1

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentResultListener
import androidx.lifecycle.LifecycleOwner
import com.example.dialogfragmenttest.R
import com.example.dialogfragmenttest.entities.AvailableVolumeValues

class SingleChoiceConfirmationFragment: DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var volume = requireArguments().getInt(ARG_KEY)

        val availableVolumeList = AvailableVolumeValues.getVolumeList(volume)
        val listVolume = availableVolumeList.volumeList.map {
            getString(R.string.volume_description, it)
        }.toTypedArray()

        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.volume_setup)
            .setSingleChoiceItems(listVolume, availableVolumeList.currentIndex) { dialog, which ->
                val choseVolume = availableVolumeList.volumeList[which]
                volume = choseVolume
            }
            .setPositiveButton(R.string.action_confirm) { dialog, which ->
                parentFragmentManager.setFragmentResult(REQUEST_KEY, bundleOf(BUNDLE_KEY to volume))
                dismiss()
            }

            .create()
    }

    companion object {

        const val TAG = "TAG3"
        const val REQUEST_KEY = "REQUEST_KEY3"
        const val BUNDLE_KEY = "BUNDLE_KEY3"
        const val ARG_KEY = "ARG_KEY3"

        fun showAlertDialog(fm: FragmentManager, volume: Int) {
            val alertDialog = SingleChoiceConfirmationFragment()
            alertDialog.arguments = bundleOf(ARG_KEY to volume)
            alertDialog.show(fm, TAG)
        }

        fun setupDialogListener(fm: FragmentManager, lifecycleOwner: LifecycleOwner, listener: (Int) -> Unit) {
            fm.setFragmentResultListener(REQUEST_KEY, lifecycleOwner) { _, result ->
                listener.invoke(result.getInt(BUNDLE_KEY))
                 }
        }
    }
}