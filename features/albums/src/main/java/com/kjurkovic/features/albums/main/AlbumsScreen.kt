package com.kjurkovic.features.albums.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.kjurkovic.common.async.Fail
import com.kjurkovic.common.async.Loading
import com.kjurkovic.common.async.Success
import com.kjurkovic.common.async.Uninitialized
import com.kjurkovic.common.ui.components.AppScreen
import com.kjurkovic.common.ui.components.AppToolbar
import com.kjurkovic.common.ui.components.LoadingScreen
import com.kjurkovic.common.ui.theme.ImageOverlayGradient
import com.kjurkovic.common.ui.theme.allColors
import com.kjurkovic.data.repo.album.models.AlbumModel
import com.kjurkovic.features.albums.R
import kotlinx.coroutines.flow.MutableSharedFlow

@Composable
fun AlbumsScreen(viewModel: AlbumsViewModel) {
    val viewState = viewModel.viewState.collectAsState().value
    val actionsChannel = viewModel.actionsChannel

    AppScreen(
        topBarBackgroundColor = Color.White.copy(alpha = .95f),
        topBarElevation = dimensionResource(R.dimen.elevation_medium),
        topBarContent = {
            AppToolbar(
                titleContent = {
                    Text(
                        text = stringResource(R.string.albums_screen_title),
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            letterSpacing = (-0.64).sp,
                        )
                    )
                },
            )
        },
    ) {

        if (viewState.isNetworkFetchFailed && viewState.albumList is Success && viewState.albumList.value.isEmpty()) {
            RetryScreen(actionsChannel = actionsChannel)
        } else {
            when (viewState.albumList) {
                is Fail<*> -> RetryScreen(actionsChannel = actionsChannel)
                is Loading, Uninitialized -> LoadingScreen()
                is Success -> AlbumListScreen(
                    albums = viewState.albumList.value,
                    actionsChannel = actionsChannel,
                    padding = it
                )
            }
        }
    }
}

@Composable
private fun AlbumListScreen(
    albums: List<AlbumModel>,
    actionsChannel: MutableSharedFlow<AlbumActions>,
    padding: PaddingValues
) {
    val contentPadding = dimensionResource(R.dimen.default_margin)
    val verticalSpacing = dimensionResource(R.dimen.albums_vertical_spacing_between)
    val horizontalSpacing = dimensionResource(R.dimen.albums_horizontal_spacing_between)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                bottom = padding.calculateBottomPadding(),
            )
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(contentPadding),
            verticalArrangement = Arrangement.spacedBy(verticalSpacing),
        ) {
            itemsIndexed(albums) { idx, item ->
                key(item.id) {
                    val topMargin = if (idx == 0 || idx == 1) padding.calculateTopPadding() else 0.dp
                    Box(
                        modifier = Modifier.padding(top = topMargin)
                    ) {
                        val itemPadding = PaddingValues(
                            start = if (idx % 2 == 0) 0.dp else horizontalSpacing,
                            end = if (idx % 2 != 0) 0.dp else horizontalSpacing,
                        )
                        AlbumItem(item, actionsChannel, itemPadding)
                    }
                }
            }
        }
    }
}

@Composable
private fun AlbumItem(
    albumModel: AlbumModel,
    actionsChannel: MutableSharedFlow<AlbumActions>,
    padding: PaddingValues,
) {
    Box(
        contentAlignment = Alignment.BottomStart,
        modifier = Modifier
            .padding(padding)
            .aspectRatio(1f)
            .clip(MaterialTheme.shapes.large)
            .clickable { actionsChannel.tryEmit(AlbumActions.AlbumClicked(albumModel.id)) }
    ) {
        AsyncImage(
            model = albumModel.artworkThumb,
            contentDescription = albumModel.name,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds,
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = ImageOverlayGradient)
        )

        Column(
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier.padding(dimensionResource(R.dimen.medium_margin))
        ) {
            Text(
                text = albumModel.name,
                style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.SemiBold),
                color = MaterialTheme.colors.onPrimary,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )

            albumModel.artist?.let {
                Text(
                    text = it.name,
                    style = MaterialTheme.typography.caption.copy(fontWeight = FontWeight.Medium),
                    color = MaterialTheme.allColors.caption,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}

@Composable
private fun RetryScreen(
    actionsChannel: MutableSharedFlow<AlbumActions>
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Text(
                text = stringResource(R.string.albums_retry_description),
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.secondary,
                modifier = Modifier.padding(top = dimensionResource(R.dimen.default_margin))
            )
            Button(
                onClick = { actionsChannel.tryEmit(AlbumActions.RetryClicked) },
                modifier = Modifier.padding(top = dimensionResource(R.dimen.default_margin))
            ) {
                Text(
                    text = stringResource(R.string.albums_retry)
                )
            }
        }
    }
}
