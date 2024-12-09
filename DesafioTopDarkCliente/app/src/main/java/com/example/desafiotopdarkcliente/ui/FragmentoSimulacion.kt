package com.example.desafiotopdarkcliente.ui

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.desafiotopdarkcliente.R
import com.example.desafiotopdarkcliente.databinding.FragmentFragmentoSimulacionBinding

class FragmentoSimulacion : Fragment() {

    private var _binding: FragmentFragmentoSimulacionBinding? = null
    private val binding get() = _binding!!

    private val viewmodelSimulacion : FragmentoSimulacionViewModel by viewModels()
    private val viewModelMisionesAsignadas : FragmentoMisionesAsignadasViewModel by activityViewModels()
    private val viewModelMisiones : FragmentoVMisionesViewModel by viewModels()
    private val viewModelNaves: FragmentoVNavesViewModel by viewModels()

    var idMisionSeleccionada: Int = 0
    var matriculaNave: String =""
    var tipoNave:String = ""

    companion object {
        fun newInstance() = FragmentoSimulacion()
    }

     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFragmentoSimulacionBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModelMisionesAsignadas.misionSeleccionada.observe(viewLifecycleOwner){ mision ->
            mision?.let {
                idMisionSeleccionada = mision.idmision!!
                Toast.makeText(requireContext(), "id mision seleccionad es ${idMisionSeleccionada}", Toast.LENGTH_SHORT).show()

                // obtener la mision
                viewModelMisiones.getMisionVM(idMisionSeleccionada)

            }
        }

        viewModelMisiones.myResponseM.observe(viewLifecycleOwner) { mision ->
            mision?.let {
                // Extrae la matrícula de la nave de la misión
                matriculaNave = mision.matriculanave!!
                Toast.makeText(requireContext(), "Matrícula de la nave es: ${matriculaNave}", Toast.LENGTH_SHORT).show()

                viewModelNaves.getNaveVM(matriculaNave!!)
            }
        }

        viewModelNaves.myResponse.observe(viewLifecycleOwner){ nave ->
            nave?.let {
                tipoNave = nave.tipo!!
                Toast.makeText(requireContext(), "Matrícula de la nave es: ${tipoNave}", Toast.LENGTH_SHORT).show()
            }
        }



    }


}