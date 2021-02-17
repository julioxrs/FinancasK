package com.example.financask.ui.activity

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.financask.R
import com.example.financask.databinding.ActivityListaTransacoesBinding
import com.example.financask.databinding.FormTransacaoBinding
import com.example.financask.extension.formataParaBrasileiro
import com.example.financask.model.Tipo
import com.example.financask.model.Transacao
import com.example.financask.ui.ResumoView
import com.example.financask.ui.adapter.ListaTransacoesAdapter
import java.lang.NumberFormatException
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.*

class ListaTransacoesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListaTransacoesBinding

    private val transacoes : MutableList<Transacao> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListaTransacoesBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        calculaResumo()

        configuraLista()

        binding.listaTransacoesAdicionaReceita.setOnClickListener {
            val decorView : View  = window.decorView
            val formView = FormTransacaoBinding.inflate(LayoutInflater.from(this),
                    decorView as ViewGroup,
                    false)

            val ano = 2017
            val mes = 9
            val dia = 18

            val hoje = Calendar.getInstance()

            formView.formTransacaoData.setText(hoje.formataParaBrasileiro())

            formView.formTransacaoData.setOnClickListener {
                DatePickerDialog(this,
                        DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                            val dataSelecionada = Calendar.getInstance()
                            dataSelecionada.set(year, month, dayOfMonth)
                            formView.formTransacaoData.setText(dataSelecionada.formataParaBrasileiro())
                        }, ano, mes, dia)
                        .show()
            }

            val adapter = ArrayAdapter.createFromResource(this,
                    R.array.categorias_de_receita,
                    android.R.layout.simple_spinner_dropdown_item)
            formView.formTransacaoCategoria.adapter = adapter


            AlertDialog.Builder(this)
                    .setTitle(R.string.adiciona_receita)
                    .setView(formView.root)
                    .setPositiveButton("Adicionar") { dialog, which ->
                        val dataEmTexto = formView.formTransacaoData.text.toString()
                        val valorEmTexto = formView.formTransacaoValor.text.toString()
                        val categoriaEmTexto = formView.formTransacaoCategoria.selectedItem.toString()

                        val valor = try {
                            BigDecimal(valorEmTexto)
                        }catch (exception : NumberFormatException){
                            Toast.makeText(this,
                                    "Erro na conversão do valor.",
                                    Toast.LENGTH_LONG).show()
                            BigDecimal.ZERO
                        }

                        val formatoBrasileiro = SimpleDateFormat("dd/MM/yyyy")
                        val dataConvertida = formatoBrasileiro.parse(dataEmTexto)
                        val data = Calendar.getInstance()
                        data.time = dataConvertida

                        val transacaoCriada = Transacao(tipo = Tipo.RECEITA,
                                data = data,
                                valor = valor,
                                categoria = categoriaEmTexto)
                        Toast.makeText(this, "${transacaoCriada.data.formataParaBrasileiro()} - " +
                                "${transacaoCriada.valor} - " +
                                "${transacaoCriada.categoria} - " +
                                "${transacaoCriada.tipo}", Toast.LENGTH_LONG).show()

                        atualizaTransacao(transacaoCriada)
                        binding.listaTransacoesAdicionaMenu.close(true)
                    }
                    .setNegativeButton("Cancelar", null)
                    .show()
            
        }

    }

    private fun atualizaTransacao(transacaoCriada: Transacao) {
        transacoes.add(transacaoCriada)
        calculaResumo()
        configuraLista()
    }

    private fun calculaResumo() {
        val resumoView = ResumoView(this, binding, transacoes)
        resumoView.atualiza()
    }

    private fun configuraLista() {
        binding.listaTransacoesListview.adapter = ListaTransacoesAdapter(transacoes, this)
    }

}