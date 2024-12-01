package com.example.desafiotopdarkcliente.ui

import adaptadores.AdaptadorRvMisionesEnAsignarmisiones
import adaptadores.AdaptadorRvNavesEnMisiones
import android.annotation.SuppressLint
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.desafiotopdarkcliente.R
import com.example.desafiotopdarkcliente.databinding.FragmentFragmentoMisionEnAsignamisionBinding
import com.example.desafiotopdarkcliente.databinding.FragmentFragmentoNavesEnMisionesBinding
import modelo.MostrarMision

class FragmentoMisionEnAsignamision : Fragment() {

    private var _binding: FragmentFragmentoMisionEnAsignamisionBinding? = null
    private val binding get() = _binding!!

    private val fragmentoMisionEnAsignarMisionVM : FragmentoMisionEnAsignamisionViewModel by activityViewModels()

    var datosRepreentar: ArrayList<MostrarMision> = ArrayList()
    lateinit var customAdapter: AdaptadorRvMisionesEnAsignarmisiones

    companion object {
        fun newInstance() = FragmentoMisionEnAsignamision()
    }

       override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fragmentoMisionEnAsignarMisionVM.getMisionesVM()
        fragmentoMisionEnAsignarMisionVM.myResponseListM.observe(this){ misiones ->
            if (!misiones.isEmpty()){
                datosRepreentar.clear()
                for (mision in misiones){
                    datosRepreentar.add(MostrarMision(mision.experiencia,mision.experiencia.toString()))
                }
                customAdapter.updateData(datosRepreentar)
            }
        }

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFragmentoMisionEnAsignamisionBinding.inflate(inflater, container, false)
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

        fragmentoMisionEnAsignarMisionVM.myResponseList.observe(viewLifecycleOwner){ listaMisiones ->
            datosRepreentar.clear()
            datosRepreentar.addAll(listaMisiones)
            customAdapter.notifyDataSetChanged()
        }

        binding.btnAceptarMisionesEnAsignarMisiones.setOnClickListener {
            if (AdaptadorRvMisionesEnAsignarmisiones.seleccionado != -1){
                val misionSeleccionada = fragmentoMisionEnAsignarMisionVM.misionSeleccionada.value?.nombre
                Log.e("Izaskun", "misionSeleccionada botón AceptarMisionEnasignarMision ${misionSeleccionada}")

                misionSeleccionada?.let {
                    parentFragmentManager.popBackStack()
                } ?: run{
                    Toast.makeText(requireContext(), "No se pudo recuperar la misión seleccionada", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(requireContext(), "Debe seleccionar una misión antes de continuar", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnCancelarMisionesEnAsignarMisiones.setOnClickListener {
            //Vuelve al fragmento anterior
            requireActivity().onBackPressed()
        }

    }

    private fun setupRecyclerView(){
        val linearLayoutManager = LinearLayoutManager(requireContext())
        binding.listaMisionesEnAsignarmision.layoutManager = linearLayoutManager
        customAdapter = AdaptadorRvMisionesEnAsignarmisiones(datosRepreentar, requireContext(), fragmentoMisionEnAsignarMisionVM)
        binding.listaMisionesEnAsignarmision.adapter = customAdapter
    }
}