package com.uanish.userdirectory

import android.app.Application
import com.uanish.userdirectory.data.UserRepository
import com.uanish.userdirectory.di.AppModule

class UserDirectoryApp : Application() {

    lateinit var userRepository: UserRepository
        private set

    override fun onCreate() {
        super.onCreate()

        val db = AppModule.provideDatabase(this)
        val api = AppModule.provideUserApi()
        userRepository = UserRepository(api, db.userDao())
    }
}
