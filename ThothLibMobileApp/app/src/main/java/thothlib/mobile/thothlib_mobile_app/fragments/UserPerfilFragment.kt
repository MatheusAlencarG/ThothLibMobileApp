package thothlib.mobile.thothlib_mobile_app.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import thothlib.mobile.thothlib_mobile_app.R
import thothlib.mobile.thothlib_mobile_app.infoClass.Livro
import thothlib.mobile.thothlib_mobile_app.infoClass.Studant
import thothlib.mobile.thothlib_mobile_app.services.ThothLibs
import thothlib.mobile.thothlib_mobile_app.infoClass.User

class UserPerfilFragment : Fragment() {

    lateinit var tvNome: TextView
    lateinit var tvCpf: TextView
    lateinit var tvEmail: TextView
    lateinit var id:SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        id = requireActivity().getSharedPreferences("idUser", AppCompatActivity.MODE_PRIVATE)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_user_perfil, container, false)

        tvNome = view.findViewById(R.id.tv_user_name)
        tvCpf = view.findViewById(R.id.tv_user_cpf)
        tvEmail = view.findViewById(R.id.tv_user_email)

        consultarUsuario()


        return view
    }

    fun consultarUsuario() {

        val idUsuario = id.getInt("id", 0)

        val getUsuario = ThothLibs.criar().getUsuario(idUsuario)

        getUsuario.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    val usuario = response.body()
                    tvNome.text = "${usuario?.nome}"
                    tvCpf.text = "${usuario?.cpf}"
                    tvEmail.text = "${usuario?.email}"
                    // renderBookCards("${usuario?.livrosReservados.}")
                    Toast.makeText (context, "usuario aqui: ${usuario}", Toast.LENGTH_SHORT).show()

                } else {
                    Toast.makeText (context, "Não encontrado", Toast.LENGTH_SHORT).show()
                }

                Toast.makeText(context, "response aqui ${response.body()}", Toast.LENGTH_SHORT).show()

            }
            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(context, "Erro na API ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }


}