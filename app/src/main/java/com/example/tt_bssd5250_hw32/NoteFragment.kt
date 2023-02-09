package com.example.tt_bssd5250_hw32

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.commit
import androidx.fragment.app.setFragmentResultListener

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NoteFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NoteFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var resultKey: String = ""
    private var sentColor: String = ""

    private lateinit var nameView: TextView
    private lateinit var dateView: TextView
    private lateinit var descView: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        setFragmentResultListener(resultKey) { requestKey, bundle ->
            nameView.text = bundle.getString("nameKey").toString()
            dateView.text = bundle.getString("dateKey").toString()
            descView.text = bundle.getString("descKey").toString()
        }
    }

    var deleteNumber = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Text for the left side
        nameView = TextView(context).apply {
            setHint(R.string.name_place_holder)
            textSize = 15f
        }
        dateView = TextView(context).apply {
            setHint(R.string.date_place_holder)
            textSize = 15f
        }
        descView = TextView(context).apply {
            setHint(R.string.desc_place_holder)
            textSize = 15f
        }

        val textHolderL = LinearLayoutCompat(requireContext()).apply {
            orientation = LinearLayoutCompat.VERTICAL
            addView(nameView)
            addView(dateView)
            addView(descView)
            layoutParams = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
            )
            (layoutParams as RelativeLayout.LayoutParams).addRule(
                RelativeLayout.ALIGN_PARENT_LEFT
            )
        }

        val editButton = Button(requireContext()).apply {
            text = "Edit"
            id = View.generateViewId()
            layoutParams = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
            )
            (layoutParams as RelativeLayout.LayoutParams).addRule(
                RelativeLayout.ALIGN_PARENT_RIGHT,
            )
            setOnClickListener {
                val currentData = bundleOf(
                    "name" to nameView.text,
                    "date" to dateView.text,
                    "desc" to descView.text,
                )
                NoteEditorDialog.newInstance(resultKey, currentData).show(
                    parentFragmentManager, NoteEditorDialog.TAG
                )
            }
        }


        val deleteButton = Button(requireContext()).apply {
            text = "Delete"
            setTextColor(Color.RED)
            layoutParams = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
            )
            (layoutParams as RelativeLayout.LayoutParams).addRule(
                RelativeLayout.LEFT_OF, editButton.id
            )

            setOnClickListener {
                deleteNumber = 1
                AlertDialog.Builder(requireContext()).apply {
                    setTitle("Delete Note?")
                    setPositiveButton("Yes", DialogInterface.OnClickListener { dialogInterface, i ->
                        activity?.supportFragmentManager?.commit {
                            remove(this@NoteFragment)
                        }
                        val parentActivity = activity as MainActivity
                        parentActivity.noteTotal--

                    })
                    setNegativeButton("No", null)
                    create()
                    show()
                }
            }
        }

        val relativeLayout = RelativeLayout(requireContext()).apply {
            if (sentColor != "") {
                setBackgroundColor(Color.RED)
            } else {
                setBackgroundColor(Color.WHITE)
            }

            layoutParams = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
            )
            addView(editButton)
            addView(deleteButton)
            addView(textHolderL)
        }
        return relativeLayout
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment NoteFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(unique: Int, color: String) =
            NoteFragment().apply {
                resultKey = "NoteDataChange$unique"
                sentColor = "$color"
            }
    }
}