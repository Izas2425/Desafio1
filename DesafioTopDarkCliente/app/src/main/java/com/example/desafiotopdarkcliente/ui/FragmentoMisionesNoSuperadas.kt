package com.example.desafiotopdarkcliente.ui

import adaptadores.AdaptadorRvMisionesAsignadas
import adaptadores.AdaptadorRvMisionesNoSuperadas
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import api.MisionViewModel
import com.example.desafiotopdarkcliente.R
import com.example.desafiotopdarkcliente.databinding.FragmentFragmentoMisionesAsignadasBinding
import com.example.desafiotopdarkcliente.databinding.FragmentFragmentoMisionesNoSuperadasBinding
import modelo.MostrarMision
import parametros.Parametros

class FragmentoMisionesNoSuperadas : Fragment() {
    private var _binding: FragmentFragmentoMisionesNoSuperadasBinding?= null
    private val  binding get() = _binding!!

    private lateinit var misionViewModel: MisionViewModel

    private val fragmentoMisionesNoSuperadas: FragmentoMisionesNoSuperadasViewModel by viewModels()
    private val fragmentoVMisionesViewModel : FragmentoVMisionesViewModel by viewModels()

    // Para representar la RV
    var datosRepresentar: ArrayList<MostrarMision> = ArrayList()
    lateinit var customAdapter: AdaptadorRvMisionesNoSuperadas

    companion object {
        fun newInstance() = FragmentoMisionesNoSuperadas()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        misionViewModel = ViewModelProvider(this)[MisionViewModel::class.java]

        fragmentoMisionesNoSuperadas.getMisionesNoSuperadasVM(Parametros.usuarioLogeado!!)
        fragmentoMisionesNoSuperadas.myResponseListA.observe(this){misionesAsignadas ->
            if(!misionesAsignadas.isEmpty()){
                datosRepresentar.clear()
                for (mis in misionesAsignadas){
                    misionViewModel.getMisionVM(mis.idmision!!)

                }

            }
        }

        misionViewModel.myResponseM.observe(this){mision ->
            mision?.let {
                if (datosRepresentar.none { it.idmision == mision.idmision }){
                    datosRepresentar.add(MostrarMision(mision.idmision, mision.nombre.toString(), mision.experiencia))
                    customAdapter.updateData(datosRepresentar)
                }

            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFragmentoMisionesNoSuperadasBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        fragmentoVMisionesViewModel.myResponseList.observe(viewLifecycleOwner){ listaMisiones ->
            datosRepresentar.clear()
            datosRepresentar.add(listaMisiones)
            customAdapter.notifyDataSetChanged()
        }
    }

    private fun setupRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(requireContext())
        binding.listaMisionesNoSuperadas.layoutManager = linearLayoutManager
        //Le pasamos también el viewModel de naves  para poder pasar datos desde el fragmento B a la activity.
        customAdapter = AdaptadorRvMisionesNoSuperadas( datosRepresentar, requireContext(), fragmentoMisionesNoSuperadas, fragmentoVMisionesViewModel)
        binding.listaMisionesNoSuperadas.adapter = customAdapter
    }
}