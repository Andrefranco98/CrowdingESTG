package com.tp3.crowdingestg

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.tp3.crowdingestg.api.EndPoints
import com.tp3.crowdingestg.api.OutputPost
import com.tp3.crowdingestg.api.ServiceBuilder
import kotlinx.android.synthetic.main.activity_registar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Registar : AppCompatActivity() {
    private var userid : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registar)

    }

    fun addRegistar(view: View) {

        val user = user.text.toString().trim()
        val password = password.text.toString().trim()

        val request = ServiceBuilder.buildService(EndPoints::class.java)
        val call = request.postRegister(user, password)


        call.enqueue(object : Callback<OutputPost> {

            override fun onResponse(call: Call<OutputPost>, response: Response<OutputPost>) {

                if (response.isSuccessful) {
                    val intent = Intent(this@Registar, Login::class.java)
                    Toast.makeText(this@Registar, "Novo Marcador inserido com sucesso", Toast.LENGTH_SHORT).show()
                    startActivity(intent)

                }
            }
            override fun onFailure(call: Call<OutputPost>, t: Throwable) {
                Toast.makeText(this@Registar, "Erro na inserção", Toast.LENGTH_SHORT).show()
            }
        })
    }
}