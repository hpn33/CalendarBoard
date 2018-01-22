package hpn332.cb

import android.app.Application
import hpn332.cb.model.data.room.RoomDB

/**
 * Created by hpn332 on 14/11/2017.
 */
class App : Application() {

    companion object {
        lateinit var db: RoomDB
    }

    override fun onCreate() {
        super.onCreate()

        db = RoomDB.build(this)

//        ProviderHelper.init(this)
    }

}