package com.example.cointracker.screen.header

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.cointracker.R


@Composable
fun Header() {
    Box(
        modifier = Modifier
            .height(100.dp)  // Altura ajustada conforme solicitado
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.Black,
                        Color.DarkGray
                    )
                )
            )
    ) {
        // Centralizando a imagem dentro do Box
        Image(
            painter = painterResource(id = R.drawable.ftftp),
            contentDescription = "Logo",
            modifier = Modifier
                .size(200.dp)
                .align(Alignment.CenterStart)

        )
    }

    // Linha abaixo do header
    Box(
        modifier = Modifier
            .height(2.5.dp)
            .fillMaxWidth()
            .background(Color.Yellow)
    )
}