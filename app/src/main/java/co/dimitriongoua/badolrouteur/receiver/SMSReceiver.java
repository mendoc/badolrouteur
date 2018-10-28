package co.dimitriongoua.badolrouteur.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import co.dimitriongoua.badolrouteur.service.SMSHandlerService;

import static co.dimitriongoua.badolrouteur.config.Constants.KEY_BODY;
import static co.dimitriongoua.badolrouteur.config.Constants.KEY_TIMESTAMP;
import static co.dimitriongoua.badolrouteur.config.Constants.SMS_ADDRESS;
import static co.dimitriongoua.badolrouteur.config.Constants.SMS_PREFIX;
import static co.dimitriongoua.badolrouteur.config.Constants.SMS_RECU;

public class SMSReceiver extends BroadcastReceiver {

    public static final String SMS_BUNDLE = "pdus";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle intentExtras = intent.getExtras();
        if (intentExtras != null) {
            Object[] sms = (Object[]) intentExtras.get(SMS_BUNDLE);
            if (sms != null) {
                for (Object sm : sms) {
                    SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) sm);

                    String smsBody = smsMessage.getMessageBody();
                    String address = smsMessage.getOriginatingAddress();
                    String timestamp = "" + smsMessage.getTimestampMillis();

                    //address = "AirtelMoney";
                    //smsBody = "Trans.ID : PP180216.2107.A19378. Vous avez recu 25000 FCFA du 04213803. Votre solde disponible est 2410 FCFA. Decouvrez les points Airtel Money les plus proches en composant *556#.";

                    if (smsBody.contains(SMS_PREFIX) && smsBody.contains(SMS_RECU) && address.equals(SMS_ADDRESS)) {

                        Intent smsIntent = new Intent(context, SMSHandlerService.class);
                        smsIntent.putExtra(KEY_BODY, smsBody);
                        smsIntent.putExtra(KEY_TIMESTAMP, timestamp);
                        context.startService(smsIntent);
                    }
                }
            }
        }
    }
}
