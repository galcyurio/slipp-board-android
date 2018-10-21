package net.slipp.slippboard

import android.app.Application
import com.github.galcyurio.slippboardapi.SlippBoardClient

class SlippBoardApp : Application() {
    override fun onCreate() {
        super.onCreate()
        SlippBoardClient.init()
    }
}