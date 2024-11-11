package adaptadores

import adaptadores.AdaptadorRvPilotos.ViewHolder
import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.desafiotopdarkcliente.R
import com.example.desafiotopdarkcliente.ui.FragmentoVNavesViewModel
import com.example.desafiotopdarkcliente.ui.FragmentoVPilotosViewModel
import modelo.MostrarNave
import modelo.MostrarPiloto

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


        @SuppressLint("ResourceAsColor")
        fun bind(nav: MostrarNave, context: Context, pos: Int, adaptadorRvDatos: AdaptadorRvNaves){
            matriculaNave.text = nav.matricula
            tipoNave.text = nav.tipo
        }
    }

    }
