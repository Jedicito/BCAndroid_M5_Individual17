package chl.ancud.m5_individual17

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import chl.ancud.m5_individual17.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val divisas = listOf("USD", "CLP", "EUR")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.spinner1.adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, divisas)
        binding.spinner2.adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, divisas)

        initListener()
    }

    private fun initListener(){
        binding.btnConvertir.setOnClickListener{
            val monto = binding.editTextNumber1.text.toString().toDouble()
            val divisaActual = binding.spinner1.selectedItem.toString()
            val divisaCambio = binding.spinner2.selectedItem.toString()

            val resultado = conversorDivisas(monto, divisaActual, divisaCambio)
            binding.txvResultado.text = resultado.toString()
        }

        binding.btnLimpiar.setOnClickListener{
            binding.editTextNumber1.text.clear()
            binding.txvResultado.text = ""
        }
    }

    fun conversorDivisas(monto: Double, divisaActual: String, divisaCambio: String): Double {
        var factorCambio: Double = 0.0

        when(divisaActual) {
            "USD" -> when (divisaCambio) {
                        "USD" -> factorCambio = 1.0
                        "CLP" -> factorCambio = 817.0
                        "EUR" -> factorCambio = 0.89
            }
            "CLP" -> when (divisaCambio) {
                        "USD" -> factorCambio = 0.001
                        "CLP" -> factorCambio = 1.0
                        "EUR" -> factorCambio = 0.001
            }
            "EUR" -> when (divisaCambio) {
                        "USD" -> factorCambio = 1.11
                        "CLP" -> factorCambio = 910.0
                        "EUR" -> factorCambio = 1.0
            }
        }

        return monto * factorCambio
    }
}