package id.toriqwah.project.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import id.toriqwah.project.R
import id.toriqwah.project.model.List

class OrderAdapter(context : Context, list: ArrayList<List>)
    : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    private val contexts = context
    private val itemList = list

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(contexts).inflate(R.layout.item_order, parent,false)
        return OrderViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val qty = itemList[position].quantity
        holder.qty.text =  "${qty}x"
        holder.item.text = itemList[position].name
        holder.price.text = itemList[position].price.toString()
    }

    inner class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val qty: TextView = itemView.findViewById(R.id.txt_qty)
        val item: TextView = itemView.findViewById(R.id.txt_item)
        val price: TextView = itemView.findViewById(R.id.txt_price)
    }

}