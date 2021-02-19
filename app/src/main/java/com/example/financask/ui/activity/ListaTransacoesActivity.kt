package com.example.financask.ui.activity

import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.financask.databinding.ActivityListaTransacoesBinding
import com.example.financask.delegate.TransacaoDelegate
import com.example.financask.model.Transacao
import com.example.financask.ui.ResumoView
import com.example.financask.ui.adapter.ListaTransacoesAdapter
import com.example.financask.ui.dialog.AdicionaTransacaoDialog

class ListaTransacoesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListaTransacoesBinding

    private val transacoes: MutableList<Transacao> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListaTransacoesBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        calculaResumo()

        configuraLista()

        binding.listaTransacoesAdicionaReceita.setOnClickListener {
            AdicionaTransacaoDialog(window.decorView as ViewGroup, this)
                    .configuraDialog(object : TransacaoDelegate {
                        override fun delegate(transacao: Transacao) {
                            atualizaTransacao(transacao)
                            binding.listaTransacoesAdicionaMenu.close(true)
                        }
                    })
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