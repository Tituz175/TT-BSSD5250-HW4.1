package com.example.tt_bssd5250_hw32

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import java.util.Date

class NoteEditorDialog : DialogFragment() {

    var targetResultKey:String = ""

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val editName = EditText(requireContext()).apply {
            setHint(R.string.name_place_holder)
            setText(existingData.getString("name"))
        }
        val editDate = EditText(requireContext()).apply {
            setText(Date().toString())
        }
        val editDesc = EditText(requireContext()).apply {
            setHint(R.string.desc_place_holder)
            setText(existingData.getString("desc"))
        }

        val linearLayout = LinearLayoutCompat(requireContext()).apply {
            orientation = LinearLayoutCompat.VERTICAL
            addView(editName)
            addView(editDate)
            addView(editDesc)
        }

        val ad = AlertDialog.Builder(requireContext()).apply {
            setTitle("Note Editor")
            setTitle("Edit Content")
            setView(linearLayout)
            setPositiveButton("Save") { _, _ ->
                setFragmentResult(
                    targetResultKey,
                    bundleOf(
                        "nameKey" to editName.text.toString(),
                        "dateKey" to editDate.text.toString(),
                        "descKey" to editDesc.text.toString(),
                    )
                )
            }
            setNegativeButton("Cancel") { _, _ -> }
        }
        return ad.create()
    }

    companion object {
        const val TAG = "NoteEditorDialog"

        var existingData:Bundle = Bundle.EMPTY

        @JvmStatic
        fun newInstance(target:String, existing:Bundle) =
            NoteEditorDialog().apply {
                targetResultKey = target
                existingData = existing
            }
    }
}