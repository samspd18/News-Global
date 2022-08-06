package com.satya.newsglobal.ui.constants

import com.google.android.material.bottomnavigation.BottomNavigationView

data class Category(var name: String?,var imageUrl: String? )
class CategoryArray  {

    companion object {
        val categories = arrayOf(
            Category("National ", "https://cdn-icons-png.flaticon.com/128/3326/3326488.png"),
            Category("Business","https://cdn-icons-png.flaticon.com/128/921/921591.png"),
            Category("Sports","https://cdn-icons-png.flaticon.com/128/857/857455.png"),
            Category("Politics","https://cdn-icons-png.flaticon.com/128/7440/7440506.png"),
            Category("Technology","https://cdn-icons-png.flaticon.com/128/1087/1087840.png"),
            Category("StartUp","https://cdn-icons-png.flaticon.com/128/609/609107.png"),
            Category("Entertainment","https://cdn-icons-png.flaticon.com/128/647/647740.png"),
            Category("Miscellaneous","https://cdn-icons-png.flaticon.com/128/860/860442.png"),
            Category("Hatke","https://cdn-icons-png.flaticon.com/128/2850/2850551.png"),
            Category("Science","https://cdn-icons-png.flaticon.com/128/3081/3081530.png"),
            Category("Automobile","https://cdn-icons-png.flaticon.com/128/741/741460.png")
        )

        const val sharedPreferenceFileName = "NewsGlobalData"
        lateinit var navBar: BottomNavigationView
    }

}