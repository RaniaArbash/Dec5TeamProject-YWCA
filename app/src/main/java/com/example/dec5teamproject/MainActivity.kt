package com.example.dec5teamproject
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.example.dec5teamproject.ui.theme.Dec5TeamProjectTheme
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Dec5TeamProjectTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                   MainScaffold()
                }
            }
        }
    }
}
