package com.example.inventory.ui

import androidx.compose.foundation.background
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier

@Composable
fun GenericAppBar(
    title: String,
    onIconClick: (() -> Unit)?,
    icon: @Composable() (() -> Unit)?,
    iconState: MutableState<Boolean>
) {
    androidx.compose.material.TopAppBar(
        title = { androidx.compose.material.Text(title) },
        backgroundColor = androidx.compose.material.MaterialTheme.colors.primary,
        actions = {
            androidx.compose.material.IconButton(
                onClick = {
                    onIconClick?.invoke()
                },
                content = {
                    if (iconState.value) {
                        icon?.invoke()
                    }
                }

            )
        }
    )
}