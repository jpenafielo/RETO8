import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dadm.reto8.data.local.entities.Company

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompanyFormScreen(
    viewModel: CompanyViewModel,
    companyId: Int?,
    onSave: () -> Unit
) {
    val isEditMode = companyId != null

    var name by rememberSaveable { mutableStateOf("") }
    var url by rememberSaveable { mutableStateOf("") }
    var phone by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var productsServices by rememberSaveable { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) } // Controla si el menú está desplegado
    var classification by rememberSaveable { mutableStateOf("") }
    var selectedOption by remember { mutableStateOf("Seleccione una clasificación") }
    val classifications = listOf("Consultoría", "Desarrollo a la medida", "Fábrica de software") // Opciones

    LaunchedEffect(companyId) {
        if (isEditMode) {
            val company = viewModel.getCompanyById(companyId ?: 0)
            company?.let {
                name = it.name
                url = it.url
                phone = it.phone
                email = it.email
                productsServices = it.productsServices
                classification = it.classification
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Campos de entrada
            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = url,
                onValueChange = { url = it },
                label = { Text("URL") },
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = phone,
                onValueChange = {
                    // Asegura que solo se ingresen números
                    if (it.all { char -> char.isDigit() }) {
                        phone = it
                    }
                },
                label = { Text("Teléfono") },
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = productsServices,
                onValueChange = { productsServices = it },
                label = { Text("Productos/Servicios") },
                modifier = Modifier.fillMaxWidth()
            )

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                TextField(
                    value = classification,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Clasificación") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    },
                    modifier = Modifier
                        .menuAnchor() // Necesario para el menú desplegable
                        .fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    classifications.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = {
                                classification = option
                                expanded = false
                            }
                        )
                    }
                }
            }
            // Botón
            Button(
                onClick = {
                    if (name.isNotBlank() && email.isNotBlank()) {
                        if (isEditMode) {
                            viewModel.updateCompany(
                                Company(
                                    companyId ?: 0,
                                    name,
                                    url,
                                    phone,
                                    email,
                                    productsServices,
                                    classification
                                )
                            )
                        } else {
                            viewModel.addCompany(
                                Company(
                                    0,
                                    name,
                                    url,
                                    phone,
                                    email,
                                    productsServices,
                                    classification
                                )
                            )
                        }
                        onSave()
                    } else {
                        println("Los campos obligatorios no pueden estar vacíos")
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp)
            ) {
                Text("Guardar", style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}
