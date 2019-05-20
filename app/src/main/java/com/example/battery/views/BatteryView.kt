package com.example.battery.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.example.battery.R
import kotlinx.android.synthetic.main.battery_view_layout.view.*

//Custom view to display battery
class BatteryView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0): RelativeLayout(context, attrs, defStyleAttr) {

    var mContext: Context = context

    init {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        View.inflate(context, R.layout.battery_view_layout, this)
        val typedArr = context.obtainStyledAttributes(attrs, R.styleable.BatteryView)

        try {
            //Check for values in attributes
            if (typedArr != null) {
                val percent = typedArr.getInt(R.styleable.BatteryView_percent, 0)

                setPercent(percent)
            }
        } finally {
            typedArr.recycle()
        }
    }

    //set text value and color for battery view
    fun setPercent(percent: Int) {
        var correctPercent = percent

        //check for boundary values
        if (percent < 0) correctPercent = 0
        else if (percent > 100) correctPercent = 100

        //set text
        percentTextView.text = mContext.resources.getString(R.string.battery_percent, correctPercent)

        //set color
        when (correctPercent) {
            in 0..25 -> batteryImageView.setImageResource(R.drawable.ic_battery_low)
            in 26..75 -> batteryImageView.setImageResource(R.drawable.ic_battery_middle)
            in 76..100 -> batteryImageView.setImageResource(R.drawable.ic_battery_full)
        }
    }
}