package com.mawjj.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Song(
    val title: String,
    val artist: String
)

private val demoSongs = listOf(
    Song("أغنية تجريبية 1", "MAWJ Artist"),
    Song("أغنية تجريبية 2", "MAWJ Artist"),
    Song("أغنية تجريبية 3", "MAWJ Artist"),
    Song("أغنية تجريبية 4", "MAWJ Artist"),
    Song("أغنية تجريبية 5", "MAWJ Artist")
)

@Composable
fun HomeScreen(
    onSongClick: (Song) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 20.dp)
    ) {

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "مرحباً",
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                )

                Text(
                    text = "MAWJ",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "بحث"
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "أغانيك",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(demoSongs) { song ->
                SongItem(
                    song = song,
                    onClick = {
                        onSongClick(song)
                    }
                )
            }
        }
    }
}

@Composable
private fun SongItem(
    song: Song,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Row(
                modifier = Modifier
                    .size(54.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.MusicNote,
                    contentDescription = null
                )
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 14.dp)
            ) {
                Text(
                    text = song.title,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = song.artist,
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = "المفضلة"
                )
            }

            IconButton(onClick = onClick) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "تشغيل"
                )
            }
        }
    }
}
