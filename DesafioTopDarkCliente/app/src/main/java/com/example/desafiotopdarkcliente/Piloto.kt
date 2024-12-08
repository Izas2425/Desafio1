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
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.desafiotopdarkcliente.databinding.ActivityPilotoBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

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

        val navView: BottomNavigationView = binding.btnNavPilotos

        val appBarConfiguration = AppBarConfiguration(
            setOf( R.id.navegation_fragmento_misiones_asignadas, R.id.navegation_fragmento_misiones_superadas, R.id.navegation_fragmento_misiones_no_superadas)
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener{_, destination, _ ->
            when(destination.id){
                R.id.navegation_fragmento_misiones_asignadas ->{
                    supportActionBar?.title = "Misiones asignadas"
                }
                R.id.navegation_fragmento_misiones_superadas ->{
                    supportActionBar?.title = "Misiones superadas"
                }
                R.id.navegation_fragmento_misiones_no_superadas ->{
                    supportActionBar?.title = "Misiones no superadas"
                }
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.miFragContainerP)
        return navController.navigateUp() || super.onSupportNavigateUp()

    }
}