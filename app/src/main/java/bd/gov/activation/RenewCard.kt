package bd.gov.activation

import android.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.MifareClassic
import android.os.Parcelable
import android.provider.Settings.ACTION_NFC_SETTINGS
import android.text.Editable
import android.text.TextWatcher
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
import kotlinx.android.synthetic.main.activity_renew_card.*
import org.jetbrains.anko.doAsync

class RenewCard : AppCompatActivity() {
    //    private val output = StringBuilder()
    private var nfcAdapter: NfcAdapter? = null
    private var pendingIntent: PendingIntent? = null
    private val output = StringBuilder()
    private lateinit var tag: Tag

    val url = "https://rcc-ars.com/api/mobile/PXQEV34qq1p9iyJN9WFG/check?tracking_id="
    val updateUrl = "https://rcc-ars.com/api/mobile/h7EEXGs8WhcwNCEQ9Spj/update?tracking_id="
    val renewUrl = "https://rcc-ars.com/api/v2/mobile/h7EEXGs8WhcwNCEQ9Spj/renewcard?rfid_no="
    val secureUrl = "https://rcc-ars.com/api/v2/mobile/h7EEXGs8WhcwNCEQ9Spj/securecard?rfid_no="

    private val FactoryKey: ByteArray? =
        ubyteArrayOf(0xFFU, 0xFFU, 0xFFU, 0xFFU, 0xFFU, 0xFFU).toByteArray()
    private var CustomKeyA: String? = null
    private var CustomKeyB: String? = null
    private val SetCustomKey: ByteArray? = ubyteArrayOf(
        0x01U,
        0x23U,
        0x45U,
        0x67U,
        0x89U,
        0xABU,
        0x78U,
        0x77U,
        0x88U,
        0x69U,
        0xBAU,
        0x98U,
        0x76U,
        0x54U,
        0x32U,
        0x10U
    ).toByteArray()
    private val SetFactoryKey: ByteArray? = ubyteArrayOf(
        0xFFU,
        0xFFU,
        0xFFU,
        0xFFU,
        0xFFU,
        0xFFU,
        0x78U,
        0x77U,
        0x88U,
        0x69U,
        0xFFU,
        0xFFU,
        0xFFU,
        0xFFU,
        0xFFU,
        0xFFU
    ).toByteArray()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_renew_card)
        var cardInformaiton = findViewById<View>(R.id.textCardInformation) as TextView
        var textCardUID = findViewById<View>(R.id.textCardUID) as TextView
        nfcAdapter = NfcAdapter.getDefaultAdapter(this)
        if (nfcAdapter == null) {
            Toast.makeText(this, "No NFC", Toast.LENGTH_SHORT).show()
            finish()
            return
        }
        pendingIntent = PendingIntent.getActivity(
            this, 0,
            Intent(this, this.javaClass)
                .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0
        )

        textCardUID?.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                Log.i("Text Change: ", "afterTextChanged")
                Log.i("RFID Number", textCardUID?.text.toString())

                if (textCardUID?.length() == 0) {
                    // Initialize a new instance of
                    val builder = AlertDialog.Builder(this@RenewCard)
                    builder.setNeutralButton(getString(R.string.done)) { _, _ ->
                        Toast.makeText(
                            applicationContext,
                            getString(R.string.rfid_field_empty_message),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    builder.create().show()
                } else {
                    val renewUrl = renewUrl + textCardUID?.text
                    //txtContents.setText("Please wait for a moment. \nFetching Data From Server")
                    doAsync {
                        output.setLength(0)
                        val jsongData = RequestAPI(renewUrl).run()
                        var jsonArray2 = Gson().fromJson(jsongData, ModelContainerRenew::class.java)

                        runOnUiThread {
                            if (jsonArray2.status == 1) {
                                textCardInformation.text =
                                    "ID: " + jsonArray2.renewCard.id.toString() + "\n" +
                                            "Card NO: " + jsonArray2.renewCard.cardNo.toString() + "\n" +
                                            "RFID NO: " + jsonArray2.renewCard.rfidNo.toString() + "\n" +
                                            "Expiry: " + jsonArray2.renewCard.expiry.toString() + "\n" +
                                            "Key A: " + jsonArray2.renewCard.keyA.toString() + "\n" +
                                            "Key B: " + jsonArray2.renewCard.keyB.toString() + "\n" +
                                            "Disabled: " + jsonArray2.renewCard.disabled.toString() + "\n" +
                                            "Secured: " + jsonArray2.renewCard.secured.toString() + "\n" +
                                            "Cloned: " + jsonArray2.renewCard.cloned.toString() + "\n"
                                doAsync {
                                    Log.i("Card Writing", "Card Writing async task")

                                    val cardStatus = WriteIntent2(
                                        tag,
                                        jsonArray2.renewCard.keyB.toString(),
                                        jsonArray2.renewCard.cardNo.toString(),
                                        jsonArray2.renewCard.secured.toString(),
                                        0
                                    )

                                    Log.i("Card Status", cardStatus.toString())
                                    if (cardStatus == 3) {
                                        doAsync {
                                            val secureUrl =
                                                secureUrl + jsonArray2.renewCard.rfidNo.toString()
                                            Log.i("Secure URL: ", secureUrl)
                                            val jsongData3 = RequestAPI(renewUrl).run()
                                            var jsonArray3 = Gson().fromJson(
                                                jsongData,
                                                ModelContainerRenew::class.java
                                            )
                                            runOnUiThread {
                                                if (jsonArray3.status == 1) {
                                                    textCardUID.text = "Secured"
                                                }
                                            }

                                        }
                                    }
                                }
                            } else {
                            }

                        }
                    }
                }
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
                Log.i("Text Change: ", "beforeTextChanged")
                //progressBar.visibility = View.VISIBLE
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                Log.i("Text Change: ", "onTextChanged")
            }
        })
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
        sb.append(Utils.toDec(id))
        return sb.toString()
    }

    private fun resolveIntent(intent: Intent) {
        val action = intent.action

        if (NfcAdapter.ACTION_TAG_DISCOVERED == action
            || NfcAdapter.ACTION_TECH_DISCOVERED == action
            || NfcAdapter.ACTION_NDEF_DISCOVERED == action
        ) {
            val rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)

            if (rawMsgs != null) {
                Log.i("NFC", "Size:" + rawMsgs.size)
                val ndefMessages: Array<NdefMessage> =
                    Array(rawMsgs.size, { i -> rawMsgs[i] as NdefMessage })
                displayNfcMessages(ndefMessages)
            } else {
                val empty = ByteArray(0)
                val id = intent.getByteArrayExtra(NfcAdapter.EXTRA_ID)
                tag = intent.getParcelableExtra<Parcelable>(NfcAdapter.EXTRA_TAG) as Tag
                Log.i("RFID Tag", tag.toString())

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


        textCardUID.text = builder.toString()
    }

//Qaosar' resolveIntent function

//    private fun resolveIntent(intent: Intent) {
//        val action = intent.action
//        val charset = Charsets.UTF_8
//        textCardInformation.text = ""
//        if (NfcAdapter.ACTION_TAG_DISCOVERED == action
//            || NfcAdapter.ACTION_TECH_DISCOVERED == action
//            || NfcAdapter.ACTION_NDEF_DISCOVERED == action
//        ) {
//            val tag = intent.getParcelableExtra<Parcelable>(NfcAdapter.EXTRA_TAG) as Tag
//            val id = intent.getByteArrayExtra(NfcAdapter.EXTRA_ID)
//            var status = WriteIntent2(tag, "ABCDEF", "123456", "KHA-03915", 0)
//            //card status: 0=key not modified, 1=key modified
//            textCardUID.text = id.toString()
//            textCardInformation.setText(status.toString())
//        }
//    }

    private val KeySetting: ByteArray? = ubyteArrayOf(0x78U, 0x77U, 0x88U, 0x69U).toByteArray()

    /*
return status
0: card not found
1: operation succeeded
2: authentication failed
3: card write failed
*/
    private fun WriteIntent2(
        tag: Tag,
        keyA: String,
        keyB: String,
        CardNo: String,
        CardStatus: Int
    ): Int {
        val mfc = MifareClassic.get(tag) as MifareClassic
        var blockno = 20

        mfc.connect()
        if (mfc.isConnected) {
            var KeyB: ByteArray? =
                ubyteArrayOf(0xFFU, 0xFFU, 0xFFU, 0xFFU, 0xFFU, 0xFFU).toByteArray()
            if (CardStatus == 1) {
                KeyB = keyB.toByteArray()
            }
            var sectorno = mfc.blockToSector(blockno)
            var keyblockno = mfc.sectorToBlock(sectorno + 1) - 1

            if (mfc.authenticateSectorWithKeyB(sectorno, KeyB) == true) {
                val KeyData: ByteArray? = ubyteArrayOf(
                    0xFFU,
                    0xFFU,
                    0xFFU,
                    0xFFU,
                    0xFFU,
                    0xFFU,
                    0x78U,
                    0x77U,
                    0x88U,
                    0x69U,
                    0xFFU,
                    0xFFU,
                    0xFFU,
                    0xFFU,
                    0xFFU,
                    0xFFU
                ).toByteArray()
                if (KeyData != null) {
                    keyA.toByteArray().copyInto(KeyData, 0, 0, 6)
                }
                if (KeyData != null) {
                    if (KeySetting != null) {
                        KeySetting.copyInto(KeyData, 6, 0, 4)
                    }
                }
                if (KeyData != null) {
                    keyB.toByteArray().copyInto(KeyData, 10, 0, 6)
                }

                var CrdNo = CardNo
                var len = CardNo.length
                if (len > 16) {
                    CrdNo = CardNo.substring(0, 16)
                }

                while (CrdNo.length < 16) {
                    CrdNo = CrdNo + " "
                }

                try {
                    mfc.writeBlock(blockno, CrdNo.toByteArray())
                    mfc.writeBlock(keyblockno, KeyData)
                } catch (ex: Exception) {
                    return 3
                }
            } else {
                return 2
            }

            mfc.close()
        } else {
            return 0
        }
        return 1
    }
}