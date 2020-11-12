package id.toriqwah.project.model

import com.google.gson.annotations.SerializedName

data class Order (
    @SerializedName("id")
    var id: Long? = null,
    @SerializedName("id_tenant")
    var id_tenant: Long? = null,
    @SerializedName("payment_method")
    var payment_method: String = "",
    @SerializedName("order_type")
    var order_type: String = "",
    @SerializedName("location")
    var location: String = "",
    @SerializedName("by")
    var by: String = "",
    @SerializedName("date")
    var date: String = "",
    @SerializedName("status")
    var status: String = "",
    @SerializedName("total_price")
    var total_price: Long? = null,
    @SerializedName("menu")
    var menu: ArrayList<List>? = null
)

data class List (
    @SerializedName("id")
    var id: Long? = null,
    @SerializedName("name")
    var name: String = "",
    @SerializedName("quantity")
    var quantity: Long? = null,
    @SerializedName("price")
    var price: Long? = null
)