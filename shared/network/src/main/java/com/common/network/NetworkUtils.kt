package com.common.network

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import com.common.common.config.CommonBuildConfig
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File

class NetworkUtils(private val context: Context, private val commonBuildConfig: CommonBuildConfig) {

    private var baseUrl = ""

    fun setBaseUrl(baseUrl: String) {
        this.baseUrl = baseUrl
    }

    fun getBaseUrl(): String = baseUrl

    fun createRequestBodyFromString(desc: String): RequestBody {
        return RequestBody.create(MediaType.parse("text/plain"), desc)
    }

    private fun createRequestBodyFromFile(file: File, mimeType: String): RequestBody {
        return RequestBody.create(MediaType.parse(mimeType), file)
    }

    fun createPartFromFile(paramName: String, file: File, mimeType: String): MultipartBody.Part {
        return MultipartBody.Part.createFormData(
            paramName,
            file.name,
            createRequestBodyFromFile(file, mimeType)
        )
    }

//    fun createPartFromUri(paramName: String, contentUri: Uri): MultipartBody.Part {
//        return MultipartBody.Part.createFormData(
//            paramName,
//            StorageUtil.getFileNameFromUri(context.contentResolver, contentUri),
//            ContentUriRequestBody(context.contentResolver, contentUri)
//        )
//    }

    fun createPartFromResources(paramName: String, drawable: Drawable): MultipartBody.Part {
        return MultipartBody.Part.createFormData(
            paramName,
            "default_avatar.png",
            createRequestBodyFromResources(drawable)
        )
    }

    fun createRequestBodyFromResources(drawable: Drawable): RequestBody {
        val bmp = (drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bmp.compress(Bitmap.CompressFormat.PNG, MAX_QUALITY_IMAGE, baos)
        val bmpData = baos.toByteArray()
        return RequestBody.create(MediaType.parse("image/png"), bmpData)
    }

    companion object {
        private const val MAX_QUALITY_IMAGE = 100
    }
}
