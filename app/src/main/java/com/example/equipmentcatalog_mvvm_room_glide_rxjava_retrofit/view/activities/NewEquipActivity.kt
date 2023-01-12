package com.example.equipmentcatalog_mvvm_room_glide_rxjava_retrofit.view.activities

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.Nullable
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.equipmentcatalog_mvvm_room_glide_rxjava_retrofit.R
import com.example.equipmentcatalog_mvvm_room_glide_rxjava_retrofit.application.EquipCatalogApplication
import com.example.equipmentcatalog_mvvm_room_glide_rxjava_retrofit.databinding.ActivityNewEquipBinding
import com.example.equipmentcatalog_mvvm_room_glide_rxjava_retrofit.databinding.DialogCustomImageSelectionBinding
import com.example.equipmentcatalog_mvvm_room_glide_rxjava_retrofit.databinding.DialogCustomListBinding
import com.example.equipmentcatalog_mvvm_room_glide_rxjava_retrofit.model.entities.EquipCatalog
import com.example.equipmentcatalog_mvvm_room_glide_rxjava_retrofit.utils.Constants
import com.example.equipmentcatalog_mvvm_room_glide_rxjava_retrofit.view.adapters.ListEquipAdapter
import com.example.equipmentcatalog_mvvm_room_glide_rxjava_retrofit.viewmodel.EquipCatalogViewModel
import com.example.equipmentcatalog_mvvm_room_glide_rxjava_retrofit.viewmodel.EquipCatalogViewModelFactory
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.*

class NewEquipActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mBinding: ActivityNewEquipBinding

    private var mImagePath: String = ""

    private var mEquipCatalogDetails: EquipCatalog? = null

    private val mEquipCatalogViewModel: EquipCatalogViewModel by viewModels {
        EquipCatalogViewModelFactory((application as EquipCatalogApplication).repository)
    }

    private lateinit var mCustomListDialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityNewEquipBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        if (intent.hasExtra(Constants.EXTRA_EQUIP_DETAILS)) {
            mEquipCatalogDetails = intent.getParcelableExtra(Constants.EXTRA_EQUIP_DETAILS)
        }

        setupActionBar()

        mEquipCatalogDetails?.let {

            if (it.id != 0) {
                mImagePath = it.image

                Glide.with(this@NewEquipActivity)
                    .load(mImagePath)
                    .centerCrop()
                    .into(mBinding.ivEquipImage)

                mBinding.etEquipment.setText(it.equipment)
                mBinding.etLocal1.setText(it.local1)
                mBinding.etLocal2.setText(it.local2)
                mBinding.etNote.setText(it.note)
                mBinding.etType.setText(it.type)
                mBinding.etCode.setText(it.code)

                mBinding.btnAddEquip.text = resources.getString(R.string.lbl_update_equip)
            }
        }

        mBinding.ivNewEquipImage.setOnClickListener(this@NewEquipActivity)

        mBinding.etLocal1.setOnClickListener(this@NewEquipActivity)
        mBinding.etLocal2.setOnClickListener(this@NewEquipActivity)
        mBinding.etType.setOnClickListener(this@NewEquipActivity)

        mBinding.btnAddEquip.setOnClickListener(this@NewEquipActivity)
    }

    override fun onClick(v: View) {

        when (v.id) {

            R.id.iv_new_equip_image -> {
                customImageSelectionDialog()
                return
            }

            R.id.et_local1 -> {
                customItemsListDialog(
                    resources.getString(R.string.title_select_equip_local1),
                    Constants.equipsLocal1(),
                    Constants.EQUIP_LOCAL1
                )
                return
            }

            R.id.et_local2 -> {
                customItemsListDialog(
                    resources.getString(R.string.title_select_equip_local2),
                    Constants.equipsLocal2(),
                    Constants.EQUIP_LOCAL2
                )
                return
            }

            R.id.et_type -> {

                customItemsListDialog(
                    resources.getString(R.string.title_select_equip_type),
                    Constants.equipType(),
                    Constants.EQUIP_TYPE
                )
                return
            }

            R.id.btn_add_equip -> {

                val equipment = mBinding.etEquipment.text.toString().trim { it <= ' ' }
                val local1 = mBinding.etLocal1.text.toString().trim { it <= ' ' }
                val local2 = mBinding.etLocal2.text.toString().trim { it <= ' ' }
                val note = mBinding.etNote.text.toString().trim { it <= ' ' }
                val type = mBinding.etType.text.toString().trim { it <= ' ' }
                val code = mBinding.etCode.text.toString().trim { it <= ' ' }

                when {

                    TextUtils.isEmpty(mImagePath) -> {
                        Toast.makeText(
                            this@NewEquipActivity,
                            resources.getString(R.string.err_msg_select_equip_image),
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    TextUtils.isEmpty(equipment) -> {
                        Toast.makeText(
                            this@NewEquipActivity,
                            resources.getString(R.string.err_msg_enter_equipment_title),
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    TextUtils.isEmpty(local1) -> {
                        Toast.makeText(
                            this@NewEquipActivity,
                            resources.getString(R.string.err_msg_select_equip_local1),
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    TextUtils.isEmpty(local2) -> {
                        Toast.makeText(
                            this@NewEquipActivity,
                            resources.getString(R.string.err_msg_select_equip_local2),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    TextUtils.isEmpty(note) -> {
                        Toast.makeText(
                            this@NewEquipActivity,
                            resources.getString(R.string.err_msg_enter_equip_note),
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    TextUtils.isEmpty(type) -> {
                        Toast.makeText(
                            this@NewEquipActivity,
                            resources.getString(R.string.err_msg_select_equip_type),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    TextUtils.isEmpty(code) -> {
                        Toast.makeText(
                            this@NewEquipActivity,
                            resources.getString(R.string.err_msg_enter_equip_code),
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    else -> {

                        var equipID = 0
                        var imageSource = Constants.EQUIP_IMAGE_SOURCE_LOCAL
                        var criticalEquip = false

                        mEquipCatalogDetails?.let {
                            if (it.id != 0) {
                                equipID = it.id
                                imageSource = it.imageSource
                                criticalEquip = it.criticalEquipment
                            }
                        }

                        val equipCatalogDetails = EquipCatalog(
                            mImagePath,
                            imageSource,
                            equipment,
                            local1,
                            local2,
                            note,
                            type,
                            code,
                            criticalEquip,
                            equipID
                        )

                        if (equipID == 0) {
                            mEquipCatalogViewModel.insert(equipCatalogDetails)
                            Toast.makeText(
                                this@NewEquipActivity,
                                "You successfully added your equipment details.",
                                Toast.LENGTH_SHORT
                            ).show()
                            Log.e("Insertion", "Success")
                        } else {
                            mEquipCatalogViewModel.update(equipCatalogDetails)
                            Toast.makeText(
                                this@NewEquipActivity,
                                "You successfully updated your equipment details.",
                                Toast.LENGTH_SHORT
                            ).show()
                            Log.e("Updating", "Success")
                        }
                        finish()
                    }
                }
            }
        }
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CAMERA) {

                data?.extras?.let {
                    val thumbnail: Bitmap =
                        data.extras!!.get("data") as Bitmap // Bitmap from camera

                    Glide.with(this@NewEquipActivity)
                        .load(thumbnail)
                        .centerCrop()
                        .into(mBinding.ivEquipImage)

                    mImagePath = saveImageToInternalStorage(thumbnail)
                    Log.i("ImagePath", mImagePath)

                    mBinding.ivNewEquipImage.setImageDrawable(
                        ContextCompat.getDrawable(
                            this@NewEquipActivity,
                            R.drawable.ic_vector_edit
                        )
                    )
                }
            } else if (requestCode == GALLERY) {

                data?.let {

                    val selectedPhotoUri = data.data

                    Glide.with(this@NewEquipActivity)
                        .load(selectedPhotoUri)
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .listener(object : RequestListener<Drawable> {
                            override fun onLoadFailed(
                                @Nullable e: GlideException?,
                                model: Any?,
                                target: Target<Drawable>?,
                                isFirstResource: Boolean
                            ): Boolean {
                                Log.e("TAG", "Error loading image", e)
                                return false
                            }

                            override fun onResourceReady(
                                resource: Drawable,
                                model: Any?,
                                target: Target<Drawable>?,
                                dataSource: DataSource?,
                                isFirstResource: Boolean
                            ): Boolean {

                                val bitmap: Bitmap = resource.toBitmap()

                                mImagePath = saveImageToInternalStorage(bitmap)
                                Log.i("ImagePath", mImagePath)
                                return false
                            }
                        })
                        .into(mBinding.ivEquipImage)

                    mBinding.ivNewEquipImage.setImageDrawable(
                        ContextCompat.getDrawable(
                            this@NewEquipActivity,
                            R.drawable.ic_vector_edit
                        )
                    )
                }
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            Log.e("Cancelled", "Cancelled")
        }
    }

    private fun setupActionBar() {
        setSupportActionBar(mBinding.toolbarNewEquipActivity)

        if (mEquipCatalogDetails != null && mEquipCatalogDetails!!.id != 0) {
            supportActionBar?.let {
                it.title = resources.getString(R.string.title_edit_equip)
            }
        } else {
            supportActionBar?.let {
                it.title = resources.getString(R.string.title_add_equip)
            }
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back)

        mBinding.toolbarNewEquipActivity.setNavigationOnClickListener { onBackPressed() }
    }

    private fun customImageSelectionDialog() {
        val dialog = Dialog(this@NewEquipActivity)

        val binding: DialogCustomImageSelectionBinding =
            DialogCustomImageSelectionBinding.inflate(layoutInflater)

        dialog.setContentView(binding.root)

        binding.tvCamera.setOnClickListener {

            Dexter.withContext(this@NewEquipActivity)
                .withPermissions(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA
                )
                .withListener(object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport?) {

                        report?.let {

                            if (report.areAllPermissionsGranted()) {

                                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                                startActivityForResult(intent, CAMERA)
                            }
                        }
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        permissions: MutableList<PermissionRequest>?,
                        token: PermissionToken?
                    ) {
                        showRationalDialogForPermissions()
                    }
                }).onSameThread()
                .check()

            dialog.dismiss()
        }

        binding.tvGallery.setOnClickListener {

            Dexter.withContext(this@NewEquipActivity)
                .withPermission(
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
                .withListener(object : PermissionListener {

                    override fun onPermissionGranted(response: PermissionGrantedResponse?) {

                        val galleryIntent = Intent(
                            Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                        )

                        startActivityForResult(galleryIntent, GALLERY)
                    }

                    override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                        Toast.makeText(
                            this@NewEquipActivity,
                            "You have denied the storage permission to select image.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        permission: PermissionRequest?,
                        token: PermissionToken?
                    ) {
                        showRationalDialogForPermissions()
                    }
                }).onSameThread()
                .check()

            dialog.dismiss()
        }

        dialog.show()
    }

    private fun showRationalDialogForPermissions() {
        AlertDialog.Builder(this)
            .setMessage("It Looks like you have turned off permissions required for this feature. It can be enabled under Application Settings")
            .setPositiveButton(
                "GO TO SETTINGS"
            ) { _, _ ->
                try {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", packageName, null)
                    intent.data = uri
                    startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    e.printStackTrace()
                }
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }.show()
    }

    private fun saveImageToInternalStorage(bitmap: Bitmap): String {

        val wrapper = ContextWrapper(applicationContext)

        var file = wrapper.getDir(IMAGE_DIRECTORY, Context.MODE_PRIVATE)

        file = File(file, "${UUID.randomUUID()}.jpg")

        try {

            val stream: OutputStream = FileOutputStream(file)

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)

            stream.flush()

            stream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return file.absolutePath
    }

    private fun customItemsListDialog(title: String, itemsList: List<String>, selection: String) {

        mCustomListDialog = Dialog(this@NewEquipActivity)

        val binding: DialogCustomListBinding = DialogCustomListBinding.inflate(layoutInflater)

        mCustomListDialog.setContentView(binding.root)

        binding.tvEquipment.text = title

        binding.rvList.layoutManager = LinearLayoutManager(this@NewEquipActivity)

        val adapter = ListEquipAdapter(this@NewEquipActivity, null, itemsList, selection)

        binding.rvList.adapter = adapter

        mCustomListDialog.show()
    }

    fun selectedListItem(item: String, selection: String) {

        when (selection) {

            Constants.EQUIP_LOCAL1 -> {
                mCustomListDialog.dismiss()
                mBinding.etLocal1.setText(item)
            }

            Constants.EQUIP_LOCAL2 -> {
                mCustomListDialog.dismiss()
                mBinding.etLocal2.setText(item)
            }
            else -> {
                mCustomListDialog.dismiss()
                mBinding.etType.setText(item)
            }
        }
    }

    companion object {
        private const val CAMERA = 1

        private const val GALLERY = 2

        private const val IMAGE_DIRECTORY = "EquipCatalogImages"
    }
}