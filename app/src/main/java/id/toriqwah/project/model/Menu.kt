package id.toriqwah.project.model

import com.google.gson.annotations.SerializedName

data class Menu (
    @SerializedName("id")
    var id: Long? = null,
    @SerializedName("name")
    var name: String = "",
    @SerializedName("image")
    var image: String = "",
    @SerializedName("price")
    var price: Long? = null
)