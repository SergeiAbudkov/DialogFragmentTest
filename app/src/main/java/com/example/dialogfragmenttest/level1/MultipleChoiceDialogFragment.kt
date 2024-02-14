package com.example.dialogfragmenttest.level1

import android.app.AlertDialog
import android.app.Dialog
import android.app.FragmentManagerNonConfig
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentResultListener
import androidx.lifecycle.LifecycleOwner
import com.example.dialogfragmenttest.R

class MultipleChoiceDialogFragment : DialogFragment() {

    val color: Int
    get() = requireArguments().getInt(ARG_KEY)


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val arrayColor: Array<String> = resources.getStringArray(R.array.colors)
        val quantityColor: MutableList<Int> = mutableListOf(
            Color.red(this.color),
            Color.green(this.color),
            Color.blue(this.color)
        )

        val checkedColors = quantityColor.map {
            it > 0
        }.toBooleanArray()

        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.volume_setup)
            .setMultiChoiceItems(arrayColor, checkedColors) { dialog, which, isChecked ->
            quantityColor[which] = if(isChecked) 255 else 0
                val result = Color.rgb(
                    quantityColor[0],
                    quantityColor[1],
                    quantityColor[2],
                )
                parentFragmentManager.setFragmentResult(REQUEST_KEY, bundleOf(BUNDLE_KEY to result))
                 }
            .setPositiveButton(R.string.action_close, null)

            .create()
    }


    companion object {

        const val REQUEST_KEY = "REQUEST_KEY4"
        const val BUNDLE_KEY = "BUNDLE_KEY4"
        const val ARG_KEY = "ARG_KEY4"
        const val TAG = "TAG4"

        fun showDialog(color: Int, fm: FragmentManager) {
            val dialog = MultipleChoiceDialogFragment()
            dialog.arguments = bundleOf(ARG_KEY to color)
            dialog.show(fm, TAG)
        }

        fun setupColorListener(fm: FragmentManager, lifecycleOwner: LifecycleOwner, listener: (Int) -> Unit) {
            fm.setFragmentResultListener(REQUEST_KEY, lifecycleOwner) { _, result ->
                listener.invoke(result.getInt(BUNDLE_KEY))
            }
        }

    }
}