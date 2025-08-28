package com.example.smswipe

import android.app.admin.DevicePolicyManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.widget.Toast

class SmsReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Telephony.Sms.Intents.SMS_RECEIVED_ACTION) {
            val messages = Telephony.Sms.Intents.getMessagesFromIntent(intent)
            for (sms in messages) {
                val messageBody = sms.messageBody
                if (messageBody == "YOUR_CUSTOM_MESSAGE") {
                    val dpm = context.getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
                    val adminComponent = ComponentName(context, MyDeviceAdminReceiver::class.java)
                    if (dpm.isAdminActive(adminComponent)) {
                        dpm.wipeData(0)
                        Toast.makeText(context, "Factory reset triggered", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
