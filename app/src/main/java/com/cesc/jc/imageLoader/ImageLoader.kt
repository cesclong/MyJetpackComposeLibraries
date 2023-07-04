package com.cesc.jc.imageLoader

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.RememberObserver
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import java.lang.Exception

//https://github.com/agarasul/RemoteImage/blob/main/RemoteImage/src/main/java/dev/rasul/remoteimage/RemoteImage.kt

// inspired by https://github.com/agarasul/RemoteImage

sealed class ImageLoadState {
    object Loading : ImageLoadState()
    data class Loaded(val image: Bitmap) : ImageLoadState()
    object Error : ImageLoadState()
}


@Composable
fun RemoteImage(
    url: String,
    modifier: Modifier,
    error: @Composable (() -> Unit)? = null,
    loading: @Composable (() -> Unit)? = null,
) {
    val loader = rememberImageLoader(url = url)
    val state = loader.loadState
    Box(
        modifier = modifier.then(Modifier.fillMaxWidth()),
        contentAlignment = Alignment.Center
    ) {
        when (state) {
            is ImageLoadState.Loading -> {
                if (loading != null) {
                    loading()
                } else {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            strokeWidth = 2.dp,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }

            is ImageLoadState.Loaded -> {
                Image(
                    bitmap = state.image.asImageBitmap(),
                    contentDescription = null,

                    )
            }

            is ImageLoadState.Error -> {
                if (error != null) {
                    error.invoke()
                } else {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "Could not load image")
                    }
                }
            }

        }//end when
    }
}


@Composable
@NonRestartableComposable
fun rememberImageLoader(url: String): ImageLoader {
    val loader = remember {
        ImageLoader(url)
    }
    loader.onRemembered()
    return loader
}


@Stable
class ImageLoader(
    private val url: String
) : RememberObserver {
    var loadState by mutableStateOf<ImageLoadState>(ImageLoadState.Loading)

    @Volatile
    private var requesting: Boolean = false

    override fun onRemembered() {
        if (url.isEmpty()) {
            loadState = ImageLoadState.Error
            return
        }

        if (requesting) return
        requesting = false

        Picasso.get().load(url).into(object : Target {
            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                if (bitmap == null) {
                    onBitmapFailed(Exception("Incorrect"), null)
                } else {
                    loadState = ImageLoadState.Loaded(bitmap)
                }
            }

            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                loadState = ImageLoadState.Error
            }

            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
            }
        })
    }

    override fun onAbandoned() {
        onClear()
    }

    override fun onForgotten() {
        onClear()
    }

    private fun onClear() {
        requesting = false
    }

}