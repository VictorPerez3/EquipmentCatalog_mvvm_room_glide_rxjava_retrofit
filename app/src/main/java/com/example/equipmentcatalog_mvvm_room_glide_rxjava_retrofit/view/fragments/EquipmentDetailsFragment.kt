package com.example.equipmentcatalog_mvvm_room_glide_rxjava_retrofit.view.fragments

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.equipmentcatalog_mvvm_room_glide_rxjava_retrofit.R
import com.example.equipmentcatalog_mvvm_room_glide_rxjava_retrofit.application.EquipCatalogApplication
import com.example.equipmentcatalog_mvvm_room_glide_rxjava_retrofit.databinding.FragmentEquipmentDetailsBinding
import com.example.equipmentcatalog_mvvm_room_glide_rxjava_retrofit.model.entities.EquipCatalog
import com.example.equipmentcatalog_mvvm_room_glide_rxjava_retrofit.utils.Constants
import com.example.equipmentcatalog_mvvm_room_glide_rxjava_retrofit.viewmodel.EquipCatalogViewModel
import com.example.equipmentcatalog_mvvm_room_glide_rxjava_retrofit.viewmodel.EquipCatalogViewModelFactory
import java.io.IOException
import java.util.*

class EquipmentDetailsFragment : Fragment() {

    private var mBinding: FragmentEquipmentDetailsBinding? = null

    private var mEquipCatalogDetails: EquipCatalog? = null

    private val mEquipCatalogViewModel: EquipCatalogViewModel by viewModels {
        EquipCatalogViewModelFactory((requireActivity().application as EquipCatalogApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_share, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.action_share_equip -> {
                val type = "text/plain"
                val subject = "Checkout this Equipment recipe"
                val extraText = ""
                val shareWith = "Share with"

                val intent = Intent(Intent.ACTION_SEND)
                intent.type = type
                intent.putExtra(Intent.EXTRA_SUBJECT, subject)
                intent.putExtra(Intent.EXTRA_TEXT, extraText)
                startActivity(Intent.createChooser(intent, shareWith))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentEquipmentDetailsBinding.inflate(inflater, container, false)
        return mBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args: EquipmentDetailsFragmentArgs by navArgs()

        mEquipCatalogDetails = args.equipDetails
        args.let {
            try {
                Glide.with(requireActivity())
                    .load(it.equipDetails.image)
                    .centerCrop()
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            Log.e("TAG", "ERROR loading Image", e)
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            resource.let {

                                Palette.from(resource!!.toBitmap())
                                    .generate(fun(palette: Palette?) {
                                        val intColor = palette?.vibrantSwatch?.rgb ?: 0
                                        mBinding!!.rlEquipDetailMain.setBackgroundColor(intColor)
                                    })
                            }
                            return false
                        }

                    })
                    .into(mBinding!!.ivEquipImage)
            } catch (e: IOException) {
                e.printStackTrace()
            }

            mBinding!!.tvEquipment.text = it.equipDetails.equipment
            mBinding!!.tvCode.text = it.equipDetails.code
            mBinding!!.tvLocal1.text =
                it.equipDetails.local1.capitalize(Locale.ROOT)
            mBinding!!.tvLocal2.text = it.equipDetails.local2
            mBinding!!.tvNote.text = it.equipDetails.note
            mBinding!!.tvType.text = it.equipDetails.type

            if (args.equipDetails.criticalEquipment) {
                mBinding!!.ivCriticalEquip.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireActivity(),
                        R.drawable.ic_critical_selected
                    )
                )
            } else {
                mBinding!!.ivCriticalEquip.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireActivity(),
                        R.drawable.ic_critical_unselected
                    )
                )
            }
        }
        mBinding!!.ivCriticalEquip.setOnClickListener {

            args.equipDetails.criticalEquipment = !args.equipDetails.criticalEquipment
            mEquipCatalogViewModel.update(args.equipDetails)

            if (args.equipDetails.criticalEquipment) {
                mBinding!!.ivCriticalEquip.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireActivity(),
                        R.drawable.ic_critical_selected
                    )
                )

                Toast.makeText(
                    requireActivity(),
                    resources.getString(R.string.msg_added_to_criticals),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                mBinding!!.ivCriticalEquip.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireActivity(),
                        R.drawable.ic_critical_unselected
                    )
                )

                Toast.makeText(
                    requireActivity(),
                    resources.getString(R.string.msg_removed_from_critical),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding = null
    }
}