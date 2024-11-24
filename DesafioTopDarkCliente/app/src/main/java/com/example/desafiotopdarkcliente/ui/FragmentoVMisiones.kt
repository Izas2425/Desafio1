package com.example.desafiotopdarkcliente.ui

import adaptadores.AdaptadorRvMisiones
import adaptadores.AdaptadorRvNaves
import android.annotation.SuppressLint
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import api.MisionViewModel
import api.NaveViewModel
import com.example.desafiotopdarkcliente.R
import com.example.desafiotopdarkcliente.databinding.FragmentFragmentoVMisionesBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import modelo.MostrarMision

class FragmentoVMisiones : Fragment() {

    private var _binding: FragmentFragmentoVMisionesBinding? = null
    private val binding get() = _binding!!

    private lateinit var misionViewModel: MisionViewModel

    // ViewModel Fragmento
    private val fragmentoVMisionesViewModel : FragmentoVMisionesViewModel by viewModels()
    private lateinit var naveViewModel: NaveViewModel



    // Para la RV
    var datosRepresentar: ArrayList<MostrarMision> = ArrayList()
    lateinit var customAdapter: AdaptadorRvMisiones

    companion object {
        fun newInstance() = FragmentoVMisiones()
    }

    private val viewModel: FragmentoVMisionesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        misionViewModel = ViewModelProvider(this)[MisionViewModel::class.java]
        naveViewModel = ViewModelProvider(this)[NaveViewModel::class.java]

        fragmentoVMisionesViewModel.getMisionesVM()
        fragmentoVMisionesViewModel.myResponseListM.observe(this){ misiones ->
            if(!misiones.isEmpty()){
                datosRepresentar.clear()
                for(mis in misiones){
                    datosRepresentar.add(MostrarMision(mis.idmision, mis.nombre.toString(), mis.experiencia.toString()))
                }
                customAdapter.updateData(datosRepresentar)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFragmentoVMisionesBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }
    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializa el RecyclerView y el adaptador
        setupRecyclerView()

        fragmentoVMisionesViewModel.myResponseList.observe(viewLifecycleOwner){ listaMisiones ->
            datosRepresentar.clear()
            datosRepresentar.add(listaMisiones)
            customAdapter.notifyDataSetChanged()
        }

        val fabAddMision: FloatingActionButton = view.findViewById(R.id.fabAddMision)
        fabAddMision.setOnClickListener {
            findNavController().navigate(R.id.action_VMisiones_to_AddMision)
        }
    }

    private fun setupRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(requireContext())
        binding.listaMisionesRecycler.layoutManager = linearLayoutManager
        //Le pasamos también el viewModel de naves  para poder pasar datos desde el fragmento B a la activity.
        customAdapter = AdaptadorRvMisiones( datosRepresentar, requireContext(), fragmentoVMisionesViewModel, naveViewModel )
        binding.listaMisionesRecycler.adapter = customAdapter
    }

}