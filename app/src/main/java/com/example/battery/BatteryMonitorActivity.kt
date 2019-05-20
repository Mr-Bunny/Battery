package com.example.battery

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.battery.views.BatteryView
import com.example.battery.views.TextWithLabelView
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.content.BroadcastReceiver

/**Main activity*/
class BatteryMonitorActivity : AppCompatActivity() {

    lateinit var mBatteryView: BatteryView

    lateinit var mUntilDrainedView: TextWithLabelView
    lateinit var mTemperature: TextWithLabelView
    lateinit var mVoltage: TextWithLabelView
    lateinit var mCondition: TextWithLabelView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initUI()
    }

    override fun onStop() {
        super.onStop()
        //Stop monitoring
        unregisterReceiver(mBatteryInfoReceiver)
    }

    override fun onStart() {
        super.onStart()
        //Start monitoring
        registerReceiver(this.mBatteryInfoReceiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
    }

    private fun initUI() {
        mBatteryView = findViewById(R.id.batteryView)

        mUntilDrainedView = findViewById(R.id.twl_untilDrained)
        mTemperature = findViewById(R.id.twl_temperature)
        mVoltage = findViewById(R.id.twl_voltage)
        mCondition = findViewById(R.id.twl_condition)

        mUntilDrainedView.setText("-")
    }

    //Receiver for battery monitoring
    private val mBatteryInfoReceiver = object : BroadcastReceiver() {
        override fun onReceive(arg0: Context, intent: Intent) {
            try {
                //set charge percent
                val level = intent.getIntExtra("level", 0)
                mBatteryView.setPercent(level)

                //set temperature
                val temp = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -1)
                if (temp == -1)
                    mTemperature.setText("-")
                else
                    mTemperature.setText((temp/10).toFloat().toString() + "\u00B0")

                //set health
                val health = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, -1)
                var batteryHealth = "-"
                when (health) {
                    BatteryManager.BATTERY_HEALTH_COLD -> batteryHealth = getString(R.string.battery_condition_cold)
                    BatteryManager.BATTERY_HEALTH_DEAD -> batteryHealth = getString(R.string.battery_condition_dead)
                    BatteryManager.BATTERY_HEALTH_GOOD -> batteryHealth = getString(R.string.battery_condition_good)
                    BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE -> batteryHealth = getString(R.string.battery_condition_over_voltage)
                    BatteryManager.BATTERY_HEALTH_OVERHEAT -> batteryHealth = getString(R.string.battery_condition_overheat)
                    BatteryManager.BATTERY_HEALTH_UNKNOWN -> batteryHealth = getString(R.string.battery_condition_unknown)
                    BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE -> batteryHealth = getString(R.string.battery_condition_failure)
                }
                mCondition.setText(batteryHealth)

                //set voltage
                val voltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, -1)

                if (voltage == -1) mVoltage.setText("-")
                else {
                    var correctVoltage = voltage.toFloat()

                    if (voltage >= 1000) {
                        correctVoltage = voltage / 1000f
                    }

                    mVoltage.setText(getString(R.string.voltage_value, correctVoltage))
                }

            } catch (ignored: Exception) {}
        }
    }
}
