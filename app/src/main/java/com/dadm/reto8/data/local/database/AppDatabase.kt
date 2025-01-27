package com.dadm.reto8.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dadm.reto8.data.local.dao.CompanyDao
import com.dadm.reto8.data.local.entities.Company

@Database(entities = [Company::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun companyDao(): CompanyDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, // Asegúrate de usar `applicationContext`
                    AppDatabase::class.java,
                    "directory_database"
                ).fallbackToDestructiveMigration() // Maneja migraciones de forma básica
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
