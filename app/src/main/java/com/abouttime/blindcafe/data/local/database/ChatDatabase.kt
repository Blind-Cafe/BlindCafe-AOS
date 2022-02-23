package com.abouttime.blindcafe.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.abouttime.blindcafe.data.local.database.dao.MessageDao
import com.abouttime.blindcafe.data.local.database.entity.MessageEntity

@Database(entities = [MessageEntity::class], version = 1)
abstract class ChatDatabase: RoomDatabase() {
    abstract fun messageDao(): MessageDao
}