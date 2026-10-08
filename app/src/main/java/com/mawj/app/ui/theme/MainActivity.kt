package com.mawj.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mawj.app.ui.theme.MAWJTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MAWJTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MawjMainScreen()
                }
            }
        }
    }
}

data class Song(val id: Int, val title: String, val artist: String, val duration: String, var isFavorite: Boolean = false)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MawjMainScreen() {
    var isPlaying by remember { mutableStateOf(false) }
    var sliderPosition by remember { mutableStateOf(30f) }
    var currentSongIndex by remember { mutableStateOf(0) }
    var searchQuery by remember { mutableStateOf("") }

    val playlist = remember {
        mutableStateListOf(
            Song(1, "الموجة الأولى", "تطبيق MAWJ الصوتي", "3:00", true),
            Song(2, "نبض الساحل", "أشباح ميوزك", "3:45", false),
            Song(3, "صدى الأفق", "MAWJ Experience", "4:12", false),
            Song(4, "إيقاع العاصفة", "سيمفونية الموج", "2:55", true)
        )
    }

    // تصفية الأغاني بناءً على بحث المستخدم
    val filteredPlaylist = playlist.filter { 
        it.title.contains(searchQuery, ignoreCase = true) || it.artist.contains(searchQuery, ignoreCase = true)
    }

    val currentSong = if (filteredPlaylist.isNotEmpty()) {
        filteredPlaylist.getOrElse(currentSongIndex) { filteredPlaylist[0] }
    } else {
        Song(0, "لا توجد نتائج", "بحث فارغ", "0:00")
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("MAWJ - مشغل الأمواج", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // شريط البحث السريع
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("ابحث عن أغنية أو فنان...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "بحث") },
                shape = RoundedCornerShape(12.dp),
                singleLine = true
            )

            // غلاف الأغنية مع المؤثر البصري للأمواج
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.MusicNote,
                        contentDescription = null,
                        modifier = Modifier.size(48.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    AudioVisualizerBars(isPlaying = isPlaying)
                }
            }

            // معلومات الأغنية الحالية
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = currentSong.title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = currentSong.artist,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }

            // شريط التقدم الزمني
            Column(modifier = Modifier.fillMaxWidth()) {
                Slider(
                    value = sliderPosition,
                    onValueChange = { sliderPosition = it },
                    valueRange = 0f..180f
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "0:30", fontSize = 12.sp, color = Color.Gray)
                    Text(text = currentSong.duration, fontSize = 12.sp, color = Color.Gray)
                }
            }

            // أزرار التحكم بالتشغيل
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    if (filteredPlaylist.isNotEmpty()) {
                        currentSongIndex = if (currentSongIndex > 0) currentSongIndex - 1 else filteredPlaylist.size - 1
                    }
                }) {
                    Icon(imageVector = Icons.Default.SkipPrevious, contentDescription = "السابق", modifier = Modifier.size(32.dp))
                }

                IconButton(
                    onClick = { isPlaying = !isPlaying },
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary)
                ) {
                    Icon(imageVector = Icons.Default.PlayArrow, contentDescription = "تشغيل", tint = Color.White, modifier = Modifier.size(32.dp))
                }

                IconButton(onClick = {
                    if (filteredPlaylist.isNotEmpty()) {
                        currentSongIndex = if (currentSongIndex < filteredPlaylist.size - 1) currentSongIndex + 1 else 0
                    }
                }) {
                    Icon(imageVector = Icons.Default.SkipNext, contentDescription = "التالي", modifier = Modifier.size(32.dp))
                }
            }

            // قائمة الأغاني مع دعم المفضلة
            Text(
                text = "قائمة التشغيل والمفضلة",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(vertical = 2.dp)
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
            ) {
                itemsIndexed(filteredPlaylist) { index, song ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 3.dp)
                            .clickable { currentSongIndex = index },
                        colors = CardDefaults.cardColors(
                            containerColor = if (index == currentSongIndex) 
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.2f) 
                            else 
                                MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(song.title, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                Text(song.artist, fontSize = 11.sp, color = Color.Gray)
                            }
                            
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(song.duration, fontSize = 11.sp, color = Color.Gray)
                                Spacer(modifier = Modifier.width(8.dp))
                                // زر المفضلة التفاعلي داخل القائمة
                                IconButton(onClick = {
                                    val originalIndex = playlist.indexOfFirst { it.id == song.id }
                                    if (originalIndex != -1) {
                                        playlist[originalIndex] = song.copy(isFavorite = !song.isFavorite)
                                    }
                                }) {
                                    Icon(
                                        imageVector = if (song.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                        contentDescription = "مفضلة",
                                        tint = if (song.isFavorite) Color.Red else Color.Gray,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AudioVisualizerBars(isPlaying: Boolean) {
    val infiniteTransition = rememberInfiniteTransition(label = "wave")
    
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.height(24.dp)
    ) {
        repeat(5) { index ->
            val heightAnim by infiniteTransition.animateFloat(
                initialValue = 6f,
                targetValue = if (isPlaying) (12..24).random().toFloat() else 6f,
                animationSpec = infiniteRepeatable(
                    animation = tween(durationMillis = 300 + (index * 80), easing = FastOutSlowInEasing),
                    repeatMode = RepeatMode.Reverse
                ),
                label = "bar_$index"
            )

            Box(
                modifier = Modifier
                    .width(4.dp)
                    .height(heightAnim.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(MaterialTheme.colorScheme.primary)
            )
        }
    }
}
