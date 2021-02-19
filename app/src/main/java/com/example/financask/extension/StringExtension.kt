package com.example.financask.extension

import java.text.SimpleDateFormat
import java.util.*

fun String.limitaEmAte(caracteres: Int) : String{
    if(this.length > caracteres){
        return "${this.substring(0, caracteres)}..."
    }else{
        return this
    }
}

fun String.converteParaCalendar(): Calendar {
    val formatoBrasileiro = SimpleDateFormat("dd/MM/yyyy")
    val dataConvertida = formatoBrasileiro.parse(this)
    val data = Calendar.getInstance()
    data.time = dataConvertida
    return data
}