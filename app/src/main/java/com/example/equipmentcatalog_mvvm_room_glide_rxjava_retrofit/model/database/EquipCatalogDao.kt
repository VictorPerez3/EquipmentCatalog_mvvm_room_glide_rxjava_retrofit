package com.example.equipmentcatalog_mvvm_room_glide_rxjava_retrofit.model.database

import androidx.room.*
import com.example.equipmentcatalog_mvvm_room_glide_rxjava_retrofit.model.entities.EquipCatalog
import kotlinx.coroutines.flow.Flow

@Dao
interface EquipCatalogDao {

    @Insert
    suspend fun insertEquipCatalogDetails(equipCatalog: EquipCatalog)

    @Update
    suspend fun updateEquipCatalogDetails(equipCatalog: EquipCatalog)

    @Delete
    suspend fun deleteEquipCatalogDetails(equipCatalog: EquipCatalog)

    @Query("SELECT * FROM EQUIP_CATALOG_TABLE ORDER BY ID")
    fun getAllEquipmentsList(): Flow<List<EquipCatalog>>

    @Query("SELECT * FROM EQUIP_CATALOG_TABLE WHERE critical_equip = 1")
    fun getCriticalEquipsList(): Flow<List<EquipCatalog>>

    @Query("SELECT * FROM EQUIP_CATALOG_TABLE WHERE local1 = :filterLocal1")
    fun getFilterEquipsList(filterLocal1: String): Flow<List<EquipCatalog>>
}