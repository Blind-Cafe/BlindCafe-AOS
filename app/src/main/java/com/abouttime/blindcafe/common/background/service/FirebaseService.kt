package com.abouttime.blindcafe.common.background.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_ONE_SHOT
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.constants.LogTag.FCM
import com.abouttime.blindcafe.presentation.NavHostActivity
import com.google.firebase.iid.FirebaseInstanceIdReceiver
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlin.random.Random

private const val CHANNEL_ID = "FCM Message Channel"

class FirebaseService: FirebaseMessagingService() {


    companion object {
        var sharedPref: SharedPreferences? = null

        var token: String?
            get() {
                return sharedPref?.getString("token", "")
            }
            set(value) {
                sharedPref?.edit()?.putString("token", value)?.apply()
            }
    }


    // 메세지 받을 때마다 호출됨
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.e(FCM, "onMessageReceived 호출")

        val intent = Intent(this, NavHostActivity::class.java)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationId = Random.nextInt()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(notificationManager) // 채널 만들기
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, FLAG_ONE_SHOT)
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(message.data["title"])
            .setContentText(message.data["message"])
            .setSmallIcon(R.drawable.ic_logo)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()


        notificationManager.notify(notificationId, notification)

    }

    override fun onNewToken(newToken: String) {
        super.onNewToken(newToken)
        token = newToken
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(notificationManager: NotificationManager) {
        val channelName = "FCM Message Channel"
        val channel = NotificationChannel(CHANNEL_ID, channelName, IMPORTANCE_HIGH).apply {
            description = "FCM Message"
            enableLights(true)
            lightColor = resources.getColor(R.color.main, null)
        }
        notificationManager.createNotificationChannel(channel)
    }

}