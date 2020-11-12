package id.toriqwah.project.view.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.RadioButton
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import id.toriqwah.project.R
import id.toriqwah.project.adapter.OrderAdapter
import id.toriqwah.project.databinding.ActivityOrderBinding
import id.toriqwah.project.helper.UtilityHelper
import id.toriqwah.project.model.List
import id.toriqwah.project.model.Order
import id.toriqwah.project.util.AppPreference
import id.toriqwah.project.view.base.BaseActivity
import kotlinx.android.synthetic.main.activity_order.*
import org.koin.android.ext.android.inject
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class OrderActivity : BaseActivity() {

    private lateinit var binding: ActivityOrderBinding
    private val viewModel by inject<MainViewModel>()
    private var listMenu = arrayListOf<List>()
    private var order = Order()
    private var total: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_order)
        binding.vm = viewModel
        binding.lifecycleOwner = this

        with(viewModel) {
            hideKeyBoard.observe(this@OrderActivity, {
                UtilityHelper.hideSoftKeyboard(this@OrderActivity)
            })
            snackbarMessage.observe(this@OrderActivity, {
                when (it) {
                    is Int -> UtilityHelper.snackbarLong(view_parent, getString(it))
                    is String -> UtilityHelper.snackbarLong(view_parent, it)
                }
            })
            networkError.observe(this@OrderActivity, {
                UtilityHelper.snackbarLong(view_parent, getString(R.string.error_network))
            })
            isLoading.observe(this@OrderActivity, { bool ->
                bool.let { loading ->
                    if (loading) {
                        showWaitingDialog()
                    } else {
                        hideWaitingDialog()
                    }
                }
            })
            clickProceed.observe(this@OrderActivity, {
                viewModel.getDataOrder("order")
            })
            orderSuccess.observe(this@OrderActivity, {
                openWhatsApp()
            })
            orderId.observe(this@OrderActivity, {
                proceedOrder(it)
            })
        }
        setView()
    }

    private fun setView(){
        setToolbar("Order")
        setListOrder(AppPreference.getListOrder())
    }

    private fun proceedOrder(id: Long){
        val paymentMethod = findViewById<RadioButton>(payment_method.checkedRadioButtonId)
        val orderType = findViewById<RadioButton>(order_type.checkedRadioButtonId)
        order.menu = listMenu
        order.status = "in progress"
        order.total_price = total
        order.by = number.text.toString()
        order.order_type = orderType.text.toString()
        order.payment_method = paymentMethod.text.toString()
        order.id = id + 1
        order.id_tenant = AppPreference.getIdTenant()
        order.location = location.text.toString()
        order.date = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())

        viewModel.pushOrder(order)
    }
    private fun setListOrder(list: ArrayList<List>) {

        for (data in list){
            if (data.quantity?.toInt() != 0) {
                listMenu.add(data)
                total += data.price!!
            }
        }
        total_price.text = "$total"
        rv_order.apply {
            layoutManager = LinearLayoutManager(this@OrderActivity)
            adapter = OrderAdapter(this@OrderActivity, listMenu)
        }
    }

    private fun openWhatsApp(){
        try {
            val text = "This is a test\nThis is a test"

            val toNumber = "6282230612512"


            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("http://api.whatsapp.com/send?phone=$toNumber&text=$text")
            startActivity(intent)
        }
        catch (e: Exception){
            e.printStackTrace()
        }

    }

}