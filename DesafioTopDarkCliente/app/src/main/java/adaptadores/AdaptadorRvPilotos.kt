package adaptadores

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.desafiotopdarkcliente.R
import com.example.desafiotopdarkcliente.ui.FragmentoVPilotosViewModel
import modelo.MostrarPiloto
import modelo.Usuario

class AdaptadorRvPilotos (
    var pilotos: ArrayList<MostrarPiloto>,
    var context: Context,
    var viewModelVPilotosViewModel: FragmentoVPilotosViewModel) : RecyclerView.Adapter<AdaptadorRvPilotos.ViewHolder>(){

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = pilotos.get(position)
        holder.bind(item,context, position, this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.item_card_piloto, parent, false)
        val viewHolder = ViewHolder(vista)



        return viewHolder
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
        fun bind(user: MostrarPiloto, context: Context, pos: Int, adaptadorRvDatos: AdaptadorRvPilotos){
            nombrePiloto.text = user.nombre
            nivelPiloto.text = user.nivel

            btnDetalleP.setOnClickListener {


               adaptadorRvDatos.viewModelVPilotosViewModel.getUsuarioVM(user.id!!)

                adaptadorRvDatos.viewModelVPilotosViewModel.myResponse.observe(context as LifecycleOwner){ piloto ->
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


            itemView.setOnLongClickListener(View.OnLongClickListener {
                Log.e("Izaskun", "has pulsado el boton de ${user.nombre}")
                AlertDialog.Builder(context)
                    .setTitle("Aviso")
                    .setMessage("¿Seguro que quieres eliminar a ${user.nombre}")
                    .setPositiveButton("Si", DialogInterface.OnClickListener(function = {dialog: DialogInterface?, which: Int ->

                        adaptadorRvDatos.viewModelVPilotosViewModel.deletePilotoVM(user)

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

    
