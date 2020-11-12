package id.toriqwah.project.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import id.toriqwah.project.R
import id.toriqwah.project.helper.UtilityHelper
import id.toriqwah.project.model.Menu

class MenuAdapter(context : Context, list: ArrayList<Menu>, private val listener: Listener)
    : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    private val contexts = context
    private val itemList = list

    interface Listener{
        fun onTotalChanged(position: Int, size: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val view = LayoutInflater.from(contexts).inflate(R.layout.item_menu, parent,false)
        return MenuViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {

        var total = 0
        UtilityHelper.setImage(contexts, itemList[position].image, holder.image)
        holder.title.text = itemList[position].name
        holder.price.text = itemList[position].price.toString()

        holder.txtPlus.setOnClickListener {
            total++
            if (total >= 0) {
                holder.txtTotal.text = "$total"
                listener.onTotalChanged(position, total)
            } else {
                total = 0
            }
        }
        holder.txtMinus.setOnClickListener {
            total--
            if (total >= 0) {
                holder.txtTotal.text = "$total"
                listener.onTotalChanged(position, total)
            } else {
                total = 0
            }
        }

    }

    inner class MenuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val image: ImageView = itemView.findViewById(R.id.image)
        val title: TextView = itemView.findViewById(R.id.title)
        val price: TextView = itemView.findViewById(R.id.price)
        val txtPlus: TextView = itemView.findViewById(R.id.txt_plus)
        val txtMinus: TextView = itemView.findViewById(R.id.txt_minus)
        val txtTotal: TextView  = itemView.findViewById(R.id.txt_total)
    }

}