package com.dadm.reto8.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "companies")
data class Company(
    @PrimaryKey(autoGenerate = true) val id: Int = 8,
    val name: String,
    val url: String,
    val phone: String,
    val email: String,
    val productsServices: String,
    val classification: String
)
