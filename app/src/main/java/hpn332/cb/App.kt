package hpn332.cb

import android.app.Application
import hpn332.cb.utils.helper.ProviderHelper

/**
 * Created by hpn332 on 14/11/2017.
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        ProviderHelper(applicationContext)
    }

}