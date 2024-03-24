package com.example.callalerting.buttonhandlers

import android.view.View
import android.widget.Toast

class CallMeButtonHandler : View.OnClickListener {
    override fun onClick(v: View) {
        // Aquí va el código que se ejecuta cuando se hace clic en el botón "Call me"
        Toast.makeText(v.context, "Button 'Call me' clicked!", Toast.LENGTH_SHORT).show()
    }
}