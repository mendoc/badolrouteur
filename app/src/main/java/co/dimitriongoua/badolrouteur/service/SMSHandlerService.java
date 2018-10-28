package co.dimitriongoua.badolrouteur.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import co.dimitriongoua.badolrouteur.model.Recharge;
import co.dimitriongoua.badolrouteur.util.Master;

import static co.dimitriongoua.badolrouteur.config.Constants.KEY_BODY;
import static co.dimitriongoua.badolrouteur.config.Constants.KEY_TIMESTAMP;
import static co.dimitriongoua.badolrouteur.config.EndPoints.NEW_RECHARGE_URL;

public class SMSHandlerService extends Service {

    private static final String TAG = SMSHandlerService.class.getSimpleName();

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent != null) {
            Bundle intentExtras = intent.getExtras();
            if (intentExtras != null) {
                String body = (String) intentExtras.get(KEY_BODY);
                String timestamp = (String) intentExtras.get(KEY_TIMESTAMP);
                Recharge recharge = extractData(body);
                recharge.setTimestamp(timestamp);

                // On envoie le recharge en ligne
                sendRecharge(recharge);
            }
        }
        return START_STICKY;
    }


    private Recharge extractData(String sms) {

        String[] parts = sms.split(":");
        String reference = parts[1].trim().split(" ")[0];
        if (reference.endsWith(".")) reference = reference.substring(0, reference.length() - 1);

        parts = sms.split("FCFA");
        parts = parts[0].trim().split(" ");
        String montant = parts[parts.length - 1];

        parts = sms.split("FCFA");
        parts = parts[1].replace("du", "").trim().split(" ");
        String numero = parts[0].trim();
        if (numero.endsWith(".")) numero = numero.substring(0, numero.length() - 1);

        return new Recharge(reference, montant, numero);
    }

    public void sendRecharge(final Recharge recharge) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, NEW_RECHARGE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (obj.has("error")) {
                                if (!obj.getBoolean("error")) {
                                    if (obj.has("numero") && obj.has("pass")) {
                                        String numero = obj.getString("numero");
                                        String pass = obj.getString("pass");
                                        new Master(SMSHandlerService.this).sendSMS(numero, pass);
                                    }
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                Log.d(TAG, "Envoi des paramètres en cours ...");
                params.put("reference", recharge.getReference());
                params.put("montant",   recharge.getMontant());
                params.put("numero",    recharge.getNumero());
                params.put("timestamp", recharge.getTimestamp());
                return params;
            }
        };

        Volley.newRequestQueue(this).add(stringRequest);
    }
}
