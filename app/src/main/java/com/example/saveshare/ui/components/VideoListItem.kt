package com.example.saveshare.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.example.saveshare.R
import com.example.saveshare.data.models.Video
import com.example.saveshare.ui.theme.Purple
import com.example.saveshare.util.AppConstants
import com.example.saveshare.util.convertDateFormat
import java.math.BigDecimal
import java.math.RoundingMode

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun VideoListItem(
    video: Video,
    showHeader: Boolean,
    isFavourite: Boolean = false,
    onClick: () -> Unit
) {
    if (showHeader) {
        Text(
            text = video.releaseDate.convertDateFormat(
                AppConstants.DateFormats.VIDEO_DATE_FORMAT,
                AppConstants.DateFormats.MONTH_YEAR_FORMAT
            ).toString(),
            modifier = Modifier.padding(
                horizontal = dimensionResource(R.dimen.ten), vertical = dimensionResource(
                    R.dimen.five
                )
            )
        )
    }
    Card(
        onClick = {},//can add logic to open video details by clicking on the card
        elevation = CardDefaults.cardElevation(
            defaultElevation = dimensionResource(R.dimen.five)
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = dimensionResource(R.dimen.ten),
                vertical = dimensionResource(R.dimen.two)
            )
    ) {
        Row {
            Column(
                Modifier
                    .wrapContentWidth()
                    .padding(dimensionResource(R.dimen.sixteen))
            ) {
                GlideImage(
                    model = AppConstants.Image.BASE_LINK + video.posterPath,
                    loading = placeholder(R.drawable.ic_loader),
                    failure = placeholder(R.drawable.ic_default_placeholder),
                    contentDescription = "",
                    modifier = Modifier
                        .height(40.dp)
                        .width(40.dp)
                        .align(Alignment.Start),
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = BigDecimal(video.voteAverage.toDouble()).setScale(
                        1,
                        RoundingMode.HALF_EVEN
                    ).toString(),
                    textAlign = TextAlign.Start,
                    maxLines = 1,
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(top = dimensionResource(R.dimen.six))
                        .align(Alignment.CenterHorizontally)
                )
            }
            Column(
                Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .padding(top = dimensionResource(R.dimen.ten))
                    .weight(1.0f)
            ) {
                Text(
                    text = video.title,
                    textAlign = TextAlign.Start,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = dimensionResource(R.dimen.five),
                            top = dimensionResource(R.dimen.five),
                            bottom = dimensionResource(R.dimen.five),
                            end = dimensionResource(R.dimen.twenty)
                        )
                        .align(Alignment.Start)
                )
                Text(
                    text = video.description,
                    textAlign = TextAlign.Start,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = dimensionResource(R.dimen.five),
                            top = dimensionResource(R.dimen.five),
                            bottom = dimensionResource(R.dimen.five),
                            end = dimensionResource(R.dimen.twenty)
                        )
                        .align(Alignment.Start)
                )
                Row(modifier = Modifier.align(Alignment.End)) {
                    OutlinedButton(
                        colors = ButtonDefaults.outlinedButtonColors(Color.Transparent),
                        border = BorderStroke(dimensionResource(R.dimen.zero), Color.Transparent),
                        onClick = {
                            onClick.invoke()
                        }
                    ) {
                        Text(
                            text = if (isFavourite && video.isFavourite) stringResource(R.string.remove) else stringResource(
                                R.string.like
                            ),
                            style = MaterialTheme.typography.titleMedium,
                            color = Purple
                        )
                    }
                    Share(AppConstants.Share.SHARE_LINK + video.id, LocalContext.current)
                }
            }
        }
    }
}