package bd.gov.activation

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.os.Parcelable
import android.provider.Settings.ACTION_NFC_SETTINGS
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import bd.gov.activation.APICall.RequestAPI
import bd.gov.activation.model.ModelContainer
import bd.gov.activation.model.ModelContainerRenew
import bd.gov.activation.parser.NdefMessageParser
import bd.gov.activation.utils.Utils
import com.google.gson.Gson
import org.jetbrains.anko.doAsync

class MainActivity : AppCompatActivity() {

//    private val output = StringBuilder()
    private var nfcAdapter: NfcAdapter? = null
    private var pendingIntent: PendingIntent? = null


//    val url = "https://rajshahircc.herokuapp.com/api/vehicle/read_one.php?RFID="
    val url = "https://rcc-ars.com/api/mobile/PXQEV34qq1p9iyJN9WFG/check?tracking_id="

    val updateUrl = "https://rcc-ars.com/api/mobile/h7EEXGs8WhcwNCEQ9Spj/update?tracking_id="

    val renewUrl = "https://rcc-ars.com/api/v2/mobile/h7EEXGs8WhcwNCEQ9Spj/renewcard?rfid_no="

    val secureUrl = "https://rcc-ars.com/api/v2/mobile/h7EEXGs8WhcwNCEQ9Spj/securecard?rfid_no="


    private val FactoryKey: ByteArray? = ubyteArrayOf(0xFFU, 0xFFU, 0xFFU, 0xFFU, 0xFFU, 0xFFU).toByteArray()
    private var CustomKeyA: String? = null
    private var CustomKeyB: String? = null
    private val SetCustomKey: ByteArray? =  ubyteArrayOf(0x01U, 0x23U, 0x45U, 0x67U, 0x89U, 0xABU, 0x78U, 0x77U, 0x88U, 0x69U, 0xBAU, 0x98U, 0x76U, 0x54U, 0x32U, 0x10U).toByteArray()
    private val SetFactoryKey: ByteArray? = ubyteArrayOf(0xFFU, 0xFFU, 0xFFU, 0xFFU, 0xFFU, 0xFFU, 0x78U, 0x77U, 0x88U, 0x69U, 0xFFU, 0xFFU, 0xFFU, 0xFFU, 0xFFU, 0xFFU).toByteArray()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val sharedPreference =  getSharedPreferences("APPID_AND_RFID", Context.MODE_PRIVATE)

        var applicationInformaiton = findViewById<View>(R.id.textViewApplicationInformation) as TextView
        var applicationSeries = findViewById<View>(R.id.textViewApplicationType) as TextView
        var rfidNumber = findViewById<View>(R.id.textViewRFIDNumber) as TextView
        var serialNumber = findViewById<View>(R.id.editTextSerialNumber) as EditText
        var applicationID = findViewById<View>(R.id.editTextApplicationID) as EditText
        var btnGetData = findViewById<View>(R.id.buttonGetApplication) as Button
        var btnActivateCard = findViewById<View>(R.id.buttonActivateCard) as Button
        var btnRenewCard = findViewById<View>(R.id.buttonRenewCard) as Button
        var btnSecureCard = findViewById<View>(R.id.buttonSecureCard) as Button


        btnRenewCard.setOnClickListener{
            startActivity(Intent(this, RenewCard::class.java))
//            applicationInformaiton.text = "Getting Application Information"
//            if(serialNumber.length() == 0 && applicationID.length() == 0 && rfidNumber.length() != 0){
//                applicationInformaiton.text = "Renewing Process"
//
//                val renewUrl = renewUrl + rfidNumber.text
//                doAsync {
//                    Log.i("Renew URL: ", renewUrl)
//                    val jsongData = RequestAPI(renewUrl).run()
//                    var jsonArray2 = Gson().fromJson(jsongData, ModelContainerRenew::class.java)
//
//                    runOnUiThread(Runnable{
//                        if(jsonArray2.status.toInt() == 1){
//                            applicationInformaiton.text =
//                                        "ID: " + jsonArray2.renewCard.id.toString() + "\n" +
//                                        "Card NO: " + jsonArray2.renewCard.cardNo.toString() + "\n" +
//                                        "RFID NO: " + jsonArray2.renewCard.rfidNo.toString() + "\n" +
//                                        "Expiry: " + jsonArray2.renewCard.expiry.toString() + "\n" +
//                                        "Key A: " + jsonArray2.renewCard.keyA.toString() + "\n" +
//                                        "Key B: " + jsonArray2.renewCard.keyB.toString() + "\n" +
//                                        "Disabled: " + jsonArray2.renewCard.disabled.toString() + "\n" +
//                                        "Secured: " + jsonArray2.renewCard.secured.toString() + "\n" +
//                                        "Cloned: " + jsonArray2.renewCard.cloned.toString() + "\n"
//                            CustomKeyA = jsonArray2.renewCard.keyA.toString()
//                            CustomKeyB = jsonArray2.renewCard.keyB.toString()
//                        }else{
//                            applicationInformaiton.text = jsonArray2.msg.toString()
//                        }
//                    })
//                }
//            }
        }

