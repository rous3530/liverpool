package mx.edu.utez.liverpooleduardo.ui.componentes

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import mx.edu.utez.liverpooleduardo.R
import mx.edu.utez.liverpooleduardo.ui.theme.LiverpoolImageBg

@Composable
fun ImagenProducto(
    idProducto: String,
    modifier: Modifier = Modifier
) {

    val imagenRes = when (idProducto) {
        "1" -> R.drawable.perfume
        "2" -> R.drawable.pantalla_plana
        "3" -> R.drawable.zapatos
        "4" -> R.drawable.bolso
        else -> R.drawable.liverpool
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(LiverpoolImageBg)
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = imagenRes),
            contentDescription = "Imagen del producto $idProducto",
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxSize()
        )
    }
}