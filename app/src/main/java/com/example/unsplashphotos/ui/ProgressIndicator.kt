package com.example.unsplashphotos.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.unsplashphotos.LineSpinFadeLoaderProgressIndicator

@Composable
fun ProgressIndicator(backGroundColor: Color = Color.Transparent) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backGroundColor),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LineSpinFadeLoaderProgressIndicator(color = Color.White, rectCornerRadius = 5.dp)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProgressIndicatorPreview() {
    ProgressIndicator()
}
