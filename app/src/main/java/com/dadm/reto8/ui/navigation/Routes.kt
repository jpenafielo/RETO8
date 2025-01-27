package com.dadm.reto8.ui.navigation

sealed class Routes(val route: String) {
    object CompanyList : Routes("company_list")
    object CompanyForm : Routes("company_form/{companyId}") {
        fun createRoute(companyId: Int?) = "company_form/${companyId}"
    }
    object DetailsCompany : Routes("details/{companyId}") {
        fun createRoute(companyId: Int?) = "details/${companyId}"
    }}
