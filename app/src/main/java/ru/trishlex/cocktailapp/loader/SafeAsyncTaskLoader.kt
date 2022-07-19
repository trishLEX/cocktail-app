package ru.trishlex.cocktailapp.loader

import android.content.Context
import androidx.loader.content.AsyncTaskLoader

abstract class SafeAsyncTaskLoader<T>(
    context: Context
) : AsyncTaskLoader<AsyncResult<T>>(context) {

    companion object {
        private const val START = 0
    }

    protected var res: T? = null

    override fun onStartLoading() {
        if (res == null) {
            forceLoad()
        }
    }
}