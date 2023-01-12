package com.example.equipmentcatalog_mvvm_room_glide_rxjava_retrofit.model.database

import androidx.annotation.WorkerThread
import com.example.equipmentcatalog_mvvm_room_glide_rxjava_retrofit.model.entities.EquipCatalog
import kotlinx.coroutines.flow.Flow

class EquipCatalogRepository(private val equipCatalogDao: EquipCatalogDao) {

    @WorkerThread
    suspend fun insertEquipCatalogData(equipCatalog: EquipCatalog) {
        equipCatalogDao.insertEquipCatalogDetails(equipCatalog)
    }

    val allEquipmentsList: Flow<List<EquipCatalog>> = equipCatalogDao.getAllEquipmentsList()

    val criticalEquipments: Flow<List<EquipCatalog>> = equipCatalogDao.getCriticalEquipsList()

    @WorkerThread
    suspend fun updateEquipCatalogData(equipCatalog: EquipCatalog) {
        equipCatalogDao.updateEquipCatalogDetails(equipCatalog)
    }

    @WorkerThread
    suspend fun deleteEquipCatalogData(equipCatalog: EquipCatalog) {
        equipCatalogDao.deleteEquipCatalogDetails(equipCatalog)
    }

    fun filteredListEquipments(value: String): Flow<List<EquipCatalog>> =
        equipCatalogDao.getFilterEquipsList(value)
}