package com.example.acamove.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.acamove.R

class HomeFragment : Fragment() {
//Home es Rutas

    private lateinit var homeViewModel: HomeViewModel
    var listaRutas: ArrayList<String> = ArrayList()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
val rutas = view?.findViewById<ListView>(R.id.lista_de_rutas)

        listaRutas.add("Rutas de Acabus")
//ArrayAdapter<String>

        return root
    }
}