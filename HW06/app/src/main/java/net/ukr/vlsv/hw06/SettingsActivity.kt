package net.ukr.vlsv.hw06

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val tempUnit = intent.getStringExtra(resources.getText(R.string.KEY_Settings_TempUnit).toString())

        if (tempUnit == resources.getText(R.string.Settings_Kelvin_Shot).toString())
            radioButton_Calvin.isChecked = true
        else
            radioButton_Celsius.isChecked = true

        button_SettingsCancel.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }

        button_SettingsApply.setOnClickListener {
            var tempUnit = resources.getText(R.string.Settings_Celsius_Shot)

            if (radioButton_Calvin.isChecked) tempUnit = resources.getText(R.string.Settings_Kelvin_Shot)

            val mainIntent = Intent()
            mainIntent.putExtra(resources.getText(R.string.KEY_Settings_TempUnit).toString(), tempUnit)
            setResult(Activity.RESULT_OK, mainIntent)
            finish()
        }
    }
}
