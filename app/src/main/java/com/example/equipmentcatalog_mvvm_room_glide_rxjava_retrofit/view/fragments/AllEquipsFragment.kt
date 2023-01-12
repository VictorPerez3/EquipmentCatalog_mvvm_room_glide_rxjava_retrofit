package com.example.equipmentcatalog_mvvm_room_glide_rxjava_retrofit.view.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.equipmentcatalog_mvvm_room_glide_rxjava_retrofit.R
import com.example.equipmentcatalog_mvvm_room_glide_rxjava_retrofit.application.EquipCatalogApplication
import com.example.equipmentcatalog_mvvm_room_glide_rxjava_retrofit.databinding.DialogCustomListBinding
import com.example.equipmentcatalog_mvvm_room_glide_rxjava_retrofit.databinding.FragmentAllEquipsBinding
import com.example.equipmentcatalog_mvvm_room_glide_rxjava_retrofit.model.entities.EquipCatalog
import com.example.equipmentcatalog_mvvm_room_glide_rxjava_retrofit.utils.Constants
import com.example.equipmentcatalog_mvvm_room_glide_rxjava_retrofit.utils.Utils
import com.example.equipmentcatalog_mvvm_room_glide_rxjava_retrofit.view.activities.NewEquipActivity
import com.example.equipmentcatalog_mvvm_room_glide_rxjava_retrofit.view.activities.MainActivity
import com.example.equipmentcatalog_mvvm_room_glide_rxjava_retrofit.view.adapters.ListEquipAdapter
import com.example.equipmentcatalog_mvvm_room_glide_rxjava_retrofit.view.adapters.EquipCatalogAdapter
import com.example.equipmentcatalog_mvvm_room_glide_rxjava_retrofit.viewmodel.EquipCatalogViewModel
import com.example.equipmentcatalog_mvvm_room_glide_rxjava_retrofit.viewmodel.EquipCatalogViewModelFactory

class AllEquipsFragment : Fragment() {

    private lateinit var mBinding: FragmentAllEquipsBinding

    private lateinit var mEquipCatalogAdapter: EquipCatalogAdapter

    private lateinit var mCustomListDialog: Dialog

