package net.ukr.vlsv.hw06

 import android.app.Activity
 import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_no_internet.*
import net.ukr.vlsv.hw06.Clases.InternetInfo
import android.content.Intent



class NoInternetActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_no_internet)

        Button_TryAgain.setOnClickListener {
            val internetInfo: InternetInfo = InternetInfo()

            if (internetInfo.getInternetStatus() == true) {
                val intent = Intent()
                setResult(Activity.RESULT_OK, intent)
                finish()
            }

        }
    }
}
