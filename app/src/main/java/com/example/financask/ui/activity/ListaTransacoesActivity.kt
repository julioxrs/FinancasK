package com.example.financask.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.financask.R
import com.example.financask.databinding.ActivityListaTransacoesBinding
import com.example.financask.model.Tipo
import com.example.financask.model.Transacao

import com.example.financask.ui.adapter.ListaTransacoesAdapter
import java.math.BigDecimal

class ListaTransacoesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListaTransacoesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListaTransacoesBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val transacoes = transacoesExemplos()

        configuraLista(transacoes)

    }

    private fun configuraLista(transacoes: List<Transacao>) {
        binding.listaTransacoesListview.adapter = ListaTransacoesAdapter(transacoes, this)
    }

    private fun transacoesExemplos() = listOf(Transacao(valor = BigDecimal(10.0),
            tipo = Tipo.DESPESA),
            Transacao(valor = BigDecimal(11.0),
                    categoria = "lazer", Tipo.RECEITA))
}