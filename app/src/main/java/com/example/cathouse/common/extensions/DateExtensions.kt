package com.example.cathouse.common.extensions

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date

private const val appDateFormatStyle = DateFormat.SHORT
private val dateFormatter = SimpleDateFormat.getDateInstance()
private val timeFormatter = SimpleDateFormat.getTimeInstance(appDateFormatStyle)

val Date.displayedDate get() =  dateFormatter.format(this)
val Date.displayedTime get() =  timeFormatter.format(this)