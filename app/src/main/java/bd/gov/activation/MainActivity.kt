package bd.gov.activation

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

//import android.app.Activity
import android.app.PendingIntent
import android.content.Intent
import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.os.Parcelable
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.provider.Settings.ACTION_NFC_SETTINGS
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import bd.gov.activation.APICall.RequestAPI
import bd.gov.activation.model.Vehicle
import bd.gov.activation.parser.NdefMessageParser
import bd.gov.activation.utils.Utils
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync
import org.w3c.dom.Text

//import org.jetbrains.anko.doAsync
//import com.google.gson.Gson
//import com.beust.klaxon.JsonArray

class MainActivity : AppCompatActivity() {

    private val output = StringBuilder()
    private var nfcAdapter: NfcAdapter? = null
    private var pendingIntent: PendingIntent? = null

//    val url = "https://rajshahircc.herokuapp.com/api/vehicle/read_one.php?RFID="
    val url = "http://rcc-ars.com/api/mobile/PXQEV34qq1p9iyJN9WFG/check?tracking_id="

    val updateUrl = "http://rcc-ars.com/api/mobile/h7EEXGs8WhcwNCEQ9Spj/update?tracking_id="


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var applicationInformaiton = findViewById<View>(R.id.textViewApplicationInformation) as TextView
        var rfidNumber = findViewById<View>(R.id.textViewRFIDNumber) as TextView
        var serialNumber = findViewById<View>(R.id.editTextSerialNumber) as EditText
        var applicationID = findViewById<View>(R.id.editTextApplicationID) as EditText
        var btnGetData = findViewById<View>(R.id.buttonGetApplication) as Button
        var btnActivateCard = findViewById<View>(R.id.buttonActivateCard) as Button

        btnActivateCard.setOnClickListener{
            applicationInformaiton.text = "Getting Application Information"
            if(serialNumber.length() == 0){
                applicationInformaiton.text = "Serial Number is Empty"
            }else if(rfidNumber.length() == 0){
                applicationInformaiton.text = "RFID Number is Empty"
            }else{
                applicationInformaiton.text = "Card is activating, please wait!"

                val finalURL = url + applicationID.text + "&card_no=" + serialNumber.text + "&rfid_no=" + rfidNumber.text
//                txtContents.setText(finalURL + "\nPlease wait for a moment. \n Fetching Data From Server")
                doAsync {
                    output.setLength(0)
                    val jsongData = RequestAPI(finalURL).run()
//
//                    var jsonArray2 = Gson().fromJson(jsongData, Vehicle::class.java)
//                    output.append("Name: ").append(jsonArray2.Name).append("\n")
//                    output.append("VIN: ").append(jsonArray2.VIN).append("\n")
//                    output.append("Address: ").append(jsonArray2.Address).append("\n")
//                    output.append("RFID: ").append(jsonArray2.RFID).append("\n")
//                    output.append("Valid Till: ").append(jsonArray2.ValidTill).append("\n")
//                    txtContents.setText(output.toString())
//
                }
            }
        }

//        btnRecharge.setOnClickListener {
//            val intent = Intent(this, RechargeActivity::class.java)
//            startActivity(intent)
//        }

        btnGetData.setOnClickListener { _ ->
            applicationInformaiton.text = "Getting Application Information"
            if (applicationID.length() == 0){
                applicationInformaiton.text = "application ID is Empty"
            }else{
                applicationInformaiton.text = "Getting Application Information"
                val finalURL = url + applicationID.text
//                txtContents.setText(finalURL + "\nPlease wait for a moment. \n Fetching Data From Server")
                doAsync {
                    output.setLength(0)
                    val jsongData = RequestAPI(finalURL).run()
//
//                    var jsonArray2 = Gson().fromJson(jsongData, Vehicle::class.java)
//                    output.append("Name: ").append(jsonArray2.Name).append("\n")
//                    output.append("VIN: ").append(jsonArray2.VIN).append("\n")
//                    output.append("Address: ").append(jsonArray2.Address).append("\n")
//                    output.append("RFID: ").append(jsonArray2.RFID).append("\n")
//                    output.append("Valid Till: ").append(jsonArray2.ValidTill).append("\n")
//                    txtContents.setText(output.toString())
//
                }
            }
        }



        nfcAdapter = NfcAdapter.getDefaultAdapter(this)


