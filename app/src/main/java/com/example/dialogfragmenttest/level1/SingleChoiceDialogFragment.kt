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

class SingleChoiceDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val volume = requireArguments().getInt(ARG_KEY)

        val availableVolumeList = AvailableVolumeValues.getVolumeList(volume)
        val volumeList = availableVolumeList.volumeList.map {
            getString(R.string.volume_description, it)
        }.toTypedArray()

        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.volume_setup)
            .setSingleChoiceItems(volumeList, availableVolumeList.currentIndex) { dialog, which ->
            val choseItem = availableVolumeList.volumeList[which]
                parentFragmentManager.setFragmentResult(REQUEST_KEY, bundleOf(BUNDLE_KEY to choseItem))
                    dismiss()
            }
            .create()
    }

    fun show(fm: FragmentManager, volume: Int) {
        val alertDialog = SingleChoiceDialogFragment()
        alertDialog.arguments = bundleOf(ARG_KEY to  volume)
        alertDialog.show(fm, TAG)
    }

    companion object {
        const val TAG = "TAG2"
        const val REQUEST_KEY = "REQUEST_KEY2"
        const val BUNDLE_KEY = "BUNDLE_KEY2"
        const val ARG_KEY = "ARG_KEY2"

        fun setupListener(
            fm: FragmentManager,
            lifecycleOwner: LifecycleOwner,
            listener: (Int) -> Unit
        ) {
            fm.setFragmentResultListener(REQUEST_KEY, lifecycleOwner) { _, result ->
                listener.invoke(result.getInt(BUNDLE_KEY))
            }
        }

    }
}