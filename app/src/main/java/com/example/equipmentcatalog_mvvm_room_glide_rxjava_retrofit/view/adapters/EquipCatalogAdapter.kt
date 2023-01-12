package com.example.equipmentcatalog_mvvm_room_glide_rxjava_retrofit.view.adapters

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.equipmentcatalog_mvvm_room_glide_rxjava_retrofit.R
import com.example.equipmentcatalog_mvvm_room_glide_rxjava_retrofit.databinding.ItemEquipLayoutBinding
import com.example.equipmentcatalog_mvvm_room_glide_rxjava_retrofit.model.entities.EquipCatalog
import com.example.equipmentcatalog_mvvm_room_glide_rxjava_retrofit.utils.Constants
import com.example.equipmentcatalog_mvvm_room_glide_rxjava_retrofit.view.activities.NewEquipActivity
import com.example.equipmentcatalog_mvvm_room_glide_rxjava_retrofit.view.fragments.AllEquipsFragment
import com.example.equipmentcatalog_mvvm_room_glide_rxjava_retrofit.view.fragments.CriticalEquipsFragment

class EquipCatalogAdapter(private val fragment: Fragment) :
    RecyclerView.Adapter<EquipCatalogAdapter.ViewHolder>() {

    private var equipments: List<EquipCatalog> = listOf()

    class ViewHolder(view: ItemEquipLayoutBinding) : RecyclerView.ViewHolder(view.root) {

        val ivEquipImage = view.ivEquipImage
        val tvEquipment = view.tvEquipTitle
        val ibMoore = view.ibMore

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemEquipLayoutBinding =
            ItemEquipLayoutBinding.inflate(LayoutInflater.from(fragment.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val equip = equipments[position]

        Glide.with(fragment)
            .load(equip.image)
            .into(holder.ivEquipImage)
        holder.tvEquipment.text = equip.equipment + "\nCode: " + equip.code

        holder.itemView.setOnClickListener {
            if (fragment is AllEquipsFragment) {
                fragment.equipDetails(equip)
            }
            if (fragment is CriticalEquipsFragment) {
                fragment.equipDetails(equip)
            }
        }

        holder.ibMoore.setOnClickListener {

            val popup = PopupMenu(fragment.context, holder.ibMoore)
            popup.menuInflater.inflate(R.menu.menu_adapter, popup.menu)

            popup.setOnMenuItemClickListener {
                if (it.itemId == R.id.action_edit_equip) {
                    val intent =
                        Intent(fragment.requireActivity(), NewEquipActivity::class.java)
                    intent.putExtra(Constants.EXTRA_EQUIP_DETAILS, equip)
                    fragment.requireActivity().startActivity(intent)
                    Log.i("You have clicked on", "Edit Option of ${equip.equipment}")
                } else if (it.itemId == R.id.action_delete_equip) {
                    if (fragment is AllEquipsFragment) {
                        fragment.deleteEquip(equip)
                        Log.i("You have clicked on", "Delete Option of ${equip.equipment}")
                    }
                }
                true
            }
            popup.show()
        }
        if (fragment is AllEquipsFragment) {
            holder.ibMoore.visibility = View.VISIBLE
        } else if (fragment is CriticalEquipsFragment) {
            holder.ibMoore.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return equipments.size
    }

    fun equipmentsList(list: List<EquipCatalog>) {
        equipments = list
        notifyDataSetChanged()
    }

}