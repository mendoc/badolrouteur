package co.dimitriongoua.badolrouteur.util;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import static co.dimitriongoua.badolrouteur.config.Constants.URL_APP;

public class Master {

    private static final String TAG = Master.class.getSimpleName();

    private Context context;

    public Master(Context context) {
        this.context = context;
    }

    public void sendSMS(String numero, String pass) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context, "Pas de permission d'envoi de SMS", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            String message = "Bienvenue sur Badol. Votre mot de passe est " + pass + ". " +
                    "Téléchargez l'application mobile Badol pour gérer votre compte. " + URL_APP;

            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(numero, null, message, null, null);
            Log.d(TAG, "Message envoyé");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
