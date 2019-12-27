package ksayker.weather.view.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import ksayker.weather.R

class MessageDialogFragment : DialogFragment() {

    var listener: ConfirmationListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return AlertDialog.Builder(context!!)
            .setMessage(arguments?.getString(MESSAGE_ARG))
            .setPositiveButton(R.string.all_ok) { _, _ ->
                listener?.confirmButtonClicked()
            }
            .setOnDismissListener {
                listener?.onDismiss()
            }
            .create()
    }

    interface ConfirmationListener {
        fun confirmButtonClicked()
        fun onDismiss()
    }

    companion object {
        private const val MESSAGE_ARG = "MESSAGE_ARG"

        fun newInstance(message: String, listener: ConfirmationListener?): MessageDialogFragment {
            val bundle = Bundle()
            val dialog = MessageDialogFragment()

            bundle.putString(MESSAGE_ARG, message)
            dialog.arguments = bundle
            dialog.listener = listener

            return dialog
        }
    }
}