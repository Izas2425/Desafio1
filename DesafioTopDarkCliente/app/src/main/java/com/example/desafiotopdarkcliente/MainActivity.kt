package com.example.desafiotopdarkcliente

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import api.UsuarioViewModel
import com.example.desafiotopdarkcliente.databinding.ActivityMainBinding
import modelo.UsuarioLogIn
import parametros.Parametros

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var usuarioViewModel: UsuarioViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableEdgeToEdge()
       // setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        usuarioViewModel = ViewModelProvider(this).get(UsuarioViewModel::class.java)


        usuarioViewModel.myResponse.observe(this, Observer { user ->
            user?.let {
              //  val intent = Intent(this, VentanaVader::class.java)
             //   startActivity(intent)
                Parametros.usuarioLogeado = user.id
                limpiar()
                usuarioViewModel.limpiarRespuesta()
                if (user.role == "Vader"){
                    Toast.makeText(this, "se ha logeado Vader", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, Vader::class.java)
                    startActivity(intent)
                }
                else{
                    if (user.role == "Piloto")
                     Toast.makeText(this, "se ha logeado un piloto", Toast.LENGTH_SHORT).show()
                }
            }
        })
        usuarioViewModel.errorCode.observe(this, Observer { code ->
            if (code != null) {
                when (code) {
                    200 -> Toast.makeText(this, "Sesion Iniciada", Toast.LENGTH_SHORT).show()
                    400 -> Toast.makeText(this,"Error 400: Contraseña incorrecta", Toast.LENGTH_SHORT).show()
                    404 -> Toast.makeText(this, "Error 404: El usuario no existe", Toast.LENGTH_SHORT).show()
                    else -> Toast.makeText(this, "Error Desconocido", Toast.LENGTH_SHORT).show()
                }
                usuarioViewModel.limpiarError()
            }
        })

        binding.btnLogin.setOnClickListener {
            if (binding.tfUsuario.editText?.text.toString().isEmpty() ||  binding.tfPassword.editText?.text.toString().isEmpty()) {
                Toast.makeText(this, "Rellena todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                usuarioViewModel.loginVM(UsuarioLogIn(binding.tfUsuario.editText?.text.toString(), binding.tfPassword.editText?.text.toString()))
                Log.e("Izaskun", binding.tfPassword.editText?.text.toString())
            }
        }

    }

    fun limpiar(){
        binding.tfUsuario.editText?.text?.clear()
        binding.tfPassword.editText?.text?.clear()
    }
}