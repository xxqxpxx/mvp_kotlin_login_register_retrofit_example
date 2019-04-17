package com.alphagene.presenter

import android.net.Uri

import java.io.File

/**
 * Created on : Jan 06, 2019
 * Author     : AndroidWave
 * Website    : https://androidwave.com/
 */
class ImageContract {
    interface View {

        val filePath: File

        fun checkPermission(): Boolean

        fun showPermissionDialog()

        fun openSettings()

        fun startCamera(file: File)

        fun chooseGallery()

        fun showNoSpaceDialog()

        fun availableDisk(): Int

        fun newFile(): File

        fun showErrorDialog()

        fun displayImagePreview(mFilePath: String)

        fun displayImagePreview(mFileUri: Uri)

        fun getRealPathFromUri(contentUri: Uri): String
    }

    internal interface Presenter {

        fun cameraClick()

        fun ChooseGalleryClick()

        fun saveImage(uri: Uri)

        fun permissionDenied()

        fun showPreview(mFilePath: String)

        fun showPreview(mFileUri: Uri)
    }
}