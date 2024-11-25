package com.example.desafiotopdarkcliente.ui

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

class FragmentoAddMision : Fragment() {

    private var _binding: FragmentFragmentoAddMisionBinding? = null
    private val binding get() = _binding!!

    private val viewModelCompartir: FragmentoNavesEnMisionesViewModel by activityViewModels()

    companion object {
        fun newInstance() = FragmentoAddMision()
    }

    private val viewModel: FragmentoAddMisionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFragmentoAddMisionBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
            }
        }
//        binding.tvMatriculaNaveM.text= viewModelCompartir.naveSeleccionada.value?.matricula
//        Log.e("Izaskun", "tvMatriculaNaveM en AddMision${viewModelCompartir.naveSeleccionada.value?.matricula}")
    }

}