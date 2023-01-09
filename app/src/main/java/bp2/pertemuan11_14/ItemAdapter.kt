package bp2.pertemuan11_14

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.items_row.view.*

class ItemAdapter(val context: Context, val items:ArrayList<DataModelClass>) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.items_row,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items.get(position)

        holder.tvNama.text = item.nama
        holder.tvNim.text = item.nim
        //Mengabdate warna background
        if (position % 2 == 0) {
            holder.llMain.setBackgroundColor(
                ContextCompat.getColor(
                    context,
                    R.color.colorLightGray
                )
            )
        } else {
            holder.llMain.setBackgroundColor(ContextCompat.getColor(context, R.color.colorWhite))
        }

        holder.ivEdit.setOnClickListener { view ->
            if (context is MainActivity) {
                context.updateDataMahasiswa(item)
            }
        }

        holder.ivDelete.setOnClickListener { view ->
            if (context is MainActivity) {
                context.hapusDataMahasiswa(item)
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val llMain = view.llMain
        val tvNama = view.tv_nama
        val tvNim = view.tv_nim
        val ivEdit = view.iv_edit
        val ivDelete = view.iv_delete
    }
}
