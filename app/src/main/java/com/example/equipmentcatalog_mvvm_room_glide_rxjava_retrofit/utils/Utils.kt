package com.example.equipmentcatalog_mvvm_room_glide_rxjava_retrofit.utils

import com.example.equipmentcatalog_mvvm_room_glide_rxjava_retrofit.model.entities.EquipCatalog

class Utils {

    companion object {

        fun getFilteredList(s: String, it: List<EquipCatalog>): ArrayList<EquipCatalog> {

            var i = 0
            val size = it.size
            val filteredList: ArrayList<EquipCatalog> = ArrayList()

            while (i < size) {
                var goAdd = false

                if (it[i].equipment.uppercase()
                        .contains(s.uppercase())
                ) {
                    filteredList.add(it[i])
                    goAdd = true
                }

                if (!goAdd) {

                    if (it[i].code.uppercase().contains(
                            s.uppercase()
                        )
                    ) {
                        filteredList.add(it[i])
                    }
                }
                i++
            }
            return filteredList
        }
    }
}