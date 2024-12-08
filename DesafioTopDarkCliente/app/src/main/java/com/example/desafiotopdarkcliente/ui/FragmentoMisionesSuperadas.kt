package com.example.desafiotopdarkcliente.ui

import adaptadores.AdaptadorRvMisionesAsignadas
import adaptadores.AdaptadorRvMisionesSuperadas
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
import com.example.desafiotopdarkcliente.databinding.FragmentFragmentoMisionesSuperadasBinding
import modelo.MostrarMision
import parametros.Parametros

class FragmentoMisionesSuperadas : Fragment() {
    private var _binding: FragmentFragmentoMisionesSuperadasBinding? = null
    private val binding get() = _binding!!

    private lateinit var misionViewModel: MisionViewModel

    private val fragmentoMisionesSuperadas: FragmentoMisionesSuperadasViewModel by viewModels()
    private val fragmentoVMisionesViewModel : FragmentoVMisionesViewModel by viewModels()

    // Para representar la RV
    var datosRepresentar: ArrayList<MostrarMision> = ArrayList()
    lateinit var customAdapter: AdaptadorRvMisionesSuperadas

    companion object {
        fun newInstance() = FragmentoMisionesSuperadas()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        misionViewModel = ViewModelProvider(this)[MisionViewModel::class.java]

        fragmentoMisionesSuperadas.getMisionesSuperadasVM(Parametros.usuarioLogeado!!)
        fragmentoMisionesSuperadas.myResponseListA.observe(this) { misionesSuperadas ->
            if (!misionesSuperadas.isEmpty()) {
                datosRepresentar.clear()
                for (mis in misionesSuperadas) {
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
        _binding = FragmentFragmentoMisionesSuperadasBinding.inflate(inflater, container, false)
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
        binding.listaMisionesSuperadas.layoutManager = linearLayoutManager
        //Le pasamos también el viewModel de naves  para poder pasar datos desde el fragmento B a la activity.
        customAdapter = AdaptadorRvMisionesSuperadas( datosRepresentar, requireContext(), fragmentoMisionesSuperadas, fragmentoVMisionesViewModel)
        binding.listaMisionesSuperadas.adapter = customAdapter
    }
}