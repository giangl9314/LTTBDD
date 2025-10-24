package com.example.uthsmarttasks.model
import androidx.annotation.DrawableRes

data class OnboardingPage(
    val title: String,
    val description: String,
    @DrawableRes val image: Int
)
