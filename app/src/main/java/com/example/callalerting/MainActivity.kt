package com.example.callalerting

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
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

class MainActivity : ComponentActivity() {
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
                            onCallMeButtonClick = { handleCallMeButtonClick(this@MainActivity) },
                            onSendAvailabilityButtonClick = { handleSendAvailabilityButtonClick(this@MainActivity) }
                        )
                    }
                }
            }
        }
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