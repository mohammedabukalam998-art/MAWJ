package com.mawjj.app.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LibraryMusic
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.mawjj.app.ui.screens.HomeScreen

@Composable
fun AppNavigation() {

    var selectedItem by remember {
        mutableIntStateOf(0)
    }

    val items = listOf(
        "الرئيسية",
        "البحث",
        "المفضلة",
        "المكتبة"
    )

    val icons = listOf(
        Icons.Default.Home,
        Icons.Default.Search,
        Icons.Default.Favorite,
        Icons.Default.LibraryMusic
    )

    Scaffold(
        bottomBar = {
            NavigationBar {

                items.forEachIndexed { index, title ->

                    NavigationBarItem(
                        selected = selectedItem == index,

                        onClick = {
                            selectedItem = index
                        },

                        icon = {
                            Icon(
                                imageVector = icons[index],
                                contentDescription = title
                            )
                        },

                        label = {
                            Text(title)
                        }
                    )
                }
            }
        }
    ) { paddingValues ->

        when (selectedItem) {

            0 -> {
                HomeScreen(
                    modifier = Modifier.padding(paddingValues)
                )
            }

            1 -> {
                Text(
                    text = "البحث",
                    modifier = Modifier.padding(paddingValues)
                )
            }

            2 -> {
                Text(
                    text = "المفضلة",
                    modifier = Modifier.padding(paddingValues)
                )
            }

            3 -> {
                Text(
                    text = "المكتبة",
                    modifier = Modifier.padding(paddingValues)
                )
            }
        }
    }
}
