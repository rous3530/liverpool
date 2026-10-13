package mx.edu.utez.liverpooleduardo.ui.pantallas

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mx.edu.utez.liverpooleduardo.ui.componentes.ImagenProducto
import mx.edu.utez.liverpooleduardo.ui.theme.FreeShippingBg
import mx.edu.utez.liverpooleduardo.ui.theme.FreeShippingGreen
import mx.edu.utez.liverpooleduardo.ui.theme.LiverpoolPink
import mx.edu.utez.liverpooleduardo.ui.theme.StarYellow
import mx.edu.utez.liverpooleduardo.ui.viewmodel.ProductoViewModel
import java.text.NumberFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleProductoScreen(
    viewModel: ProductoViewModel,
    alRegresar: () -> Unit
) {
    val productoSeleccionado by viewModel.productoSeleccionado.collectAsState()
    val contexto = LocalContext.current
    val formatoMoneda = NumberFormat.getCurrencyInstance(Locale.Builder().setLanguage("es").setRegion("MX").build())

    val producto = productoSeleccionado ?: return

    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = alRegresar) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Atrás",
                            tint = Color(0xFF1F1F1F)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        Toast.makeText(contexto, "Compartir producto", Toast.LENGTH_SHORT).show()
                    }) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Compartir",
                            tint = Color(0xFF1F1F1F)
                        )
                    }
                    IconButton(onClick = {
                        viewModel.actualizarFavorito(producto.id)
                    }) {
                        Icon(
                            imageVector = if (producto.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Favorito",
                            tint = if (producto.isFavorite) LiverpoolPink else Color(0xFF1F1F1F)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                Button(
                    onClick = {
                        Toast.makeText(contexto, "¡Producto agregado a tu bolsa!", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = LiverpoolPink,
                        contentColor = Color.White
                    ),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
                ) {
                    Text(
                        text = "Agregar a mi bolsa",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        },
        containerColor = Color.White
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 8.dp)
        ) {
            // Imagen principal
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
            ) {
                ImagenProducto(
                    idProducto = producto.id,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Marca
            Text(
                text = producto.brand.uppercase(),
                color = LiverpoolPink,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Nombre
            Text(
                text = producto.name,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                color = Color(0xFF1F1F1F),
                lineHeight = 28.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Calificación
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(5) { indice ->
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = if (indice < producto.rating.toInt()) StarYellow else Color.LightGray,
                        modifier = Modifier.size(18.dp)
                    )
                }
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = producto.rating.toString(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color(0xFF1F1F1F)
                )
                Spacer(modifier = Modifier.width(4.dp))

                // Muestra la cantidad dinámica basada en las reseñas de la lista
                Text(
                    text = "(${producto.reviews.size} opiniones)",
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Precio
            Text(
                text = formatoMoneda.format(producto.price),
                fontSize = 28.sp,
                fontWeight = FontWeight.Black,
                color = Color(0xFF1F1F1F)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Badge de Envío Gratis
            if (producto.isFreeShipping) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(FreeShippingBg)
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocalShipping,
                            contentDescription = "Envío gratis",
                            tint = FreeShippingGreen,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Envío gratis",
                            color = FreeShippingGreen,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            HorizontalDivider(color = Color(0xFFEEEEEE))
            Spacer(modifier = Modifier.height(16.dp))

            // Descripción
            Text(
                text = "Descripción",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1F1F1F)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = producto.description,
                fontSize = 14.sp,
                color = Color(0xFF555555),
                lineHeight = 22.sp
            )

            Spacer(modifier = Modifier.height(20.dp))
            HorizontalDivider(color = Color(0xFFEEEEEE))
            Spacer(modifier = Modifier.height(16.dp))

            // Opiniones de clientes
            Text(
                text = "Opiniones de clientes",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1F1F1F)
            )

            Spacer(modifier = Modifier.height(12.dp))

            if (producto.reviews.isEmpty()) {
                Text(
                    text = "Aún no hay opiniones para este producto.",
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            } else {
                producto.reviews.forEach { resena ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 10.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFF9F9FB)
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = resena.author,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = Color(0xFF222222)
                                )
                                Row {
                                    repeat(resena.rating.toInt()) {
                                        Icon(
                                            imageVector = Icons.Default.Star,
                                            contentDescription = null,
                                            tint = StarYellow,
                                            modifier = Modifier.size(14.dp)
                                        )
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = resena.comment,
                                fontSize = 13.sp,
                                color = Color(0xFF666666)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}