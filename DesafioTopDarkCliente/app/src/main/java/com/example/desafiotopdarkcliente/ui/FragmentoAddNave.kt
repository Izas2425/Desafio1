package com.example.desafiotopdarkcliente.ui

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.net.Uri
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import com.example.desafiotopdarkcliente.R
import com.example.desafiotopdarkcliente.databinding.FragmentFragmentoAddNaveBinding
import com.example.desafiotopdarkcliente.databinding.FragmentFragmentoAddPilotoBinding
import modelo.Nave

class FragmentoAddNave : Fragment() {

    private var _binding: FragmentFragmentoAddNaveBinding? = null
    private val binding get() = _binding!!

    var tipoNave: String = ""

    var uriImagen : Uri? = null
    private lateinit var bitmap: Bitmap
    var albumName = "NavesAlbum"

    private lateinit var nave: Nave

    var pasajeros: Boolean = false
    var carga: Boolean= false

    private val fragmentoAddNaveViewModel: FragmentoAddNaveViewModel by viewModels()

    companion object {
        fun newInstance() = FragmentoAddNave()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFragmentoAddNaveBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    //Activity para lanzar la galería de imágenes.
    val pickMedia = registerForActivityResult(PickVisualMedia()) { uri ->
        if (uri != null) {
            Log.d("Izaskun", "Selected URI: $uri")
            uriImagen = uri
            binding.imCamaraAddN.setImageURI(uri)
            Log.d("Izaskun", "Cargada")
        } else {
            Log.d("Izaskun", "No media selected")
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        nave = Nave()

        // Para el spinner
        val adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.tipo_naves,
            android.R.layout.simple_spinner_item
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spTipoNave.adapter = adapter

        binding.spTipoNave.onItemSelectedListener =  object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: android.view.View?, position: Int, id: Long) {
                val selectedItem = parent.getItemAtPosition(position).toString()
                tipoNave = selectedItem
                Toast.makeText(requireContext(), "Seleccionaste: $selectedItem", Toast.LENGTH_SHORT).show()

                if (tipoNave == "Combate"){
                    binding.cbCargaN.isEnabled = false
                    binding.cbPasajerosN.isEnabled = false
                    binding.imTipoNave.setImageResource((R.drawable.nave_combate))
                }
                else{
                    binding.cbCargaN.isEnabled = true
                    binding.cbPasajerosN.isEnabled = true
                    if(tipoNave == "Vuelo"){
                        binding.imTipoNave.setImageResource((R.drawable.nave_vuelo))
                    }
                    else{
                        binding.imTipoNave.setImageResource(R.drawable.nava_bombadeo)
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                //Acción cuando no se selecciona nada
            }
        }

        binding.imCamaraAddN.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        binding.cbCargaN.setOnClickListener {

            carga = binding.cbCargaN.isChecked
            nave.carga = carga

        }

        binding.cbPasajerosN.setOnClickListener {

            pasajeros = binding.cbPasajerosN.isChecked
            nave.pasajeros = pasajeros

        }

        binding.btAceptarAddN.setOnClickListener {


            // Se recogen los datos
            val matricula = binding.tfMatriculaNave.editText?.text.toString()

           // Comprueba que la matricula esté rellena
            if(binding.tfMatriculaNave.editText?.text.toString().isEmpty()){
                Toast.makeText(requireContext(), "Rellena todos los campos", Toast.LENGTH_SHORT).show()
            }
            else{
                nave.matricula = matricula
                nave.tipo = tipoNave
                nave.foto = ""

                Log.e("Izaskun", "matricula ${nave.matricula}, tipo ${nave.tipo}, carga ${nave.carga}, pasajeros ${nave.pasajeros}")

                fragmentoAddNaveViewModel.addNavVM(nave)

                //Vuelve al fragmento anterior
                requireActivity().onBackPressed()
             }
        }

        binding.btnCancelarAddN.setOnClickListener {
            //Vuelve al fragmento anterior
            requireActivity().onBackPressed()
        }

    }
}