        btnSecureCard.setOnClickListener{

            Log.i("CustomKeyA", CustomKeyA.toString())
            Log.i("CustomKeyB", CustomKeyB.toString())
            
            applicationInformaiton.text = "Getting Application Information"
            if(serialNumber.length() == 0 && applicationID.length() == 0 && rfidNumber.length() != 0){
                applicationInformaiton.text = "Securing Process"
                val secureUrl = secureUrl + rfidNumber.text
                doAsync {
                    val jsongData = RequestAPI(secureUrl).run()
                    var jsonArray2 = Gson().fromJson(jsongData, ModelContainerRenew::class.java)

                    runOnUiThread(Runnable{
                            applicationInformaiton.text = jsonArray2.msg.toString()
                    })
                }

            }
        }

        btnActivateCard.setOnClickListener{
            if(serialNumber.length() == 0){
                applicationInformaiton.text = "Serial Number is Empty"
            }else if(rfidNumber.length() == 0){
                applicationInformaiton.text = "RFID Number is Empty"
            }else{
                applicationInformaiton.text = "Card is activating, please wait!"
                var typeOfRegistration = applicationSeries.text
                val finalURL = updateUrl + applicationID.text + "&card_no=" + typeOfRegistration + serialNumber.text + "&rfid_no=" + rfidNumber.text
                doAsync {
                    val jsongData = RequestAPI(finalURL).run()
                    var jsonArray2 = Gson().fromJson(jsongData, ModelContainer::class.java)
                    runOnUiThread(Runnable{
                        if(jsonArray2.status.toInt() == 1){
                            applicationInformaiton.text = "Update Successful"
                        }else{
                            applicationInformaiton.text = jsonArray2.msg.toString()
                        }
                    })
                }
            }
        }


        btnGetData.setOnClickListener { _ ->
            applicationInformaiton.text = "Getting Application Information"
            serialNumber.setText("")
            rfidNumber.text = ""
            if (applicationID.length() == 0){
                applicationInformaiton.text = "application ID is Empty"
            }else{
                applicationInformaiton.text = "Getting Application Information"
                val finalURL = url + applicationID.text
                doAsync {
                    val jsongData = RequestAPI(finalURL).run()
                    var jsonArray2 = Gson().fromJson(jsongData, ModelContainer::class.java)
                    runOnUiThread(Runnable{
                        val applicationType: String
                        if(jsonArray2.application.applicationTypeId.toInt() == 1){
                            applicationType = "AutoRickshaw Registration"
                            applicationSeries.text = "KHA-"
                        }else if(jsonArray2.application.applicationTypeId.toInt() == 2){
                            applicationType = "ChargerRickshaw Registration"
                            applicationSeries.text = "GA-"
                        }else if(jsonArray2.application.applicationTypeId.toInt() == 3){
                            applicationType = "Driver Registration"
                            applicationSeries.text = " D-"
                        }else if(jsonArray2.application.applicationTypeId.toInt() == 4){
                            applicationType = "Driver Registration"
                            applicationSeries.text = " D-"
                        }else if(jsonArray2.application.applicationTypeId.toInt() == 5){
                            applicationType = "Driver Registration"
                            applicationSeries.text = " D-"
                        }else if(jsonArray2.application.applicationTypeId.toInt() == 6){
                            applicationType = "Driver Registration"
                            applicationSeries.text = " D-"
                        }else{
                            applicationType = "Other Registration"
                        }
                        applicationInformaiton.text =
                            applicationType + "\n" +
                                    jsonArray2.application.registration.name.toString() + "\n" +
                                    jsonArray2.application.registration.fatherName.toString() + "\n" +
                                    jsonArray2.application.registration.mobile.toString() + "\n" +
                                    jsonArray2.application.registration.nid.toString()
                    })
                }
            }
        }



        nfcAdapter = NfcAdapter.getDefaultAdapter(this)
//        if (nfcAdapter == null) {
//            Toast.makeText(this, "No NFC", Toast.LENGTH_SHORT).show()
//            finish()
//            return
//        }

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
