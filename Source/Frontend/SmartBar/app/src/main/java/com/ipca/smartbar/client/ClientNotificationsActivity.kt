package com.ipca.smartbar.client

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.ipca.smartbar.R
import com.ipca.smartbar.client.models.Notification
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ClientNotificationsActivity : AppCompatActivity() {

    val notification = arrayListOf<Notification>()

    val adapter = NotificationAdapter()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_notifications)

        notification.add(Notification("100", LocalDateTime.now(), "Pedido Encontra-se em preparação"))

        val listViewNotification = findViewById<ListView>(R.id.listViewNotification)
        listViewNotification.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_client, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        return when(item.itemId) {
            R.id.action_profile_client -> {
                val intent = Intent(this@ClientNotificationsActivity, ClientProfileActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.action_products_client -> {
                val intent = Intent(this@ClientNotificationsActivity, ClientProductsActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.action_cart_client -> {
                val intent = Intent(this@ClientNotificationsActivity, ClientCartActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.action_historic_client -> {
                val intent = Intent(this@ClientNotificationsActivity, ClientHistoricActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.action_notifications_client -> {
                val intent = Intent(this@ClientNotificationsActivity, ClientNotificationsActivity::class.java)
                startActivity(intent)
                true
            }
            else -> {
                false
            }
        }
        return super.onOptionsItemSelected(item)
    }

    inner class NotificationAdapter : BaseAdapter() {
        override fun getCount(): Int {
            return notification.size
        }

        override fun getItem(position: Int): Any {
            return notification[position]
        }

        override fun getItemId(position: Int): Long {
            return 0L
        }

        override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
            val rootView = layoutInflater.inflate(R.layout.row_notification, parent, false)
            val textViewPedidoNotif = rootView.findViewById<TextView>(R.id.textViewPedido)
            val textViewDataNotif = rootView.findViewById<TextView>(R.id.textViewDataNotification)
            val textViewNotif = rootView.findViewById<TextView>(R.id.textViewNotification)

            textViewPedidoNotif.text = notification[position].idPedido
            textViewDataNotif.text = notification[position].dataAtual.toString()
            textViewNotif.text = notification[position].notificacao

            textViewDataNotif.text = (textViewDataNotif.text as String).replace('T', ' ')

            return rootView
        }
    }
}