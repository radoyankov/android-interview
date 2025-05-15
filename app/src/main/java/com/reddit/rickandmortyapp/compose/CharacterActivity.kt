package com.reddit.rickandmortyapp.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.GlideImage
import com.reddit.rickandmortyapp.compose.composables.RickAndMortyAppBar
import com.reddit.rickandmortyapp.compose.theme.AppTheme
import com.reddit.rickandmortyapp.network.RickAndMortyCharacter

class CharacterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val character: RickAndMortyCharacter? = getIntent().getSerializableExtra(CHARACTER_IMAGE_URL_KEY)
                as RickAndMortyCharacter?
        setContent {
            AppTheme {
                Column {
                    RickAndMortyAppBar()
                    Surface {
                        Column(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            GlideImage(
                                model = character?.image,
                                contentDescription = "Image of character",
                                modifier = Modifier
                                    .align(alignment = Alignment.CenterHorizontally)
                                    .padding(end = 8.dp)
                                    .size(200.dp),

                                )

                            Text(text = character?.name?:"", modifier = Modifier.padding(8.dp))
                            Text(text = character?.species?:"", modifier = Modifier.padding(8.dp))
                            Text(text = character?.location?.name?:"", modifier = Modifier.padding(8.dp))
                            Text(text = character?.gender?:"", modifier = Modifier.padding(8.dp))
                        }
                    }
                }
            }
        }
    }
}