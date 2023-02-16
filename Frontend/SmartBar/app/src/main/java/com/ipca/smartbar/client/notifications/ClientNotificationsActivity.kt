package com.ipca.smartbar.client.notifications

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.ipca.smartbar.R
import com.ipca.smartbar.client.cart.ClientCartActivity
import com.ipca.smartbar.client.historic.ClientHistoricActivity
import com.ipca.smartbar.client.products.ClientProductsActivity
import com.ipca.smartbar.client.profile.ClientProfileActivity
import com.ipca.smartbar.getToken

class ClientNotificationsActivity : AppCompatActivity() {

    private val adapter = NotificationsAdapter()
    private var notifications = ArrayList<ClientNotificationsModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_notifications)

        findViewById<View>(R.id.viewProgressBarClientNotifications).visibility = View.VISIBLE
        findViewById<ProgressBar>(R.id.progressBarClientNotifications).visibility = View.VISIBLE

        val token = getToken()

        ClientNotificationsRequests.getNotifications(lifecycleScope, token){
            if(it.isEmpty()) Toast.makeText(this@ClientNotificationsActivity, "Não existem notificações", Toast.LENGTH_SHORT).show()
            else {
                notifications = it
                adapter.notifyDataSetChanged()
            }
            findViewById<View>(R.id.viewProgressBarClientNotifications).visibility = View.GONE
            findViewById<ProgressBar>(R.id.progressBarClientNotifications).visibility = View.GONE
        }

        findViewById<ListView>(R.id.listViewNotifications).adapter = adapter
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

    inner class NotificationsAdapter: BaseAdapter(){
        override fun getCount(): Int {
            return notifications.size
        }

        override fun getItem(position: Int): Any {
            return notifications[position]
        }

        override fun getItemId(position: Int): Long {
            return 0L
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val rowView = layoutInflater.inflate(R.layout.row_notification, parent, false)
            val textViewNotificationTitle = rowView.findViewById<TextView>(R.id.textViewNotificationTitle)
            val textViewNotificationMessage = rowView.findViewById<TextView>(R.id.textViewNotificationMessage)
            val textViewNotificationDate = rowView.findViewById<TextView>(R.id.textViewNotificationDate)
            val imageButtonDelete = rowView.findViewById<ImageButton>(R.id.imageButtonNotificationDelete)
            val notification = notifications[position]

            textViewNotificationTitle.text = notification.title
            textViewNotificationMessage.text = notification.message
            textViewNotificationDate.append(" ${notification.date}")

            imageButtonDelete.setOnClickListener() {
                ClientNotificationsRequests.deleteNotification(lifecycleScope, notification.id, getToken()) {
                    if (it == "OK") {
                        val intent = Intent(this@ClientNotificationsActivity, ClientNotificationsActivity::class.java)
                        startActivity(intent)
                    } else { Toast.makeText(this@ClientNotificationsActivity, "ERRO AO APAGAR A NOTIFICAÇÃO", Toast.LENGTH_SHORT).show() }
                }
            }
            return rowView
        }
    }
}