        if (nfcAdapter == null) {
            Toast.makeText(this, "No NFC", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        pendingIntent = PendingIntent.getActivity(this, 0,
                Intent(this, this.javaClass)
                        .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0)
    }

    override fun onResume() {
        super.onResume()

        val nfcAdapterRefCopy = nfcAdapter
        if (nfcAdapterRefCopy != null) {
            if (!nfcAdapterRefCopy.isEnabled)
                showNFCSettings()

            nfcAdapterRefCopy.enableForegroundDispatch(this, pendingIntent, null, null)
        }
    }

    override fun onNewIntent(intent: Intent) {
        setIntent(intent)
        resolveIntent(intent)
    }

    private fun showNFCSettings() {
        Toast.makeText(this, "You need to enable NFC", Toast.LENGTH_SHORT).show()
        val intent = Intent(ACTION_NFC_SETTINGS)
        startActivity(intent)
    }

    /**
     * Tag data is converted to string to display
     *
     * @return the data dumped from this tag in String format
     */
    private fun dumpTagData(tag: Tag): String {
        val sb = StringBuilder()
        val id = tag.id
//        sb.append("ID (hex): ").append(Utils.toHex(id)).append('\n')
//        sb.append("ID (rev): ").append(Utils.toReversedHex(id)).append('\n')
        sb.append(Utils.toDec(id))
//        sb.append(Utils.toReversedHex(id).toUpperCase())
//        sb.append("ID (reversed dec): ").append(Utils.toReversedDec(id)).append('\n')


//        val prefix = "android.nfc.tech."
//        sb.append("Technologies: ")
//        for (tech in tag.getTechList()) {
//            sb.append(tech.substring(prefix.length))
//            sb.append(", ")
//        }
//
//        sb.delete(sb.length - 2, sb.length)
//
//        for (tech in tag.getTechList()) {
//            if (tech == MifareClassic::class.java.name) {
//                sb.append('\n')
//                var type = "Unknown"
//
//                try {
//                    val mifareTag = MifareClassic.get(tag)
//
//                    when (mifareTag.type) {
//                        MifareClassic.TYPE_CLASSIC -> type = "Classic"
//                        MifareClassic.TYPE_PLUS -> type = "Plus"
//                        MifareClassic.TYPE_PRO -> type = "Pro"
//                    }
//                    sb.append("Mifare Classic type: ")
//                    sb.append(type)
//                    sb.append('\n')
//
//                    sb.append("Mifare size: ")
//                    sb.append(mifareTag.size.toString() + " bytes")
//                    sb.append('\n')
//
//                    sb.append("Mifare sectors: ")
//                    sb.append(mifareTag.sectorCount)
//                    sb.append('\n')
//
//                    sb.append("Mifare blocks: ")
//                    sb.append(mifareTag.blockCount)
//                } catch (e: Exception) {
//                    sb.append("Mifare classic error: " + e.message)
//                }
//
//            }
//
//            if (tech == MifareUltralight::class.java.name) {
//                sb.append('\n')
//                val mifareUlTag = MifareUltralight.get(tag)
//                var type = "Unknown"
//                when (mifareUlTag.type) {
//                    MifareUltralight.TYPE_ULTRALIGHT -> type = "Ultralight"
//                    MifareUltralight.TYPE_ULTRALIGHT_C -> type = "Ultralight C"
//                }
//                sb.append("Mifare Ultralight type: ")
//                sb.append(type)
//            }
//        }

        return sb.toString()
    }

    private fun resolveIntent(intent: Intent) {
        val action = intent.action

        if (NfcAdapter.ACTION_TAG_DISCOVERED == action
                || NfcAdapter.ACTION_TECH_DISCOVERED == action
                || NfcAdapter.ACTION_NDEF_DISCOVERED == action) {
            val rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)

            if (rawMsgs != null) {
                Log.i("NFC", "Size:" + rawMsgs.size)
                val ndefMessages: Array<NdefMessage> = Array(rawMsgs.size, {i -> rawMsgs[i] as NdefMessage})
                displayNfcMessages(ndefMessages)
            } else {
                val empty = ByteArray(0)
                val id = intent.getByteArrayExtra(NfcAdapter.EXTRA_ID)
                val tag = intent.getParcelableExtra<Parcelable>(NfcAdapter.EXTRA_TAG) as Tag
                val payload = dumpTagData(tag).toByteArray()
                val record = NdefRecord(NdefRecord.TNF_UNKNOWN, empty, id, payload)
                val emptyMsg = NdefMessage(arrayOf(record))
                val emptyNdefMessages: Array<NdefMessage> = arrayOf(emptyMsg)
                displayNfcMessages(emptyNdefMessages)
            }
        }
    }


    private fun displayNfcMessages(msgs: Array<NdefMessage>?) {
        if (msgs == null || msgs.isEmpty())
            return

        val builder = StringBuilder()
        val records = NdefMessageParser.parse(msgs[0])
        val size = records.size

        for (i in 0 until size) {
            val record = records[i]
            val str = record.str()
            builder.append(str).append("\n")
        }

        val rfidNumber = findViewById<View>(R.id.textViewRFIDNumber) as TextView
        rfidNumber.text = builder.toString()
    }
}
