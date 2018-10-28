package co.dimitriongoua.badolrouteur.util;

import android.content.Context;
import android.telephony.SmsManager;
import android.util.Log;

import static co.dimitriongoua.badolrouteur.config.Constants.URL_APP;

public class Master {

    private static final String TAG = Master.class.getSimpleName();

    private Context context;

    public Master(Context context) {
        this.context = context;
    }

    public void sendSMS(String numero, String pass) {
        try {
            String message = "Bienvenue sur i-Money. Votre mot de passe est " + pass + ". " +
                    "Téléchargez l'application mobile Badol pour gérer votre compte. " + URL_APP;

            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(numero, null, message, null, null);
            Log.d(TAG, "Message envoyé");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
