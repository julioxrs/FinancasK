package com.example.financask.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.core.content.ContextCompat
import com.example.financask.R
import com.example.financask.databinding.TransacaoItemBinding
import com.example.financask.extension.formataParaBrasileiro
import com.example.financask.extension.limitaEmAte
import com.example.financask.model.Tipo
import com.example.financask.model.Transacao

class ListaTransacoesAdapter(private val transacoes : List<Transacao>,
                             private val context : Context) : BaseAdapter() {


    override fun getCount(): Int {
        return transacoes.size
    }

    override fun getItem(position: Int): Transacao {
        return transacoes[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    private val limiteDeCaracteres = 14

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val viewCriada = TransacaoItemBinding.inflate(LayoutInflater.from(context), parent, false)
        val transacao = transacoes[position]

        adicionaTransacao(transacao, viewCriada)

        return viewCriada.root
    }

    private fun adicionaTransacao(transacao: Transacao, viewCriada: TransacaoItemBinding) {
        adicionaValor(transacao, viewCriada)
        adicionaIcone(transacao, viewCriada)
        adicionaCategoria(viewCriada, transacao)
        adcionaData(viewCriada, transacao)
    }

    private fun adcionaData(viewCriada: TransacaoItemBinding, transacao: Transacao) {
        viewCriada.transacaoData.text = transacao.data.formataParaBrasileiro()
    }

    private fun adicionaCategoria(viewCriada: TransacaoItemBinding, transacao: Transacao) {
        viewCriada.transacaoCategoria.text = transacao.categoria.limitaEmAte(limiteDeCaracteres)
    }

    private fun adicionaIcone(transacao: Transacao, viewCriada: TransacaoItemBinding) {
        val icone: Int = iconePor(transacao.tipo)
        viewCriada.transacaoIcone.setBackgroundResource(icone)
    }

    private fun iconePor(tipo: Tipo) =
            when (tipo) {
                Tipo.RECEITA -> R.drawable.icone_transacao_item_receita
                Tipo.DESPESA -> R.drawable.icone_transacao_item_despesa
            }

    private fun adicionaValor(transacao: Transacao, viewCriada: TransacaoItemBinding) {
        val cor = corPor(transacao.tipo)
        viewCriada.transacaoValor.setTextColor(cor)
        viewCriada.transacaoValor.text = transacao.valor.formataParaBrasileiro()
    }

    private fun corPor(tipo: Tipo): Int {
        when (tipo) {
            Tipo.RECEITA -> return ContextCompat.getColor(context, R.color.receita)
            Tipo.DESPESA -> return ContextCompat.getColor(context, R.color.despesa)
        }
    }
}