package com.example.desafiotopdarkcliente

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.desafiotopdarkcliente.databinding.ActivityVaderBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class Vader : AppCompatActivity() {

    lateinit var binding: ActivityVaderBinding

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        //setContentView(R.layout.activity_vader)

        binding = ActivityVaderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Para el control de los fragmentos dentro del "contenedor" miFragContainer
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.miFragContainer) as NavHostFragment
        navController = navHostFragment.navController

        // Configura la ToolBar como ActionBar
        setSupportActionBar(binding.tbVader)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        // Cuando se pinche vuelva hacia atras
        binding.tbVader.setNavigationOnClickListener {
            val navController = findNavController(R.id.miFragContainer)
            if (!navController.navigateUp()){
                finish()
            }

        }

        //*********** Para la Navigation Bottom Bar *****************
        val navView: BottomNavigationView = binding.btnNavVader
        //Pasamos cada ID del menú para que sean tenidos en cuenta como la selección hecha cada vez que pulsamos en uno de ellos.
        //Recuerda que en el xml de main definimos esto 'app:menu="@menu/mi_menu"' para la bottom navigation bar y asociarla.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navegation_fragmento_v_pilotos, R.id.navegation_fragmento_v_naves, R.id.navegation_fragmento_v_misiones
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        //*********** Para la Navigation Bottom Bar *****************
        //En el archivo mi_nav.xml los id de los fragmentos en el nav_graph debe coincidir con los id de los elementos en mi_menu.xml.
        //Es decir para que funcione esto de arriba los fragmentos se llamarán:
        // -- en mi_nav: navigation_fragmento_a
        // -- en menu: navigation_fragmento_a
        //Lo de aquí abajo es para jugar con el título de la Toolbar. Se dispara automáticamente cuando cambia la navegación de un Fragmento a otro.
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.navegation_fragmento_v_pilotos -> {
                    supportActionBar?.title = "Pilotos"
                    //supportActionBar?.hide()
                }
                R.id.navegation_fragmento_v_naves -> {
                    //supportActionBar?.hide()
                    supportActionBar?.title = "Naves"
                }
                R.id.navegation_fragmento_v_misiones -> {
                    //supportActionBar?.hide()
                    supportActionBar?.title = "Misiones"
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
        val navController = findNavController(R.id.miFragContainer)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}