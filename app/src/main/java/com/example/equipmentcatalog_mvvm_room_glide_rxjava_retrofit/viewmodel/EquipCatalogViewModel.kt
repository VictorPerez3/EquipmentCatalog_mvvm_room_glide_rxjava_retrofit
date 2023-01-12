package com.example.equipmentcatalog_mvvm_room_glide_rxjava_retrofit.viewmodel

import androidx.lifecycle.*
import com.example.equipmentcatalog_mvvm_room_glide_rxjava_retrofit.model.database.EquipCatalogRepository
import com.example.equipmentcatalog_mvvm_room_glide_rxjava_retrofit.model.entities.EquipCatalog
import kotlinx.coroutines.launch

class EquipCatalogViewModel(private val repository: EquipCatalogRepository) : ViewModel() {

    fun insert(equip: EquipCatalog) = viewModelScope.launch {
        repository.insertEquipCatalogData(equip)
    }

    fun update(equip: EquipCatalog) = viewModelScope.launch {
        repository.updateEquipCatalogData(equip)
    }

    fun delete(equip: EquipCatalog) = viewModelScope.launch {
        repository.deleteEquipCatalogData(equip)
    }

    fun getFilteredList(value: String): LiveData<List<EquipCatalog>> =
        repository.filteredListEquipments(value).asLiveData()

    val allEquipmentsList: LiveData<List<EquipCatalog>> = repository.allEquipmentsList.asLiveData()

    val criticalEquipments: LiveData<List<EquipCatalog>> =
        repository.criticalEquipments.asLiveData()
}

class EquipCatalogViewModelFactory(private val repository: EquipCatalogRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EquipCatalogViewModel::class.java)) {
            @Suppress("UNCHECK_CAST")
            return EquipCatalogViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}