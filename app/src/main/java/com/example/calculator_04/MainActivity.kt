package com.example.calculator_04

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.badge.BadgeUtils

class MainActivity : AppCompatActivity() {

    //ประกาศตัวแปร
    private lateinit var display: TextView //แสดงผลตัวเลข/ผลลัพธ์ บนหน้าจอ
    private var current = "" //ค่าปัจจุบันที่กำลังพิมพ์
    private var operator = "" //เครื่องหมายที่เราเลือก(+-*/)
    private var firstnum = 0.0 //เลขแรก
    private var secondnum = 0.0 //เลขสอง
    private var result = 0.0 //ผลลัพธ์
    private var newoperation = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //เขื่อมตัวแปร
        display = findViewById(R.id.txtdisplay)
        val numberbutton = listOf(
            R.id.btnzero, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9
        )

        for (id in numberbutton) {
            findViewById<Button>(id).setOnClickListener {
                numberClicked((it as Button).text.toString())
            }
        }

        findViewById<Button>(R.id.btnmod).setOnClickListener { operatorClicked("%") }
        findViewById<Button>(R.id.btnadd).setOnClickListener { operatorClicked("+") }
        findViewById<Button>(R.id.btnminus).setOnClickListener { operatorClicked("-") }
        findViewById<Button>(R.id.btnmulti).setOnClickListener { operatorClicked("*") }
        findViewById<Button>(R.id.btndevine).setOnClickListener { operatorClicked("/") }

        findViewById<Button>(R.id.btneaual).setOnClickListener { calculateresult() }
        findViewById<Button>(R.id.btnac).setOnClickListener { clearAll() }
        findViewById<Button>(R.id.btndot).setOnClickListener { addDot() }
        findViewById<Button>(R.id.btncrist).setOnClickListener { togglesign() }
        findViewById<Button>(R.id.btndel).setOnClickListener { backspace() }

    }

    private fun numberClicked(value: String) {
        if (newoperation) { //ล้างหน้าจอตอนเริ่มใหม่
            display.text = ""
            newoperation = false
        }
        current += value //เพิ่มค่าตัวเลขต่อท้าย
        display.text = current //แสดงค่าบนหน้าจอ
    }

    //เมื่อกดเครื่องหมาย
    private fun operatorClicked(op: String) {
        if (current.isNotEmpty()) { //ต้องมีตัวเลขก่อนถึงเลือกเครื่องหมายได้
            firstnum = current.toDouble()
            operator = op
            current = "" //เคลียจอรอรับเลขใหม่
        }
    }

    //เมื่อกดปุ่ม เท่ากับ คำนวณ
    private fun calculateresult() {
        if (current.isNotEmpty()) {
            secondnum = current.toDouble()
            result = when (operator) {
                "+" -> firstnum + secondnum
                "-" -> firstnum - secondnum
                "*" -> firstnum * secondnum
                "%" -> firstnum % secondnum
                "/" -> if (secondnum != 0.0) firstnum / secondnum
                else Double.NaN

                else -> 0.0

            }
            //ถ้าหารด้วย 0 แสดงข้อความ Eror
            display.text = if (result.isNaN()) "Cannot Dived by Zero" else result.toString()
            current = result.toString()
            newoperation = true
        }
    }

    //เคลียร์ข้อมูล
    private fun clearAll() {
        current = ""
        operator = ""
        firstnum = 0.0
        secondnum = 0.0
        result = 0.0
        display.text = "0"
        newoperation = true
    }

    //กดเพิ่มจุดทศนิยม
    private fun addDot() {
        if (!current.contains(".")) {
            if (current.isEmpty()) current = "0"
            current += "."
            display.text = current
        }
    }

    //ปุ่มเปลี่ยนเครื่องหมาย +-
    private fun togglesign() {
        if (current.isNotEmpty() && current != "0") {
            current = if (current.startsWith("-")) {
                current.substring(1)
            } else {
                "-$current"
            }
        }
    }
    //ลบทีละตัว Del
    private fun backspace() {
        if (newoperation) return
        if (current.isNotEmpty()) {
            current = current.dropLast(1)

            if (current == "-" || current.isEmpty()) {
                current ="0"
                display.text = "0"
            }else{
                display.text = current
            }
        }
    }

}
