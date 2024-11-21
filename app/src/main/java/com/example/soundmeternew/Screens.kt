package com.example.soundmeternew

sealed class Screens(val screen: String) {
    object Home : Screens("home")
    object Search : Screens("search")
    object Dictionary : Screens("dictionary")
    object Records : Screens("records")
}