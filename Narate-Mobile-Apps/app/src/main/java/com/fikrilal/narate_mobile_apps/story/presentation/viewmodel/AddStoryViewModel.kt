package com.fikrilal.narate_mobile_apps.story.presentation.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fikrilal.narate_mobile_apps._core.data.model.stories.StoryDetailResponse
import com.fikrilal.narate_mobile_apps._core.data.repository.story.StoryRepository
import com.fikrilal.narate_mobile_apps.story.data.utils.CompressImageUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class StoryViewModel @Inject constructor(
    private val storyRepository: StoryRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _imageUri = MutableLiveData<Uri?>()
    val imageUri: LiveData<Uri?> = _imageUri

    private val _text = MutableLiveData<String>()
    val text: LiveData<String> = _text

    private val _uploadResult = MutableLiveData<Result<StoryDetailResponse>>()
    val uploadResult: LiveData<Result<StoryDetailResponse>> = _uploadResult

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading

    fun setImageUri(uri: Uri?) {
        _imageUri.value = uri
    }

    fun setText(input: String) {
        _text.value = input
    }

    private fun uriToFile(uri: Uri, context: Context): File {
        val inputStream = context.contentResolver.openInputStream(uri)
        val file = File(context.cacheDir, "temp_image")
        inputStream?.use { input ->
            file.outputStream().use { output ->
                input.copyTo(output)
            }
        }
        return file
    }

    fun uploadStory() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val currentImageUri = _imageUri.value
                val currentText = _text.value

                if (currentImageUri != null && currentText != null) {
                    val bitmap = CompressImageUtils.uriToBitmap(context.contentResolver, currentImageUri)
                    val resizedBitmap = CompressImageUtils.resizeBitmap(bitmap, 1024)
                    val compressedImage = CompressImageUtils.compressBitmap(resizedBitmap, 80)
                    val photoFile = byteArrayToFile(context, compressedImage, "compressed_image.jpg")
                    val token = storyRepository.userPreferences.getUserToken()
                    token.let {
                        Log.d("StoryViewModel", "Using token for uploadStory: Bearer $it")
                        val response = storyRepository.addNewStory(
                            description = currentText,
                            photo = photoFile,
                            lat = null,
                            lon = null,
                            token = "Bearer $it"
                        )
                        _uploadResult.value = Result.success(response)
                    }
                } else {
                    _uploadResult.value = Result.failure(Exception("Image or text is null"))
                }
            } catch (e: Exception) {
                _uploadResult.value = Result.failure(e)
            } finally {
                _isLoading.postValue(false)
            }
        }
    }

    private fun byteArrayToFile(context: Context, imageData: ByteArray, fileName: String): File {
        val file = File(context.cacheDir, fileName)
        file.writeBytes(imageData)
        return file
    }


}
