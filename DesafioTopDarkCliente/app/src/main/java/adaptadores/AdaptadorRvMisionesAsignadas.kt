package adaptadores

import adaptadores.AdaptadorRvMisiones.ViewHolder
import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.desafiotopdarkcliente.R
import com.example.desafiotopdarkcliente.ui.FragmentoMisionesAsignadasViewModel
import com.example.desafiotopdarkcliente.ui.FragmentoVMisionesViewModel
import modelo.MostrarMision

class AdaptadorRvMisionesAsignadas(
    var misiones: ArrayList<MostrarMision>,
    var context: Context,
    private val viewModelVMisionesAsignadas: FragmentoMisionesAsignadasViewModel):RecyclerView.Adapter<AdaptadorRvMisionesAsignadas.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdaptadorRvMisionesAsignadas.ViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.item_card_mision, parent, false)
        val viewHolder = ViewHolder(vista)
        return viewHolder
    }

    override fun onBindViewHolder(holder: AdaptadorRvMisionesAsignadas.ViewHolder, position: Int) {
        val item = misiones.get(position)
        holder.bind(item, context, position, this)
    }

    override fun getItemCount(): Int {
        return misiones.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(nuevaLista: ArrayList<MostrarMision>){
        misiones = nuevaLista
        notifyDataSetChanged()
    }

    class ViewHolder(view: View):RecyclerView.ViewHolder(view){

        val nombreMision = view.findViewById(R.id.tvNombreM) as TextView
        val experienciaMision = view.findViewById(R.id.tvExperienciaM) as TextView

        val btnDetelleM = view.findViewById(R.id.btnDetalleM) as Button

        @SuppressLint("ResourceAsColor")
        fun bind(mis: MostrarMision, context: Context, pos: Int, adaptadorRvDatos: AdaptadorRvMisionesAsignadas){
            nombreMision.text = mis.nombre
            experienciaMision.text = mis.experiencia.toString()

            btnDetelleM.setOnClickListener {

//
            }
        }
    }
}