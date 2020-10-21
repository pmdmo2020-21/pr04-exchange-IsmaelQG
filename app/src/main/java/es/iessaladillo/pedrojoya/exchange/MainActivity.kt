package es.iessaladillo.pedrojoya.exchange

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import es.iessaladillo.pedrojoya.exchange.databinding.MainActivityBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding
    private var idFromCurrency = R.id.rdbFromCurrencyEuro
    private var idToCurrency = R.id.rdbToCurrencyDollar
    private var symbolFromCurrency = Currency.EURO.symbol
    private var symbolToCurrency = Currency.DOLLAR.symbol
    private var initValue = 0.0
    private var convValue = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fromCurrencyCheck()
        toCurrencyCheck()
        setupViews()
    }

    private fun setupViews(){
        binding.txtAmount.selectAll()
        binding.rdbFromCurrencyEuro.isChecked = true
        binding.rdbToCurrencyDollar.isChecked = true
        binding.btnExchange.setOnClickListener(){ v ->
            calc()
            closeKeyBoard()
        }
        binding.txtAmount.setOnEditorActionListener { v, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_DONE){
                calc()
                false
            } else {
                true
            }
        }
        changeFromCurrencyCheckImg(R.id.rdbFromCurrencyEuro)
        changeFromCurrencyCheckImg(R.id.rdbToCurrencyDollar)
        money()
    }

    private fun closeKeyBoard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    fun toEditable(i: String): Editable {
        return Editable.Factory.getInstance().newEditable(i)
    }

    private fun money(){
        binding.txtAmount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (binding.txtAmount.text.isBlank()) {
                    binding.txtAmount.text = toEditable("0")
                    binding.txtAmount.selectAll()
                }
                initValue = binding.txtAmount.text.toString().toDouble()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
        binding.txtAmount.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                binding.txtAmount.selectAll()
            }
        })
        binding.txtAmount.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                binding.lblAmount.setTextColor(resources.getColor(R.color.pink_200))
            } else {
                binding.lblAmount.setTextColor(resources.getColor(R.color.design_default_color_on_primary))
            }
        }
    }


    private fun fromCurrencyCheck(){
        binding.rdgFromCurrency.setOnCheckedChangeListener(){ view, idButton ->
            changeFromCurrencyCheckImg(idButton)
            idFromCurrency = idButton
        }
    }

    private fun changeFromCurrencyCheckImg(idButton: Int){
        when(idButton){
            R.id.rdbFromCurrencyDollar -> {
                binding.imgFromCurrency.setImageResource(Currency.DOLLAR.drawableResId)
                binding.rdbToCurrencyDollar.isEnabled = false
                binding.rdbToCurrencyEuro.isEnabled = true
                binding.rdbToCurrencyPound.isEnabled = true
                symbolFromCurrency = Currency.DOLLAR.symbol;
            }
            R.id.rdbFromCurrencyEuro -> {
                binding.imgFromCurrency.setImageResource(Currency.EURO.drawableResId)
                binding.rdbToCurrencyDollar.isEnabled = true
                binding.rdbToCurrencyEuro.isEnabled = false
                binding.rdbToCurrencyPound.isEnabled = true
                symbolFromCurrency = Currency.EURO.symbol;
            }
            R.id.rdbFromCurrencyPound -> {
                binding.imgFromCurrency.setImageResource(Currency.POUND.drawableResId)
                binding.rdbToCurrencyDollar.isEnabled = true
                binding.rdbToCurrencyEuro.isEnabled = true
                binding.rdbToCurrencyPound.isEnabled = false
                symbolFromCurrency = Currency.POUND.symbol;
            }
        }
    }

    private fun toCurrencyCheck(){
        binding.rdgToCurrency.setOnCheckedChangeListener(){ view, idButton ->
            changeToCurrencyCheckImg(idButton)
            idToCurrency = idButton
        }
    }

    private fun changeToCurrencyCheckImg(idButton: Int){
        when(idButton){
            R.id.rdbToCurrencyDollar -> {
                binding.imgToCurrency.setImageResource(Currency.DOLLAR.drawableResId)
                binding.rdbFromCurrencyDollar.isEnabled = false
                binding.rdbFromCurrencyEuro.isEnabled = true
                binding.rdbFromCurrencyPound.isEnabled = true
                symbolToCurrency = Currency.DOLLAR.symbol
            }
            R.id.rdbToCurrencyEuro -> {
                binding.imgToCurrency.setImageResource(Currency.EURO.drawableResId)
                binding.rdbFromCurrencyDollar.isEnabled = true
                binding.rdbFromCurrencyEuro.isEnabled = false
                binding.rdbFromCurrencyPound.isEnabled = true
                symbolToCurrency = Currency.EURO.symbol
            }
            R.id.rdbToCurrencyPound -> {
                binding.imgToCurrency.setImageResource(Currency.POUND.drawableResId)
                binding.rdbFromCurrencyDollar.isEnabled = true
                binding.rdbFromCurrencyEuro.isEnabled = true
                binding.rdbFromCurrencyPound.isEnabled = false
                symbolToCurrency = Currency.POUND.symbol
            }
        }

    }

    private fun calc(){
        var dollar = 0.0;
        dollar = Currency.DOLLAR.toDollar(initValue)
        when(idFromCurrency){
            R.id.rdbFromCurrencyDollar -> dollar = Currency.DOLLAR.toDollar(initValue)
            R.id.rdbFromCurrencyEuro -> dollar = Currency.EURO.toDollar(initValue)
            R.id.rdbFromCurrencyPound -> dollar = Currency.POUND.toDollar(initValue)
        }
        when(idToCurrency){
            R.id.rdbToCurrencyDollar -> convValue = Currency.DOLLAR.fromDollar(dollar)
            R.id.rdbToCurrencyEuro -> convValue = Currency.EURO.fromDollar(dollar)
            R.id.rdbToCurrencyPound -> convValue = Currency.POUND.fromDollar(dollar)
        }
        Toast.makeText(this, String.format("%.2f", initValue)
                                    .plus(symbolFromCurrency)
                                    .plus(" = ")
                                    .plus(String.format("%.2f", convValue))
                                    .plus(symbolToCurrency), Toast.LENGTH_SHORT).show()
    }

}