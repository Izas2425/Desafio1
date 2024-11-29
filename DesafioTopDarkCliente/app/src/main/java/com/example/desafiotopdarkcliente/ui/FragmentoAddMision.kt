package com.example.desafiotopdarkcliente.ui

import android.annotation.SuppressLint
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.desafiotopdarkcliente.R
import com.example.desafiotopdarkcliente.databinding.FragmentFragmentoAddMisionBinding
import com.example.desafiotopdarkcliente.databinding.FragmentFragmentoAddNaveBinding
import modelo.Bombardero
import modelo.Combate
import modelo.Mision
import modelo.Vuelo

class FragmentoAddMision : Fragment() {

    private var _binding: FragmentFragmentoAddMisionBinding? = null
    private val binding get() = _binding!!

    private val viewModelCompartir: FragmentoNavesEnMisionesViewModel by activityViewModels()

    private val fragmentoVNavesViewModel : FragmentoVNavesViewModel by viewModels()

    private val viewModelMision: FragmentoAddMisionViewModel by viewModels()

    private lateinit var mision: Mision
    private lateinit var vuelo: Vuelo
    private lateinit var combate: Combate
    private lateinit var bombardero: Bombardero

    var matriculaNave: String =""
    var carga: Boolean = false
    var pasajeros: Boolean = false
    var tipo: String = ""
    var ultimoId: Int = 0


    companion object {
        fun newInstance() = FragmentoAddMision()
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFragmentoAddMisionBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mision = Mision()

        binding.tvMatriculaNaveM.text = ""
        carga = false
        pasajeros = false

        binding.btnMatriculaNaveM.setOnClickListener {

            viewModelCompartir.naveSeleccionada.value = null

            val fragmentoNaves = FragmentoNavesEnMisiones()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.miFragContainer, fragmentoNaves)
                .addToBackStack(null)
                .commit()

        }

        viewModelCompartir.naveSeleccionada.observe(viewLifecycleOwner){ nave ->
            nave?.let{
                binding.tvMatriculaNaveM.text = nave.matricula
                Log.e("Izaskun", "tvMatriculaNaveM en AddMision${viewModelCompartir.naveSeleccionada.value?.matricula}")
                matriculaNave = nave.matricula.toString()

                fragmentoVNavesViewModel.getNaveVM(matriculaNave)

            }
        }


        fragmentoVNavesViewModel.myResponse.observe(viewLifecycleOwner){ nave ->
            nave?.let {
                tipo = nave.tipo.toString()
                carga = nave.carga
                pasajeros = nave.pasajeros
                Log.e("Izaskun", "tipo de nave  despues de observar myResponse: ${tipo}")
                actualizarCamposSegunTipo()
            }
        }

        viewModelMision.ultimaMisionMv()
        viewModelMision.ultimoId.observe(viewLifecycleOwner){ misionId ->
            if (misionId != null) {
                ultimoId = misionId.toInt()
            }
        }


    }

    fun actualizarCamposSegunTipo(){
        Log.e("Izaskun", "tipo de nave en actualizarCampoSegunTipo:  ${tipo}")
        when (tipo) {
            "Combate" -> {
                binding.txtCazasM.isEnabled = true
                binding.txtDuracionM.isEnabled = false
                binding.txtObjetivosM.isEnabled = false
                binding.cbCargaAddM.isEnabled = false
                binding.cbPasajerosAddM.isEnabled = false
            }
            "Vuelo" -> {
                binding.txtCazasM.isEnabled = false
                binding.txtDuracionM.isEnabled = true
                binding.txtObjetivosM.isEnabled = false
                binding.cbCargaAddM.isEnabled = true
                binding.cbPasajerosAddM.isEnabled = true
            }
            else -> { // Bombardero
                binding.txtCazasM.isEnabled = false
                binding.txtDuracionM.isEnabled = false
                binding.txtObjetivosM.isEnabled = true
                binding.cbCargaAddM.isEnabled = true
                binding.cbPasajerosAddM.isEnabled = true
            }
        }
    }

}