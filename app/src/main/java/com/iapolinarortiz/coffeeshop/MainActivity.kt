package com.iapolinarortiz.coffeeshop

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.iapolinarortiz.coffeeshop.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding
    private var totalPrice = 0.0
    private var name = "No name"
    private var hasChocolate = false
    private var hasWhippedCream = false
    private var quantity = 0

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("OnCreated", "OnCreate getting called")
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        val quantityTextView: TextView = activityMainBinding.tvQuantity
        val tvPrice: TextView = activityMainBinding.tvPrice
        val cbWhippedCream: CheckBox = activityMainBinding.cbToppingWhippedcream
        cbWhippedCream.setOnCheckedChangeListener { _, isChecked ->
            hasWhippedCream = isChecked
            getTotalPrice(tvPrice)
        }
        val cbChocolate: CheckBox = activityMainBinding.cbToppingChocolate
        cbChocolate.setOnCheckedChangeListener { _, isChecked ->
            hasChocolate = isChecked
            getTotalPrice(tvPrice)
        }
        val etName: EditText = activityMainBinding.etUserName
        etName.addTextChangedListener { text: Editable? -> name = text.toString() }
        val tvOrderTicket: TextView = activityMainBinding.tvTicket
        val btnOrder: Button = activityMainBinding.btnOrder
        btnOrder.setOnClickListener {
            getTotalPrice(tvPrice)
            tvOrderTicket.text = "Name: ${this.name}\n" +
                    "Quantity: ${this.quantity} cups of coffee\n" +
                    "Total price: ${this.totalPrice}\n" +
                    "Whipped cream: ${this.hasWhippedCream}\n" +
                    "Chocolate: ${this.hasChocolate}\n" +
                    "Thank you!"
        }
        val btnCancel: Button = activityMainBinding.btnCancel
        btnCancel.setOnClickListener {
            cancelOrder(
                quantityTextView,
                cbWhippedCream,
                cbChocolate,
                btnOrder,
                tvPrice,
                tvOrderTicket
            )
        }
        val increaseButton: Button = activityMainBinding.btnQuantityAdd
        increaseButton.setOnClickListener {
            this.quantity += 1
            manageQuantity(cbWhippedCream, cbChocolate, btnOrder, quantityTextView, tvPrice)
        }
        val decreaseButton: Button = activityMainBinding.btnQuantityRemove
        decreaseButton.setOnClickListener {
            this.quantity = if (quantity <= 0) 0 else quantity - 1
            manageQuantity(cbWhippedCream, cbChocolate, btnOrder, quantityTextView, tvPrice)
        }
    }

    private fun manageQuantity(
        cbWhippedCream: CheckBox,
        cbChocolate: CheckBox,
        btnOrder: Button,
        quantityTextView: TextView,
        tvPrice: TextView
    ) {
        enableOptions(quantity > 0, cbWhippedCream, cbChocolate, btnOrder)
        quantityTextView.text = "Quantity: $quantity"
        getTotalPrice(tvPrice)
    }

    private fun getTotalPrice(tvPrice: TextView) {
        totalPrice = CUP_PRICE * quantity
        if (hasChocolate) {
            totalPrice += CHOCOLATE_PRICE
        }
        if (hasWhippedCream) {
            totalPrice += CREAM_PRICE
        }
        tvPrice.text = totalPrice.toString()
    }

    private fun enableOptions(
        enable: Boolean,
        cbWhippedCream: CheckBox,
        cbChocolate: CheckBox,
        btnOrder: Button
    ) {
        cbWhippedCream.isEnabled = enable
        cbChocolate.isEnabled = enable
        btnOrder.isEnabled = enable
        cbWhippedCream.isChecked = false
        cbChocolate.isChecked = false
    }

    private fun cancelOrder(
        quantityTextView: TextView,
        cbWhippedCream: CheckBox,
        cbChocolate: CheckBox,
        btnOrder: Button,
        tvPrice: TextView,
        tvOrderTicket: TextView
    ) {
        quantity = 0
        hasChocolate = false
        hasWhippedCream = false
        name = "No name"
        quantity = 0
        quantityTextView.text = "Quantity: $quantity"
        enableOptions(false, cbWhippedCream, cbChocolate, btnOrder)
        getTotalPrice(tvPrice)
        tvOrderTicket.text = ""
    }

    override fun onStart() {
        super.onStart()
        Log.d("onStart", "onStart getting called")
    }

    override fun onResume() {
        super.onResume()
        Log.d("onResume", "onResume getting called")
    }

    override fun onPause() {
        super.onPause()
        Log.d("onPause", "onPause getting called")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("onRestart", "onRestart getting called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("onDestroy", "onDestroy getting called")
    }

    companion object {
        var CUP_PRICE = 5.00
        var CREAM_PRICE = 1.0
        var CHOCOLATE_PRICE = 2.0
    }
}