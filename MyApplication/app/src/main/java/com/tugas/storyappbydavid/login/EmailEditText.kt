package com.tugas.storyappbydavid.login

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.tugas.storyappbydavid.R

class EmailEditText : AppCompatEditText {
    private lateinit var textBackground: Drawable

    constructor(context: Context) : super(context) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    // Pattern Regex https://www.w3resource.com/javascript/form/email-validation.php
    val emailPattern = "[a-zA-Z0-9.!#\$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)".toRegex()

    private fun showErrorText() {
        this.error = "Enter a valid email address!"
    }
    private fun hideErrorText() {
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        background = textBackground
        hint = "Email..."
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
    }
    private fun init() {
        textBackground = ContextCompat.getDrawable(context, R.drawable.edittextstyle) as Drawable
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Do nothing.
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.matches(emailPattern) || s.isEmpty()) {
                    hideErrorText()
                }
                else {
                    showErrorText()
                }
            }
            override fun afterTextChanged(s: Editable) {
                // Do nothing.
            }
        })
    }
}

