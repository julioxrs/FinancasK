package com.example.financask.ui.dialog

import android.content.Context
import android.view.ViewGroup
import com.example.financask.R
import com.example.financask.model.Tipo

class AdicionaTransacaoDialog(view: ViewGroup,
                              context: Context) : FormularioTransacaoDialog(context, view) {
    override val textoPositiveButton: String
        get() = "Adicionar"

    override fun tituloPor(tipo: Tipo): Int {
       return if(tipo == Tipo.RECEITA) {
            R.string.adiciona_receita
        } else {
            R.string.adiciona_despesa
        }
    }
}