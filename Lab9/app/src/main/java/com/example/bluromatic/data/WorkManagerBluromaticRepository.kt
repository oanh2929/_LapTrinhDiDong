package com.example.bluromatic.data

import android.content.Context
import android.net.Uri
import androidx.work.*
import com.example.bluromatic.KEY_BLUR_LEVEL
import com.example.bluromatic.KEY_IMAGE_URI
import com.example.bluromatic.TAG_OUTPUT
import com.example.bluromatic.getImageUri
import com.example.bluromatic.workers.BlurWorker
import com.example.bluromatic.workers.CleanupWorker
import com.example.bluromatic.workers.SaveImageToFileWorker
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class WorkManagerBluromaticRepository(context: Context) : BluromaticRepository {

    private var imageUri: Uri = context.getImageUri()
    private val workManager = WorkManager.getInstance(context)

    // ✅ FIX QUAN TRỌNG NHẤT
    override val outputWorkInfo: Flow<WorkInfo?> =
        workManager.getWorkInfosByTagFlow(TAG_OUTPUT)
            .map { list -> list.firstOrNull() }

    override fun applyBlur(blurLevel: Int) {

        var continuation = workManager.beginUniqueWork(
            "blur_work",
            ExistingWorkPolicy.REPLACE,
            OneTimeWorkRequest.from(CleanupWorker::class.java)
        )

        // blur nhiều lần theo level
        repeat(blurLevel) {
            val blurRequest =
                OneTimeWorkRequestBuilder<BlurWorker>()
                    .setInputData(createInputDataForWorkRequest(blurLevel, imageUri))
                    .build()

            continuation = continuation.then(blurRequest)
        }

        // save file
        val saveRequest =
            OneTimeWorkRequestBuilder<SaveImageToFileWorker>()
                .addTag(TAG_OUTPUT) // ⚠️ QUAN TRỌNG
                .build()

        continuation = continuation.then(saveRequest)

        continuation.enqueue()
    }

    override fun cancelWork() {
        workManager.cancelUniqueWork("blur_work")
    }

    private fun createInputDataForWorkRequest(
        blurLevel: Int,
        imageUri: Uri
    ): Data {
        return Data.Builder()
            .putString(KEY_IMAGE_URI, imageUri.toString())
            .putInt(KEY_BLUR_LEVEL, blurLevel)
            .build()
    }
}