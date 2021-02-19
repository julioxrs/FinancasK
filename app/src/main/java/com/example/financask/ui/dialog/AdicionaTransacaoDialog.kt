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
import com.example.financask.delegate.TransacaoDelegate
import com.example.financask.extension.converteParaCalendar
import com.example.financask.extension.formataParaBrasileiro
import com.example.financask.model.Tipo
import com.example.financask.model.Transacao
import java.math.BigDecimal
import java.util.*

class AdicionaTransacaoDialog(private val view: ViewGroup,
                              private val context: Context) {

    private val formView = criaLayout()

     fun configuraDialog(transacaoDelegate: TransacaoDelegate) {

        configuraCampoData()
        configuraCampoCategoria()
        configuraFomulario(transacaoDelegate)
    }

    private fun configuraFomulario(transacaoDelegate: TransacaoDelegate) {
        AlertDialog.Builder(context)
                .setTitle(R.string.adiciona_receita)
                .setView(formView.root)
                .setPositiveButton("Adicionar") { _, _ ->
                    val dataEmTexto = formView.formTransacaoData.text.toString()
                    val valorEmTexto = formView.formTransacaoValor.text.toString()
                    val categoriaEmTexto = formView.formTransacaoCategoria.selectedItem.toString()

                    val valor = converteCampoValor(valorEmTexto)

                    val data = dataEmTexto.converteParaCalendar()

                    val transacaoCriada = Transacao(tipo = Tipo.RECEITA,
                            data = data,
                            valor = valor,
                            categoria = categoriaEmTexto)

                    transacaoDelegate.delegate(transacaoCriada)
                }
                .setNegativeButton("Cancelar", null)
                .show()
    }

    private fun converteCampoValor(valorEmTexto: String) = try {
        BigDecimal(valorEmTexto)
    } catch (exception: NumberFormatException) {
        Toast.makeText(context,
                "Erro na conversão do valor.",
                Toast.LENGTH_LONG).show()
        BigDecimal.ZERO
    }

    private fun configuraCampoCategoria() {
        val adapter = ArrayAdapter.createFromResource(context,
                R.array.categorias_de_receita,
                android.R.layout.simple_spinner_dropdown_item)
        formView.formTransacaoCategoria.adapter = adapter
    }

    private fun configuraCampoData() {
        val hoje = Calendar.getInstance()
        val ano = hoje.get(Calendar.YEAR)
        val mes = hoje.get(Calendar.MONTH)
        val dia = hoje.get(Calendar.DAY_OF_MONTH)


        formView.formTransacaoData.setText(hoje.formataParaBrasileiro())

        formView.formTransacaoData.setOnClickListener {
            DatePickerDialog(context,
                    { _, year, month, dayOfMonth ->
                        val dataSelecionada = Calendar.getInstance()
                        dataSelecionada.set(year, month, dayOfMonth)
                        formView.formTransacaoData.setText(dataSelecionada.formataParaBrasileiro())
                    }, ano, mes, dia)
                    .show()
        }
    }


    fun criaLayout() : FormTransacaoBinding {
        return FormTransacaoBinding.inflate(LayoutInflater.from(context),
                view,
                false)
    }

}