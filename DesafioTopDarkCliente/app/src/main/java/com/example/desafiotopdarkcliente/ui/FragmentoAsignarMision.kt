package com.example.desafiotopdarkcliente.ui

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.desafiotopdarkcliente.R
import com.example.desafiotopdarkcliente.databinding.FragmentFragmentoAsignarMisionBinding
import modelo.Misionasignada
import modelo.MostrarMision

class FragmentoAsignarMision : Fragment() {

    private var _binding: FragmentFragmentoAsignarMisionBinding? = null
    private val binding get() = _binding!!

    private val viewModelCompartirMision: FragmentoMisionEnAsignamisionViewModel by activityViewModels()
    private val viewModelMision: FragmentoAsignarMisionViewModel by viewModels()

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvMisionElegidaAsignarMision.text = ""
        binding.tvPilotoElegidoAsignarMision.text = ""

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
    }

}