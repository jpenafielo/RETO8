import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dadm.reto8.data.local.entities.Company

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompanyListScreen(
    viewModel: CompanyViewModel,
    onAddCompany: () -> Unit,
    onEditCompany: (Int) -> Unit,
    onViewDetails: (Int) -> Unit
) {
    var searchQuery by rememberSaveable { mutableStateOf("") }
    var classificationQuery by rememberSaveable { mutableStateOf("") }
    val classifications = listOf("Consultoría", "Desarrollo a la medida", "Fábrica de software", "Ninguno")
    var expanded by remember { mutableStateOf(false) }
    val companies by viewModel.companies.collectAsState()
    var companyToDelete by remember { mutableStateOf<Company?>(null) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAddCompany) {
                Text("+")
            }
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(16.dp)
        ) {
            // Campo de búsqueda
            TextField(
                value = searchQuery,
                onValueChange = {
                    searchQuery = it
                    viewModel.filterCompanies(searchQuery, classificationQuery) // Filtra las empresas
                },
                label = { Text("Buscar por nombre") },
                modifier = Modifier.fillMaxWidth()
            )

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                TextField(
                    value = classificationQuery,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Filtrar por clasificación") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    classifications.forEach { classification ->
                        DropdownMenuItem(
                            text = { Text(classification) },
                            onClick = {
                                classificationQuery = classification
                                expanded = false
                                viewModel.filterCompanies(searchQuery, classificationQuery) // Actualiza el filtro
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Lista de empresas
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(companies) { company ->
                    CompanyCard(
                        company = company,
                        onEdit = { onEditCompany(company.id) },
                        onViewDetails = { onViewDetails(company.id) },
                        onDelete = { companyToDelete = company } // Almacena la empresa a eliminar
                    )
                }
            }
        }

        // Diálogo de confirmación de eliminación
        companyToDelete?.let { company ->
            AlertDialog(
                onDismissRequest = { companyToDelete = null },
                title = { Text("Confirmar eliminación") },
                text = {
                    Text("¿Estás seguro de que deseas eliminar la empresa \"${company.name}\"?")
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            viewModel.deleteCompany(company)
                            companyToDelete = null // Cierra el diálogo después de eliminar
                        }
                    ) {
                        Text("Eliminar")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = { companyToDelete = null } // Cierra el diálogo sin eliminar
                    ) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}