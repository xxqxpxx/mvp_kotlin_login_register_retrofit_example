package com.alphagene.view

import android.Manifest
import android.annotation.TargetApi
import android.app.Activity
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.content.FileProvider
import android.support.v7.app.AppCompatActivity
import android.util.Base64
import android.view.View
import android.widget.Toast
import com.alphagene.model.responseModels.LoginResponseModel
import com.alphagene.presenter.implemenation.ICameraOrderPresenterImpl
import com.alphagene.presenter.interfaces.ICameraOrderPresenter
import com.alphagene.view.interfaces.ICameraOrderView
import com.facebook.drawee.backends.pipeline.Fresco
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_camera_ordering.*
import java.io.ByteArrayOutputStream
import java.io.File

class CameraOrderingActivity : AppCompatActivity() , ICameraOrderView/*, ImageContract.View*/ {


    private val REQUEST_TAKE_PHOTO = 101
    private val REQUEST_GALLERY_PHOTO = 102
    private var permissions = arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    private lateinit var imageUri: Uri
    private lateinit var  iCameraOrderPresenter: ICameraOrderPresenter
    private lateinit var mPrefs: SharedPreferences
    private lateinit var currentUser: LoginResponseModel
    private lateinit var strBase64:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.alphagene.R.layout.activity_camera_ordering)

        getUserData()
        setupView()
    }

    private fun getUserData() {
        mPrefs = getSharedPreferences("id", Context.MODE_PRIVATE)
        val gson = Gson()
        val json = mPrefs.getString("MyObject", "")
        currentUser = gson.fromJson<LoginResponseModel>(json, LoginResponseModel::class.java!!)
    }

    private fun setupView() {
        Fresco.initialize(this)

        cameraButton.setOnClickListener {
            val checkSelfPermission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
            if (checkSelfPermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, permissions, REQUEST_TAKE_PHOTO)
            } else {
                val outputImage = File(externalCacheDir, "output_image.jpg")
                if (outputImage.exists()) {
                    outputImage.delete()
                }
                outputImage.createNewFile()
                imageUri = if (Build.VERSION.SDK_INT >= 24) {
                    FileProvider.getUriForFile(this, "com.alphagene.view.fileprovider", outputImage)
                } else {
                    Uri.fromFile(outputImage)
                }

                val intent = Intent("android.media.action.IMAGE_CAPTURE")
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
                startActivityForResult(intent, REQUEST_TAKE_PHOTO)
            }
        }
        galleryButton.setOnClickListener {
            val checkSelfPermission =
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            if (checkSelfPermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, permissions, REQUEST_GALLERY_PHOTO)
            } else {
                openAlbum()
            }
        }

        submitPrescription.setOnClickListener {
            iCameraOrderPresenter.setProgressBarVisibility(View.VISIBLE)
            submitPrescription.isEnabled = false
            val x = 4
            if (x ==4) {
                Toast.makeText(this, "Please Fill the needed data", Toast.LENGTH_SHORT).show()
                submitPrescription.isEnabled = true
                iCameraOrderPresenter.setProgressBarVisibility(View.INVISIBLE)
            }
            else
               iCameraOrderPresenter.doCameraOrder(currentUser.getId().toString() ,"sessionId" ,  strBase64)
        }

        iCameraOrderPresenter = ICameraOrderPresenterImpl(this)
        iCameraOrderPresenter.setProgressBarVisibility(View.INVISIBLE)
    }

    private fun openAlbum() {
        val intent = Intent("android.intent.action.GET_CONTENT")
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_GALLERY_PHOTO)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_TAKE_PHOTO ->
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    cameraButton.performClick()
                } else if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    galleryButton.performClick()
                } else {
                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show()
                }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_TAKE_PHOTO ->
                if (resultCode == Activity.RESULT_OK) {
                    val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(imageUri))
                    picture.setImageBitmap(bitmap)
                    toBase64()

                }
            REQUEST_GALLERY_PHOTO ->
                if (resultCode == Activity.RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= 19) {
                        handleImageOnKitkat(data)
                    }
                }
        }
    }

    @TargetApi(19)
    private fun handleImageOnKitkat(data: Intent?) {
        var imagePath: String? = null
        val uri = data!!.data
        imageUri = uri
        if (DocumentsContract.isDocumentUri(this, uri)) {
//            document类型的Uri，用document id处理
            val docId = DocumentsContract.getDocumentId(uri)
            if ("com.android.providers.media.documents" == uri.authority) {
                val id = docId.split(":")[1]
                val selsetion = MediaStore.Images.Media._ID + "=" + id
                imagePath = imagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selsetion)
            } else if ("com.android.providers.downloads.documents" == uri.authority) {
                val contentUri = ContentUris.withAppendedId(
                    Uri.parse("content://downloads/public_downloads"),
                    java.lang.Long.valueOf(docId)
                )
                imagePath = imagePath(contentUri, null)
            }
        } else if ("content".equals(uri.scheme, ignoreCase = true)) {
//            content类型Uri 普通方式处理
            imagePath = imagePath(uri, null)
        } else if ("file".equals(uri.scheme, ignoreCase = true)) {
            imagePath = uri.path
        }
        displayImage(imagePath)
    }


    private fun imagePath(uri: Uri, selection: String?): String {
        var path: String? = null
//        通过Uri和selection获取路径
        val cursor = contentResolver.query(uri, null, selection, null, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
            }
            cursor.close()
        }
        return path!!
    }

    private fun displayImage(imagePath: String?) {
        if (imagePath != null) {
            val bitmap = BitmapFactory.decodeFile(imagePath)
            picture.setImageBitmap(bitmap)
            toBase64()
        } else {
            Toast.makeText(this, "Failed to get image", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCameraOrderResult(result: Boolean, code: Int) {
        iCameraOrderPresenter.setProgressBarVisibility(View.INVISIBLE)
        if (result && code == 1) {
            Toast.makeText(this,  getString(com.alphagene.R.string.Success), Toast.LENGTH_SHORT).show()

        } else {
            Toast.makeText(this,  getString(com.alphagene.R.string.please_try_again), Toast.LENGTH_SHORT).show()
            submitPrescription.isEnabled = true
        }
    }

    override fun onSetProgressBarVisibility(visibility: Int) {
        progress_camer_order.visibility = visibility
    }

    private fun toBase64() {

       // val file =  imagePath(imageUri, null)
     //   val selectedImage = BitmapFactory.decodeFile(file.getAbsolutePath())

        val selectedImage = BitmapFactory.decodeFile(picture.drawable.toString())
        val stream = ByteArrayOutputStream()
        selectedImage.compress(Bitmap.CompressFormat.JPEG, 30, stream)
        val byteArray = stream.toByteArray()
        strBase64 = Base64.encodeToString(byteArray, 0)
    }
}