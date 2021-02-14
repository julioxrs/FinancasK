package com.example.financask.extension

import java.text.SimpleDateFormat
import java.util.Calendar

fun Calendar.formataParaBrasileiro(): String {
    return SimpleDateFormat("dd/MM/yyyy").format(this.time)
}