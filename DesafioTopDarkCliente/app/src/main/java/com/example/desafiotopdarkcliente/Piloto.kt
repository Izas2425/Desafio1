package com.example.desafiotopdarkcliente

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.DifferentialMotionFlingController
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.desafiotopdarkcliente.databinding.ActivityPilotoBinding

class Piloto : AppCompatActivity() {

    lateinit var binding: ActivityPilotoBinding

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

//        setContentView(R.layout.activity_piloto)

        binding = ActivityPilotoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "Piloto"

        // Para el control de los fragmentos dentro del "contenedor" mi miFragContainerP
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.miFragContainerP) as NavHostFragment
        navController = navHostFragment.navController

        // Configura la Toolbar como ActionBar
        setSupportActionBar(binding.tbPilotos)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        // Cuando pinche vuelva hace atrás
        binding.tbPilotos.setNavigationOnClickListener {
            val navController = findNavController(R.id.miFragContainerP)
            if (!navController.navigateUp()){
                finish()
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}