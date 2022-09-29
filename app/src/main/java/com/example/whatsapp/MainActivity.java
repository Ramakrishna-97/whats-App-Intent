package com.example.whatsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hbb20.CountryCodePicker;

public class MainActivity extends AppCompatActivity {

    CountryCodePicker countryCodePicker;
    EditText phone,message;
    Button sendbtn;
    String messagestr,phonestr = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        countryCodePicker = findViewById(R.id.countryCode);
        phone = findViewById(R.id.phoneNo);
        message = findViewById(R.id.message);
        sendbtn=findViewById(R.id.sendbtn);


        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                messagestr=message.getText().toString();
                phonestr = phone.getText().toString();

                if (!messagestr.isEmpty() && !phonestr.isEmpty()){

                    countryCodePicker.registerCarrierNumberEditText(phone);
                    phonestr = countryCodePicker.getFullNumber();

                    if (isWhatsAppInstalled()){

                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone="+phonestr+
                                "&text="+messagestr));
                        startActivity(i);
                        message.setText("");
                        phone.setText("");

                    }else {

                        Toast.makeText(MainActivity.this, "whatsapp is not Installed", Toast.LENGTH_SHORT).show();
                    }



                }else {

                    Toast.makeText(MainActivity.this, "Please fill in the phone no. and message, it can't be empty", Toast.LENGTH_LONG).show();
                }

                if (phone.length()==0){
                    phone.setError("Enter Mobile Number");
                }
                else if (message.length()==0){
                    message.setError("Enter Message");
                }
            }
        });

    }

    private  boolean isWhatsAppInstalled(){

        PackageManager packageManager = getPackageManager();
        boolean whatsAppInstalled;

        try {

            packageManager.getPackageInfo("com.whatsapp",PackageManager.GET_ACTIVITIES);
            whatsAppInstalled = true;

        }catch (PackageManager.NameNotFoundException e){

            whatsAppInstalled = false;
        }

        return whatsAppInstalled;
    }
}