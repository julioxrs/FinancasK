package com.example.financask.model

import java.math.BigDecimal
import java.util.Calendar

class Transacao(val valor: BigDecimal,
           val categoria: String = "Indefinidamente super indefinida",
           val tipo : Tipo,
           val data : Calendar = Calendar.getInstance())