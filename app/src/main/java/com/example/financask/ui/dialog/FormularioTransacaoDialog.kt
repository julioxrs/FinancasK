package com.example.financask.ui.dialog

import android.app.DatePickerDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.financask.R
import com.example.financask.databinding.FormTransacaoBinding
import com.example.financask.extension.converteParaCalendar
import com.example.financask.extension.formataParaBrasileiro
import com.example.financask.model.Tipo
import com.example.financask.model.Transacao
import java.math.BigDecimal
import java.util.Calendar

abstract class FormularioTransacaoDialog(
      private val context: Context, 
      private val view: ViewGroup?) {
    
    private val formView = criaLayout()
    protected val campoData = formView.formTransacaoData
    protected val campoValor = formView.formTransacaoValor
    protected val campoCategoria = formView.formTransacaoCategoria
    
    fun chama(tipo: Tipo, delegate: (transacao: Transacao) -> Unit ) {
        configuraCampoData()
        configuraCampoCategoria(tipo)
        configuraFomulario(tipo, delegate)
    }

    protected abstract val textoPositiveButton : String

    private fun configuraFomulario(tipo: Tipo, delegate: (transacao: Transacao) -> Unit) {
        val titulo = tituloPor(tipo)

        AlertDialog.Builder(context)
                .setTitle(titulo)
                .setView(formView.root)
                .setPositiveButton(textoPositiveButton) { _, _ ->
                    val dataEmTexto = campoData.text.toString()
                    val valorEmTexto = campoValor.text.toString()
                    val categoriaEmTexto = campoCategoria.selectedItem.toString()

                    val valor = converteCampoValor(valorEmTexto)
                    val data = dataEmTexto.converteParaCalendar()

                    val transacaoCriada = Transacao(tipo = tipo,
                            data = data,
                            valor = valor,
                            categoria = categoriaEmTexto)

                    delegate(transacaoCriada)
                }
                .setNegativeButton("Cancelar", null)
                .show()
    }

   abstract fun tituloPor(tipo: Tipo): Int

    private fun converteCampoValor(valorEmTexto: String) = try {
        BigDecimal(valorEmTexto)
    } catch (exception: NumberFormatException) {
        Toast.makeText(context,
                "Erro na conversão do valor.",
                Toast.LENGTH_LONG).show()
        BigDecimal.ZERO
    }

    private fun configuraCampoCategoria(tipo: Tipo) {

        val categorias = categoriaPor(tipo)

        val adapter = ArrayAdapter.createFromResource(context,
                categorias,
                android.R.layout.simple_spinner_dropdown_item)
        campoCategoria.adapter = adapter
    }

    protected fun categoriaPor(tipo: Tipo) : Int {
        if (tipo == Tipo.RECEITA) {
            return R.array.categorias_de_receita
        }
        return R.array.categorias_de_despesa
    }

    private fun configuraCampoData() {
        val hoje = Calendar.getInstance()
        val ano = hoje.get(Calendar.YEAR)
        val mes = hoje.get(Calendar.MONTH)
        val dia = hoje.get(Calendar.DAY_OF_MONTH)


        campoData.setText(hoje.formataParaBrasileiro())

        campoData.setOnClickListener {
            DatePickerDialog(context,
                    { _, year, month, dayOfMonth ->
                        val dataSelecionada = Calendar.getInstance()
                        dataSelecionada.set(year, month, dayOfMonth)
                        campoData.setText(dataSelecionada.formataParaBrasileiro())
                    }, ano, mes, dia)
                    .show()
        }
    }

   private fun criaLayout() : FormTransacaoBinding {
        return FormTransacaoBinding.inflate(LayoutInflater.from(context),
                view,
                false)
    }
}