package id.toriqwah.project.view.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import id.toriqwah.project.model.Menu
import id.toriqwah.project.model.Tenant
import id.toriqwah.project.util.SingleLiveEvent
import id.toriqwah.project.view.base.BaseViewModel
import kotlinx.coroutines.launch

class MainViewModel : BaseViewModel(){

    /**
     * Login
     */

    private val database = FirebaseDatabase.getInstance()
    val username = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val loginSuccess = SingleLiveEvent<Unit>()

    val listTenant = MutableLiveData<ArrayList<Tenant>>()
    val listMenu = MutableLiveData<ArrayList<Menu>>()
    val clickOrder = SingleLiveEvent<Unit>()

    fun onClickOrder(){
        clickOrder.call()
    }

    fun getDataTenant(child: String) {
        isLoading.value = true
        viewModelScope.launch {
            val tenantListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    isLoading.value = false
                    val data = arrayListOf<Tenant>()
                    for (tenant in dataSnapshot.children){
                        data.add(tenant.getValue(Tenant::class.java)!!)
                        Log.d("tenant", tenant.toString())
                    }
                    listTenant.value = data
                    Log.d("tenant", listTenant.value.toString())
                }


                override fun onCancelled(error: DatabaseError) {
                    isLoading.value = false
                }

            }
            database.reference.child(child).addListenerForSingleValueEvent(tenantListener)
        }
    }

    fun getDataMenu(child: String) {
        val tenantListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (tenant in dataSnapshot.children){
                    tenant.getValue(Tenant::class.java)?.let { listTenant.value?.add(it) }
                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        }
        database.reference.child(child).addListenerForSingleValueEvent(tenantListener)
    }
}