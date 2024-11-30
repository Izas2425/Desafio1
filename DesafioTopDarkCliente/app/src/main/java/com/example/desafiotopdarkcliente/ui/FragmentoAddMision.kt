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
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.desafiotopdarkcliente.R
import com.example.desafiotopdarkcliente.databinding.FragmentFragmentoAddMisionBinding
import com.example.desafiotopdarkcliente.databinding.FragmentFragmentoAddNaveBinding
import modelo.Bombardero
import modelo.Combate
import modelo.Mision
import modelo.Vuelo

class FragmentoAddMision : Fragment() {

    private var _binding: FragmentFragmentoAddMisionBinding? = null
    private val binding get() = _binding!!

    private val viewModelCompartir: FragmentoNavesEnMisionesViewModel by activityViewModels()
    private val fragmentoVNavesViewModel : FragmentoVNavesViewModel by viewModels()
    private val viewModelMision: FragmentoAddMisionViewModel by viewModels()

    private lateinit var mision: Mision
    private lateinit var vuelo: Vuelo
    private lateinit var combate: Combate
    private lateinit var bombardero: Bombardero

    var matriculaNave: String =""
    var carga: Boolean = false
    var pasajeros: Boolean = false
    var tipo: String = ""
    var ultimoId: Int = 0
    var duracion: Int = 0
    var cazas: Int = 0
    var objetivos: Int = 0

    var seguir: Boolean = false



    companion object {
        fun newInstance() = FragmentoAddMision()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFragmentoAddMisionBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mision = Mision()
        vuelo = Vuelo()
        combate = Combate()
        bombardero = Bombardero()

        binding.tvMatriculaNaveM.text = ""
        carga = false
        pasajeros = false


        viewModelMision.myResponseListM.observe(viewLifecycleOwner, Observer { misiones ->

        })


       viewModelMision.ultimaMisionMv()
        viewModelMision.ultimoId.observe(viewLifecycleOwner, Observer { id ->
            id?.let {
                ultimoId = id.toInt()
            }
        })

        Log.e("Izaskun", "id ultimo  antes de btnMatriculaNave:  ${ultimoId}")
        binding.btnMatriculaNaveM.setOnClickListener {

            viewModelCompartir.naveSeleccionada.value = null

            val fragmentoNaves = FragmentoNavesEnMisiones()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.miFragContainer, fragmentoNaves)
                .addToBackStack(null)
                .commit()
        }

        viewModelCompartir.naveSeleccionada.observe(viewLifecycleOwner){ nave ->
            nave?.let{
                binding.tvMatriculaNaveM.text = nave.matricula
                Log.e("Izaskun", "tvMatriculaNaveM en AddMision${viewModelCompartir.naveSeleccionada.value?.matricula}")
                matriculaNave = nave.matricula.toString()
                fragmentoVNavesViewModel.getNaveVM(matriculaNave)
            }
        }

        fragmentoVNavesViewModel.myResponse.observe(viewLifecycleOwner){ nave ->
            nave?.let {
                tipo = nave.tipo.toString()

                Log.e("Izaskun", "tipo de nave  despues de observar myResponse: ${tipo}")
                actualizarCamposSegunTipo()
            }
        }




        guardarDatos()

        binding.btnAceptarAddM.setOnClickListener {

            // Se comprueba que los datos estén rellenos
            if(validarCampos()){
                // primero insertar mision
                mision.idmision = 0
                mision.nombre = binding.tfNombreM.editText?.text.toString()
                mision.descripcion = binding.tfDescripcionM.editText?.text.toString()
                mision.matriculanave = matriculaNave
                mision.experiencia = binding.tfExperienciaM.editText?.text.toString().toIntOrNull()

                viewModelMision.addMisMV(mision)

//                viewModelMision.obtenerTodasLasMisiones()

//                viewModelMision.ultimaMisionMv()
                viewModelMision.ultimoId.observe(viewLifecycleOwner, Observer { id ->
                    id?.let {
                        ultimoId = id.toInt()
                        Log.e("Izaskun", "id ultimo mision despues de addMiss:  ${ultimoId} it ${id.toInt()}")
                    }
                })

                when (tipo) {
                    "Combate" -> {
                        // solucion temporal
                        combate.idmision = ultimoId +1
//                        combate.idmision = ultimoId
                        Log.e("Izaskun", "id combate:  ${vuelo.idmision}")
                        combate.cazas = binding.tfCazasM.editText?.text.toString().toIntOrNull()
                        viewModelMision.addComMV(combate)
                    }
                    "Vuelo" -> {
                        // solucion temporal
                        vuelo.idmision = ultimoId + 1
//                        vuelo.idmision = ultimoId
                        Log.e("Izaskun", "id vuelo:  ${vuelo.idmision}")
                        vuelo.carga = binding.cbCargaAddM.isChecked
                        vuelo.pasajeros = binding.cbPasajerosAddM.isChecked
                        vuelo.duracion = binding.tfDuracionM.editText?.text.toString().toIntOrNull()
                        viewModelMision.addVueMV(vuelo)
                    }
                    else -> { // Bombardero
                        // solucion temporal
                        bombardero.idmision = ultimoId + 1
//                        bombardero.idmision = ultimoId
                        Log.e("Izaskun", "id bombardero:  ${vuelo.idmision}")
                        bombardero.carga = binding.cbCargaAddM.isChecked
                        bombardero.pasajeros = binding.cbPasajerosAddM.isChecked
                        bombardero.objetivos = binding.tfObjetivosM.editText?.text.toString().toIntOrNull()
                        viewModelMision.addBomMV(bombardero)
                    }
                }

                limpiar()
                requireActivity().onBackPressed()

            }
            else{
                Toast.makeText(requireContext(), "Rellena los campos", Toast.LENGTH_SHORT).show()
            }

        }

