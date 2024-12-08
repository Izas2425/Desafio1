package adaptadores

import adaptadores.AdaptadorRvMisionesAsignadas.ViewHolder
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
import com.example.desafiotopdarkcliente.ui.FragmentoMisionesAsignadasViewModel
import com.example.desafiotopdarkcliente.ui.FragmentoMisionesNoSuperadasViewModel
import com.example.desafiotopdarkcliente.ui.FragmentoVMisionesViewModel
import modelo.MostrarMision

class AdaptadorRvMisionesNoSuperadas(
    var misiones: ArrayList<MostrarMision>,
    var context: Context,
    private val viewModelVMisionesNoSuperadas: FragmentoMisionesNoSuperadasViewModel,
    private val viewModelVMisiones: FragmentoVMisionesViewModel
): RecyclerView.Adapter<AdaptadorRvMisionesNoSuperadas.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdaptadorRvMisionesNoSuperadas.ViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.item_card_mision, parent, false)
        val viewHolder = ViewHolder(vista)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return misiones.size
    }

    override fun onBindViewHolder(holder: AdaptadorRvMisionesNoSuperadas.ViewHolder, position: Int) {
        val item = misiones.get(position)
        holder.bind(item, context, position, this)
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
        fun bind(mis: MostrarMision, context: Context, pos: Int, adaptadorRvDatos: AdaptadorRvMisionesNoSuperadas){
            nombreMision.text = mis.nombre
            experienciaMision.text = mis.experiencia.toString()

            btnDetelleM.setOnClickListener {
                adaptadorRvDatos.viewModelVMisiones.getMisionVM(mis.idmision!!)
                adaptadorRvDatos.viewModelVMisiones.myResponseM.observe(context as LifecycleOwner){ mision ->
                    mision?.let {
                        AlertDialog.Builder(context)
                            .setTitle("Detalle de la misión")
                            .setMessage("Nombre: ${mision.nombre}\n" +
                                    "Experiencia: ${mision.experiencia}\n" +
                                    "Descripcion: ${mision.descripcion}\n" +
                                    "Matrícula de la nave: ${mision.matriculanave}")
                            .setPositiveButton("Aceptar"){dialog, _ ->
                                dialog.dismiss()
                            }
                            .show()
                    }
                }
            }
        }
    }
}