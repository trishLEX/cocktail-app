package ru.trishlex.cocktailapp.db

import android.content.ContentValues
import android.content.Context
import android.database.DatabaseUtils
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import ru.trishlex.cocktailapp.db.contract.DbContract
import ru.trishlex.cocktailapp.db.contract.ShopListContract
import ru.trishlex.cocktailapp.ingredient.model.IngredientItem

class ShopListDao(
    context: Context,
) : SQLiteOpenHelper(context, DbContract.DB_NAME, null, DbContract.VERSION) {

    private val db: SQLiteDatabase = writableDatabase!!

    companion object {
        private val CREATE_TABLE = """
            create table ${ShopListContract.TABLE_NAME} (
                ${BaseColumns._ID} INT8 PRIMARY KEY
            );
        """.trimIndent()
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

    fun save(ingredient: IngredientItem) {
        val values = ContentValues()
        values.put(BaseColumns._ID, ingredient.id)
        db.insert(ShopListContract.TABLE_NAME, null, values)
    }

    fun get(): List<Int> {
        val cursor = db.query(
            ShopListContract.TABLE_NAME,
            arrayOf(BaseColumns._ID),
            null,
            null,
            null,
            null,
            "${BaseColumns._ID} ASC"
        )
        val res = mutableListOf<Int>()
        with(cursor) {
            while (cursor.moveToNext()) {
                res.add(getInt(getColumnIndexOrThrow(BaseColumns._ID)))
            }
        }
        cursor.close()
        return res
    }

    fun remove(ingredient: IngredientItem) {
        db.delete(ShopListContract.TABLE_NAME, "${BaseColumns._ID} = ?", arrayOf(ingredient.id.toString()))
    }

    fun contains(ingredient: IngredientItem): Boolean {
        return DatabaseUtils.queryNumEntries(
            db,
            ShopListContract.TABLE_NAME,
            "${BaseColumns._ID} = ?",
            arrayOf(ingredient.id.toString())
        ) > 0
    }
}