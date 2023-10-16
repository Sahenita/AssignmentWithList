package com.example.assignment.view

import android.content.ClipData
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.assignment.PicsumImage
import com.example.assignment.R

class MyAdapter(val context: Context) : PagingDataAdapter<PicsumImage, MyAdapter.ViewHolder>(MyDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.data_item, parent, false)
        return ViewHolder(view)
    }



    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemName: TextView = itemView.findViewById(R.id.name)
        val itemDescription: TextView = itemView.findViewById(R.id.description)
        val itemImage: ImageView = itemView.findViewById(R.id.image)

    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position) // Retrieve the item at the specified position
        holder.itemName.text = item?.author
        var str="Hight is ${item?.height.toString()} and width is ${item?.width.toString()}"
        holder.itemDescription.text=str
        // Load an image from a URL into an ImageView
        Glide.with(context)
            .load(item?.downloadUrl)
            .into(holder.itemImage)

        // Show dialog on item click
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val dialog = MyDialogFragment.newInstance(item?.author,item?.height.toString(), item?.width.toString())
            dialog.show((context as AppCompatActivity).supportFragmentManager, "MyDialogFragment")
        }
    }


    private class MyDiffUtilCallback : DiffUtil.ItemCallback<PicsumImage>() {
        override fun areItemsTheSame(oldItem: PicsumImage, newItem: PicsumImage): Boolean {
            return oldItem.id == newItem.id // Assuming you have an "id" field in your Item model
        }

        override fun areContentsTheSame(oldItem: PicsumImage, newItem: PicsumImage): Boolean {
            return oldItem == newItem
        }
    }

}