package com.abouttime.blindcafe.di

import android.content.Context
import androidx.room.Room
import com.abouttime.blindcafe.common.constants.DataBase.DATABASE_NAME
import com.abouttime.blindcafe.data.local.database.ChatDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

internal val localModule = module {
    single { provideMessageDatabase(androidContext()) }
}

internal fun provideMessageDatabase(context: Context) = Room.databaseBuilder(
    context,
    ChatDatabase::class.java,
    DATABASE_NAME
).build()