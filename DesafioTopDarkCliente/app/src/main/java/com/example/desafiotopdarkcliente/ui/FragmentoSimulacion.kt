package com.example.desafiotopdarkcliente.ui

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.desafiotopdarkcliente.R
import com.example.desafiotopdarkcliente.databinding.FragmentFragmentoSimulacionBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import parametros.Parametros

class FragmentoSimulacion : Fragment() {

    private var _binding: FragmentFragmentoSimulacionBinding? = null
    private val binding get() = _binding!!

    private val viewmodelSimulacion : FragmentoSimulacionViewModel by viewModels()
    private val viewModelMisionesAsignadas : FragmentoMisionesAsignadasViewModel by activityViewModels()
    private val viewModelMisiones : FragmentoVMisionesViewModel by viewModels()
    private val viewModelNaves: FragmentoVNavesViewModel by viewModels()
    private val viewModelUsuario: FragmentoVPilotosViewModel by viewModels()


    var idMisionSeleccionada: Int = 0
    var matriculaNave: String =""
    var tipoNave:String = ""
    var idPiloto:Int = 0
    var cazas:Int = 0
    var duracion: Int = 0
    var carga: Boolean = false
    var objetivos: Int = 0
    var pasajeros: Boolean = false
    var idMision: Int =0
    var experienciaMision: Int = 0
    var idMisionAsignada: Int = 0
    var nivelPiloto: String = ""
    var experienciaPiloto: Int = 0
    var experienciaASumar: Int = 0

    companion object {
        fun newInstance() = FragmentoSimulacion()
    }

     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFragmentoSimulacionBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModelMisionesAsignadas.misionSeleccionada.observe(viewLifecycleOwner){ mision ->
            mision?.let {
                idMisionSeleccionada = mision.idmision!!
                Toast.makeText(requireContext(), "id mision seleccionad es ${idMisionSeleccionada}", Toast.LENGTH_SHORT).show()

                // obtener la mision
                viewModelMisiones.getMisionVM(idMisionSeleccionada)

            }
        }

        viewModelMisionesAsignadas.idMisionAsignadaSeleccionada.observe(viewLifecycleOwner) { idMisAsignada ->
            idMisionAsignada?.let {

                idMisionAsignada = it
                Toast.makeText(requireContext(), "ID Misión Asignada es: ${idMisionAsignada}", Toast.LENGTH_SHORT).show()
            }
        }

        viewModelMisiones.myResponseM.observe(viewLifecycleOwner) { mision ->
            mision?.let {
                // Extrae la matrícula de la nave de la misión
                matriculaNave = mision.matriculanave!!
                idMision = mision.idmision!!
                experienciaMision = mision.experiencia!!
                Toast.makeText(requireContext(), "Matrícula de la nave es: ${matriculaNave}", Toast.LENGTH_SHORT).show()

                viewModelNaves.getNaveVM(matriculaNave!!)

            }
        }

        viewModelNaves.myResponse.observe(viewLifecycleOwner){ nave ->
            nave?.let {
                tipoNave = nave.tipo!!
                Toast.makeText(requireContext(), "Matrícula de la nave es: ${tipoNave}", Toast.LENGTH_SHORT).show()

                when (tipoNave){
                    "Vuelo" -> {
                        viewModelMisiones.obtenerTodosLosVuelos()
                        viewModelMisiones.myResponseListV.observe(viewLifecycleOwner){ vuelos ->
                            val vuelo = vuelos.find { it.idmision == idMisionSeleccionada }

                            duracion = vuelo?.duracion!!
                            carga = vuelo.carga!!
                            pasajeros = vuelo.pasajeros!!

                            //llamada funcion simularVuelo
                            lifecycleScope.launch {
                                experienciaASumar = simulacionVuelo(nivelPiloto, duracion, carga, pasajeros)
                                Toast.makeText(requireContext(), "Puntos ganados: ${experienciaASumar}", Toast.LENGTH_SHORT).show()
                            }


                        }
                    }

                    "Combate" -> {
                        viewModelMisiones.obtenerTodosLosCombates()
                        viewModelMisiones.myResponseListC.observe(viewLifecycleOwner){ combates ->
                            val combate = combates.find { it.idmision == idMisionSeleccionada }

                            cazas = combate?.cazas!!

                            //llamada funcion simularCombate

                        }
                    }

                    "Bombardero" -> {
                        viewModelMisiones.obtenerTodosLosBombarderos()
                        viewModelMisiones.myResponseListB.observe(viewLifecycleOwner){ bombarderos ->
                            val bombardero = bombarderos.find { it.idmision == idMisionSeleccionada }

                            carga = bombardero?.carga!!
                            pasajeros = bombardero.pasajeros!!
                            objetivos = bombardero.objetivos!!

                            //llamada funcion simularBombardero

                        }
                    }
                }

            }
        }


         viewModelUsuario.getUsuarioVM(Parametros.usuarioLogeado!!)
        viewModelUsuario.myResponse.observe(viewLifecycleOwner){ piloto ->
            piloto?.let {
                idPiloto = piloto.id!!
                nivelPiloto = piloto.nivel!!
                experienciaPiloto = piloto.experiencia!!

            }
        }
    }


   suspend fun simulacionVuelo(nivel: String, duracion: Int, carga: Boolean, pasajeros: Boolean): Int {

        var min = 0
        var experienciaQueSuma = 0
        var posibilidades: Int

        binding.tvMensajesSimulacion.text = " Empieza la simulación"
        delay(2000)

        while (min < duracion) {
            delay(1000)
            min += 1
            binding.tvMensajesSimulacion.append("\n Minuto: $min")


            // Evento de tormenta solar cada 10 minutos
            if (min % 10 == 0) {
                binding.tvMensajesSimulacion.append("\nTormenta solar a la vista")

                delay(2000)

                posibilidades = (0..100).random()
                val probabilidadSuperar = when (nivel) {
                    "Experto" -> 90
                    "Intermedio" -> 70
                    else -> 50 // Novato
                }

                if (posibilidades > probabilidadSuperar) {
                    binding.tvMensajesSimulacion.append("\nLa tormenta solar ha sido terrible. Has fallado.")

                    return experienciaQueSuma // Termina el vuelo
                } else {
                    binding.tvMensajesSimulacion.append("\nConseguiste superar la tormenta solar")

                }
            }

            // Evento de ataque cada 20 minutos
            if (min % 20 == 0) {
                val ataqueOcurre = (0..100).random() <= 30 // 30% probabilidad de ataque
                if (ataqueOcurre) {
                    binding.tvMensajesSimulacion.append("\n¡Te están atacando!")

                    delay(2000)

                    posibilidades = (0..100).random()
                    val probabilidadSuperar = when (nivel) {
                        "Experto" -> 80
                        "Intermedio" -> 60
                        else -> 40 // Novato
                    }

                    if (posibilidades > probabilidadSuperar) {
                        binding.tvMensajesSimulacion.append("\nEl ataque fue mortal. Has fallado.")

                        return experienciaQueSuma // Termina el vuelo
                    } else {
                        binding.tvMensajesSimulacion.append("\nConseguiste superar el ataque.")

                    }
                }
            }
        }

        // Si llega al final del vuelo
        binding.tvMensajesSimulacion.append("\n¡Enhorabuena! Has conseguido sobrevivir a la simulación.")


        // Ganancia de experiencia
        experienciaQueSuma += 10 // Experiencia base por completar el vuelo
        if (carga) experienciaQueSuma+= 5
        if (pasajeros) experienciaQueSuma += 10

        return experienciaQueSuma
    }


}