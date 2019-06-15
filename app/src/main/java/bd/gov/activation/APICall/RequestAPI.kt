package bd.gov.activation.APICall

import android.util.Log
import java.net.URL

class RequestAPI(val url: String) {

    fun run(): String {
        val forecastJsonStr = URL(url).readText()
        Log . d (javaClass.simpleName, forecastJsonStr)
        return forecastJsonStr
    }
}