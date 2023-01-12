package com.example.equipmentcatalog_mvvm_room_glide_rxjava_retrofit.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.equipmentcatalog_mvvm_room_glide_rxjava_retrofit.model.entities.EquipCatalog

@Database(entities = [EquipCatalog::class], version = 1)
abstract class EquipCatalogRoomDatabase : RoomDatabase() {

    abstract fun equipCatalogDao(): EquipCatalogDao

    companion object {
        @Volatile
        private var INSTANCE: EquipCatalogRoomDatabase? = null

        fun getDatabase(context: Context): EquipCatalogRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    EquipCatalogRoomDatabase::class.java,
                    "equip_catalog_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}