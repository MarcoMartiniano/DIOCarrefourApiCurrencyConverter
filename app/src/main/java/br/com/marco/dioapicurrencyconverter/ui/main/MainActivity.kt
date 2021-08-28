package br.com.marco.dioapicurrencyconverter.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.core.widget.doAfterTextChanged
import br.com.marco.dioapicurrencyconverter.R
import br.com.marco.dioapicurrencyconverter.core.extensions.*
import br.com.marco.dioapicurrencyconverter.data.model.Coin
import br.com.marco.dioapicurrencyconverter.databinding.ActivityMainBinding
import br.com.marco.dioapicurrencyconverter.presentation.MainViewModel
import br.com.marco.dioapicurrencyconverter.ui.history.HistoryActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModel<MainViewModel>()
    private val dialog by lazy { createProgressDialog() }
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        bindAdapters()
        bindListeners()
        bindObserve()

        setSupportActionBar(binding.toolbar)




    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_history) {
            startActivity(Intent(this, HistoryActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

    private fun bindAdapters() {
        val list = Coin.values()
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, list)

        binding.tvFrom.setAdapter(adapter)
        binding.tvTo.setAdapter(adapter)

        binding.tvFrom.setText(Coin.USD.name, false)
        binding.tvTo.setText(Coin.BRL.name, false)
    }

    private fun bindListeners() {
        binding.tilValue.editText?.doAfterTextChanged {
            binding.btnConverter.isEnabled = it != null && it.toString().isNotEmpty()
            binding.btnSave.isEnabled = false
        }

        binding.btnConverter.setOnClickListener {
            it.hideSoftKeyboard()

            val search = "${binding.tilFrom.text}-${binding.tilTo.text}"

            viewModel.getExchangeValue(search)
        }

        binding.btnSave.setOnClickListener {
            val value = viewModel.state.value
            (value as? MainViewModel.State.Success)?.let {
                val date = getCurrentDateTime()
                val dateInString = date.toString("dd/MM/yyyy HH:mm:ss")
                val exchange = it.exchange.copy(cotacao = it.exchange.bid, bid = it.exchange.bid * binding.tilValue.text.toDouble(),data = dateInString)
                viewModel.saveExchange(exchange)
            }
        }
    }

    private fun bindObserve() {
        viewModel.state.observe(this) {
            when (it) {
                MainViewModel.State.Loading -> dialog.show()
                is MainViewModel.State.Error -> {
                    dialog.dismiss()
                    createDialog {
                        setMessage(it.error.message)
                    }.show()
                }
                is MainViewModel.State.Success -> success(it)
                MainViewModel.State.Saved -> {
                    dialog.dismiss()
                    createDialog {
                        setMessage("item salvo com sucesso!")
                    }.show()
                }
            }
        }
    }

    private fun success(it: MainViewModel.State.Success) {
        dialog.dismiss()
        binding.btnSave.isEnabled = true

        val selectedCoin = binding.tilTo.text
        val coin = Coin.getByName(selectedCoin)

        val result = it.exchange.bid * binding.tilValue.text.toDouble()

        binding.tvResult.text = result.formatCurrency(coin.locale)
    }

    fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }

    fun getCurrentDateTime(): Date {
        return Calendar.getInstance().time
    }
}