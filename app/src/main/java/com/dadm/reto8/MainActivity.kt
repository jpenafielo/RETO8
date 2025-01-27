package com.dadm.reto8

import CompanyViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.navigation.compose.rememberNavController
import com.dadm.reto8.data.local.database.AppDatabase
import com.dadm.reto8.ui.navigation.NavGraph
import com.dadm.reto8.ui.theme.Reto8Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializar la base de datos
        val database = AppDatabase.getDatabase(applicationContext)

        // Crear el ViewModel utilizando el DAO de Room
        val viewModel = CompanyViewModel(database.companyDao())

        // Configurar Jetpack Compose
        setContent {
            Reto8Theme {
                Surface {
                    // Configuración del NavController
                    val navController = rememberNavController()

                    // Configurar el gráfico de navegación
                    NavGraph(navController = navController, viewModel = viewModel)
                }
            }
        }
    }
}
