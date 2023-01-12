package com.example.equipmentcatalog_mvvm_room_glide_rxjava_retrofit.application

import android.app.Application
import com.example.equipmentcatalog_mvvm_room_glide_rxjava_retrofit.model.database.EquipCatalogRepository
import com.example.equipmentcatalog_mvvm_room_glide_rxjava_retrofit.model.database.EquipCatalogRoomDatabase

class EquipCatalogApplication : Application() {

    private val database by lazy { EquipCatalogRoomDatabase.getDatabase(this) }

    val repository by lazy { EquipCatalogRepository(database.equipCatalogDao()) }

}