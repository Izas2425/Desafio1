package adaptadores

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
import com.example.desafiotopdarkcliente.ui.FragmentoMisionEnAsignamisionViewModel
import modelo.MostrarMision
import modelo.MostrarNave

class AdaptadorRvMisionesEnAsignarmisiones(
    var misiones: ArrayList<MostrarMision>,
    var context: Context,
    private val viewModelMisionesEnAsignarmisiones: FragmentoMisionEnAsignamisionViewModel): RecyclerView.Adapter<AdaptadorRvMisionesEnAsignarmisiones.ViewHolder>(){

    companion object{
        var seleccionado: Int = -1
    }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdaptadorRvMisionesEnAsignarmisiones.ViewHolder {
            val vista = LayoutInflater.from(parent.context).inflate(R.layout.item_card_mision, parent, false)
            val viewHolder = adaptadores.AdaptadorRvMisionesEnAsignarmisiones.ViewHolder(vista)
            return viewHolder
    }

    override fun onBindViewHolder(holder: AdaptadorRvMisionesEnAsignarmisiones.ViewHolder, position: Int)
    {
       val item = misiones.get(position)
       holder.bind(item, context, position, this)
    }

    override fun getItemCount(): Int {
        return misiones.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(nuevaLista: ArrayList<MostrarMision>) {
        misiones = nuevaLista
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val nombreMision = view.findViewById(R.id.tvNombreM) as TextView
        var experienciaMision = view.findViewById(R.id.tvExperienciaM) as TextView

        val btndetalleM = view.findViewById(R.id.btnDetalleM) as Button

        @SuppressLint("ResourceAsColor")
        fun bind(mis: MostrarMision, context: Context, pos: Int, adaptadorRvDatos: AdaptadorRvMisionesEnAsignarmisiones){
            nombreMision.text = mis.nombre
            experienciaMision.text = mis.experiencia.toString()

            if(pos == AdaptadorRvMisionesEnAsignarmisiones.seleccionado){
                with(nombreMision){
                    this.setTextColor(resources.getColor(R.color.md_theme_inversePrimary_mediumContrast))
                }
                experienciaMision.setTextColor(R.color.md_theme_inversePrimary_mediumContrast)
            }else{
                with(nombreMision){
                    this.setTextColor(resources.getColor(R.color.black))
                }
                experienciaMision.setTextColor(R.color.black)
            }

            itemView.setOnClickListener {
                if (pos == AdaptadorRvMisionesEnAsignarmisiones.seleccionado) {
                    AdaptadorRvMisionesEnAsignarmisiones.seleccionado = -1
                    adaptadorRvDatos.viewModelMisionesEnAsignarmisiones.misionSeleccionada.value = null

                }
                else{
                    AdaptadorRvMisionesEnAsignarmisiones.seleccionado = pos
                    adaptadorRvDatos.viewModelMisionesEnAsignarmisiones.misionSeleccionada.value = mis
                }
                adaptadorRvDatos.notifyDataSetChanged()
            }

            btndetalleM.setOnClickListener {
                adaptadorRvDatos.viewModelMisionesEnAsignarmisiones.getMisionVM(mis.idmision!!)
                adaptadorRvDatos.viewModelMisionesEnAsignarmisiones.myResponse.observe(context as LifecycleOwner){ mision ->
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