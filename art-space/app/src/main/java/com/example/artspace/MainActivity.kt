package com.example.artspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.artspace.ui.theme.ArtSpaceTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.ui.tooling.preview.Devices

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ArtSpaceTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    ArtSpaceApp()
                }
            }
        }
    }
}

@Composable
fun ArtworkWall(
    @DrawableRes imageRes: Int,
    @StringRes contentDescriptionRes: Int,
    modifier: Modifier = Modifier
) {
    Surface(modifier = modifier, shadowElevation = 8.dp) {
        Image(
            painter = painterResource(imageRes),
            contentDescription = stringResource(contentDescriptionRes),
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        )
    }
}

@Composable
fun ArtworkDescriptor(
    @StringRes titleRes: Int,
    @StringRes artistRes: Int,
    @StringRes yearRes: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(titleRes),
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = stringResource(R.string.artist_year, stringResource(artistRes), stringResource(yearRes)),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun DisplayController(
    onPrevious: () -> Unit,
    onNext: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Button(onClick = onPrevious, modifier = Modifier.weight(1f)) {
            Text(stringResource(R.string.previous))
        }
        Button(onClick = onNext, modifier = Modifier.weight(1f)) {
            Text(stringResource(R.string.next))
        }
    }
}

@Composable
fun ArtSpaceApp(modifier: Modifier = Modifier) {
    var currentArtwork by remember { mutableIntStateOf(1) }

    val imageRes = when (currentArtwork) {
        1 -> R.drawable.art_1
        2 -> R.drawable.art_2
        else -> R.drawable.art_3
    }
    val titleRes = when (currentArtwork) {
        1 -> R.string.art_1_title
        2 -> R.string.art_2_title
        else -> R.string.art_3_title
    }
    val artistRes = when (currentArtwork) {
        1 -> R.string.art_1_artist
        2 -> R.string.art_2_artist
        else -> R.string.art_3_artist
    }
    val yearRes = when (currentArtwork) {
        1 -> R.string.art_1_year
        2 -> R.string.art_2_year
        else -> R.string.art_3_year
    }

    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .padding(16.dp)
    ) {
        val onPrevious = {
            currentArtwork = when (currentArtwork) {
                1 -> 3
                2 -> 1
                else -> 2
            }
        }
        val onNext = {
            currentArtwork = when (currentArtwork) {
                1 -> 2
                2 -> 3
                else -> 1
            }
        }

        if (maxWidth > maxHeight) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                ArtworkWall(
                    imageRes = imageRes,
                    contentDescriptionRes = titleRes,
                    modifier = Modifier.weight(1f).fillMaxHeight()
                )
                Column(
                    modifier = Modifier.weight(1f).fillMaxHeight(),
                    verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterVertically)
                ) {
                    ArtworkDescriptor(titleRes = titleRes, artistRes = artistRes, yearRes = yearRes)
                    DisplayController(onPrevious = onPrevious, onNext = onNext)
                }
            }
        } else {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                ArtworkWall(
                    imageRes = imageRes,
                    contentDescriptionRes = titleRes,
                    modifier = Modifier.weight(1f).fillMaxWidth()
                )
                ArtworkDescriptor(titleRes = titleRes, artistRes = artistRes, yearRes = yearRes)
                DisplayController(onPrevious = onPrevious, onNext = onNext)
            }
        }
    }
}

@Preview(name = "Tablet", device = Devices.TABLET)
@Composable
fun ArtSpaceTabletPreview() {
    ArtSpaceTheme { ArtSpaceApp() }
}

@Preview(name = "Phone landscape", widthDp = 891, heightDp = 411)
@Composable
fun ArtSpaceLandscapePreview() {
    ArtSpaceTheme { ArtSpaceApp() }
}

@Preview(showBackground = true)
@Composable
fun ArtSpaceAppPreview() {
    ArtSpaceTheme { ArtSpaceApp() }
}