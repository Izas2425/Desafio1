package com.example.desafiotopdarkcliente.ui

import android.annotation.SuppressLint
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.desafiotopdarkcliente.R
import com.example.desafiotopdarkcliente.databinding.FragmentFragmentoAddPilotoBinding
import com.example.desafiotopdarkcliente.databinding.FragmentFragmentoVPilotosBinding
import modelo.Usuario

class FragmentoAddPiloto : Fragment() {

    private var _binding: FragmentFragmentoAddPilotoBinding? = null
    private val binding get() = _binding!!

    private lateinit var usuario: Usuario

    private val fragmentoAddPilotoViewModel: FragmentoAddPilotoViewModel by viewModels()

    companion object {
        fun newInstance() = FragmentoAddPiloto()
    }

    private val viewModel: FragmentoAddPilotoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFragmentoAddPilotoBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)

        binding.btnAceptarAddPiloto.setOnClickListener {

            usuario = Usuario()


            // Se recogen los datos
            val nombre = binding.tfNombrePiloto.editText?.text.toString()
            val edadString = binding.tfEdadPiloto.editText?.text.toString()
            val experienciaString = binding.tfExperienciaPiloto.editText?.text.toString()
            val password = binding.tfpassworProvisionalPiloto.editText?.text.toString()

            val edad = edadString.toIntOrNull() ?: 0
            val experiencia = experienciaString.toIntOrNull() ?: 0

            // Se comprueba si tienen datos
            if (binding.tfNombrePiloto.editText?.text.toString().isEmpty() || binding.tfpassworProvisionalPiloto.editText?.text.toString().isEmpty()
                || edadString.isEmpty() || experienciaString.isEmpty())
            {
                Toast.makeText(requireContext(), "Rellena todos los campos", Toast.LENGTH_SHORT).show()

            }
            // Si todos los campos están rellenos, se crea el usuario y se llama a la función
            // para añadirlo a la base de datos
            else{
                usuario.id = 0
                usuario.nombre = nombre
                usuario.edad = edad
                usuario.experiencia = experiencia
                if (experiencia in 0..50){
                    usuario.nivel = "Novato"
                }else{
                    if (experiencia in 51.. 100){
                        usuario.nivel = "Intermedio"
                    }
                    else{
                        usuario.nivel = "Experto"
                    }
                }
                Log.e("Izaskun", "nombre ${usuario.nombre}, edad ${usuario.edad}")
                usuario.password = password
                usuario.foto = ""
                usuario.role = "Piloto"
                usuario.activado = 0

                fragmentoAddPilotoViewModel.addUserVM(usuario)

                //Vuelve al fragmento anterior
                requireActivity().onBackPressed()
            }
        }

        binding.btnCancelarAddPiloto.setOnClickListener {
            //Vuelve al fragmento anterior
            requireActivity().onBackPressed()
        }
    }
}