package com.alphagene.view

import android.Manifest.permission.CAMERA
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import kotlinx.android.synthetic.main.activity_camera_ordering.*
import android.content.DialogInterface
import android.annotation.TargetApi
import android.content.pm.PackageManager
import android.provider.MediaStore
import android.content.Intent
import android.app.Activity
import android.content.ComponentName
import android.graphics.Matrix
import android.media.ExifInterface
import android.support.v7.app.AlertDialog
import kotlinx.android.synthetic.main.content_main.*
import java.io.File
import java.io.IOException


class CameraOrderingActivity : AppCompatActivity() {


    private lateinit var myBitmap: Bitmap
    private lateinit var picUri: Uri

    private  var permissionsToRequest: ArrayList<String> = ArrayList()
    private  var permissionsRejected : ArrayList<String> = ArrayList()
    private var permissions: ArrayList<String> =ArrayList()

    private val ALL_PERMISSIONS_RESULT = 107
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.alphagene.R.layout.activity_camera_ordering)

        setupView()
    }

    private fun setupView() {

        fab.setOnClickListener{
            startActivityForResult(getPickImageChooserIntent(), 200)
        }


        permissions.add(CAMERA)
        permissionsToRequest = findUnAskedPermissions(permissions)
        //get the permissions we have asked for before but are not granted..
        //we will store this in a global list to access later.


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {


            if (permissionsToRequest.size > 0)
                requestPermissions(
                    permissionsToRequest.toArray(arrayOfNulls(permissionsToRequest.size)), ALL_PERMISSIONS_RESULT)
        }

    }

    /**
     * Create a chooser intent to select the source to get image from.<br></br>
     * The source can be camera's (ACTION_IMAGE_CAPTURE) or gallery's (ACTION_GET_CONTENT).<br></br>
     * All possible sources are added to the intent chooser.
     */
    private fun getPickImageChooserIntent(): Intent {

        // Determine Uri of camera image to save.
        val outputFileUri = getCaptureImageOutputUri()

        val allIntents : ArrayList<Intent> = ArrayList()
        val packageManager = packageManager

        // collect all camera intents
        val captureIntent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
        val listCam = packageManager.queryIntentActivities(captureIntent, 0)
        for (res in listCam) {
            val intent = Intent(captureIntent)
            intent.component = ComponentName(res.activityInfo.packageName, res.activityInfo.name)
            intent.setPackage(res.activityInfo.packageName)
            if (outputFileUri != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri)
            }
            allIntents.add(intent)

        }

        // collect all gallery intents
        val galleryIntent = Intent(Intent.ACTION_GET_CONTENT)
        galleryIntent.type = "image/*"
        val listGallery = packageManager.queryIntentActivities(galleryIntent, 0)
        for (res in listGallery) {
            val intent = Intent(galleryIntent)
            intent.component = ComponentName(res.activityInfo.packageName, res.activityInfo.name)
            intent.setPackage(res.activityInfo.packageName)
            allIntents.add(intent)
        }

        // the main intent is the last in the list (fucking android) so pickup the useless one
        var mainIntent = allIntents[allIntents.size - 1]
        for (intent in allIntents) {
            if (intent.component!!.className == "com.android.documentsui.DocumentsActivity") {
                mainIntent = intent
                break
            }
        }
        allIntents.remove(mainIntent)

        // Create a chooser from the main intent
        val chooserIntent = Intent.createChooser(mainIntent, "Select source")

        // Add all other intents
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, allIntents.toTypedArray())

        return chooserIntent
    }


    /**
     * Get URI to image received from capture by camera.
     */
    private fun getCaptureImageOutputUri(): Uri {
        lateinit var outputFileUri: Uri
        val getImage = externalCacheDir
        if (getImage != null) {
            outputFileUri = Uri.fromFile(File(getImage.path, "profile.png"))
        }
        return outputFileUri
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val bitmap: Bitmap
        if (resultCode == Activity.RESULT_OK) {

            if (getPickImageResultUri(data) != null) {
                picUri = getPickImageResultUri(data)

                try {
                    myBitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, picUri)
                    myBitmap = rotateImageIfRequired(myBitmap, picUri)
                    myBitmap = getResizedBitmap(myBitmap, 500)

                    img_profile.setImageBitmap(myBitmap)
                    imageView.setImageBitmap(myBitmap)

                } catch (e: IOException) {
                    e.printStackTrace()
                }


            } else {


                bitmap = data!!.extras!!.get("data") as Bitmap

                myBitmap = bitmap
                if (img_profile != null) {
                    img_profile!!.setImageBitmap(myBitmap)
                }

                imageView.setImageBitmap(myBitmap)

            }

        }
    }

    @Throws(IOException::class)
    private fun rotateImageIfRequired(img: Bitmap, selectedImage: Uri): Bitmap {

        val ei = ExifInterface(selectedImage.path)
        val orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)

        when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> return rotateImage(img, 90F)
            ExifInterface.ORIENTATION_ROTATE_180 -> return rotateImage(img, 180F)
            ExifInterface.ORIENTATION_ROTATE_270 -> return rotateImage(img, 270F)
            else -> return img
        }
    }

    private fun rotateImage(img: Bitmap, degree: Float): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(degree)
        val rotatedImg = Bitmap.createBitmap(img, 0, 0, img.width, img.height, matrix, true)
        img.recycle()
        return rotatedImg
    }

    private fun getResizedBitmap(image: Bitmap, maxSize: Int): Bitmap {
        var width = image.width
        var height = image.height

        val bitmapRatio = width.toFloat() / height.toFloat()
        if (bitmapRatio > 0) {
            width = maxSize
            height = (width / bitmapRatio).toInt()
        } else {
            height = maxSize
            width = (height * bitmapRatio).toInt()
        }
        return Bitmap.createScaledBitmap(image, width, height, true)
    }


    /**
     * Get the URI of the selected image from [.getPickImageChooserIntent].<br></br>
     * Will return the correct URI for camera and gallery image.
     *
     * @param data the returned data of the activity result
     */
    fun getPickImageResultUri(data: Intent?): Uri {
        var isCamera = true
        if (data != null) {
            val action = data.action
            isCamera = action != null && action == MediaStore.ACTION_IMAGE_CAPTURE
        }


        return if (isCamera) getCaptureImageOutputUri() else data!!.data
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        // save file url in bundle as it will be null on scren orientation
        // changes
        outState.putParcelable("pic_uri", picUri)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        // get the file url
        picUri = savedInstanceState.getParcelable("pic_uri")
    }

    private fun findUnAskedPermissions(wanted: ArrayList<String>): ArrayList<String> {
        val result = ArrayList<String>()

        for (perm in wanted) {
            if (!hasPermission(perm)) {
                result.add(perm)
            }
        }

        return result
    }

    private fun hasPermission(permission: String): Boolean {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
            }
        }
        return true
    }

    private fun showMessageOKCancel(message: String, okListener: DialogInterface.OnClickListener) {
        AlertDialog.Builder(this)
            .setMessage(message)
            .setPositiveButton("OK", okListener)
            .setNegativeButton("Cancel", null)
            .create()
            .show()
    }

    private fun canMakeSmores(): Boolean {
        return Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1
    }

    @TargetApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {

        when (requestCode) {

            ALL_PERMISSIONS_RESULT -> {
                for (perms in permissionsToRequest) {
                    if (hasPermission(perms)) {

                    } else {

                        permissionsRejected.add(perms)
                    }
                }

                if (permissionsRejected.size > 0) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(permissionsRejected[0])) {
                            showMessageOKCancel("These permissions are mandatory for the application. Please allow access.",
                                DialogInterface.OnClickListener { dialog, which ->
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        //Log.d("API123", "permisionrejected " + permissionsRejected.size());
                                        requestPermissions(permissionsRejected.toArray(arrayOfNulls<String>(permissionsRejected.size)), ALL_PERMISSIONS_RESULT
                                        )
                                    }
                                })
                            return
                        }
                    }

                }
            }
        }

    }



}
