package adaptadores

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
import api.NaveViewModel
import com.example.desafiotopdarkcliente.R
import com.example.desafiotopdarkcliente.ui.FragmentoVMisionesViewModel
import modelo.MostrarMision
import modelo.MostrarNave

class AdaptadorRvMisiones(
    var misiones: ArrayList<MostrarMision>,
    var context: Context,
    private val viewModelVMisionesViewModel: FragmentoVMisionesViewModel,
    private val viewModelVNaveViewModel: NaveViewModel): RecyclerView.Adapter<AdaptadorRvMisiones.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.item_card_mision, parent, false)
        val viewHolder = ViewHolder(vista)
        return viewHolder
    }

    override fun getItemCount(): Int {
       return misiones.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(nuevaLista: ArrayList<MostrarMision>){
        misiones = nuevaLista
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = misiones.get(position)
        holder.bind(item, context, position, this)
    }

    class ViewHolder(view: View):RecyclerView.ViewHolder(view){

        val nombreMision = view.findViewById(R.id.tvNombreM) as TextView
        val experienciaMision = view.findViewById(R.id.tvExperienciaM) as TextView

        val btnDetelleM = view.findViewById(R.id.btnDetalleM) as Button

        @SuppressLint("ResourceAsColor")
        fun bind(mis: MostrarMision, context: Context, pos: Int, adaptadorRvDatos: AdaptadorRvMisiones){
            nombreMision.text = mis.nombre
            experienciaMision.text = mis.experiencia

            btnDetelleM.setOnClickListener {

                adaptadorRvDatos.viewModelVMisionesViewModel.getMisionVM(mis.idmision!!)

                adaptadorRvDatos.viewModelVMisionesViewModel.myResponseM.observe(context as LifecycleOwner){ mision ->
                    mision?.let {
                        AlertDialog.Builder(context)
                            .setTitle("Detalle la misión")
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

            itemView.setOnLongClickListener(View.OnLongClickListener {

                adaptadorRvDatos.viewModelVMisionesViewModel.getMisionDeleteVM(mis.idmision!!)
                adaptadorRvDatos.viewModelVMisionesViewModel.myResponseDelete.observe(context as LifecycleOwner){mision ->
                    mision?.let {
                        AlertDialog.Builder(context)
                            .setTitle("Aviso")
                            .setMessage("¿Seguro que quiere eliminar a ${mis.nombre}")
                            .setPositiveButton(
                                "Si",
                                DialogInterface.OnClickListener(function = { dialog: DialogInterface?, which: Int ->
                                    adaptadorRvDatos.viewModelVMisionesViewModel.deleteMisionVM(mis.idmision!!)

                                    obtenerNaveYEliminarSegunTipo(mision.matriculanave!!, adaptadorRvDatos, context, mis.idmision!!)
                                    adaptadorRvDatos.notifyDataSetChanged()
                                }))
                            .setNegativeButton("No", ({ dialog: DialogInterface, which: Int ->

                            }))
                            .show()
                    }
                }
                true
            })
        }

        fun obtenerNaveYEliminarSegunTipo(
            matriculaNave: String,
            adaptadorRvDatos: AdaptadorRvMisiones,
            context: Context,
            idmision: Int){

            adaptadorRvDatos.viewModelVNaveViewModel.getNaveVM(matriculaNave)
            adaptadorRvDatos.viewModelVNaveViewModel.myResponse.observe(context as LifecycleOwner){ nave ->
                nave?.let{
                    when(nave.tipo){
                        "Vuelo"->adaptadorRvDatos.viewModelVMisionesViewModel.deleteVueloVM(idmision)
                        "Combate"->adaptadorRvDatos.viewModelVMisionesViewModel.deleteCombateVM(idmision)
                        "Bombardero"->adaptadorRvDatos.viewModelVMisionesViewModel.deleteBombarderoVM(idmision)
                    }
                }
            }
        }



    }
}