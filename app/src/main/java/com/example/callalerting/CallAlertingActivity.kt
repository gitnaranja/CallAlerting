package com.example.callalerting

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.callalerting.ui.theme.CallAlertingTheme
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.RemoteMessage
import java.security.KeyStore
import java.util.UUID
import javax.crypto.KeyGenerator


class CallAlertingActivity : ComponentActivity() {
    private val PICK_CONTACT_REQUEST = 1  // El código de solicitud para seleccionar un contacto
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CallAlertingTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Greeting("Android")
                        MyButtons(
                            onCallMeButtonClick = { handleCallMeButtonClick(this@CallAlertingActivity) },
                            onSendAvailabilityButtonClick = { handleSendAvailabilityButtonClick(this@CallAlertingActivity) }
                        )
                    }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_CONTACT_REQUEST && resultCode == Activity.RESULT_OK) {
            val contactUri = data?.data
            val projection = arrayOf(ContactsContract.CommonDataKinds.Phone.NUMBER)
            val cursor = contactUri?.let {
                contentResolver.query(it, projection, null, null, null)
            }

            cursor?.apply {
                if (moveToFirst()) {
                    val numberIndex = getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                    val number = getString(numberIndex)

                    // Send the notification
                    sendNotification(number)
                }
                close()
            }
        }
    }

    private fun sendNotification(number: String) {
        //    val serverKey = getServerKey() // Replace this with your method to securely get the server key
        val deviceToken = getDeviceToken() // Replace this with your method to securely get the device token
        val messageId = generateUniqueId() // Replace this with your method to generate a unique identifier

        val message = RemoteMessage.Builder(deviceToken.toString())
            .setMessageId(messageId)
            .addData("message", "You have been selected by $number")
            .build()

        FirebaseMessaging.getInstance().send(message)
        Log.d("FCM", "Message sent")
    }

    private fun getServerKey(): String {
        val keyStore = KeyStore.getInstance("AndroidKeyStore")
        keyStore.load(null)

        if (!keyStore.containsAlias("ServerKey")) {
            val keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")
            keyGenerator.init(
                KeyGenParameterSpec.Builder("ServerKey", KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .build()
            )
            keyGenerator.generateKey()
        }

        val secretKeyEntry = keyStore.getEntry("ServerKey", null) as KeyStore.SecretKeyEntry
        return Base64.encodeToString(secretKeyEntry.secretKey.encoded, Base64.DEFAULT)
    }

    private fun getDeviceToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("FCM", "Fetching FCM registration token failed", task.exception)
                return@addOnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast
            val msg = "Device Token: $token"
            Log.d("FCM", msg)
            Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
        }
    }

    private fun generateUniqueId(): String {
        return UUID.randomUUID().toString()
    }

    fun handleCallMeButtonClick(context: Context) {
        Toast.makeText(context, "Button 'Call me' clicked!", Toast.LENGTH_SHORT).show()
    }

    fun handleSendAvailabilityButtonClick(context: Context) {
        Toast.makeText(context, "Button 'Send Availability' clicked!", Toast.LENGTH_SHORT).show()
    }

    @Composable
    fun Greeting(name: String, modifier: Modifier = Modifier) {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
    }

    @Composable
    fun MyButtons(onCallMeButtonClick: () -> Unit, onSendAvailabilityButtonClick: () -> Unit) {
        Button(onClick = onCallMeButtonClick) {
            Text("Call me")
        }
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = onSendAvailabilityButtonClick) {
            Text("Send Availability")
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        CallAlertingTheme {
            Greeting("Android")
        }
    }
}