package com.example.mad_practical_11_22172012041

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private lateinit var personlistview:RecyclerView
    val personList = ArrayList<Person>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.toolbar))
        personlistview = findViewById<RecyclerView>(R.id.listview)
        personlistview.adapter = PersonAdapter(this, personList)
        val button:FloatingActionButton = findViewById(R.id.fbutton)

        button.setOnClickListener {
            setpersondatatolistview()

        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.main_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return  when(item.itemId)
        {
            R.id.jsonmenu->{
                setpersondatatolistview()
                true
            }
            R.id.sqlmenu->{
                setpersondatatolistview()
                true
            }
            else-> super.onOptionsItemSelected(item)
        }
    }
    fun setpersondatatolistview()
    {
         CoroutineScope(Dispatchers.IO).launch{
        try {
            val data = HttpRequest().makeServiceCall("https://api.json-generator.com/templates/qjeKFdjkXCdK/data","rbn0rerl1k0d3mcwgw7dva2xuwk780z1hxvyvrb1")
            withContext(Dispatchers.Main) {
                try {
                    if(data != null)
                        runOnUiThread{getPersonDetailsFromJson(data)}
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    }
    private fun getPersonDetailsFromJson(sJson: String?) {
        personList.clear()
        try {
            val jsonArray = JSONArray(sJson)
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray[i] as JSONObject
                val person = Person(jsonObject)
                personList.add(person)
            }
            personlistview.adapter?.notifyDataSetChanged()
        } catch (ee: JSONException) {
            ee.printStackTrace()
        }
    }

}