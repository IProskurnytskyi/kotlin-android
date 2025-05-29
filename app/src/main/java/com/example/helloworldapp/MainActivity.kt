package com.example.helloworldapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

import com.example.helloworldapp.ui.Dropdowns.CustomDropdownMenu
import com.example.helloworldapp.ui.Dropdowns.DropdownWithoutButton
import com.example.helloworldapp.ui.MessageSection.MessageInputSection
import com.example.helloworldapp.ui.TimerSection.TimerDisplay
import com.example.helloworldapp.ui.theme.HelloWorldAppTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var isDarkTheme by remember { mutableStateOf(false) }

            HelloWorldAppTheme(darkTheme = isDarkTheme) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        containerColor = Color.Transparent
                    ) { innerPadding ->
                        Column(
                            modifier = Modifier
                                .padding(innerPadding)
                                .padding(16.dp)
                                .verticalScroll(rememberScrollState())
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Dark Theme")
                                Spacer(modifier = Modifier.width(8.dp))
                                Switch(
                                    checked = isDarkTheme,
                                    onCheckedChange = { isDarkTheme = it }
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))
                            CustomDropdownMenu()
                            Spacer(modifier = Modifier.height(16.dp))
                            DropdownWithoutButton()
                            Spacer(modifier = Modifier.height(16.dp))
                            MessageInputSection()
                            Spacer(modifier = Modifier.height(16.dp))
                            TimerDisplay()
                        }
                    }
                }
            }
        }
    }
}
