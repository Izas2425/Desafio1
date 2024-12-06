package adaptadores

import adaptadores.AdaptadorRvPilotos.ViewHolder
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
import com.example.desafiotopdarkcliente.ui.FragmentoVNavesViewModel
import com.example.desafiotopdarkcliente.ui.FragmentoVPilotosViewModel
import modelo.MostrarNave


class AdaptadorRvNaves (
    var naves: ArrayList<MostrarNave>,
    var context: Context,
    private val viewModelVNavesViewModel: FragmentoVNavesViewModel) :  RecyclerView.Adapter<AdaptadorRvNaves.ViewHolder>(){

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = naves.get(position)
        holder.bind(item, context,position, this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.item_card_nave, parent, false)
        val viewHolder = ViewHolder(vista)
        return viewHolder
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
        fun bind(nav: MostrarNave, context: Context, pos: Int, adaptadorRvDatos: AdaptadorRvNaves){
            matriculaNave.text = nav.matricula
            tipoNave.text = nav.tipo

            btnDetalleN.setOnClickListener {

                adaptadorRvDatos.viewModelVNavesViewModel.getNaveVM(nav.matricula!!)

                adaptadorRvDatos.viewModelVNavesViewModel.myResponse.observe(context as LifecycleOwner){ nave ->
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
                            .setTitle("Detalles de la nave")
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

            itemView.setOnLongClickListener(View.OnLongClickListener {

                AlertDialog.Builder(context)
                    .setTitle("Aviso")
                    .setMessage("¿Seguro que quiere eliminar a ${nav.matricula}")
                    .setPositiveButton("Si", DialogInterface.OnClickListener(function = {dialog: DialogInterface?, which: Int ->

                        adaptadorRvDatos.viewModelVNavesViewModel.deleteNavVM(nav.matricula!!)

                        adaptadorRvDatos.notifyDataSetChanged()
                    }))
                    .setNegativeButton("No", ({ dialog: DialogInterface, which: Int ->

                    }))
                    .show()
                true
            })
        }


        }
    }


