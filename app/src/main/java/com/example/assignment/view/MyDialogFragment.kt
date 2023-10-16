package com.example.assignment.view

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.assignment.R

class MyDialogFragment : DialogFragment() {

    companion object {
        private const val ARG_TITLE = "arg_title"
        private const val ARG_HEIGHT = "arg_height"
        private const val ARG_WIDTH = "arg_width"

        fun newInstance(title: String?,height: String, width: String): MyDialogFragment {
            val fragment = MyDialogFragment()
            val args = Bundle()
            args.putString(ARG_TITLE, title)
            args.putString(ARG_HEIGHT, height)
            args.putString(ARG_WIDTH, width)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val title = requireArguments().getString(ARG_TITLE)
        val height = requireArguments().getString(ARG_HEIGHT)
        val width = requireArguments().getString(ARG_WIDTH)

        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_layout, null)
        dialogView.findViewById<TextView>(R.id.txt_description).text = "Title : $title \n Height : $height and Width $width"

        return AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setPositiveButton("OK") { _, _ -> dismiss() }
            .create()
    }
}