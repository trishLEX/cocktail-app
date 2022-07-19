package ru.trishlex.cocktailapp.loader

class AsyncResult<T>(val result: T? = null, val exception: Exception? = null) {

    companion object {

        fun <T> of(result: T): AsyncResult<T> {
            return AsyncResult(result)
        }

        fun <T> of(exception: Exception): AsyncResult<T> {
            return AsyncResult(exception = exception)
        }
    }

    fun isCompleted(): Boolean {
        return result != null
    }
}