package mx.edu.utez.liverpooleduardo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import mx.edu.utez.liverpooleduardo.ui.navegacion.NavegacionApp
import mx.edu.utez.liverpooleduardo.ui.theme.LiverpoolOswaldoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LiverpoolOswaldoTheme {
                NavegacionApp()
            }
        }
    }
}
