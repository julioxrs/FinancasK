    package com.example.financask.ui.dialog

import android.content.Context
import android.view.ViewGroup
import com.example.financask.R
import com.example.financask.extension.formataParaBrasileiro
import com.example.financask.model.Tipo
import com.example.financask.model.Transacao

class AlteraTransacaoDialog(
        view: ViewGroup,
        private val context: Context): FormularioTransacaoDialog(context, view){

    override val textoPositiveButton: String
        get() = "Alterar"

    fun chama(transacao: Transacao, delegate: (transacao : Transacao) -> Unit) {
        val tipo = transacao.tipo
        super.chama(tipo, delegate)
        inicializaCampos(transacao)
    }

    private fun inicializaCampos(transacao: Transacao) {
        iniciaCampoValor(transacao)
        iniciaCampoData(transacao)
        iniciaCampoCategoria(transacao)
    }

    private fun iniciaCampoCategoria(transacao: Transacao) {
        val categoriasRetornadas = context.resources.getStringArray(categoriaPor(transacao.tipo))
        val posicaoCategoria = categoriasRetornadas.indexOf(transacao.categoria)
        campoCategoria.setSelection(posicaoCategoria, true)
    }

    private fun iniciaCampoData(transacao: Transacao) {
        campoData.setText(transacao.data.formataParaBrasileiro())
    }

    private fun iniciaCampoValor(transacao: Transacao) {
        campoValor.setText(transacao.valor.toString())
    }

    override fun tituloPor(tipo: Tipo): Int {
       return if(tipo == Tipo.RECEITA) {
            R.string.altera_receita
        } else {
            R.string.altera_despesa
        }
    }


}