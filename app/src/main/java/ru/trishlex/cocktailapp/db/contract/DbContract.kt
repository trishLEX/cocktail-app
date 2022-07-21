package ru.trishlex.cocktailapp.db.contract

import android.provider.BaseColumns

sealed class DbContract: BaseColumns {
    companion object {
        const val DB_NAME = "cocktails"
        const val VERSION = 1
    }
}