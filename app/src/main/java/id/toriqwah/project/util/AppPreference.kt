package id.toriqwah.project.util

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.orhanobut.hawk.Hawk
import id.toriqwah.project.model.List
import id.toriqwah.project.model.Menu

class AppPreference {

    companion object {
        private const val a_first_time = "a_first_time"
        private const val a_login = "a_login"
        private const val a_token = "a_token"
        private const val a_list_menu = "a_list_menu"
        private const val a_list_order = "a_list_order"


        fun deleteAll() {
            Hawk.delete(a_first_time)
            Hawk.delete(a_login)
            Hawk.delete(a_token)
            Hawk.delete(a_list_menu)
            Hawk.delete(a_list_order)

        }

        fun putFirstTime(value: Boolean) {
            Hawk.put(a_first_time, value)
        }

        fun isFirstTime(): Boolean {
            return (Hawk.get(a_first_time, true))
        }

        fun putLogin(value: Boolean) {
            Hawk.put(a_login, value)
        }

        fun isLogin(): Boolean {
            return (Hawk.get(a_login, false))
        }
        fun putToken(value: String) {
            Hawk.put(a_token, value)
        }

        fun getToken(): String {
            return (Hawk.get(a_token, ""))
        }

        fun putMenu(value: ArrayList<Menu>) {
            Hawk.put(a_list_menu, Gson().toJson(value))
        }

        fun getMenu(): ArrayList<Menu> {
            val type = object : TypeToken<ArrayList<Menu>>() {}.type
            return Gson().fromJson(Hawk.get(a_list_menu, ""), type)
        }
        fun putListOrder(value: ArrayList<List>) {
            Hawk.put(a_list_order, Gson().toJson(value))
        }

        fun getListOrder(): ArrayList<List> {
            val type = object : TypeToken<ArrayList<List>>() {}.type
            return Gson().fromJson(Hawk.get(a_list_order, ""), type)
        }
    }

}