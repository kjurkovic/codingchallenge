package com.kjurkovic.features.albums.details

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.kjurkovic.common.async.Fail
import com.kjurkovic.common.async.Loading
import com.kjurkovic.common.async.Success
import com.kjurkovic.common.async.Uninitialized
import com.kjurkovic.common.ui.components.AppScreen
import com.kjurkovic.common.ui.components.AppToolbar
import com.kjurkovic.common.ui.components.LoadingScreen
import com.kjurkovic.common.ui.theme.allColors
import com.kjurkovic.data.repo.album.models.AlbumModel
import com.kjurkovic.features.albums.R
import timber.log.Timber
import java.time.LocalDate

@Composable
fun AlbumsDetailsScreen(viewModel: AlbumsDetailsViewModel) {
    val viewState = viewModel.viewState.collectAsState().value
    val actionsChannel = viewModel.actionsChannel

    AppScreen(
        topBarBackgroundColor = Color.Transparent,
        statusBarDarkIcons = false,
        topBarContent = {
            AppToolbar(
                leftContent = {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .padding(start = dimensionResource(R.dimen.default_margin))
                            .clip(CircleShape)
                            .background(MaterialTheme.allColors.transparentBackground)
                            .size(dimensionResource(R.dimen.default_icon_size))
                            .clickable { actionsChannel.tryEmit(AlbumDetailsActions.BackClicked) }
                    ) {
                        Image(
                            painter = painterResource(R.drawable.ic_chevron_left),
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) {
        when (viewState.albumDetails) {
            is Fail<*> -> actionsChannel.tryEmit(AlbumDetailsActions.BackClicked)
            is Loading, Uninitialized -> LoadingScreen()
            is Success -> AlbumDetailsContent(viewState.albumDetails.value, it)
        }
    }
}

@Composable
private fun AlbumDetailsContent(
    albumModel: AlbumModel,
    paddingValues: PaddingValues,
) {
    BoxWithConstraints {

        val modifier = if (maxHeight < 700.dp) Modifier.verticalScroll(rememberScrollState()) else Modifier

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(bottom = paddingValues.calculateBottomPadding())
        ) {

            val context = LocalContext.current

            AsyncImage(
                model = albumModel.artworkImage,
                contentDescription = albumModel.name,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
            )

            Column(
                modifier = Modifier
                    .padding(horizontal = dimensionResource(R.dimen.default_margin))
                    .fillMaxWidth()
            ) {
                albumModel.artist?.let {
                    val initialFontSize = MaterialTheme.typography.subtitle1.fontSize
                    var textSize by remember { mutableStateOf(initialFontSize) }

                    Text(
                        text = it.name,
                        color = MaterialTheme.allColors.caption,
                        style = MaterialTheme.typography.subtitle1.copy(fontSize = textSize),
                        modifier = Modifier.padding(top = dimensionResource(R.dimen.medium_margin)),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        onTextLayout = { textLayoutResult ->
                            val maxCurrentLineIndex: Int = textLayoutResult.lineCount - 1
                            if (textLayoutResult.isLineEllipsized(maxCurrentLineIndex)) {
                                textSize = textSize.times(.9f)
                            }
                        },
                    )
                }

                val initialFontSize = MaterialTheme.typography.h1.fontSize
                var textSize by remember { mutableStateOf(initialFontSize) }

                Text(
                    text = albumModel.name,
                    color = MaterialTheme.allColors.onBackground,
                    style = MaterialTheme.typography.h1.copy(fontWeight = FontWeight.Bold, fontSize = textSize),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    onTextLayout = { textLayoutResult ->
                        val maxCurrentLineIndex: Int = textLayoutResult.lineCount - 1
                        if (textLayoutResult.isLineEllipsized(maxCurrentLineIndex)) {
                            textSize = textSize.times(.9f)
                        }
                    },
                )

                if (albumModel.genres.isNotEmpty()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(top = dimensionResource(R.dimen.small_margin)),
                    ) {
                        albumModel.genres.forEach {
                            LabelItem(text = it.name)
                        }
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = stringResource(
                            R.string.albums_release_copyright,
                            albumModel.releaseDate,
                            albumModel.copyright
                        ),
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.allColors.caption,
                        style = MaterialTheme.typography.caption.copy(fontWeight = FontWeight.Medium),
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally)
                            .padding(dimensionResource(R.dimen.default_margin))
                    )

                    albumModel.url?.let {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(albumModel.url))
                        Button(
                            onClick = { context.startActivity(intent) },
                            contentPadding = PaddingValues(
                                horizontal = dimensionResource(R.dimen.large_margin),
                                vertical = dimensionResource(R.dimen.medium_margin),
                            ),
                            modifier = Modifier
                                .padding(
                                    top = dimensionResource(R.dimen.small_margin),
                                    bottom = 47.dp
                                )
                                .align(Alignment.CenterHorizontally)
                                .clip(MaterialTheme.shapes.large)
                                .background(MaterialTheme.colors.primary)

                        ) {
                            Text(
                                text = stringResource(R.string.albums_visit),
                                style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.SemiBold),
                                color = MaterialTheme.colors.onPrimary,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun LabelItem(
    text: String
) {
    Text(
        text = text,
        color = MaterialTheme.colors.primary,
        style = MaterialTheme.typography.caption.copy(fontWeight = FontWeight.Medium),
        modifier = Modifier
            .wrapContentWidth()
            .wrapContentHeight()
            .padding(end = dimensionResource(R.dimen.medium_margin))
            .border(
                width = 1.dp,
                color = MaterialTheme.colors.primary,
                shape = RoundedCornerShape(50),
            )
            .padding(
                start = dimensionResource(R.dimen.albums_label_horizontal_padding),
                end = dimensionResource(R.dimen.albums_label_horizontal_padding),
                top = dimensionResource(R.dimen.albums_label_top_padding),
                bottom = dimensionResource(R.dimen.albums_xs_bottom_padding)
            )
    )
}

