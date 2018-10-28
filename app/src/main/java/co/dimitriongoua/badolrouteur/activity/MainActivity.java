package co.dimitriongoua.badolrouteur.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import co.dimitriongoua.badolrouteur.R;
import co.dimitriongoua.badolrouteur.service.SMSHandlerService;

import static co.dimitriongoua.badolrouteur.config.Constants.KEY_BODY;
import static co.dimitriongoua.badolrouteur.config.Constants.KEY_TIMESTAMP;
import static co.dimitriongoua.badolrouteur.config.Constants.SMS_ADDRESS;
import static co.dimitriongoua.badolrouteur.config.Constants.SMS_PREFIX;
import static co.dimitriongoua.badolrouteur.config.Constants.SMS_RECU;

public class MainActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS},
                    MY_PERMISSIONS_REQUEST_SEND_SMS);
        }
    }
}
