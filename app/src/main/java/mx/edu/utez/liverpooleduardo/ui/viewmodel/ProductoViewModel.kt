package mx.edu.utez.liverpooleduardo.ui.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import mx.edu.utez.liverpooleduardo.modelos.ProductDetail
import mx.edu.utez.liverpooleduardo.modelos.Review

class ProductoViewModel : ViewModel() {

    // Lista de productos administrada con StateFlow (como en el ejemplo de MejoresAmigos)
    private val _elementos = MutableStateFlow<List<ProductDetail>>(emptyList())
    val elementos: StateFlow<List<ProductDetail>> = _elementos.asStateFlow()

    // Producto seleccionado para ver su detalle
    private val _productoSeleccionado = MutableStateFlow<ProductDetail?>(null)
    val productoSeleccionado: StateFlow<ProductDetail?> = _productoSeleccionado.asStateFlow()

    // Texto de búsqueda
    private val _busqueda = MutableStateFlow("")
    val busqueda: StateFlow<String> = _busqueda.asStateFlow()

    init {
        // Datos de prueba iniciales
        val prod1 = ProductDetail(
            id = "1",
            brand = "DIOR",
            name = "Perfume Dior Sauvage Eau de Parfum 100 ml",
            price = 3450.00,
            rating = 4.8f,
            reviewCount = 128,
            isFreeShipping = true,
            description = "Una composición rotundamente fresca, dictada por un nombre que suena como un manifiesto. Una obra creada entre la brutalidad y la elegancia, con notas de Bergamota y Ámbar.",
            image = null,
            reviews = listOf(
                Review("Excelente aroma y duración", 5.0f, "\"El perfume es 100% original, llegó rápido y bien empaquetado.\""),
                Review("Carlos M.", 4.5f, "Huele increíble, dura todo el día.")
            ),
            isFavorite = true
        )
        val prod2 = ProductDetail(
            id = "2",
            brand = "LG",
            name = "Smart TV LG 55\" 4K UHD ThinQ AI",
            price = 9999.00,
            rating = 4.7f,
            reviewCount = 89,
            isFreeShipping = true,
            description = "Disfruta de colores vivos y detalles increíbles con la Smart TV LG 4K UHD. Equipada con tecnología ThinQ AI que entiende tus órdenes por voz.",
            image = null,
            reviews = listOf(
                Review("María P.", 5.0f, "Excelente imagen y sonido. El sistema es muy fluido.")
            ),
            isFavorite = false
        )
        val prod3 = ProductDetail(
            id = "3",
            brand = "NIKE",
            name = "Tenis Nike Air Max Para Hombre",
            price = 2899.00,
            rating = 4.9f,
            reviewCount = 210,
            isFreeShipping = true,
            description = "Un estilo icónico que ofrece comodidad y amortiguación en cada paso. Fabricados con materiales resistentes y malla transpirable.",
            image = null,
            reviews = listOf(
                Review("Alejandro R.", 5.0f, "Súper cómodos, la talla viene exacta.")
            ),
            isFavorite = false
        )
        val prod4 = ProductDetail(
            id = "4",
            brand = "MICHAEL KORS",
            name = "Bolsa MK tote Piel Saffiano Rosa",
            price = 4200.00,
            rating = 4.6f,
            reviewCount = 64,
            isFreeShipping = true,
            description = "Elegante bolsa tote de la firma Michael Kors fabricada en fina piel Saffiano con compartimentos internos organizadores.",
            image = null,
            reviews = listOf(
                Review("Fernanda L.", 4.5f, "El color es hermoso y cabe todo perfectamente.")
            ),
            isFavorite = true
        )

        _elementos.value = listOf(prod1, prod2, prod3, prod4)
    }

    // Función exactamente igual a la del profesor para actualizar favorito
    fun actualizarFavorito(id: String) {
        _elementos.update { listaActual ->
            listaActual.map { item ->
                if (item.id == id) {
                    item.copy(isFavorite = !item.isFavorite)
                } else {
                    item
                }
            }
        }
        // Si el producto actual en detalle cambia, lo actualizamos también
        if (_productoSeleccionado.value?.id == id) {
            _productoSeleccionado.value = _productoSeleccionado.value?.let { prod ->
                prod.copy(isFavorite = !prod.isFavorite)
            }
        }
    }

    fun seleccionarProducto(producto: ProductDetail) {
        _productoSeleccionado.value = producto
    }

    fun actualizarBusqueda(texto: String) {
        _busqueda.value = texto
    }
}
