package com.example.callalerting.buttonhandlers

import android.app.Activity
import android.content.Intent
import android.provider.ContactsContract
import android.view.View
import android.widget.Toast


class CallMeButtonHandler(private val activity: Activity) : View.OnClickListener {
    // El código de solicitud que se usará para lanzar la actividad de selección de contacto
    private val PICK_CONTACT_REQUEST = 1
    override fun onClick(v: View) {

        //Crea un intent para seleccionar un contacto
        val pickContentIntent = Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
        activity.startActivityForResult(pickContentIntent, PICK_CONTACT_REQUEST)

        // Aquí va el código que se ejecuta cuando se hace clic en el botón "Call me"
        Toast.makeText(v.context, "Button 'Call me' clicked!", Toast.LENGTH_SHORT).show()
    }


}