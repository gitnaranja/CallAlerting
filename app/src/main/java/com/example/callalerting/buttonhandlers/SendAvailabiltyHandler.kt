package com.example.callalerting.buttonhandlers

import android.view.View
import android.widget.Toast

class SendAvailabiltyHandler : View.OnClickListener {
    override fun onClick(v: View) {
        // Aquí va el código que se ejecuta cuando se hace clic en el botón "Send Availability"
        Toast.makeText(v.context, "Button 'Send Availability' clicked!"
            , Toast.LENGTH_SHORT).show()
    }
}