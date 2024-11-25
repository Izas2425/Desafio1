package adaptadores

import adaptadores.AdaptadorRvNaves.ViewHolder
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
import com.example.desafiotopdarkcliente.ui.FragmentoNavesEnMisiones
import com.example.desafiotopdarkcliente.ui.FragmentoNavesEnMisionesViewModel
import modelo.MostrarNave
import androidx.fragment.app.activityViewModels

class AdaptadorRvNavesEnMisiones (
    var naves: ArrayList<MostrarNave>,
    var context: Context,
    private val viewModelNavesEnMisiones: FragmentoNavesEnMisionesViewModel): RecyclerView.Adapter<AdaptadorRvNavesEnMisiones.ViewHolder>(){




        companion object{
            var seleccionado: Int = -1
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.item_card_nave, parent, false)
        val viewHolder = adaptadores.AdaptadorRvNavesEnMisiones.ViewHolder(vista)
        return viewHolder
    }

    override fun onBindViewHolder(holder: AdaptadorRvNavesEnMisiones.ViewHolder, position: Int) {
        val item = naves.get(position)
        holder.bind(item, context,position, this)
    }

    override fun getItemCount(): Int {
        return naves.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(nuevaLista: ArrayList<MostrarNave>) {
        naves = nuevaLista
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val matriculaNave = view.findViewById(R.id.tvMatriculaN) as TextView
        val tipoNave = view.findViewById(R.id.tvTipoN) as TextView

        val btnDetalleN = view.findViewById(R.id.btnDetalleN) as Button

        var carga: String =""
        var pasajeros:String = ""

        @SuppressLint("ResourceAsColor")
        fun bind(nav: MostrarNave, context: Context, pos: Int, adaptadorRvDatos: AdaptadorRvNavesEnMisiones){
            matriculaNave.text = nav.matricula
            tipoNave.text = nav.tipo

            if(pos == AdaptadorRvNavesEnMisiones.seleccionado){
                with(matriculaNave){
                    this.setTextColor(resources.getColor(R.color.md_theme_inversePrimary_mediumContrast))
                }
                tipoNave.setTextColor((R.color.md_theme_inversePrimary_mediumContrast))
            }
            else{
                with(matriculaNave){
                    this.setTextColor(resources.getColor(R.color.black))
                }
                tipoNave.setTextColor((R.color.black))
            }

            itemView.setOnClickListener {
                if (pos == AdaptadorRvNavesEnMisiones.seleccionado){
                    AdaptadorRvNavesEnMisiones.seleccionado = -1
                    adaptadorRvDatos.viewModelNavesEnMisiones.naveSeleccionada.value = null
                }
                else{
                    AdaptadorRvNavesEnMisiones.seleccionado = pos
                    adaptadorRvDatos.viewModelNavesEnMisiones.naveSeleccionada.value = nav

                }

               adaptadorRvDatos.notifyDataSetChanged()


            }

            btnDetalleN.setOnClickListener {

                adaptadorRvDatos.viewModelNavesEnMisiones.getNaveVM(nav.matricula!!)

                adaptadorRvDatos.viewModelNavesEnMisiones.myResponse.observe(context as LifecycleOwner){ nave ->
                    nave?.let{
                        if (nave.carga == true){
                            carga = "Si"
                        }else{
                            carga = "No"
                        }
                        if (nave.pasajeros == true){
                            pasajeros = "Si"
                        }else{
                            pasajeros = "No"
                        }
                        AlertDialog.Builder(context)
                            .setTitle("@string/detallepiloto")
                            .setMessage("Matrícula: ${nave.matricula}\n" +
                                    "Tipo: ${nave.tipo}\n" +
                                    "¿Puede llevar carga?: ${carga}\n" +
                                    "¿Puede llevar pasajeros?: ${pasajeros}\n" +
                                    "Foto: ${nave.foto}")
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