    private val mEquipCatalogViewModel: EquipCatalogViewModel by viewModels {
        EquipCatalogViewModelFactory((requireActivity().application as EquipCatalogApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        mBinding = FragmentAllEquipsBinding.inflate(inflater, container, false)

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.rvEquipsList.layoutManager = GridLayoutManager(requireActivity(), 2)
        mEquipCatalogAdapter = EquipCatalogAdapter(this@AllEquipsFragment)

        mBinding.rvEquipsList.adapter = mEquipCatalogAdapter

        mEquipCatalogViewModel.allEquipmentsList.observe(viewLifecycleOwner) { equipments ->
            equipments.let {
                if (it.isNotEmpty()) {
                    mBinding.rvEquipsList.visibility = View.VISIBLE
                    mBinding.tvNoEquipsAddedYet.visibility = View.GONE

                    mEquipCatalogAdapter.equipmentsList(it)
                } else {
                    mBinding.rvEquipsList.visibility = View.GONE
                    mBinding.tvNoEquipsAddedYet.visibility = View.VISIBLE
                }
            }
        }

        val searchEditText: EditText = mBinding.root.findViewById(R.id.et_equipment_search)

        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {

            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.toString() == "") {
                    mEquipCatalogViewModel.allEquipmentsList.observe(viewLifecycleOwner) { equipments ->
                        equipments.let {
                            if (it.isNotEmpty()) {
                                mBinding.rvEquipsList.visibility = View.VISIBLE
                                mBinding.tvNoEquipsAddedYet.visibility = View.GONE

                                mEquipCatalogAdapter.equipmentsList(it)
                            } else {
                                mBinding.rvEquipsList.visibility = View.GONE
                                mBinding.tvNoEquipsAddedYet.visibility = View.VISIBLE
                            }
                        }
                    }
                } else {
                    mEquipCatalogViewModel.allEquipmentsList.observe(viewLifecycleOwner) { equipments ->
                        equipments.let {
                            if (it.isNotEmpty()) {
                                mBinding.rvEquipsList.visibility = View.VISIBLE
                                mBinding.tvNoEquipsAddedYet.visibility = View.GONE

                                mEquipCatalogAdapter.equipmentsList(
                                    Utils.getFilteredList(
                                        s.toString(),
                                        it
                                    )
                                )

                            } else {
                                mBinding.rvEquipsList.visibility = View.GONE
                                mBinding.tvNoEquipsAddedYet.visibility = View.VISIBLE
                            }
                        }
                    }
                }
            }
        })
    }

    fun equipDetails(equipCatalog: EquipCatalog) {
        findNavController().navigate(
            AllEquipsFragmentDirections.actionAllEquipsToEquipDetails(
                equipCatalog
            )
        )

        if (requireActivity() is MainActivity) {
            (activity as MainActivity).hideBottomNavigationView()
        }
    }

    fun deleteEquip(equip: EquipCatalog) {

        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle(resources.getString(R.string.title_delete_equip))
        builder.setMessage(resources.getString(R.string.msg_delete_equip_dialog, equip.equipment))
        builder.setIcon(android.R.drawable.ic_dialog_alert)
        builder.setPositiveButton(resources.getString(R.string.lbl_yes)) { dialogInterface, _ ->
            mEquipCatalogViewModel.delete(equip)
            dialogInterface.dismiss()
        }
        builder.setNegativeButton(resources.getString(R.string.lbl_no)) { dialogInterface, _ ->

            dialogInterface.dismiss()
        }

        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    override fun onResume() {
        super.onResume()
        if (requireActivity() is MainActivity) {
            (activity as MainActivity).showBottomNavigationView()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_all_equips, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_add_equip -> {
                startActivity(Intent(requireActivity(), NewEquipActivity::class.java))
                return true
            }

            R.id.action_filter_equips -> {
                filterEquipmentsListDialog()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun filterEquipmentsListDialog() {
        mCustomListDialog = Dialog(requireActivity())
        val binding: DialogCustomListBinding = DialogCustomListBinding.inflate(layoutInflater)
        mCustomListDialog.setContentView(binding.root)

        binding.tvEquipment.text = resources.getString(R.string.title_select_item_to_filter)
        val equipmentTypes = Constants.equipsLocal1()

        equipmentTypes.add(0, Constants.ALL_ITEMS)
        binding.rvList.layoutManager = LinearLayoutManager(requireActivity())

        val adapter = ListEquipAdapter(
            requireActivity(),
            this,
            equipmentTypes,
            Constants.FILTER_SELECTION
        )

        binding.rvList.adapter = adapter
        mCustomListDialog.show()
    }

    fun filterSelection(filterItemSelection: String) {

        mCustomListDialog.dismiss()
        Log.i("Filter Selection", filterItemSelection)

        if (filterItemSelection == Constants.ALL_ITEMS) {
            mEquipCatalogViewModel.allEquipmentsList.observe(viewLifecycleOwner) { equipments ->
                equipments.let {
                    if (it.isNotEmpty()) {
                        mBinding.rvEquipsList.visibility = View.VISIBLE
                        mBinding.tvNoEquipsAddedYet.visibility = View.GONE

                        mEquipCatalogAdapter.equipmentsList(it)
                    } else {
                        mBinding.rvEquipsList.visibility = View.GONE
                        mBinding.tvNoEquipsAddedYet.visibility = View.VISIBLE
                    }
                }
            }
        } else {
            mEquipCatalogViewModel.getFilteredList(filterItemSelection)
                .observe(viewLifecycleOwner) { equipments ->
                    equipments.let {
                        if (it.isNotEmpty()) {
                            mBinding.rvEquipsList.visibility = View.VISIBLE
                            mBinding.tvNoEquipsAddedYet.visibility = View.GONE
                            mEquipCatalogAdapter.equipmentsList(it)
                        } else {
                            mBinding.rvEquipsList.visibility = View.GONE
                            mBinding.tvNoEquipsAddedYet.visibility = View.VISIBLE
                        }
                    }
                }
            Log.i("Filter List", "Get Filter List")
        }
    }
}
