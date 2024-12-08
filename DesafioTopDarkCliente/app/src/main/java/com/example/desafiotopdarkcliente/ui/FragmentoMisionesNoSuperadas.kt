package com.example.desafiotopdarkcliente.ui

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.desafiotopdarkcliente.R

class FragmentoMisionesNoSuperadas : Fragment() {

    companion object {
        fun newInstance() = FragmentoMisionesNoSuperadas()
    }

    private val viewModel: FragmentoMisionesNoSuperadasViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_fragmento_misiones_no_superadas, container, false)
    }
}