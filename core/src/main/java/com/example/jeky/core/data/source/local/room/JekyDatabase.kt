package com.example.jeky.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.jeky.core.data.source.local.room.dao.UserDao
import com.example.jeky.core.data.source.local.room.entity.UserEntity


@Database(entities = [UserEntity::class], version = 1, exportSchema = true)
abstract class JekyDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
}