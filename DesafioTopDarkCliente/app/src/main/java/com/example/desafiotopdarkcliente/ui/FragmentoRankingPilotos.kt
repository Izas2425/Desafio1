package com.example.desafiotopdarkcliente.ui

import adaptadores.AdaptadorRvPilotos
import adaptadores.AdaptadorRvRankingPilotos
import android.annotation.SuppressLint
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.desafiotopdarkcliente.R
import com.example.desafiotopdarkcliente.databinding.FragmentFragmentoRankingPilotosBinding
import com.example.desafiotopdarkcliente.databinding.FragmentFragmentoVPilotosBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import modelo.MostrarPiloto

class FragmentoRankingPilotos : Fragment() {
    private var _binding: FragmentFragmentoRankingPilotosBinding?= null
    private val binding get() = _binding!!

    private val fragmentoVRankingViewModel: FragmentoRankingPilotosViewModel by viewModels()

    // Para la RV
    var datosRepresentar: ArrayList<MostrarPiloto> = ArrayList()
    lateinit var customAdapter: AdaptadorRvRankingPilotos

    companion object {
        fun newInstance() = FragmentoRankingPilotos()
    }

    private val viewModel: FragmentoRankingPilotosViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fragmentoVRankingViewModel.getPilotosRankingVM()
        fragmentoVRankingViewModel.myResponseListP.observe(this){pilotos ->
            if (!pilotos.isEmpty()) {
                datosRepresentar.clear()
                for (user in pilotos){
                    datosRepresentar.add(MostrarPiloto( user.id, user.nombre.toString(), user.nivel.toString()))
                }
                customAdapter.updateData(datosRepresentar)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFragmentoRankingPilotosBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //Inicializar el RecyclerView y el adaptador
        setupRecyclerView()

        fragmentoVRankingViewModel.myResponseList.observe(viewLifecycleOwner){ listaPilotosRanking ->
            datosRepresentar.clear()
            datosRepresentar.addAll(listaPilotosRanking)
            customAdapter.notifyDataSetChanged()
        }

    }

    private fun setupRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(requireContext())
        binding.listaRankingPRecycler.layoutManager = linearLayoutManager
        //Le pasamos también el viewModelMain para poder pasar datos desde el fragmento B a la activity.
        customAdapter = AdaptadorRvRankingPilotos( datosRepresentar, requireContext(), fragmentoVRankingViewModel )
        binding.listaRankingPRecycler.adapter = customAdapter
    }
}