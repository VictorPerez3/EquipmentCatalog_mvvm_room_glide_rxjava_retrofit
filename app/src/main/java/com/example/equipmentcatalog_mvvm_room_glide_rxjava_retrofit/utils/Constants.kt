package com.example.equipmentcatalog_mvvm_room_glide_rxjava_retrofit.utils

object Constants {

    const val EQUIP_LOCAL1: String = "EquipLocal1"
    const val EQUIP_LOCAL2: String = "EquipLocal2"
    const val EQUIP_TYPE: String = "EquipType"

    const val EQUIP_IMAGE_SOURCE_LOCAL: String = "Local"

    const val EXTRA_EQUIP_DETAILS: String = "EquipDetails"

    const val ALL_ITEMS: String = "All"
    const val FILTER_SELECTION: String = "FilterSelection"

    const val NOTIFICATION_ID = "EquipCatalog_notification_id"

    /**
     * This function will return the equips Local 1 List items.
     */
    fun equipsLocal1(): ArrayList<String> {
        val list = ArrayList<String>()
        list.add("Sede 1")
        list.add("Sede 2")
        list.add("Sede 3")
        list.add("Sede Centro")
        return list
    }

    /**
     *  This function will return the equips Local 2 list items.
     */
    fun equipsLocal2(): ArrayList<String> {
        val list = ArrayList<String>()
        list.add("Subterrâneo")
        list.add("Térreo")
        list.add("Andar 1")
        list.add("Andar 2")
        list.add("Andar 3")
        list.add("Andar 4")
        list.add("Terraço")
        return list
    }


    /**
     *  This function will return the Type list items.
     */
    fun equipType(): ArrayList<String> {
        val list = ArrayList<String>()
        list.add("Ar Condicionado")
        list.add("Cafeteira")
        list.add("Computador")
        list.add("Monitor")
        list.add("Mouse")
        list.add("Teclado")
        list.add("Televisão")
        list.add("Roteador")
        return list
    }
}