package com.example.desafiotopdarkcliente.ui

import adaptadores.AdaptadorRvMisionesEnAsignarmisiones
import adaptadores.AdaptadorRvPilotosEnAsignarmision
import android.annotation.SuppressLint
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.desafiotopdarkcliente.R
import com.example.desafiotopdarkcliente.databinding.FragmentFragmentoPilotoEnAsignarmisionBinding
import modelo.MostrarPiloto

class FragmentoPilotoEnAsignarmision : Fragment() {

    private var _binding: FragmentFragmentoPilotoEnAsignarmisionBinding? = null
    private val binding get() = _binding!!

    private val fragmentoPilotoEnAsignarMisionVM : FragmentoPilotoEnAsignarmisionViewModel by activityViewModels()

    var datosRepresentar: ArrayList<MostrarPiloto> = ArrayList()
    lateinit var customAdapter: AdaptadorRvPilotosEnAsignarmision

    companion object {
        fun newInstance() = FragmentoPilotoEnAsignarmision()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fragmentoPilotoEnAsignarMisionVM.getPilotosVM()
        fragmentoPilotoEnAsignarMisionVM.myResponseListP.observe(this){ pilotos ->
            if (!pilotos.isEmpty()) {
                datosRepresentar.clear()
                for (piloto in pilotos){
                    datosRepresentar.add(MostrarPiloto(piloto.id, piloto.nombre, piloto.nivel))
                }
                customAdapter.updateData(datosRepresentar)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        _binding = FragmentFragmentoPilotoEnAsignarmisionBinding.inflate(inflater,container, false)
        val root: View = binding.root
        return root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Ocultar la flecha de retroceso en la barra de herramientas
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)

        //Inicializar el RecyclerView y el adaptador
        setupRecyclerView()

        fragmentoPilotoEnAsignarMisionVM.myResponseList.observe(viewLifecycleOwner){ listaPilotos ->
            datosRepresentar.clear()
            datosRepresentar.addAll(listaPilotos)
            customAdapter.notifyDataSetChanged()

        }

    }

    private fun setupRecyclerView(){
        val linearLayoutManager = LinearLayoutManager(requireContext())
        binding.listaPilotosEnAsignarMision.layoutManager = linearLayoutManager
        customAdapter = AdaptadorRvPilotosEnAsignarmision(datosRepresentar, requireContext(), fragmentoPilotoEnAsignarMisionVM)
        binding.listaPilotosEnAsignarMision.adapter = customAdapter
    }

}