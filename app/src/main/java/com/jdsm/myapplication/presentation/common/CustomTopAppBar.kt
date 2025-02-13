package com.jdsm.myapplication.presentation.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(
    title: String,
    onNavigationBack: (() -> Unit)? = null,
    backgroundColor: Color = MaterialTheme.colorScheme.tertiary,
    titleColor: Color = MaterialTheme.colorScheme.onTertiary
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                color = titleColor
            )
        },
        navigationIcon = {
            onNavigationBack?.let {
                IconButton(onClick = { it() }) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = null,
                        tint = titleColor
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = backgroundColor,
            titleContentColor = titleColor
        )
    )
}
