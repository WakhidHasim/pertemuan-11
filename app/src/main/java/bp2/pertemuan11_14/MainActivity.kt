package bp2.pertemuan11_14

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_update.*

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
                et_nama.text.clear ()
                et_nim.text.clear ()
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

    public fun updateDataMahasiswa(emp: DataModelClass) {
        val updateDialog = Dialog(this, R.style.Theme_Dialog)
        updateDialog.setCancelable(false)

        updateDialog.setContentView(R.layout.dialog_update)
        updateDialog.etUpdateNama.setText(emp.nama)
        updateDialog.etUpdateNIM.setText(emp.nim)

        updateDialog.tvUpdate.setOnClickListener(View.OnClickListener {
            val nama = updateDialog.etUpdateNama.text.toString()
            val nim = updateDialog.etUpdateNIM.text.toString()

            val databaseHandler: DatabaseHandler = DatabaseHandler(this)
            if (!nama.isEmpty() && !nim.isEmpty()) {
                val status = databaseHandler.updateMahasiswa(DataModelClass(emp.id, nama, nim))
                if (status > -1) {
                    Toast.makeText(applicationContext, "Data Berhasil diUpdate", Toast.LENGTH_LONG).show()
                    aturDataRecyclerView()
                    updateDialog.dismiss()
                }
            }
            else {
                Toast.makeText(applicationContext, "Nama atau NIM tidak boleh kosong", Toast.LENGTH_LONG).show()
            }
        })
        updateDialog.tvCancel.setOnClickListener(View.OnClickListener {
            updateDialog.dismiss()
        })

        updateDialog.show()
    }

    public fun hapusDataMahasiswa(emp: DataModelClass) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Hapus Data")
        builder.setMessage("Apakah Anda Yakin Ingin Menghapus Data ${emp.nama} ?")
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        builder.setPositiveButton("Ya") { dialogInterface, which ->
            val databaseHandler: DatabaseHandler = DatabaseHandler(this)
            val status = databaseHandler.hapusMahasiswa(DataModelClass(emp.id, "", ""))
            if (status > -1) {
                Toast.makeText(applicationContext, "Data Mahasiswa Berhasil di Hapus  !", Toast.LENGTH_LONG).show()
                aturDataRecyclerView()
            }
            dialogInterface.dismiss()
        }
        builder.setNegativeButton("Tidak") { dialogInterface, which ->
            dialogInterface.dismiss()
        }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

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