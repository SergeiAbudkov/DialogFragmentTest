package com.example.dialogfragmenttest.level1

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.util.SparseBooleanArray
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentResultListener
import androidx.lifecycle.LifecycleOwner
import com.example.dialogfragmenttest.R
import com.example.dialogfragmenttest.entities.AvailableVolumeValues

class MultipleChoiceConfirmationDialogFragment : DialogFragment() {

    private val color: Int
        get() = requireArguments().getInt(ARG_KEY)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val colorArray = resources.getStringArray(R.array.colors)
        val colorComponents = mutableListOf(
            Color.red(color),
            Color.green(color),
            Color.blue(color)
        )
        val checkColors = colorComponents.map {
            it > 0
        }.toBooleanArray()

        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.volume_setup)
            .setMultiChoiceItems(colorArray, checkColors, null)
            .setPositiveButton(R.string.action_confirm) { dialog, which ->
                val choseColors: SparseBooleanArray = (dialog as AlertDialog).listView.checkedItemPositions
                val color = Color.rgb(
                booleanToColorComponent(choseColors[0]),
                booleanToColorComponent(choseColors[1]),
                booleanToColorComponent(choseColors[2]),
                )
                parentFragmentManager.setFragmentResult(REQUEST_KEY, bundleOf(BUNDLE_KEY to color))
                dismiss()
            }
            .create()
    }

    private fun booleanToColorComponent(value: Boolean): Int {
       return if (value) 255 else 0
    }


    companion object {

        const val REQUEST_KEY = "REQUEST_KEY5"
        const val BUNDLE_KEY = "BUNDLE_KEY5"
        const val TAG = "TAG5"
        const val ARG_KEY = "ARG_KEY5"

        fun showAlertDialog(fm: FragmentManager, color: Int) {
            val dialog = MultipleChoiceConfirmationDialogFragment()
            dialog.arguments = bundleOf(ARG_KEY to color)
            dialog.show(fm, TAG)
        }

        fun setupAlertDialogListener(
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