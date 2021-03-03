package com.example.financask.ui.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import com.example.financask.dao.TransacaoDAO
import com.example.financask.databinding.ActivityListaTransacoesBinding
import com.example.financask.model.Tipo
import com.example.financask.model.Transacao
import com.example.financask.ui.ResumoView
import com.example.financask.ui.adapter.ListaTransacoesAdapter
import com.example.financask.ui.dialog.AdicionaTransacaoDialog
import com.example.financask.ui.dialog.AlteraTransacaoDialog

class ListaTransacoesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListaTransacoesBinding

    private val dao = TransacaoDAO()
    private val transacoes: List<Transacao> = dao.transacoes

    private val viewDaActivity by lazy {
        window.decorView
    }

    private val viewGroupDaActivity by lazy {
        viewDaActivity as ViewGroup
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListaTransacoesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        calculaResumo()
        configuraLista()

        binding.listaTransacoesAdicionaReceita.setOnClickListener {
            chamaDialogAdicao(Tipo.RECEITA)
        }

        binding.listaTransacoesAdicionaDespesa.setOnClickListener {
            chamaDialogAdicao(Tipo.DESPESA)
        }
    }

    private fun chamaDialogAdicao(tipo: Tipo) {
        AdicionaTransacaoDialog(viewGroupDaActivity, this)
                .chama(tipo) { transacaoCriada ->
                        adiciona(transacaoCriada)
                        binding.listaTransacoesAdicionaMenu.close(true)
                }
    }

    private fun adiciona(transacao: Transacao) {
        dao.adiciona(transacao)
        atualizaTransacao()
    }

    private fun atualizaTransacao() {
        //transacoes.add(transacaoCriada)
        calculaResumo()
        configuraLista()
    }

    private fun calculaResumo() {
        val resumoView = ResumoView(this, binding, transacoes)
        resumoView.atualiza()
    }

    private fun configuraLista() {
        val listaTransacoesAdapter = ListaTransacoesAdapter(transacoes, this)
        with(binding.listaTransacoesListview) {
            adapter = listaTransacoesAdapter
            setOnItemClickListener { _, _, position, _ ->
                val transacao = transacoes[position]
                chamaDialogDeAlteracao(transacao, position)
            }
            setOnCreateContextMenuListener { menu, _, _ ->
                menu.add(Menu.NONE, 1, Menu.NONE, "Remover")
            }
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        if (item.itemId == 1){
            val adapterMenuInfo = item.menuInfo as AdapterView.AdapterContextMenuInfo
            val posicaoDaTransacao = adapterMenuInfo.position
            remove(posicaoDaTransacao)
        }
        return super.onContextItemSelected(item)
    }

    private fun remove(posicao  : Int) {
        dao.remove(posicao)
        atualizaTransacao()
    }

    private fun chamaDialogDeAlteracao(transacao: Transacao, position: Int) {
        AlteraTransacaoDialog(viewGroupDaActivity, this)
                .chama(transacao) {transacaoModificada ->
                        altera(transacaoModificada, position)
                }
    }

    private fun altera(transacao: Transacao, position: Int) {
        dao.altera(transacao,position)
        atualizaTransacao()
    }

}