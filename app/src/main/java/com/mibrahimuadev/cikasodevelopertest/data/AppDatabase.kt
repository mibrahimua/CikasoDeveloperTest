package com.mibrahimuadev.cikasodevelopertest.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mibrahimuadev.cikasodevelopertest.data.dao.BarangDao
import com.mibrahimuadev.cikasodevelopertest.data.dao.UserDao
import com.mibrahimuadev.cikasodevelopertest.data.entity.BarangEntity
import com.mibrahimuadev.cikasodevelopertest.data.entity.UserEntity

@Database(
    version = 1,
    entities = [(UserEntity::class), (BarangEntity::class)],
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun barangDao(): BarangDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        var DB_NAME = "cikaso_dev"

        fun getInstance(context: Context): AppDatabase {
            /**
             * Multiple threads can potentially ask for a database instance at the same time,
             * resulting in two databases instead of one.
             * This problem is not likely to happen in this sample app,
             * but it's possible for a more complex app.
             * Wrapping the code to get the database into synchronized means that only one thread of
             * execution at a time can enter this block of code,
             * which makes sure the database only gets initialized once.
             */
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        DB_NAME
                    )
                        .createFromAsset("database/cikaso_dev.db")
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}