package com.abouttime.blindcafe.background.service

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
import com.abouttime.BlindCafeApplication.Companion.sharedPreferences
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.constants.LogTag.FCM_TAG
import com.abouttime.blindcafe.common.constants.PreferenceKey.NOTIFICATION_CURRENT_ROOM
import com.abouttime.blindcafe.common.constants.PreferenceKey.NOTIFICATION_ENTIRE
import com.abouttime.blindcafe.common.constants.PreferenceKey.NOTIFICATION_FALSE
import com.abouttime.blindcafe.common.constants.PreferenceKey.NOTIFICATION_MESSAGE
import com.abouttime.blindcafe.common.constants.PreferenceKey.NOTIFICATION_ROOM
import com.abouttime.blindcafe.presentation.GlobalLiveData.updateHomeState
import com.abouttime.blindcafe.presentation.NavHostActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlin.random.Random

private const val CHANNEL_ID = "FCM Message Channel"

class FirebaseService() : FirebaseMessagingService() {


    companion object {
        private var sharedPref: SharedPreferences = sharedPreferences

        var token: String?
            get() {
                return sharedPref?.getString("token", "")
            }
            set(value) {
                sharedPref?.edit()?.putString("token", value)?.apply()
            }

        const val FCM_PATH = "FCM_PATH"
    }


    // 메세지 받을 때마다 호출됨
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.e(FCM_TAG, "onMessageReceived 호출")
        updateHomeState.postValue(true)

        val intent = Intent(this, NavHostActivity::class.java)
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationId = Random.nextInt()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(notificationManager) // 채널 만들기
        }


        val title = message.notification?.title
        val body = message.notification?.body
        val imageUrl = message.notification?.imageUrl
        val type = message.data["type"]
        val path = message.data["path"]
        val matchingId = message.data["matchingId"]



        Log.e(FCM_TAG, "$type, $path, $matchingId")
        val blockCurrentRoomNotification =
            sharedPreferences.getString("${matchingId}${NOTIFICATION_CURRENT_ROOM}", null)
        val blockEntireNotification = sharedPreferences.getString(NOTIFICATION_ENTIRE, null)
        val blockMessageNotification = sharedPreferences.getString(NOTIFICATION_MESSAGE, null)
        val blockSpecificNotification =
            sharedPreferences.getString("${matchingId}${NOTIFICATION_ROOM}", null)


        if (blockCurrentRoomNotification == NOTIFICATION_FALSE) return // 현재 채팅방에 있다면 푸시를 받지 않는다
        if (blockEntireNotification == NOTIFICATION_FALSE) return
        if (blockMessageNotification == NOTIFICATION_FALSE && type == "T") return
        if (blockSpecificNotification == NOTIFICATION_FALSE && matchingId != null) return

        intent.putExtra(FCM_PATH, path)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, FLAG_ONE_SHOT)
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(body)
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