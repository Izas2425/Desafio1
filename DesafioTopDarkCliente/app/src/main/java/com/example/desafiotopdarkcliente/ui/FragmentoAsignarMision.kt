package com.example.desafiotopdarkcliente.ui

import android.annotation.SuppressLint
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.desafiotopdarkcliente.R
import com.example.desafiotopdarkcliente.databinding.FragmentFragmentoAsignarMisionBinding
import modelo.Misionasignada
import modelo.MostrarMision

class FragmentoAsignarMision : Fragment() {

    private var _binding: FragmentFragmentoAsignarMisionBinding? = null
    private val binding get() = _binding!!

    private val viewModelCompartirMision: FragmentoMisionEnAsignamisionViewModel by activityViewModels()
    private val viewModelCompartirPilotos: FragmentoPilotoEnAsignarmisionViewModel by activityViewModels()
    private val viewModelAsignarMision: FragmentoAsignarMisionViewModel by viewModels()

    private lateinit var misionasignada: Misionasignada


    var nombreMision: String =""
    var experiencia: Int = 0
    var estado: String=""

    companion object {
        fun newInstance() = FragmentoAsignarMision()
    }

    private val viewModel: FragmentoAsignarMisionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFragmentoAsignarMisionBinding.inflate(inflater, container, false)
       val root: View = binding.root
        return root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        misionasignada = Misionasignada()

        binding.tvMisionElegidaAsignarMision.text = ""
        binding.tvPilotoElegidoAsignarMision.text = ""

        // Para el spinner
        val adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.estado_mision_asignada,
            android.R.layout.simple_spinner_item
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spEstadoAsignarMision.adapter = adapter

        binding.btnElegirMisionEnAsignarMisiones.setOnClickListener {
            viewModelCompartirMision.misionSeleccionada.value = null
            val fragmentoMisiones = FragmentoMisionEnAsignamision()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.miFragContainer, fragmentoMisiones)
                .addToBackStack(null)
                .commit()
        }

        viewModelCompartirMision.misionSeleccionada.observe(viewLifecycleOwner){mision ->
            mision?.let {
                binding.tvMisionElegidaAsignarMision.text = mision.nombre

            }
            if (mision != null) {
                nombreMision = mision.nombre.toString()
                viewModelCompartirMision.getMisionVM(mision.idmision!!)
                experiencia = mision.experiencia!!
            }

        }

        binding.btnElegirPilotoEnAsignarMisiones.setOnClickListener {
            viewModelCompartirPilotos.pilotosSeleccionados.value = null
            val fragmentoPilotos = FragmentoPilotoEnAsignarmision()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.miFragContainer, fragmentoPilotos)
                .addToBackStack(null)
                .commit()
        }

        viewModelCompartirPilotos.pilotosSeleccionados.observe(viewLifecycleOwner){ pilotos ->
            pilotos?.let{
                if(it.isNotEmpty()){
                    val nombresPilotos = it.joinToString(", "){piloto ->
                        piloto?.nombre ?: "Desconocido"
                    }
                    binding.tvPilotoElegidoAsignarMision.text = "$nombresPilotos"
                }

            }
        }

        binding.btnAceptarAsignaMision.setOnClickListener{
            if (validarCampos()){
                val pilotosSeleccionados = viewModelCompartirPilotos.pilotosSeleccionados.value
                Log.d("Izaskun", "Pilotos seleccionados: $pilotosSeleccionados")
                if (!pilotosSeleccionados.isNullOrEmpty()){
                    for (piloto in pilotosSeleccionados){
                        piloto?.let {
                            val nuevaMisionAsignada = Misionasignada(
                                id = 0,
                                idusuario = piloto.id,
                                idmision = viewModelCompartirMision.misionSeleccionada.value?.idmision,
                                estado = binding.spEstadoAsignarMision.selectedItem.toString())

                            Log.d("Izaskun", "Creando misión asignada (botón Aceptar): $nuevaMisionAsignada")
//                            misionasignada.id = 0
//                            misionasignada.idusuario = piloto.id
//                            misionasignada.idmision = viewModelCompartirMision.misionSeleccionada.value?.idmision
//                            misionasignada.estado = binding.spEstadoAsignarMision.selectedItem.toString()

                            try {
                                viewModelAsignarMision.addVM(nuevaMisionAsignada)
                            }catch (e: Exception) {
                                Log.e("Error", "Error al asignar la misión: ${e.message}")
                                Toast.makeText(requireContext(), "Hubo un error al asignar la misión", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                    //Vuelve al fragmento anterior
                    requireActivity().onBackPressed()
                }

            }

        }

        binding.btnCancelarAsignaMision.setOnClickListener {
            //Vuelve al fragmento anterior
            requireActivity().onBackPressed()
        }
    }

    private fun validarCampos():Boolean{
        return  if (binding.tvMisionElegidaAsignarMision.text.toString().isEmpty() ||
                    binding.tvPilotoElegidoAsignarMision.text.toString().isEmpty()){
            Toast.makeText(requireContext(), "Rellena los campos", Toast.LENGTH_SHORT).show()
            false
        }else{
            true
        }
    }

}