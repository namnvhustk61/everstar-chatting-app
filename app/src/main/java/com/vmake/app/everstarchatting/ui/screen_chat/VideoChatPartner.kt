package com.vmake.app.everstarchatting.ui.screen_chat

import android.content.Context
import android.view.View
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.PlaybackException
import com.google.android.exoplayer2.Player
import com.vmake.app.base.helper.mainLaunch
import com.vmake.app.everstarchatting.databinding.VideoPlayerViewBinding
import com.vmake.app.everstarchatting.helper.ExoPlayerHelper

class VideoChatPartner : DefaultLifecycleObserver {

    companion object {
        fun newInstance() = VideoChatPartner()
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        release()
    }

    private var exoPlayer: ExoPlayer? = null

    private var listener: Listener? = null

    fun build(binding: VideoPlayerViewBinding?, url: String?) {
        binding?.imgPlay?.setOnClickListener { play() }
        binding?.playerView?.setOnClickListener { pause() }
        setListener(object : VideoChatPartner.Listener {
            override fun onPlay() {
                binding?.imgPlay?.visibility = View.INVISIBLE
            }

            override fun onPause() {
                binding?.imgPlay?.visibility = View.VISIBLE
            }

            override fun onProgressChange(position: Int) {

            }

            override fun onPlayError() {

            }

            override fun onBuffering(isBuffering: Boolean) {

            }

            override fun onRenderedFirstFrame() {

            }
        })
        mainLaunch {
            binding?.playerView?.player = prepareVideo(
                binding?.playerView?.context,
                url
            )
        }
    }

    private suspend fun prepareVideo(
        context: Context?,
        sourceUrl: String?
    ): ExoPlayer? {
        if (context == null) return null
        if (sourceUrl == null) return null
        if (exoPlayer == null) {
            exoPlayer = ExoPlayerHelper.getInstance(context)
        }
        exoPlayer?.let {
            val success = ExoPlayerHelper.prepareMediaSourceAsync(it, context, sourceUrl)
            if (success) {
                exoPlayer?.addListener(playerListener)
            }
        }
        return exoPlayer
    }

    private val playerListener = object : Player.Listener {
        override fun onIsPlayingChanged(isPlaying: Boolean) {
            super.onIsPlayingChanged(isPlaying)
            if (isPlaying) listener?.onPlay()
            else listener?.onPause()
        }

        override fun onPlaybackStateChanged(playbackState: Int) {
            super.onPlaybackStateChanged(playbackState)
            if (playbackState == ExoPlayer.STATE_BUFFERING) {
                listener?.onBuffering(true)
            } else {
                listener?.onBuffering(false)
            }
        }

        override fun onPlayerError(error: PlaybackException) {
            super.onPlayerError(error)
            listener?.onPlayError()
        }

        override fun onRenderedFirstFrame() {
            super.onRenderedFirstFrame()
            listener?.onRenderedFirstFrame()
        }
    }

    fun play() {
        if (exoPlayer?.isPlaying != true)
            exoPlayer?.play()

    }

    fun pause(force: Boolean = false) {
        if (exoPlayer?.isPlaying != false || force) {
            exoPlayer?.pause()
        }
    }

    fun seekToStartPosition() {
        exoPlayer?.run {
            if (currentPosition != C.TIME_UNSET)
                seekToDefaultPosition()
        }
    }

    fun seekToPosition(position: Int) {
        exoPlayer?.run {
            this.seekTo(position.toLong())
            this.play()
        }
    }

    private fun setListener(listener: Listener) {
        this.listener = listener
    }

    private fun releasePlayer() {
        exoPlayer?.run {
            stop()
            removeListener(playerListener)
            clearMediaItems()
            release()
        }
    }

    fun release() {
        releasePlayer()
        exoPlayer = null
    }

    interface Listener {
        fun onPlay()
        fun onPause()
        fun onProgressChange(position: Int)
        fun onPlayError()
        fun onBuffering(isBuffering: Boolean)
        fun onRenderedFirstFrame()

    }
}