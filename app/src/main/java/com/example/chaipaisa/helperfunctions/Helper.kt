package com.example.chaipaisa.helperfunctions

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast

import androidx.core.content.ContextCompat.startActivity

fun SendWhatsappreminder(number:String,msg:String,context: Context){

    val phoneNumber = "+91xxxxxxxxxx" // Replace with the recipient's phone number (including country code)
    val message =msg// Replace with your message

    try {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse("https://api.whatsapp.com/send?phone=$phoneNumber&text=${Uri.encode(message)}")
            `package` = "com.whatsapp" // Ensures it opens in WhatsApp
        }
        context.startActivity(intent)
    } catch (e: Exception) {
        Toast.makeText(context, "WhatsApp not installed or an error occurred", Toast.LENGTH_SHORT).show()
    }
}

sealed class DeleteResponse {
    object Success : DeleteResponse()
    data class Error(val message: String) : DeleteResponse()
    object Loading : DeleteResponse()
}
