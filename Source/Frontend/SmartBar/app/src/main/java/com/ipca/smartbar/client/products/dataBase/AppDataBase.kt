package com.ipca.smartbar.client.products.dataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ipca.smartbar.client.products.Product


    @Database(entities = [Product::class], version = 1)
    abstract class AppDatabase : RoomDatabase() {
        abstract fun productDao(): ProductDAO

        companion object {

            @Volatile
            private var INSTANCE: AppDatabase? = null

            fun getDatabase(context: Context): AppDatabase? {
                if (INSTANCE == null) {
                    synchronized(AppDatabase::class.java) {
                        if (INSTANCE == null) {
                            INSTANCE = Room.databaseBuilder(
                                context.applicationContext,
                                AppDatabase::class.java,
                                "db_products"
                            ).fallbackToDestructiveMigration().build()
                        }
                    }
                }
                return INSTANCE
            }
        }
    }
