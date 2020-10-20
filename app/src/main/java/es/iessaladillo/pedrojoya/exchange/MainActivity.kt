package es.iessaladillo.pedrojoya.exchange

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.text.set
import androidx.core.view.ViewCompat
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import es.iessaladillo.pedrojoya.exchange.databinding.MainActivityBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fromCurrencyCheck()
        toCurrencyCheck()
        start()
    }

    private fun start(){
        binding.txtAmount.selectAll()
        binding.rdbFromCurrencyEuro.isChecked = true
        binding.rdbToCurrencyDollar.isChecked = true
        changeFromCurrencyCheckImg(R.id.rdbFromCurrencyEuro)
        changeFromCurrencyCheckImg(R.id.rdbToCurrencyDollar)
        money()
    }

    fun toEditable(i: String): Editable {
        return Editable.Factory.getInstance().newEditable(i)
    }

    private fun money(){
        binding.txtAmount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (binding.txtAmount.text.isBlank()){
                    binding.txtAmount.text = toEditable("0")
                    binding.txtAmount.selectAll()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }

    private fun fromCurrencyCheck(){
        binding.rdgFromCurrency.setOnCheckedChangeListener(){ view, idButton -> changeFromCurrencyCheckImg(idButton)}
    }

    private fun changeFromCurrencyCheckImg(idButton : Int){
        when(idButton){
            R.id.rdbFromCurrencyDollar -> {
                binding.imgFromCurrency.setImageResource(R.drawable.ic_dollar)
                binding.rdbToCurrencyDollar.isEnabled = false
                binding.rdbToCurrencyEuro.isEnabled = true
                binding.rdbToCurrencyPound.isEnabled = true
            }
            R.id.rdbFromCurrencyEuro -> {
                binding.imgFromCurrency.setImageResource(R.drawable.ic_euro)
                binding.rdbToCurrencyDollar.isEnabled = true
                binding.rdbToCurrencyEuro.isEnabled = false
                binding.rdbToCurrencyPound.isEnabled = true
            }
            R.id.rdbFromCurrencyPound -> {
                binding.imgFromCurrency.setImageResource(R.drawable.ic_pound)
                binding.rdbToCurrencyDollar.isEnabled = true
                binding.rdbToCurrencyEuro.isEnabled = true
                binding.rdbToCurrencyPound.isEnabled = false
            }
        }
    }

    private fun toCurrencyCheck(){
        binding.rdgToCurrency.setOnCheckedChangeListener(){ view, idButton -> changeToCurrencyCheckImg(idButton)}
    }

    private fun changeToCurrencyCheckImg(idButton : Int){
        when(idButton){
            R.id.rdbToCurrencyDollar -> {
                binding.imgToCurrency.setImageResource(R.drawable.ic_dollar)
                binding.rdbFromCurrencyDollar.isEnabled = false
                binding.rdbFromCurrencyEuro.isEnabled = true
                binding.rdbFromCurrencyPound.isEnabled = true
            }
            R.id.rdbToCurrencyEuro -> {
                binding.imgToCurrency.setImageResource(R.drawable.ic_euro)
                binding.rdbFromCurrencyDollar.isEnabled = true
                binding.rdbFromCurrencyEuro.isEnabled = false
                binding.rdbFromCurrencyPound.isEnabled = true
            }
            R.id.rdbToCurrencyPound -> {
                binding.imgToCurrency.setImageResource(R.drawable.ic_pound)
                binding.rdbFromCurrencyDollar.isEnabled = true
                binding.rdbFromCurrencyEuro.isEnabled = true
                binding.rdbFromCurrencyPound.isEnabled = false
            }
        }
    }


}