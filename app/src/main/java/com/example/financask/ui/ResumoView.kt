package com.example.financask.ui

import android.content.Context
import androidx.core.content.ContextCompat
import com.example.financask.R
import com.example.financask.databinding.ActivityListaTransacoesBinding
import com.example.financask.extension.formataParaBrasileiro
import com.example.financask.model.Resumo
import com.example.financask.model.Transacao
import java.math.BigDecimal

class ResumoView(private val context: Context,
                 private val binding: ActivityListaTransacoesBinding,
                 transacoes: List<Transacao>) {

    private val resumo = Resumo(transacoes)
    private val corReceita = ContextCompat.getColor(context, R.color.receita)
    private val corDespesa = ContextCompat.getColor(context, R.color.despesa)

    fun atualiza(){
        somaReceita()
        somaDespesa()
        somaTotal()
    }

    private fun somaReceita() {
        val totalReceita = resumo.receita
        with(binding.listaTransacoesResumo.resumoCardReceita){
            setTextColor(corReceita)
            text = totalReceita.formataParaBrasileiro()
        }
    }

    private fun somaDespesa() {
        val totalDespesa = resumo.despesa
        with(binding.listaTransacoesResumo.resumoCardDespesa){
            setTextColor(corDespesa)
            text = totalDespesa.formataParaBrasileiro()
        }
    }

    private fun somaTotal(){
        val total = resumo.total
        val cor = corPor(total)
        with(binding.listaTransacoesResumo.resumoCardTotal){
            setTextColor(cor)
            text = total.formataParaBrasileiro()
        }
    }

    private fun corPor(valor: BigDecimal) :Int {
        if (valor >= BigDecimal.ZERO) {
            return corReceita
        }
        return  corDespesa
    }
}