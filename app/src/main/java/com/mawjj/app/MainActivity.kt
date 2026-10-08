package com.mawjj.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.mawjj.app.navigation.AppNavigation
import com.mawjj.app.ui.theme.MAWJTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MAWJTheme {
                AppNavigation()
            }
        }
    }
}
