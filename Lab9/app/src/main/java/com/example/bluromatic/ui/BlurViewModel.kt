package com.example.bluromatic.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.work.WorkInfo
import com.example.bluromatic.BluromaticApplication
import com.example.bluromatic.data.BlurAmountData
import com.example.bluromatic.data.BluromaticRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class BlurViewModel(private val bluromaticRepository: BluromaticRepository) : ViewModel() {

    internal val blurAmount = BlurAmountData.blurAmount

    private val _blurUiState = MutableStateFlow<BlurUiState>(BlurUiState.Default)
    val blurUiState: StateFlow<BlurUiState> = _blurUiState

    init {
        viewModelScope.launch {
            bluromaticRepository.outputWorkInfo.collect { workInfo ->
                if (workInfo == null) return@collect

                when (workInfo.state) {
                    WorkInfo.State.RUNNING -> {
                        _blurUiState.value = BlurUiState.Loading
                    }

                    WorkInfo.State.SUCCEEDED -> {
                        val outputUri =
                            workInfo.outputData.getString("KEY_IMAGE_URI") ?: ""
                        _blurUiState.value = BlurUiState.Complete(outputUri)
                    }

                    else -> {}
                }
            }
        }
    }

    fun applyBlur(blurLevel: Int) {
        _blurUiState.value = BlurUiState.Loading
        bluromaticRepository.applyBlur(blurLevel)
    }

    fun cancelWork() {
        bluromaticRepository.cancelWork()
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val bluromaticRepository =
                    (this[APPLICATION_KEY] as BluromaticApplication).container.bluromaticRepository
                BlurViewModel(bluromaticRepository)
            }
        }
    }
}

sealed interface BlurUiState {
    object Default : BlurUiState
    object Loading : BlurUiState
    data class Complete(val outputUri: String) : BlurUiState
}