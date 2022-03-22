package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {
    var tvcal:TextView? = null
    var lastDecimal=false
    var lastNumeric=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvcal=findViewById(R.id.calctv)
    }
    fun onDigit( view: View){
        tvcal?.append((view as Button).text)
        lastNumeric=true

    }
    fun onClear(view: View){
        tvcal?.text=""
        lastNumeric=false
        lastDecimal=false
    }
    fun onDecimal(view: View){
        if(lastNumeric && !lastDecimal){
            tvcal?.append(".")
            lastDecimal=true
            lastNumeric=false
        }
    }
    private fun isOperatorAdded(value: String): Boolean{
        return if(value.startsWith('-')){
            false
        }
        else{
            (value.contains('/') ||
                    value.contains('*') ||
                    value.contains('-') ||
                    value.contains('+'))
        }
    }
    fun onOperator(view: View){
        tvcal?.text?.let {
            if ( lastNumeric && !isOperatorAdded(it.toString())) {
                tvcal?.append((view as Button).text)
                lastNumeric=false
                lastDecimal=false
            }
        }
    }
    fun decimalOrNot(string: String): String{
        var value=string
        var splitT= value.split('.')
        var one=splitT[0]
        var two=splitT[1].substring(0,1)

        if(two.toInt()==0){
            value= one
        }else{
            value=one +"." +two
        }
        return value

    }
    fun onEquals(view: View){
        if(lastNumeric){
            var tvVal= tvcal?.text.toString()
            var pre=""
            try{
                if(tvVal.startsWith('-')){
                    pre="-"
                    tvVal=tvVal.substring(1)
                }

                if(tvVal.contains('-')){
                    val splitTv= tvVal.split('-')
                    var fPart=splitTv[0]
                    val lPart=splitTv[1]
                    fPart= pre + fPart
                    tvcal?.text= decimalOrNot((fPart.toDouble() - lPart.toDouble()).toString())
                } else if(tvVal.contains('+')) {
                    val splitTv= tvVal.split('+')
                    var fPart=splitTv[0]
                    val lPart=splitTv[1]
                    fPart= pre + fPart
                    tvcal?.text= decimalOrNot((fPart.toDouble() + lPart.toDouble()).toString())
                }else if(tvVal.contains('*')){
                    val splitTv= tvVal.split('*')
                    var fPart=splitTv[0]
                    val lPart=splitTv[1]
                    fPart= pre + fPart
                    tvcal?.text= decimalOrNot((fPart.toDouble() * lPart.toDouble()).toString())
                }else if(tvVal.contains('/')){
                    val splitTv= tvVal.split('/')
                    var fPart=splitTv[0]
                    val lPart=splitTv[1]
                    fPart= pre + fPart
                    if(lPart.toInt()!=0)
                        tvcal?.text= decimalOrNot((fPart.toDouble() / lPart.toDouble()).toString())
                    else
                        tvcal?.text=""
                }
            }catch (e: ArithmeticException){
                e.printStackTrace()
            }
        }
    }
}

