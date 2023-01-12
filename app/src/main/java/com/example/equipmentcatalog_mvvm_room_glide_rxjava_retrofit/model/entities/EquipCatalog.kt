package com.example.equipmentcatalog_mvvm_room_glide_rxjava_retrofit.model.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
// Define the Table name
@Entity(tableName = "equip_catalog_table")
data class EquipCatalog(
    @ColumnInfo val image: String,
    @ColumnInfo(name = "image_source") val imageSource: String,
    @ColumnInfo val equipment: String,
    @ColumnInfo val local1: String,
    @ColumnInfo val local2: String,
    @ColumnInfo val note: String,

    @ColumnInfo(name = "type") val type: String,
    @ColumnInfo(name = "code") val code: String,
    @ColumnInfo(name = "critical_equip") var criticalEquipment: Boolean = false,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
) : Parcelable
