package com.example.rickandmorty.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.network.models.domain.CharacterStatus
import com.example.rickandmorty.ui.theme.RickAction

@Composable
fun CharacterDetailsNamePlateComponent(name: String, status: CharacterStatus) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
    ) {
        CharacterStatusComponent(characterStatus = status)
        Text(
            text = name,
            fontSize = 42.sp,
            lineHeight = 42.sp,
            fontWeight = FontWeight.Bold,
            color = RickAction
        )
    }
}