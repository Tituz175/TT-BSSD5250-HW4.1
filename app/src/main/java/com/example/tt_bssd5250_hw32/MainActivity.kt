package com.example.tt_bssd5250_hw32

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RelativeLayout
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.fragment.app.commit

class MainActivity : AppCompatActivity() {
    var noteTotal = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val fragmentLinearLayout = LinearLayoutCompat(this).apply {
            id = View.generateViewId()
            orientation = LinearLayoutCompat.VERTICAL
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
            )
        }

        val addButton = Button(this).apply {
            text = "New Todo"
            id = View.generateViewId()
            layoutParams = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
            )
            setOnClickListener {
                supportFragmentManager.commit {
                    if (noteTotal < 10) {
                        add(
                            fragmentLinearLayout.id,
                            NoteFragment.newInstance(View.generateViewId(),""),
                            null
                        )
                        noteTotal++
                    }
                }
            }
        }
        val redAddButton = Button(this).apply {
            text = "New Todo"
            setTextColor(Color.RED)
            layoutParams = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
            )
            setOnClickListener {
                supportFragmentManager.commit {
                    if (noteTotal < 10) {
                        add(
                            fragmentLinearLayout.id,
                            NoteFragment.newInstance(View.generateViewId(),"red"),
                            null
                        )
                        noteTotal++
                    }
                }
            }
        }

        val buttonLayout = LinearLayoutCompat(this).apply {
            orientation = LinearLayoutCompat.HORIZONTAL
            addView(addButton)
            addView(redAddButton)
        }


        val linearLayout = LinearLayoutCompat(this).apply {
            setBackgroundColor(Color.LTGRAY)
            orientation = LinearLayoutCompat.VERTICAL
            addView(buttonLayout)
            addView(fragmentLinearLayout)
        }

        setContentView(linearLayout)

    }
}