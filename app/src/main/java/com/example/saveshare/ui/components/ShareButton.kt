package com.example.saveshare.ui.components

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat.startActivity
import com.example.saveshare.R
import com.example.saveshare.ui.theme.Purple

@Composable
fun Share(text: String, context: Context) {
    val sendIntent = Intent(Intent.ACTION_SEND).apply {
        putExtra(Intent.EXTRA_TEXT, text)
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, null)

    OutlinedButton(
        colors = ButtonDefaults.outlinedButtonColors(Color.Transparent),
        border = BorderStroke(dimensionResource(R.dimen.zero), Color.Transparent),
        onClick = {
            startActivity(context, shareIntent, null)
        }
    ) {
        Text(
            text = stringResource(R.string.share),
            style = MaterialTheme.typography.titleMedium,
            color = Purple
        )
    }
}