package com.dadm.reto8.ui.navigation

import CompanyDetailScreen
import CompanyFormScreen
import CompanyListScreen
import CompanyViewModel
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun NavGraph(navController: NavHostController, viewModel: CompanyViewModel) {
    NavHost(navController = navController, startDestination = Routes.CompanyList.route) {
        composable(Routes.CompanyList.route) {
            CompanyListScreen(
                viewModel = viewModel,
                onAddCompany = { navController.navigate(Routes.CompanyForm.createRoute(null)) },
                onEditCompany = { companyId ->
                    navController.navigate(Routes.CompanyForm.createRoute(companyId))
                },
                onViewDetails = { companyId -> navController.navigate("details/$companyId") }
            )
        }
        composable(Routes.CompanyForm.route) { backStackEntry ->
            val companyId = backStackEntry.arguments?.getString("companyId")?.toIntOrNull()
            CompanyFormScreen(
                viewModel = viewModel,
                companyId = companyId, // Pasamos el ID recibido al Composable
                onSave = { navController.popBackStack() }
            )
        }
        composable(Routes.DetailsCompany.route ) { backStackEntry ->
            println(backStackEntry.arguments)
            val companyId = backStackEntry.arguments?.getString("companyId")?.toInt() ?: 0
            CompanyDetailScreen(
                companyId = companyId,
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
