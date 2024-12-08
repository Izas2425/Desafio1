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
import com.example.desafiotopdarkcliente.ui.FragmentoMisionesSuperadasViewModel
import com.example.desafiotopdarkcliente.ui.FragmentoVMisionesViewModel
import modelo.MostrarMision

class AdaptadorRvMisionesSuperadas(
    var misiones: ArrayList<MostrarMision>,
    var context: Context,
    private val viewModelVMisionesASuperadas: FragmentoMisionesSuperadasViewModel,
    private val viewModelVMisiones: FragmentoVMisionesViewModel
): RecyclerView.Adapter<AdaptadorRvMisionesSuperadas.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdaptadorRvMisionesSuperadas.ViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.item_card_mision, parent, false)
        val viewHolder = ViewHolder(vista)
        return viewHolder
    }

    override fun onBindViewHolder(holder: AdaptadorRvMisionesSuperadas.ViewHolder, position: Int) {
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
        fun bind(mis: MostrarMision, context: Context, pos: Int, adaptadorRvDatos: AdaptadorRvMisionesSuperadas){
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