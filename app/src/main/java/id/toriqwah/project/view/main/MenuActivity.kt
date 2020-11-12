package id.toriqwah.project.view.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import id.toriqwah.project.R
import id.toriqwah.project.adapter.MenuAdapter
import id.toriqwah.project.databinding.ActivityMenuBinding
import id.toriqwah.project.helper.UtilityHelper
import id.toriqwah.project.model.List
import id.toriqwah.project.model.Menu
import id.toriqwah.project.util.AppPreference
import id.toriqwah.project.view.base.BaseActivity
import kotlinx.android.synthetic.main.activity_menu.*
import org.koin.android.ext.android.inject

class MenuActivity : BaseActivity(), MenuAdapter.Listener {

    private lateinit var binding: ActivityMenuBinding
    private val viewModel by inject<MainViewModel>()
    private var listMenuOrder = arrayListOf<List>()
    private var listMenu = arrayListOf<Menu>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_menu)
        binding.vm = viewModel
        binding.lifecycleOwner = this

        with(viewModel) {
            hideKeyBoard.observe(this@MenuActivity, {
                UtilityHelper.hideSoftKeyboard(this@MenuActivity)
            })
            snackbarMessage.observe(this@MenuActivity, {
                when (it) {
                    is Int -> UtilityHelper.snackbarLong(view_parent, getString(it))
                    is String -> UtilityHelper.snackbarLong(view_parent, it)
                }
            })
            networkError.observe(this@MenuActivity, {
                UtilityHelper.snackbarLong(view_parent, getString(R.string.error_network))
            })
            isLoading.observe(this@MenuActivity, { bool ->
                bool.let { loading ->
                    if (loading) {
                        showWaitingDialog()
                    } else {
                        hideWaitingDialog()
                    }
                }
            })
            clickOrder.observe(this@MenuActivity, {
                AppPreference.putListOrder(listMenuOrder)
                Log.d("list order", listMenuOrder.toString())
                startActivity(Intent(this@MenuActivity, OrderActivity::class.java))
            })
        }
        setView()
    }

    private fun setView(){
        setToolbar("Tenant")
        listMenu = AppPreference.getMenu()
        setListMenu(listMenu)
    }

    private fun setListMenu(list: ArrayList<Menu>) {
        for (data in list){
            listMenuOrder.add(
                List(
                data.id,
                data.name,
                0,
                0
            )
            )
        }
        rv_menu.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            adapter = MenuAdapter(this@MenuActivity, list, this@MenuActivity)
        }
    }

    override fun onTotalChanged(position: Int, size: Int) {
        listMenuOrder[position].quantity = size.toLong()
        listMenuOrder[position].price = size.toLong() * listMenu[position].price!!
    }
}