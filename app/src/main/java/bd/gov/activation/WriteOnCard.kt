package bd.gov.activation

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import kotlin.text.trim

class WriteOnCard : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write_on_card)

        val applicationName = intent.getStringExtra("applicationName").toString()
        val applicationFather = intent.getStringExtra("applicationFather").toString()
        val applicationNID = intent.getStringExtra("applicationNID").toString()
        val applicationMobile = intent.getStringExtra("applicationMobile").toString()
        val trackingId = intent.getStringExtra("trackingId").toString()
        val rfidNumber = intent.getStringExtra("rfidNumber").toString().trim()
        val cardSerialNumber = intent.getStringExtra("serialNumber").toString()

        var applicantNameTextView = findViewById<View>(R.id.textViewName) as TextView
        var applicationFatherTextView = findViewById<View>(R.id.textViewFatherName) as TextView
        var applicationNIDTextView = findViewById<View>(R.id.textViewNID) as TextView
        var applicationMobileTextView = findViewById<View>(R.id.textViewMobile) as TextView
        var trackingIdTextView = findViewById<View>(R.id.textViewTracking) as TextView
        var rfidNumberTextView = findViewById<View>(R.id.textViewRFID) as TextView
        var cardSerialNumberTextView = findViewById<View>(R.id.textViewCardNumber) as TextView

        applicantNameTextView.text = applicationName
        applicationFatherTextView.text = applicationFather
        applicationNIDTextView.text = applicationNID
        applicationMobileTextView.text = applicationMobile
        trackingIdTextView.text = trackingId
        rfidNumberTextView.text = rfidNumber
        cardSerialNumberTextView.text = cardSerialNumber
    }
}