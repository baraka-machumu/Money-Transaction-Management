package com.lubasu.barakamachumu.crdbtrans;

/**
 * Created by baraka.machumu on 7/13/2018.
 */


        import android.content.BroadcastReceiver;
        import android.content.Context;
        import android.content.Intent;
        import android.os.Bundle;
        import android.telephony.SmsManager;
        import android.telephony.SmsMessage;
        import android.widget.Toast;
public class SMSReceiver extends BroadcastReceiver {
    // SmsManager class is responsible for all SMS related actions
    final SmsManager sms = SmsManager.getDefault();
    public void onReceive(Context context, Intent intent)
    {
        // Get the SMS message received
        final Bundle bundle = intent.getExtras();
        try {
            if (bundle != null) {
                // A PDU is a "protocol data unit". This is the industrial standard for SMS message
                final Object[] pdusObj = (Object[]) bundle.get("pdus");
                for (int i = 0; i < pdusObj.length; i++) {

                    SmsMessage sms = SmsMessage.createFromPdu((byte[]) pdusObj[i]);

                    String phoneNumber = sms.getDisplayOriginatingAddress();
                    String sender = phoneNumber;
                    String message = sms.getDisplayMessageBody();
                    String formattedText = String.format(context.getResources().getString(R.string.sms_message), sender, message);
                    // Display the SMS message in a Toast
                    Toast.makeText(context, formattedText, Toast.LENGTH_LONG).show();
                    CrdbFragment inst = CrdbFragment.instance();
                    inst.updateList(formattedText);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}