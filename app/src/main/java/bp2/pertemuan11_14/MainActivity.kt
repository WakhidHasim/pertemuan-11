package bp2.pertemuan11_14

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_simpan.setOnClickListener { view ->
            tambahData()
            aturDataRecyclerView()
        }
    }

    private fun tambahData() {
        val nama = et_nama.text.toString()
        val nim = et_nim.text.toString()
        val databaseHandler: DatabaseHandler = DatabaseHandler(this)
        if (!nama.isEmpty() && !nim.isEmpty()) {
            val status = databaseHandler.tambahMahasiswa(DataModelClass(0, nama, nim))
            if (status > -1) {
                Toast.makeText(applicationContext, "Data disimpan ", Toast.LENGTH_LONG).show()
                et_nama . text . clear ()
                et_nim . text . clear ()
            }
        } else {
            Toast.makeText(applicationContext, "Nama atau NIM tidak boleh kosong", Toast.LENGTH_LONG).show()
        }
    }

    //Metode menambahkan item data dalam tabel
    private fun aksesItemData(): ArrayList<DataModelClass> {
        val databaseHandler: DatabaseHandler = DatabaseHandler(this)
        val daftarMahasiswa: ArrayList<DataModelClass> = databaseHandler.tampilMahasiswa()
        return daftarMahasiswa
    }

    //Metode yang digunakan untuk manampilkan data
    private fun aturDataRecyclerView() {
        if (aksesItemData().size > 0) {
            rv_ItemData.visibility = View.VISIBLE
            tvNoRecordsAvailable.visibility = View.GONE
            rv_ItemData.layoutManager = LinearLayoutManager(this)
            val itemAdapter = ItemAdapter(this, aksesItemData())
            rv_ItemData.adapter = itemAdapter
        } else {
            rv_ItemData.visibility = View.GONE
            tvNoRecordsAvailable.visibility = View.VISIBLE
        }
    }
}