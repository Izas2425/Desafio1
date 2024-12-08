package com.example.desafiotopdarkcliente.ui


import adaptadores.AdaptadorRvMisionesAsignadas
import adaptadores.AdaptadorRvMisionesNoSuperadas
import android.annotation.SuppressLint

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
import modelo.MostrarMision
import parametros.Parametros

class FragmentoMisionesAsignadas : Fragment() {
    private var _binding: FragmentFragmentoMisionesAsignadasBinding? = null
    private val binding get() = _binding!!

    private lateinit var misionViewModel: MisionViewModel

    private val fragmentoMisionesAsignadas : FragmentoMisionesAsignadasViewModel by viewModels()
    private val fragmentoVMisionesViewModel : FragmentoVMisionesViewModel by viewModels()

    // Para representar la RV
    var datosRepresentar: ArrayList<MostrarMision> = ArrayList()
    lateinit var customAdapter: AdaptadorRvMisionesAsignadas


    companion object {
        fun newInstance() = FragmentoMisionesAsignadas()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        misionViewModel = ViewModelProvider(this)[MisionViewModel::class.java]

        fragmentoMisionesAsignadas.getMisionesAsignadasVM(Parametros.usuarioLogeado!!)
        fragmentoMisionesAsignadas.myResponseListA.observe(this) { misionesAsignadas ->
            if (!misionesAsignadas.isEmpty()) {
                datosRepresentar.clear()
                for (mis in misionesAsignadas) {
                    misionViewModel.getMisionVM(mis.idmision!!)
                }
            }
        }
        misionViewModel.myResponseM.observe(this) { mision ->
            mision?.let {
                if (datosRepresentar.none { it.idmision == mision.idmision }) {
                    datosRepresentar.add(
                        MostrarMision(mision.idmision, mision.nombre.toString(), mision.experiencia))
                    customAdapter.updateData(datosRepresentar)
                }

            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFragmentoMisionesAsignadasBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

//    @SuppressLint("NotifyDataSetChanged")
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
        binding.listaMisionesAsignadas.layoutManager = linearLayoutManager
        //Le pasamos también el viewModel de naves  para poder pasar datos desde el fragmento B a la activity.
        customAdapter = AdaptadorRvMisionesAsignadas( datosRepresentar, requireContext(), fragmentoMisionesAsignadas, fragmentoVMisionesViewModel)
        binding.listaMisionesAsignadas.adapter = customAdapter
    }
}