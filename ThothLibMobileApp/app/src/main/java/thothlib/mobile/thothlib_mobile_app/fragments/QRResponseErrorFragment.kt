package thothlib.mobile.thothlib_mobile_app.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import org.json.JSONObject
import thothlib.mobile.thothlib_mobile_app.R
import java.lang.ClassCastException

class QRResponseErrorFragment : Fragment() {

    private lateinit var listener: OnReserveMoreErrorPage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_qr_response_error, container, false)

        val reservedBook = arguments?.getString("qrCodeResponse") as String

        val reservedBookSON = JSONObject(reservedBook)

        if (reservedBookSON.get("status") == "RESERVADO") {
            view.findViewById<TextView>(R.id.title).text = getString(R.string.text_screen_retirada)
            view.findViewById<TextView>(R.id.description).text = getString(R.string.text_screen_message_retirada)
        } else {
            view.findViewById<TextView>(R.id.title).text = getString(R.string.text_screen_devolucao)
            view.findViewById<TextView>(R.id.description).text = getString(R.string.text_screen_message_devolucao)
        }

        view.findViewById<Button>(R.id.tentar_novamente).setOnClickListener {
            listener.tryAgainErrorPage()
        }

        view.findViewById<Button>(R.id.contato).setOnClickListener {
            listener.contactUsErrorPage()
        }

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is OnReserveMoreErrorPage) {
            listener = context
        } else {
            throw ClassCastException("$context must implemented")
        }
    }

    interface OnReserveMoreErrorPage {
        fun tryAgainErrorPage()
        fun contactUsErrorPage()
    }

}