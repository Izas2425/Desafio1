package adaptadores

import adaptadores.AdaptadorRvPilotos.ViewHolder
import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.desafiotopdarkcliente.R
import com.example.desafiotopdarkcliente.ui.FragmentoPilotoEnAsignarmisionViewModel
import modelo.MostrarPiloto

class AdaptadorRvPilotosEnAsignarmision(
    var pilotos: ArrayList<MostrarPiloto>,
    var context: Context,
    var viewModelVPilotosEnAsignarmisionVM: FragmentoPilotoEnAsignarmisionViewModel): RecyclerView.Adapter<AdaptadorRvPilotosEnAsignarmision.ViewHolder>() {

    companion object{
        var seleccionado: Int = -1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdaptadorRvPilotosEnAsignarmision.ViewHolder {
        val vista =
            LayoutInflater.from(parent.context).inflate(R.layout.item_card_piloto, parent, false)
        val viewHolder = ViewHolder(vista)
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = pilotos.get(position)
        holder.bind(item, context, position, this)
    }

    override fun getItemCount(): Int {
        return pilotos.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(nuevaLista: ArrayList<MostrarPiloto>) {
        pilotos = nuevaLista
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombrePiloto = view.findViewById(R.id.tvMatriculaN) as TextView
        val nivelPiloto = view.findViewById(R.id.tvTipoN) as TextView

        val btnDetalleP = view.findViewById(R.id.btnDetalleP) as Button

        @SuppressLint("ResourceAsColor")
        fun bind(user: MostrarPiloto, context: Context, pos: Int, adaptadorRvDatos: AdaptadorRvPilotosEnAsignarmision){
            nombrePiloto.text = user.nombre
            nivelPiloto.text = user.nivel

            if(pos == AdaptadorRvPilotosEnAsignarmision.seleccionado){
                with(nombrePiloto){
                    this.setTextColor(resources.getColor(R.color.md_theme_inversePrimary_mediumContrast))
                }
                nivelPiloto.setTextColor(R.color.md_theme_inversePrimary_mediumContrast)
            }else{
                with(nombrePiloto){
                    this.setTextColor(resources.getColor(R.color.black))
                }
                nivelPiloto.setTextColor(R.color.black)
            }

            itemView.setOnClickListener {
                 val listaActual = adaptadorRvDatos.viewModelVPilotosEnAsignarmisionVM.pilotosSeleccionados.value ?: mutableListOf()

                if(pos == AdaptadorRvPilotosEnAsignarmision.seleccionado){
                    AdaptadorRvPilotosEnAsignarmision.seleccionado = -1
                    listaActual.remove(user)
                    Toast.makeText(context, "Piloto ${user.nombre} quitado de la lista", Toast.LENGTH_SHORT).show()
//                    adaptadorRvDatos.viewModelVPilotosEnAsignarmisionVM.pilotosSeleccionados.value = null
                }
                else{
                    AdaptadorRvPilotosEnAsignarmision.seleccionado = pos
                    if (!listaActual.contains(user)){
                        listaActual.add(user)
                        Toast.makeText(context, "Piloto ${user.nombre} añadido a la lista", Toast.LENGTH_SHORT).show()
                    }
//                  adaptadorRvDatos.viewModelVPilotosEnAsignarmisionVM.pilotosSeleccionados.value = user
                }
                adaptadorRvDatos.viewModelVPilotosEnAsignarmisionVM.pilotosSeleccionados.value = listaActual
                adaptadorRvDatos.notifyDataSetChanged()
            }

            btnDetalleP.setOnClickListener {
                adaptadorRvDatos.viewModelVPilotosEnAsignarmisionVM.getUsuarioVM(user.id!!)

                adaptadorRvDatos.viewModelVPilotosEnAsignarmisionVM.myResponse.observe(context as LifecycleOwner){ piloto ->
                    piloto?.let{

                        AlertDialog.Builder(context)
                            .setTitle("Detalles del piloto")
                            .setMessage("Nombre: ${piloto.nombre}\n" +
                                    "Edad: ${piloto.edad}\n" +
                                    "Experiencia: ${piloto.experiencia}\n" +
                                    "Nivel: ${piloto.nivel}\n" +
                                    "Foto: ${piloto.foto}")
                            .setPositiveButton("Aceptar"){dialog, _ ->
                                dialog.dismiss()
                            }
                            .show() // muestra el dialogo

                        // porque cuando le daba a detalle de un piloto después de haberle dado antes a detalle de otro
                        // se abrian los dos dialogos
                        adaptadorRvDatos.viewModelVPilotosEnAsignarmisionVM.clearMyResponse()
                    }
                }
            }
        }


    }
}