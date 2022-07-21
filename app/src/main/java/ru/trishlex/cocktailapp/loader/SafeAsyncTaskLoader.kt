package ru.trishlex.cocktailapp.loader

import android.content.Context
import android.util.Log
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

    abstract fun load(): T

    override fun loadInBackground(): AsyncResult<T>? {
        return try {
            res = load()
            AsyncResult.of(res!!)
        } catch (ex: Exception) {
            Log.e("errorLog", "exception", ex)
            AsyncResult.of(ex)
        }
    }
}