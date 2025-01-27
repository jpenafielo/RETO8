import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dadm.reto8.data.local.dao.CompanyDao
import com.dadm.reto8.data.local.entities.Company
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CompanyViewModel(private val dao: CompanyDao) : ViewModel() {
    private val _companies = MutableStateFlow<List<Company>>(emptyList())
    val companies: StateFlow<List<Company>> = _companies

    init {
        fetchCompanies()
    }


    fun fetchCompanies() {
        viewModelScope.launch(Dispatchers.IO) {
            _companies.value = dao.getAll()
        }
    }

    fun filterCompanies(name: String, classification: String) {
        var query = classification
        if(classification=="Ninguno"){
            query=""
        }
        viewModelScope.launch(Dispatchers.IO) {
            _companies.value = dao.filter("%$name%", "%$query%")
        }
    }

    fun addCompany(company: Company) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.insert(company)
            fetchCompanies()
        }
    }

    fun deleteCompany(company: Company) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.delete(company)
            fetchCompanies()
        }
    }

    suspend fun getCompanyById(companyId: Int): Company? {
        println(companyId)
        return dao.getCompanyById(companyId)
    }

    fun updateCompany(company: Company) {
        viewModelScope.launch {
            dao.update(company)
            fetchCompanies()
        }
    }

}
