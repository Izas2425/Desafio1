package com.example.desafiotopdarkcliente.ui

import adaptadores.AdaptadorRvNavesEnMisiones
import android.annotation.SuppressLint
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.desafiotopdarkcliente.R
import com.example.desafiotopdarkcliente.databinding.FragmentFragmentoNavesEnMisionesBinding
import modelo.MostrarNave

class FragmentoNavesEnMisiones : Fragment() {

    private var _binding : FragmentFragmentoNavesEnMisionesBinding? = null
    private val binding get() = _binding!!

    private val fragmentoNavesEnMisionesViewModel : FragmentoNavesEnMisionesViewModel by viewModels()

    var datosRepresentar: ArrayList<MostrarNave> = ArrayList()
    lateinit var customAdapter: AdaptadorRvNavesEnMisiones


    companion object {
        fun newInstance() = FragmentoNavesEnMisiones()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fragmentoNavesEnMisionesViewModel.getNavesVM()
        fragmentoNavesEnMisionesViewModel.myResponseListN.observe(this){ naves ->
            if (!naves.isEmpty()){
                datosRepresentar.clear()
                for (nave in naves){
                    datosRepresentar.add(MostrarNave(nave.matricula.toString(), nave.tipo.toString()))
                }
                customAdapter.updateData(datosRepresentar)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFragmentoNavesEnMisionesBinding.inflate(inflater,container, false)
        val root: View = binding.root
        return root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Inicializar el RecyclerView y el adaptador
        setupRecyclerView()

        fragmentoNavesEnMisionesViewModel.myResponseList.observe(viewLifecycleOwner){ listaNaves ->
            datosRepresentar.clear()
            datosRepresentar.addAll(listaNaves)
            customAdapter.notifyDataSetChanged()
        }
    }


    private fun setupRecyclerView(){
        val linearLayoutManager = LinearLayoutManager(requireContext())
        binding.listaNavesEnMisiones.layoutManager = linearLayoutManager
        customAdapter = AdaptadorRvNavesEnMisiones(datosRepresentar, requireContext(), fragmentoNavesEnMisionesViewModel)
        binding.listaNavesEnMisiones.adapter = customAdapter
    }


}