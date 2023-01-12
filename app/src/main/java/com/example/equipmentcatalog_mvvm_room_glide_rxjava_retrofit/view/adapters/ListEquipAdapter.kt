package com.example.equipmentcatalog_mvvm_room_glide_rxjava_retrofit.view.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.equipmentcatalog_mvvm_room_glide_rxjava_retrofit.databinding.ItemCustomListLayoutBinding
import com.example.equipmentcatalog_mvvm_room_glide_rxjava_retrofit.view.activities.NewEquipActivity
import com.example.equipmentcatalog_mvvm_room_glide_rxjava_retrofit.view.fragments.AllEquipsFragment

class ListEquipAdapter(
    private val activity: Activity,
    private val fragment: Fragment?,
    private val listItems: List<String>,
    private val selection: String
) :
    RecyclerView.Adapter<ListEquipAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemCustomListLayoutBinding =
            ItemCustomListLayoutBinding.inflate(LayoutInflater.from(activity), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = listItems[position]

        holder.tvText.text = item
        holder.itemView.setOnClickListener {

            if (activity is NewEquipActivity) {
                activity.selectedListItem(item, selection)
            }

            if (fragment is AllEquipsFragment) {
                fragment.filterSelection(item)
            }
        }
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    class ViewHolder(view: ItemCustomListLayoutBinding) : RecyclerView.ViewHolder(view.root) {
        // Holds the TextView that will add each item to
        val tvText = view.tvText
    }

}