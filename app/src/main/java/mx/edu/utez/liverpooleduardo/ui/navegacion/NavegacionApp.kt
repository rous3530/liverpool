package mx.edu.utez.liverpooleduardo.ui.navegacion

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import mx.edu.utez.liverpooleduardo.ui.pantallas.BienvenidaScreen
import mx.edu.utez.liverpooleduardo.ui.pantallas.DetalleProductoScreen
import mx.edu.utez.liverpooleduardo.ui.pantallas.ListaProductosScreen
import mx.edu.utez.liverpooleduardo.ui.viewmodel.ProductoViewModel

sealed class RutasScreen(val ruta: String) {
    object Bienvenida : RutasScreen("bienvenida")
    object Productos : RutasScreen("productos")
    object DetalleProducto : RutasScreen("detalle_producto")
}

@Composable
fun NavegacionApp(
    navController: NavHostController = rememberNavController(),
    viewModel: ProductoViewModel = viewModel()
) {
    NavHost(
        navController = navController,
        startDestination = RutasScreen.Bienvenida.ruta
    ) {
        composable(RutasScreen.Bienvenida.ruta) {
            BienvenidaScreen(
                alHacerClicEnVerProductos = {
                    navController.navigate(RutasScreen.Productos.ruta)
                }
            )
        }

        composable(RutasScreen.Productos.ruta) {
            ListaProductosScreen(
                viewModel = viewModel,
                alRegresar = {
                    navController.popBackStack()
                },
                alSeleccionarProducto = { producto ->
                    navController.navigate(RutasScreen.DetalleProducto.ruta)
                }
            )
        }

        composable(RutasScreen.DetalleProducto.ruta) {
            DetalleProductoScreen(
                viewModel = viewModel,
                alRegresar = {
                    navController.popBackStack()
                }
            )
        }
    }
}