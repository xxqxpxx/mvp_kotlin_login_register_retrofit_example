package com.alphagene.presenter.implemenation

import android.net.Uri
import com.alphagene.presenter.ImageContract
import java.io.File

class ImagePresenterImpl (var view: ImageContract.View) : ImageContract.Presenter {

    override fun cameraClick() {

        if (!view.checkPermission()) {
            view.showPermissionDialog()
            return
        }
        if (view.availableDisk() <= 5) {
            view.showNoSpaceDialog()
            return
        }

        val file = view.newFile()

        if (file == null) {
            view.showErrorDialog()
            return
        }

        view.startCamera(file)    }

    override fun ChooseGalleryClick() {
        if (!view.checkPermission()) {
            view.showPermissionDialog();
            return;
        }

        view.chooseGallery();    }

    override fun saveImage(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun permissionDenied() {
        view.showPermissionDialog();
    }

    override fun showPreview(mFilePath: String) {
        view.displayImagePreview(mFilePath);
    }

    override fun showPreview(mFileUri: Uri) {
        view.displayImagePreview(mFileUri);
    }

}