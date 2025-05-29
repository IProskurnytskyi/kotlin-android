package com.example.helloworldapp.ui.Dropdowns

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

import com.example.helloworldapp.R


@Composable
fun CustomDropdownMenu() {
    val context = LocalContext.current
    val options = listOf(
        stringResource(R.string.option_red),
        stringResource(R.string.option_green),
        stringResource(R.string.option_blue)
    )
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(options[0]) }

    val colorMap = mapOf(
        context.getString(R.string.option_red) to Color.Red,
        context.getString(R.string.option_green) to Color.Green,
        context.getString(R.string.option_blue) to Color.Blue
    )

    val surfaceColor = MaterialTheme.colorScheme.surface
    val textColor = MaterialTheme.colorScheme.onSurface

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(surfaceColor),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Selected: $selectedOption",
            color = textColor
        )
        Box(
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .background(colorMap[selectedOption] ?: Color.LightGray)
        )
        Button(onClick = { expanded = true }) {
            Text(
                text = stringResource(R.string.choose_color),
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        selectedOption = option
                        expanded = false
                        Toast.makeText(context, "You chose: $option", Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownWithoutButton() {
    val context = LocalContext.current
    val options = listOf(
        stringResource(R.string.option_red),
        stringResource(R.string.option_green),
        stringResource(R.string.option_blue)
    )
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(options[0]) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            TextField(
                value = selectedOption,
                onValueChange = {},
                readOnly = true,
                label = { Text(stringResource(R.string.color_selector)) },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                modifier = Modifier.menuAnchor(),
                colors = ExposedDropdownMenuDefaults.textFieldColors()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            selectedOption = option
                            expanded = false
                            Toast.makeText(context, "Selected: $option", Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }
        }
    }
}
