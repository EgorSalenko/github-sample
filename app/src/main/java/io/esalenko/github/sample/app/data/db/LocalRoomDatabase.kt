package io.esalenko.github.sample.app.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import io.esalenko.github.sample.app.data.db.dao.SearchDao
import io.esalenko.github.sample.app.data.db.entity.SearchItemEntity

@Database(entities = [SearchItemEntity::class], version = 1)
abstract class LocalRoomDatabase : RoomDatabase() {

    companion object {
        @Volatile
        private var INSTANCE: LocalRoomDatabase? = null

        @Synchronized
        fun getInstance(ctx: Context): LocalRoomDatabase? {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    ctx.applicationContext,
                    LocalRoomDatabase::class.java,
                    "github_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

    abstract val searchDao: SearchDao
}