import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dadm.reto8.data.local.entities.Company

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompanyDetailScreen(
    companyId: Int,
    viewModel: CompanyViewModel,
    onBack: () -> Unit
) {

    var company by remember { mutableStateOf<Company?>(null) }

    LaunchedEffect(companyId) {
             company= viewModel.getCompanyById(companyId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalles de la compañía") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás")
                    }
                }
            )
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            company?.let {
                Text("Nombre: ${it.name}", style = MaterialTheme.typography.titleLarge)
                Text("URL: ${it.url}", style = MaterialTheme.typography.bodyLarge)
                Text("Teléfono: ${it.phone}", style = MaterialTheme.typography.bodyLarge)
                Text("Email: ${it.email}", style = MaterialTheme.typography.bodyLarge)
                Text("Productos/Servicios: ${it.productsServices}", style = MaterialTheme.typography.bodyLarge)
                Text("Clasificación: ${it.classification}", style = MaterialTheme.typography.bodyLarge)
            } ?: Text("La compañía no fue encontrada.")
        }
    }
}
