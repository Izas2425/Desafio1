package adaptadores

import adaptadores.AdaptadorRvPilotos.ViewHolder
import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.desafiotopdarkcliente.R
import com.example.desafiotopdarkcliente.ui.FragmentoRankingPilotosViewModel
import modelo.MostrarPiloto

class AdaptadorRvRankingPilotos(

    var pilotos: ArrayList<MostrarPiloto>,
    var context: Context,
    var viewModelVRankingPilotos: FragmentoRankingPilotosViewModel): RecyclerView.Adapter<AdaptadorRvRankingPilotos.ViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdaptadorRvRankingPilotos.ViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.item_card_piloto, parent, false)
        val viewHolder = ViewHolder(vista)
        return viewHolder
    }

    override fun onBindViewHolder(holder: AdaptadorRvRankingPilotos.ViewHolder, position: Int) {
        val item = pilotos.get(position)
        holder.bind(item,context, position, this)
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
        fun bind(user: MostrarPiloto, context: Context, pos: Int, adaptadorRvDatos: AdaptadorRvRankingPilotos){
            nombrePiloto.text = user.nombre
            nivelPiloto.text = user.nivel

            btnDetalleP.setOnClickListener {
                adaptadorRvDatos.viewModelVRankingPilotos.getUsuarioVM(user.id!!)
                adaptadorRvDatos.viewModelVRankingPilotos.myResponse.observe(context as LifecycleOwner){ piloto ->
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
                    }
                }
            }

        }
    }
}