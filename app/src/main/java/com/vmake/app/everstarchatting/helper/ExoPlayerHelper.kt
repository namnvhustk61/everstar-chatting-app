package com.vmake.app.everstarchatting.helper

import android.content.Context
import android.net.Uri
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.database.StandaloneDatabaseProvider
import com.google.android.exoplayer2.ext.cronet.CronetDataSource
import com.google.android.exoplayer2.ext.cronet.CronetUtil
import com.google.android.exoplayer2.extractor.Extractor
import com.google.android.exoplayer2.extractor.ExtractorsFactory
import com.google.android.exoplayer2.extractor.mp4.Mp4Extractor
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.upstream.FileDataSource
import com.google.android.exoplayer2.upstream.HttpDataSource
import com.google.android.exoplayer2.upstream.cache.Cache
import com.google.android.exoplayer2.upstream.cache.CacheDataSource
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor
import com.google.android.exoplayer2.upstream.cache.SimpleCache
import com.google.android.exoplayer2.util.EventLogger
import kotlinx.coroutines.suspendCancellableCoroutine
import org.chromium.net.UrlRequest
import java.io.File
import java.net.CookieHandler
import java.net.CookieManager
import java.net.CookiePolicy
import java.util.concurrent.Executors


object ExoPlayerHelper {
    private const val ENABLE_LOG = true
    private const val HTTP_TIMEOUT = 3000
    private var cache: SimpleCache? = null

    fun preInit(context: Context) {
        getInstance(context)
    }

    suspend fun prepareMediaSourceAsync(
        exoPlayer: ExoPlayer,
        context: Context,
        url: String,
        position: Long = C.TIME_UNSET
    ): Boolean =
        suspendCancellableCoroutine {
            var seekPosition = position
            exoPlayer.setVideoSource(context, url)
            val listener = object : Player.Listener {
                override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                    if (playbackState == Player.STATE_READY) {
                        if (seekPosition != C.TIME_UNSET) {
                            exoPlayer.seekTo(seekPosition)
                            seekPosition = C.TIME_UNSET
                        } else {
                            exoPlayer.removeListener(this)
                            it.resume(true) {

                            }
                        }
                    }
                }

                override fun onPlayerError(error: PlaybackException) {
                    super.onPlayerError(error)
                    it.resume(false) {

                    }
                }
            }
            exoPlayer.addListener(listener)
            exoPlayer.prepare()
        }

    fun getInstance(context: Context) =
        getExoBuilder(context)
            .setLoadControl(getLoadControl())
            .setUseLazyPreparation(false)
            .build().apply {
                repeatMode = Player.REPEAT_MODE_ONE
                if (ENABLE_LOG)
                    addAnalyticsListener(EventLogger())
            }

    private fun getExoBuilder(context: Context): ExoPlayer.Builder {
        val renderersFactory = DefaultRenderersFactory(context)
            .forceEnableMediaCodecAsynchronousQueueing()
        return ExoPlayer.Builder(context, renderersFactory, MediaSource.Factory.UNSUPPORTED)
    }

    private fun ExoPlayer.setVideoSource(context: Context, url: String) {
        setMediaSource(makeMediaSource(context, url))
    }

    private fun getLoadControl() = DefaultLoadControl
        .Builder()
        .setBufferDurationsMs(3 * 1000, 3 * 1000, 300, 1200)
        .build()

    private fun makeMediaSource(context: Context, url: String): MediaSource {
        val mediaItem = makeMediaItem(url)
        return if (url.startsWith("http")) {
            makeProgressiveMediaSourceFactory(context)
                .createMediaSource(mediaItem)
        } else {
            ProgressiveMediaSource.Factory(FileDataSource.Factory())
                .createMediaSource(mediaItem)
        }
    }

    private fun makeMediaItem(url: String): MediaItem {
        return MediaItem.fromUri(
            Uri.parse(url)
        )
    }

    private fun makeMediaSourceFactory(context: Context) =
        DefaultMediaSourceFactory(context, getExtractor()).setDataSourceFactory(
            createCacheDataSource(context)
        )

    private fun makeProgressiveMediaSourceFactory(context: Context) =
        ProgressiveMediaSource.Factory(createCacheDataSource(context), getExtractor())

    private fun getExtractor() = ExtractorsFactory {
        arrayOf<Extractor>(
            Mp4Extractor()
        )
    }

    @Synchronized
    private fun createCacheDataSource(context: Context): CacheDataSource.Factory {
        return CacheDataSource.Factory()
            .setCache(getCache(context))
            .setUpstreamDataSourceFactory(getHttpDataSourceFactory(context))
            .setFlags(CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR)
    }

    @Synchronized
    private fun getCache(context: Context): Cache {
        if (cache == null)
            cache = SimpleCache(
                getCacheFolder(context),
                getCacheEvictor(),
                getDatabaseProvider(context)
            )
        return cache!!
    }

    @Synchronized
    private fun getDatabaseProvider(context: Context) = StandaloneDatabaseProvider(context)

    @Synchronized
    private fun getCacheEvictor() = LeastRecentlyUsedCacheEvictor((3L * 1024 * 1024 * 1024))

    @Synchronized
    private fun getCacheFolder(context: Context) = File(context.cacheDir, "_videos")

    @Synchronized
    private fun getHttpDataSourceFactory(context: Context): HttpDataSource.Factory {
        return getCronetFactory(context) ?: getHttpFactory()
    }

    @Synchronized
    private fun wrapInDefaultFactory(
        context: Context,
        httpFactory: HttpDataSource.Factory
    ): DefaultDataSource.Factory = DefaultDataSource.Factory(context, httpFactory)

    @Synchronized
    private fun getCronetFactory(context: Context): CronetDataSource.Factory? {
        return CronetDataSource.Factory(
            CronetUtil.buildCronetEngine(context) ?: return null,
            Executors.newSingleThreadExecutor()
        )
            .setConnectionTimeoutMs(HTTP_TIMEOUT)
            .setReadTimeoutMs(HTTP_TIMEOUT)
            .setRequestPriority(UrlRequest.Builder.REQUEST_PRIORITY_HIGHEST)
    }

    @Synchronized
    private fun getHttpFactory(): DefaultHttpDataSource.Factory {
        CookieHandler.setDefault(CookieManager().apply {
            setCookiePolicy(
                CookiePolicy.ACCEPT_ORIGINAL_SERVER
            )
        })
        return DefaultHttpDataSource.Factory()
            .setConnectTimeoutMs(HTTP_TIMEOUT)
            .setReadTimeoutMs(HTTP_TIMEOUT)
            .setAllowCrossProtocolRedirects(true)
    }
}