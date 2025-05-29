package com.example.helloworldapp.ui.TimerSection

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class TimerViewModel(initialTime: Int) : ViewModel() {
    private val _time = MutableStateFlow(initialTime)
    val time: StateFlow<Int> = _time

    private var timerJob: Job? = null

    fun setTime(seconds: Int) {
        timerJob?.cancel()
        _time.value = seconds
        startTimer()
    }

    private fun startTimer() {
        timerJob = viewModelScope.launch {
            while (_time.value > 0) {
                delay(1000)
                _time.value -= 1
            }
        }
    }
}


class TimerViewModelFactory(private val initialTime: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TimerViewModel(initialTime) as T
    }
}


@Composable
fun TimerDisplay(startTime: Int = 60) {
    val viewModel: TimerViewModel = viewModel(factory = TimerViewModelFactory(startTime))
    val timeLeft by viewModel.time.collectAsState()
    var customTime by remember { mutableStateOf(startTime.toString()) }

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
    ) {
        OutlinedTextField(
            value = customTime,
            onValueChange = { customTime = it },
            label = { Text("Set Timer (seconds)") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                cursorColor = MaterialTheme.colorScheme.primary
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                customTime.toIntOrNull()?.let { newTime ->
                    if (newTime >= 0) {
                        viewModel.setTime(newTime)
                    }
                }
            }
        ) {
            Text("Start Timer")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = if (timeLeft > 0) "Time left: $timeLeft seconds" else "⏰ Time's up!",
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
            color = if (timeLeft == 0) Color.Red else MaterialTheme.colorScheme.onBackground
        )
    }
}
