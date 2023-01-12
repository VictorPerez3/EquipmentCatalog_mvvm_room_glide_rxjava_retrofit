package com.example.equipmentcatalog_mvvm_room_glide_rxjava_retrofit.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.equipmentcatalog_mvvm_room_glide_rxjava_retrofit.application.EquipCatalogApplication
import com.example.equipmentcatalog_mvvm_room_glide_rxjava_retrofit.databinding.FragmentCriticalEquipsBinding
import com.example.equipmentcatalog_mvvm_room_glide_rxjava_retrofit.model.entities.EquipCatalog
import com.example.equipmentcatalog_mvvm_room_glide_rxjava_retrofit.view.activities.MainActivity
import com.example.equipmentcatalog_mvvm_room_glide_rxjava_retrofit.view.adapters.EquipCatalogAdapter
import com.example.equipmentcatalog_mvvm_room_glide_rxjava_retrofit.viewmodel.EquipCatalogViewModel
import com.example.equipmentcatalog_mvvm_room_glide_rxjava_retrofit.viewmodel.EquipCatalogViewModelFactory

class CriticalEquipsFragment : Fragment() {

    private var _binding: FragmentCriticalEquipsBinding? = null

    private val mEquipCatalogViewModel: EquipCatalogViewModel by viewModels {
        EquipCatalogViewModelFactory((requireActivity().application as EquipCatalogApplication).repository)
    }

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCriticalEquipsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mEquipCatalogViewModel.criticalEquipments.observe(viewLifecycleOwner){

            _binding!!.rvCriticalEquipsList.layoutManager = GridLayoutManager(requireActivity(), 2)
            val adapter = EquipCatalogAdapter(this)
            _binding!!.rvCriticalEquipsList.adapter = adapter
            if (it.isNotEmpty()){
                _binding!!.rvCriticalEquipsList.visibility = View.VISIBLE
                _binding!!.tvNoCriticalEquipsAvailable.visibility = View.GONE
                adapter.equipmentsList(it)
            }else{
                _binding!!.rvCriticalEquipsList.visibility = View.GONE
                _binding!!.tvNoCriticalEquipsAvailable.visibility = View.VISIBLE
            }
        }
    }

    fun equipDetails(equipCatalog: EquipCatalog) {

        if (requireActivity() is MainActivity) {
            (activity as MainActivity?)!!.hideBottomNavigationView()
        }

        findNavController().navigate(CriticalEquipsFragmentDirections.actionCriticalEquipsToEquipDetails(equipCatalog))
    }

    override fun onResume() {
        super.onResume()

        (activity as MainActivity).showBottomNavigationView()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}