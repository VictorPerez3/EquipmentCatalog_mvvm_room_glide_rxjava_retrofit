package com.example.equipmentcatalog_mvvm_room_glide_rxjava_retrofit

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.equipmentcatalog_mvvm_room_glide_rxjava_retrofit.model.entities.EquipCatalog
import com.example.equipmentcatalog_mvvm_room_glide_rxjava_retrofit.utils.Constants
import com.example.equipmentcatalog_mvvm_room_glide_rxjava_retrofit.utils.Utils

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

//run run androidtest
@RunWith(AndroidJUnit4::class)
class AndroidTest {

    // Se a função equipsLocal1() retorna os dados da lista e verifica se estao corretos
    @Test
    fun testeListaCorreta() {
        val separador = "@@"
        val listaObtida = Constants.equipsLocal1()
        var stringListaObtida = ""
        var i = 0
        while (i < listaObtida.size) {
            stringListaObtida += listaObtida[i]
            if (i < (listaObtida.size - 1)) {
                stringListaObtida += separador
            }
            i++
        }

        val stringListaEsperada =
            "Sede 1" + separador + "Sede 2" + separador + "Sede 3" + separador + "Sede Centro"

        assertEquals(stringListaObtida, stringListaEsperada)
    }

    // realiza uma filtragem por nome na lista criada e verifica se o funcionamento é como esperado
    @Test
    fun testeMecanismoDeBuscaEmListaPorNome() {
        val equipamento1 = EquipCatalog("", "", "enceradeira", "", "", "", "", "221", false, 1)
        val equipamento2 = EquipCatalog("", "", "batedeira", "", "", "", "", "222", false, 2)
        val equipamento3 = EquipCatalog("", "", "enxada", "", "", "", "", "223", false, 3)
        val equipamento4 = EquipCatalog("", "", "serrote", "", "", "", "", "224", false, 4)
        val equipamento5 = EquipCatalog("", "", "trator", "", "", "", "", "225", false, 5)
        val equipamento6 = EquipCatalog("", "", "empilhadeira", "", "", "", "", "226", false, 6)
        val equipamento7 = EquipCatalog("", "", "interruptor", "", "", "", "", "227", false, 7)
        val equipamento8 = EquipCatalog("", "", "cooler", "", "", "", "", "228", false, 8)

        val listaDeTeste: List<EquipCatalog> = listOf(
            equipamento1,
            equipamento2,
            equipamento3,
            equipamento4,
            equipamento5,
            equipamento6,
            equipamento7,
            equipamento8
        )
        val listaFiltrada = Utils.getFilteredList("deira", listaDeTeste)

        val separador = "@@"
        var stringListaObtida = ""
        var i = 0
        while (i < listaFiltrada.size) {
            stringListaObtida += listaFiltrada[i].equipment
            if (i < (listaFiltrada.size - 1)) {
                stringListaObtida += separador
            }
            i++
        }

        val stringListaEsperada =
            "enceradeira" + separador + "batedeira" + separador + "empilhadeira"

        assertEquals(stringListaObtida, stringListaEsperada)

    }

    // realiza uma filtragem por codigo na lista criada e verifica se o funcionamento é como esperado
    @Test
    fun testeMecanismoDeBuscaEmListaPorCodigo() {
        val equipamento1 = EquipCatalog("", "", "enceradeira", "", "", "", "", "221", false, 1)
        val equipamento2 = EquipCatalog("", "", "batedeira", "", "", "", "", "222", false, 2)
        val equipamento3 = EquipCatalog("", "", "enxada", "", "", "", "", "223", false, 3)
        val equipamento4 = EquipCatalog("", "", "serrote", "", "", "", "", "224", false, 4)
        val equipamento5 = EquipCatalog("", "", "trator", "", "", "", "", "225", false, 5)
        val equipamento6 = EquipCatalog("", "", "empilhadeira", "", "", "", "", "226", false, 6)
        val equipamento7 = EquipCatalog("", "", "interruptor", "", "", "", "", "227", false, 7)
        val equipamento8 = EquipCatalog("", "", "cooler", "", "", "", "", "321", false, 8)

        val listaDeTeste: List<EquipCatalog> = listOf(
            equipamento1,
            equipamento2,
            equipamento3,
            equipamento4,
            equipamento5,
            equipamento6,
            equipamento7,
            equipamento8
        )
        val listaFiltrada = Utils.getFilteredList("21", listaDeTeste)


        val separador = "@@"
        var stringListaObtida = ""
        var i = 0
        while (i < listaFiltrada.size) {
            stringListaObtida += listaFiltrada[i].equipment
            if (i < (listaFiltrada.size - 1)) {
                stringListaObtida += separador
            }
            i++
        }

        val stringListaEsperada = "enceradeira" + separador + "cooler"

        assertEquals(stringListaObtida, stringListaEsperada)
    }
}