        binding.btnCancelarAddM.setOnClickListener {
            //Vuelve al fragmento anterior
            requireActivity().onBackPressed()
        }
    }



    fun actualizarCamposSegunTipo(){
        Log.e("Izaskun", "tipo de nave en actualizarCampoSegunTipo:  ${tipo}")
        when (tipo) {
            "Combate" -> {
                binding.txtCazasM.isEnabled = true
                binding.txtDuracionM.isEnabled = false
                binding.txtObjetivosM.isEnabled = false
                binding.cbCargaAddM.isEnabled = false
                binding.cbPasajerosAddM.isEnabled = false
            }
            "Vuelo" -> {
                binding.txtCazasM.isEnabled = false
                binding.txtDuracionM.isEnabled = true
                binding.txtObjetivosM.isEnabled = false
                binding.cbCargaAddM.isEnabled = true
                binding.cbPasajerosAddM.isEnabled = true
            }
            else -> { // Bombardero
                binding.txtCazasM.isEnabled = false
                binding.txtDuracionM.isEnabled = false
                binding.txtObjetivosM.isEnabled = true
                binding.cbCargaAddM.isEnabled = true
                binding.cbPasajerosAddM.isEnabled = true
            }
        }
    }

    fun guardarDatos(){
        var dato: String = ""
        when (tipo) {
            "Combate" -> {
                dato = binding.tfCazasM.editText?.text.toString()
                if ( dato.isEmpty()){
                    Toast.makeText(requireContext(), "Introduzca el número de cazas", Toast.LENGTH_SHORT).show()
                    seguir = false
                }
                else {
                    cazas = dato.toIntOrNull()!!
                    seguir = true
                }

            }
            "Vuelo" -> {
                dato = binding.tfDuracionM.editText?.text.toString()
                carga = binding.cbCargaAddM.isChecked
                pasajeros = binding.cbPasajerosAddM.isChecked
                if ( dato.isEmpty()){
                    Toast.makeText(requireContext(), "Introduzca la duración de la mision", Toast.LENGTH_SHORT).show()
                    seguir = false
                }
                else {
                    duracion = dato.toIntOrNull()!!
                    seguir = true
                }

            }
            else -> { // Bombardero
                dato = binding.tfObjetivosM.editText?.text.toString()
                carga = binding.cbCargaAddM.isChecked
                pasajeros = binding.cbPasajerosAddM.isChecked
                if ( dato.isEmpty()){
                    Toast.makeText(requireContext(), "Introduzca el número de objetivos", Toast.LENGTH_SHORT).show()
                    seguir = false
                }
                else {
                    seguir = true
                    objetivos = dato.toIntOrNull()!!
                }
            }
        }
    }
    private fun validarCampos(): Boolean {
        return if (binding.tfNombreM.editText?.text.toString().isEmpty() ||
            binding.tfDescripcionM.editText?.text.toString().isEmpty() ||
            binding.tfExperienciaM.editText?.text.toString().isEmpty()
        ) {
            Toast.makeText(requireContext(), "Rellena los campos", Toast.LENGTH_SHORT).show()
            false
        } else {
            true
        }
    }

    fun limpiar(){
        binding.txtDuracionM.setText("")
        binding.txtExperienciaM.setText("")
        binding.txtDescripcionM.setText("")
        binding.txtNombreM.setText("")
        binding.tvMatriculaNaveM.text = ""
        binding.cbCargaAddM.isChecked = false
        binding.cbPasajerosAddM.isChecked = false
    }

}