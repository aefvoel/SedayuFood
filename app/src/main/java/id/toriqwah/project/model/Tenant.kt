package id.toriqwah.project.model

import com.google.gson.annotations.SerializedName

data class Tenant (
    @SerializedName("id")
    var id: Long? = null,
    @SerializedName("name")
    var name: String = "",
    @SerializedName("image")
    var image: String = "",
    @SerializedName("desc")
    var desc: String = "",
    @SerializedName("no_whatsapp")
    var no_whatsapp: String = "",
    @SerializedName("menu")
    var menu: ArrayList<Menu>? = null
)