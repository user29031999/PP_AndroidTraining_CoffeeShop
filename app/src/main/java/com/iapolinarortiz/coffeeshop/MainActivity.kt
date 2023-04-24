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

class MainActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("OnCreated", "OnCreate getting called")
        setContentView(R.layout.activity_main)

        val quantityTextView: TextView = findViewById(R.id.tv_quantity)
        val tvPrice: TextView = findViewById(R.id.tv_price)
        val cbWhippedCream: CheckBox = findViewById(R.id.cb_topping_whippedcream)
        cbWhippedCream.setOnCheckedChangeListener { _, isChecked ->
            hasWhippedCream = isChecked
            getTotalPrice(tvPrice)
        }
        val cbChocolate: CheckBox = findViewById(R.id.cb_topping_chocolate)
        cbChocolate.setOnCheckedChangeListener { _, isChecked ->
            hasChocolate = isChecked
            getTotalPrice(tvPrice)
        }
        val etName: EditText = findViewById(R.id.et_user_name)
        etName.addTextChangedListener { text: Editable? -> name = text.toString() }
        val tvOrderTicket: TextView = findViewById(R.id.tv_ticket)
        val btnOrder: Button = findViewById(R.id.btn_order)
        btnOrder.setOnClickListener {
            getTotalPrice(tvPrice)
            tvOrderTicket.text = "Name: ${name}\n" +
                    "Quantity: $quantity cups of coffee\n" +
                    "Total price: $totalPrice\n" +
                    "Whipped cream: $hasWhippedCream\n" +
                    "Chocolate: $hasChocolate\n" +
                    "Thank you!"
        }
        val btnCancel: Button = findViewById(R.id.btn_cancel)
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
        val increaseButton: Button = findViewById(R.id.btn_quantity_add)
        increaseButton.setOnClickListener {
            quantity += 1
            manageQuantity(cbWhippedCream, cbChocolate, btnOrder, quantityTextView, tvPrice)
        }
        val decreaseButton: Button = findViewById(R.id.btn_quantity_remove)
        decreaseButton.setOnClickListener {
            quantity = if (quantity <= 0) 0 else quantity - 1
            manageQuantity(cbWhippedCream, cbChocolate, btnOrder, quantityTextView, tvPrice)
        }

        enableOptions(quantity > 0, cbWhippedCream, cbChocolate, btnOrder)
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
        var totalPrice = 0.0
        var name = "No name"
        var hasChocolate = false
        var hasWhippedCream = false
        var quantity = 0
    }
}