package Adapters

import com.example.dailyedu.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dailyedu.firestore.dataSubjects

class Adapter : RecyclerView.Adapter<Adapter.ViewHolder>() {

    private var data: List<dataSubjects> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_data_subjects, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        Glide.with(holder.itemView.context)
            .load(item.imageUrl)
            .into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setData(newData: List<dataSubjects>) {
        data = newData
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
    }
}
