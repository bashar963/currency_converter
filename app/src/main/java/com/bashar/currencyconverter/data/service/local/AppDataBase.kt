package com.bashar.currencyconverter.data.service.local

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.bashar.currencyconverter.model.Currency
import com.bashar.currencyconverter.model.MyBalance

@Database(entities = [MyBalance::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun myBalanceDao(): MyBalanceDao

    companion object {
        @Volatile private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase =
            instance ?: synchronized(this) { instance ?: buildDatabase(context).also { instance = it } }

        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(appContext, AppDatabase::class.java, "my_balance.db")
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        // Generating init values
                        Currency.values().forEachIndexed { index, currency  ->
                            val value = if (currency == Currency.EUR)
                                1000.0
                            else  0.0
                            val values = ContentValues().apply {
                                put("id", index)
                                put("currency", currency.displayName)
                                put("amount", value)
                            }
                            db.insert("my_balance", SQLiteDatabase.CONFLICT_IGNORE, values)
                        }
                    }

                } )
                .fallbackToDestructiveMigration()
                .build()
    }

}