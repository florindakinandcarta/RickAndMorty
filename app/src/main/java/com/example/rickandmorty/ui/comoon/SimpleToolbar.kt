package com.example.rickandmorty.ui.comoon

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rickandmorty.ui.theme.RickTextPrimary


@Composable
fun SimpleToolbar(
    title: String,
    onBackAction: (() -> Unit)? = null
) {
    Column() {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 12.dp, horizontal = 16.dp)
        ) {
            if (onBackAction != null) {
                IconButton(
                    onClick = {
                        onBackAction()
                    },
                    modifier = Modifier
                        .padding(end = 12.dp)
                        .size(32.dp),
                    content = {
                        Icon(
                            Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = "Back Arrow",
                            modifier = Modifier.size(32.dp),
                            tint = RickTextPrimary
                        )
                    }
                )
            }
            Text(
                text = title, fontSize = 30.sp, style = TextStyle(
                    color = RickTextPrimary, fontWeight = FontWeight.Bold
                )
            )
        }
        Box(
            modifier = Modifier
                .background(color = RickTextPrimary)
                .fillMaxWidth()
                .height(1.dp)
        )
    }
}