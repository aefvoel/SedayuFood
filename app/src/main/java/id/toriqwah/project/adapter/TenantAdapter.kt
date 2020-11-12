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
import id.toriqwah.project.model.Tenant

class TenantAdapter(context : Context, list: ArrayList<Tenant>, private val listener: Listener)
    : RecyclerView.Adapter<TenantAdapter.TenantViewHolder>() {

    private val contexts = context
    private val itemList = list

    interface Listener{
        fun onItemClicked(data: ArrayList<Menu>)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TenantViewHolder {
        val view = LayoutInflater.from(contexts).inflate(R.layout.item_tenant, parent,false)
        return TenantViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: TenantViewHolder, position: Int) {

        UtilityHelper.setImage(contexts, itemList[position].image, holder.image)
        holder.title.text = itemList[position].name
        holder.desc.text = itemList[position].desc
        holder.itemView.setOnClickListener {
            listener.onItemClicked(itemList[position].menu!!)
        }
    }

    inner class TenantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val image: ImageView = itemView.findViewById(R.id.image)
        val title: TextView = itemView.findViewById(R.id.title)
        val desc: TextView = itemView.findViewById(R.id.desc)
    }

}