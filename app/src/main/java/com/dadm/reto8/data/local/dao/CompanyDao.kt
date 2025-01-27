package com.dadm.reto8.data.local.dao
import androidx.room.*
import com.dadm.reto8.data.local.entities.Company

@Dao
interface CompanyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(company: Company)

    @Query("SELECT * FROM companies")
    suspend fun getAll(): List<Company>

    @Query("SELECT * FROM companies WHERE name LIKE :name AND classification LIKE :classification")
    suspend fun filter(name: String, classification: String): List<Company>

    @Update
    suspend fun update(company: Company)

    @Delete
    suspend fun delete(company: Company)

    @Query("SELECT * FROM companies WHERE id = :id")
    suspend fun getCompanyById(id: Int): Company?
}
