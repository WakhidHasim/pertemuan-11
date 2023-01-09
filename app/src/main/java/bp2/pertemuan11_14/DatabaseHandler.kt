package bp2.pertemuan11_14

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHandler(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "DatabaseMahasiswa"
        private val TABLE_CONTACTS = "TabelMahasiswa"
        private val KEY_ID = "_id"
        private val KEY_NAMA = "nama"
        private val KEY_NIM = "nim"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_CONTACTS_TABLE =
            ("CREATE TABLE " + TABLE_CONTACTS + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAMA + " TEXT," + KEY_NIM + " TEXT" + ")")
        db?.execSQL(CREATE_CONTACTS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_CONTACTS")
        onCreate(db)
    }

    fun tambahMahasiswa(emp: DataModelClass): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(KEY_NAMA, emp.nama)
        contentValues.put(KEY_NIM, emp.nim)

        val success = db.insert(TABLE_CONTACTS, null, contentValues)
        db.close()
        return success
    }

    fun tampilMahasiswa(): ArrayList<DataModelClass> {
        val empList: ArrayList<DataModelClass> = ArrayList<DataModelClass>()

        val selectQuery = "SELECT * FROM $TABLE_CONTACTS"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var id: Int
        var nama: String
        var nim: String
        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID))
                nama = cursor.getString(cursor.getColumnIndexOrThrow(KEY_NAMA))
                nim = cursor.getString(cursor.getColumnIndexOrThrow(KEY_NIM))

                val emp = DataModelClass(id = id, nama = nama, nim = nim)
                empList.add(emp)
            } while (cursor.moveToNext())
        }
        return empList
    }

    fun updateMahasiswa(emp: DataModelClass): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_NAMA, emp.nama)
        contentValues.put(KEY_NIM, emp.nim)

        val success = db.update(TABLE_CONTACTS, contentValues, KEY_ID + "=" + emp.id, null)

        db.close()
        return success
    }

    fun hapusMahasiswa(emp: DataModelClass) : Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, emp.id)

        val success = db.delete(TABLE_CONTACTS, KEY_ID + "=" + emp.id, null)

        db.close()
        return success
    }
}
