package com.example.battery.views

import android.content.Context
import android.support.v7.widget.LinearLayoutCompat
import android.util.AttributeSet
import android.view.View
import com.example.battery.R
import kotlinx.android.synthetic.main.text_with_label_layout.view.*

//Custom view class to display label and text below
class TextWithLabelView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0):
    LinearLayoutCompat(context, attrs, defStyleAttr) {

    init {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        View.inflate(context, R.layout.text_with_label_layout, this)
        val typedArr = context.obtainStyledAttributes(attrs, R.styleable.TextWithLabelView)

        try {
            //Check for values in attributes
            if (typedArr != null) {
                setLabel(typedArr.getString(R.styleable.TextWithLabelView_label))
                setText(typedArr.getString(R.styleable.TextWithLabelView_text))
            }
        } finally {
            typedArr.recycle()
        }
    }

    fun setLabel(label: String?) {
        if (label !== null && label != "") labelView.text = label
    }

    fun setText(text: String?) {
        if (text !== null && text != "") textView.text = text
    }
}