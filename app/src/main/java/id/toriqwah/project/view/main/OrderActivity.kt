package id.toriqwah.project.view.main

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import id.toriqwah.project.R
import id.toriqwah.project.adapter.OrderAdapter
import id.toriqwah.project.databinding.ActivityOrderBinding
import id.toriqwah.project.helper.UtilityHelper
import id.toriqwah.project.model.List
import id.toriqwah.project.util.AppPreference
import id.toriqwah.project.view.base.BaseActivity
import kotlinx.android.synthetic.main.activity_order.*
import org.koin.android.ext.android.inject

class OrderActivity : BaseActivity() {

    private lateinit var binding: ActivityOrderBinding
    private val viewModel by inject<MainViewModel>()
    private var listMenu = arrayListOf<List>()

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
        }
        setView()
    }

    private fun setView(){
        setToolbar("Order")
        setListOrder(AppPreference.getListOrder())
    }

    private fun setListOrder(list: ArrayList<List>) {
        var total: Long = 0
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

}