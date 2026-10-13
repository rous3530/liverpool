package mx.edu.utez.liverpooleduardo.ui.pantallas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mx.edu.utez.liverpooleduardo.R
import mx.edu.utez.liverpooleduardo.modelos.ProductDetail
import mx.edu.utez.liverpooleduardo.ui.componentes.ImagenProducto
import mx.edu.utez.liverpooleduardo.ui.theme.LiverpoolPink
import mx.edu.utez.liverpooleduardo.ui.viewmodel.ProductoViewModel
import java.text.NumberFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaProductosScreen(
    viewModel: ProductoViewModel,
    alRegresar: () -> Unit,
    alSeleccionarProducto: (ProductDetail) -> Unit
) {
    val listaProductos by viewModel.elementos.collectAsState()
    val textoBusqueda by viewModel.busqueda.collectAsState()

    val productosFiltrados = if (textoBusqueda.isBlank()) {
        listaProductos
    } else {
        listaProductos.filter { producto ->
            producto.name.contains(textoBusqueda, ignoreCase = true) ||
                    producto.brand.contains(textoBusqueda, ignoreCase = true)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Productos",
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp,
                        color = Color(0xFF1F1F1F)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = alRegresar) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Regresar",
                            tint = Color(0xFF1F1F1F)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        },
        containerColor = Color(0xFFF8F9FA)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = textoBusqueda,
                    onValueChange = { viewModel.actualizarBusqueda(it) },
                    placeholder = {
                        Text(
                            text = "Buscar en Liverpool...",
                            color = Color.Gray,
                            fontSize = 14.sp
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            tint = Color.Gray
                        )
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(16.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedIndicatorColor = LiverpoolPink,
                        unfocusedIndicatorColor = Color(0xFFE0E0E0)
                    ),
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(LiverpoolPink)
                        .clickable { },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Buscar",
                        tint = Color.White
                    )
                }
            }

            if (productosFiltrados.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No se encontraron productos",
                        color = Color.Gray,
                        fontSize = 16.sp
                    )
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(bottom = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(
                        items = productosFiltrados,
                        key = { it.id }
                    ) { producto ->
                        TarjetaProducto(
                            producto = producto,
                            alHacerClic = {
                                viewModel.seleccionarProducto(producto)
                                alSeleccionarProducto(producto)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TarjetaProducto(
    producto: ProductDetail,
    alHacerClic: () -> Unit
) {
    val formatoMoneda = NumberFormat.getCurrencyInstance(Locale.Builder().setLanguage("es").setRegion("MX").build())

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { alHacerClic() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
            ) {
                ImagenProducto(
                    idProducto = producto.id,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = producto.name,
                fontWeight = FontWeight.Medium,
                fontSize = 13.sp,
                color = Color(0xFF2B2B2B),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 16.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = formatoMoneda.format(producto.price),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = LiverpoolPink
            )

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = alHacerClic,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(36.dp),
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = LiverpoolPink,
                    contentColor = Color.White
                ),
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(
                    text = "Ver Detalle",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}