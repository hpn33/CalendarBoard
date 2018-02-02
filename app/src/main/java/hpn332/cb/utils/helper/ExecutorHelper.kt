package hpn332.cb.utils.helper

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * Created by hpn332 on 27/12/2017.
 */
object ExecutorHelper {

    val dickIO: ExecutorService = Executors.newSingleThreadExecutor()
    val networdIO = Executors.newFixedThreadPool(3)
    val mainThread = object : Executor {

        private val mainThreadHandler = Handler(Looper.getMainLooper())

        override fun execute(command: Runnable?) {
            mainThreadHandler.post(command)
        }
    }
}
