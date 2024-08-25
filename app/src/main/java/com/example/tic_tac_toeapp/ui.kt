package com.example.tic_tac_toeapp

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tic_tac_toeapp.ui.theme.deepBlue
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(navController: NavController) {
    Box(modifier = Modifier.background(deepBlue).fillMaxSize()) {
        val scale = remember { Animatable(initialValue = 0.6f) }
        var repeatCount by remember { mutableStateOf(0) }

        LaunchedEffect(key1 = repeatCount) {
            if (repeatCount < 1) {
                scale.animateTo(
                    targetValue = 0.9f,
                    animationSpec = tween(
                        durationMillis = 500,
                        easing = {
                            OvershootInterpolator(2f).getInterpolation(it)
                        }
                    )
                )
                delay(1100L)
                scale.animateTo(
                    targetValue = 0.6f,
                    animationSpec = tween(
                        durationMillis = 500,
                        easing = {
                            OvershootInterpolator(2f).getInterpolation(it)
                        }
                    )
                )
                repeatCount++
            } else {
                // Navigate to MainScreen after the animation finishes
                navController.navigate(route = "TicTacToeScreen") {
                    popUpTo("TicTacToeScreen") { inclusive = true }
                }
            }
        }

        Icon(
            painter = painterResource(id = R.drawable.vortexcraftlogo),
            contentDescription = null,
            modifier = Modifier
                .scale(scale.value)
                .align(Alignment.Center)
                .size(320.dp),
            tint = Color.Unspecified
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 55.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "vortexcraft",
                color = Color.White,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}
