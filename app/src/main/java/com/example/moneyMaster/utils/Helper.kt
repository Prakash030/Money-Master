package com.example.moneyMaster.utils

import java.text.SimpleDateFormat
import java.util.*

object Helper {
    @JvmStatic
    fun formatDate(date: Date?): String {
        val dateFormat = SimpleDateFormat("dd MMMM, yyyy")
        return dateFormat.format(date)
    }

    @JvmStatic
    fun formatDateByMonth(date: Date?): String {
        val dateFormat = SimpleDateFormat("MMMM, yyyy")
        return dateFormat.format(date)
    }
}