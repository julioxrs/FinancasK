package com.example.financask.ui.activity

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.financask.R
import com.example.financask.model.Tipo
import com.example.financask.model.Transacao
import com.example.financask.ui.adapter.ListaTransacoesAdapter
import kotlinx.android.synthetic.main.activity_lista_transacoes.*
import java.math.BigDecimal
import java.util.*

class ListaTransacoesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_transacoes)

        val frutas = listOf(Transacao(valor = BigDecimal(10.0),
        tipo = Tipo.DESPESA),
        Transacao(valor = BigDecimal(11.0),
        categoria = "lazer", Tipo.RECEITA))

        lista_transacoes_listview.adapter = ListaTransacoesAdapter(frutas, this)

    }
}