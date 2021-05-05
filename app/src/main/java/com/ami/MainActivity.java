package com.ami;

import androidx.appcompat.app.AppCompatActivity;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.analytics.FirebaseAnalytics;

import android.content.ClipData;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Firebase mref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_main);

        mref= new Firebase("https://smart-meter-4b090-default-rtdb.firebaseio.com/");
        final TextView cost_today = findViewById(R.id.cost_today);
        final TextView kwh_today = findViewById(R.id.kwh_today);
        final TextView total_cost = findViewById(R.id.total_cost);
        final TextView total_kwh = findViewById(R.id.total_kwh);
        final TextView month = findViewById(R.id.month);
        final TextView voltage_condition = findViewById(R.id.voltage_analyze);



        mref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                String phase_1_volt= dataSnapshot.child("voltage1").getValue().toString();
                String phase_2_volt= dataSnapshot.child("voltage2").getValue().toString();
                String phase_3_volt= dataSnapshot.child("voltage3").getValue().toString();
                String phase_1_amps= dataSnapshot.child("current1").getValue().toString();
                String phase_2_amps= dataSnapshot.child("current2").getValue().toString();
                String phase_3_amps= dataSnapshot.child("current3").getValue().toString();
                String phase_1_pf= dataSnapshot.child("pf1").getValue().toString();
                String phase_2_pf= dataSnapshot.child("pf2").getValue().toString();
                String phase_3_pf= dataSnapshot.child("pf3").getValue().toString();
                String power=dataSnapshot.child("power").getValue().toString();


                //VOLTAGE STRING//
                String substr_v_1=phase_1_volt.substring(2,6);
                String substr_v_2=phase_2_volt.substring(2,6);
                String substr_v_3=phase_3_volt.substring(2,6);

                float v1 = Float.parseFloat(substr_v_1);
                float v2=Float.parseFloat(substr_v_2);
                float v3=Float.parseFloat(substr_v_3);


                ///////////////voltage sag/swell////////////////////
                int voltage=240;
                if(v1>voltage || v2>voltage ||v3>voltage){

                    voltage_condition.setText("HIGH VOLTAGE");
                }
                else if(v1<voltage || v2<voltage ||v3<voltage)
                {voltage_condition.setText("LOW VOLTAGE");

                }
                //CURRENT STRING//
                String substr_i_1=phase_1_amps.substring(3,5);
                String substr_i_2=phase_2_amps.substring(3,5);
                String substr_i_3=phase_3_amps.substring(3,5);

                float i1 = Float.parseFloat(substr_i_1);
                float i2 =Float.parseFloat(substr_i_2);
                float i3 =Float.parseFloat(substr_i_3);
                //POWER FACTOR STRING//
                String substr_pf_1=phase_1_pf.substring(3,6);
                String substr_pf_2=phase_2_pf.substring(3,6);
                String substr_pf_3=phase_3_pf.substring(3,6);

               // float pf1 = Float.parseFloat(substr_pf_1);
               // float pf2 =Float.parseFloat(substr_pf_2);
                //float pf3 =Float.parseFloat(substr_pf_3);

                float Power_1= v1;
                float Power_2= v2;
                float Power_3= v3;
                float Total_Power = Power_1+Power_2+Power_3;


                float count = 0;
                count = count + Float.parseFloat(power);
                float x=count;
                float total=x+Float.parseFloat(power);
                String q = Float.toString(total);
                total_kwh.setText(q);

                //show total power
                String s = Float.toString(Total_Power);
                kwh_today.setText(s);

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }

        });
    }


}
