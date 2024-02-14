package com.example.dialogfragmenttest

import android.content.DialogInterface
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentResultListener
import com.example.dialogfragmenttest.databinding.ActivityDialogsLevel1Binding
import com.example.dialogfragmenttest.level1.MultipleChoiceConfirmationDialogFragment
import com.example.dialogfragmenttest.level1.MultipleChoiceDialogFragment
import com.example.dialogfragmenttest.level1.SimpleDialogFragment
import com.example.dialogfragmenttest.level1.SingleChoiceConfirmationFragment
import com.example.dialogfragmenttest.level1.SingleChoiceDialogFragment
import kotlin.properties.Delegates.notNull

class DialogsLevel1Activity : AppCompatActivity() {
    private lateinit var binding: ActivityDialogsLevel1Binding

    private var volume by notNull<Int>()
    private var color by notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            ActivityDialogsLevel1Binding.inflate(layoutInflater).also { setContentView(it.root) }

        binding.showDefaultAlertDialogButton.setOnClickListener {
            simpleAlertDialog()
        }

        binding.showSingleChoiceAlertDialogButton.setOnClickListener {
            singleChoiceAlertDialog()
        }

        binding.showSingleChoiceWithConfirmationAlertDialogButton.setOnClickListener {
        singleChoiceWithConfirmationAlertDialog()
        }

        binding.showMultipleChoiceAlertDialogButton.setOnClickListener {
            multipleChoiceAlertDialog()
        }

        binding.showMultipleChoiceWithConfirmationAlertDialogButton.setOnClickListener {
            multipleChoiceWithConfirmationAlertDialog()
        }

        volume = savedInstanceState?.getInt(KEY_VOLUME) ?: 50
        color = savedInstanceState?.getInt(KEY_COLOR) ?: Color.RED

        updateUi()

        setupSimpleAlertDialogListener()
        setupSingleChoiceAlertDialog()
        setupSingleChoiceWithConfirmationAlertDialog()
        setupMultipleChoiceAlertDialog()
        setupMultipleChoiceWithConfirmationAlertDialog()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_VOLUME, volume)
        outState.putInt(KEY_COLOR, color)
    }

    private fun updateUi() {
        binding.currentColorTextView.text = getString(R.string.volume_description, volume)
        binding.colorView.setBackgroundColor(color)
    }

    private fun simpleAlertDialog() {
        val dialog = SimpleDialogFragment()
        dialog.show(supportFragmentManager, SimpleDialogFragment.TAG)
    }

    private fun setupSimpleAlertDialogListener() {
        supportFragmentManager.setFragmentResultListener(SimpleDialogFragment.REQUEST_KEY, this)
            { _, result ->
                val which = result.getInt(SimpleDialogFragment.RESULT_KEY)
                when (which) {
                    DialogInterface.BUTTON_POSITIVE -> showToast(R.string.uninstall_confirmed)
                    DialogInterface.BUTTON_NEGATIVE -> showToast(R.string.uninstall_rejected)
                    DialogInterface.BUTTON_NEUTRAL -> showToast(R.string.uninstall_ignored)
                }
            }
    }

    //
    //
    //

    private fun singleChoiceAlertDialog() {
        val alertDialog = SingleChoiceDialogFragment()
        alertDialog.show(supportFragmentManager, volume)
    }

    private fun setupSingleChoiceAlertDialog() {
        SingleChoiceDialogFragment.setupListener(supportFragmentManager, this) {
            this.volume = it
            updateUi()
        }
    }

    //
    //
    //

    private fun singleChoiceWithConfirmationAlertDialog() {
        SingleChoiceConfirmationFragment.showAlertDialog(supportFragmentManager, volume)
    }

    private fun setupSingleChoiceWithConfirmationAlertDialog() {
        SingleChoiceConfirmationFragment.setupDialogListener(supportFragmentManager, this) {
            this.volume = it
            updateUi()
        }
    }

    //
    //
    //

    private fun multipleChoiceAlertDialog() {
        MultipleChoiceDialogFragment.showDialog(color,supportFragmentManager)

    }

    private fun setupMultipleChoiceAlertDialog() {
        MultipleChoiceDialogFragment.setupColorListener(supportFragmentManager,this) {
            this.color = it
            updateUi()
        }
    }


    //
    //
    //

    private fun multipleChoiceWithConfirmationAlertDialog() {
        MultipleChoiceConfirmationDialogFragment.showAlertDialog(supportFragmentManager, color)
    }

    private fun setupMultipleChoiceWithConfirmationAlertDialog() {
        MultipleChoiceConfirmationDialogFragment.setupAlertDialogListener(supportFragmentManager, this) {
            this.color = it
            updateUi()
        }
    }




    companion object {
        const val KEY_VOLUME = "KEY_VOLUME"
        const val KEY_COLOR = "KEY_COLOR"
    }
}