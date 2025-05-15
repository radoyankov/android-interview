package com.reddit.rickandmortyapp.compose

import android.content.Intent
import android.os.Bundle
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.GlideImage
import com.reddit.rickandmortyapp.network.RickAndMortyCharacter


const val CHARACTER_IMAGE_URL_KEY = "character"

@Composable
fun RickAndMortyCharacter(character: RickAndMortyCharacter) {
    val c = LocalContext.current
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .clickable {
                val context = c
                val intent = Intent(context, CharacterActivity::class.java)
                val playerBundle = Bundle()
                playerBundle.putSerializable(CHARACTER_IMAGE_URL_KEY, character)
                intent.putExtras(playerBundle)
                context.startActivity(intent)
            }
            .padding(8.dp)
    ) {
        Row {
            GlideImage(
                model = character.image,
                contentDescription = "Image of ${character.name}",
                modifier = Modifier
                    .align(alignment = Alignment.CenterVertically)
                    .padding(end = 8.dp)
                    .size(100.dp),

                )
            Column {
                Text(text = character.name, modifier = Modifier.padding(8.dp))
                Text(text = character.origin.name, modifier = Modifier.padding(8.dp))
            }
        }
    }
}