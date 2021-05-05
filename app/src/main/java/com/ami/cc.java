package com.ami;

import androidx.appcompat.app.AppCompatActivity;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.analytics.FirebaseAnalytics;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;

public class cc extends AppCompatActivity {

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

        mref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                String phase_1_volt= dataSnapshot.child("voltage1").getValue().toString();
                kwh_today.setText(phase_1_volt);
                String phase_2_volt= dataSnapshot.child("voltage2").getValue().toString();
                total_cost.setText(phase_2_volt);
                String phase_3_volt= dataSnapshot.child("voltage3").getValue().toString();
                String phase_1_amps= dataSnapshot.child("current1").getValue().toString();
                String phase_2_amps= dataSnapshot.child("current2").getValue().toString();
                String phase_3_amps= dataSnapshot.child("current3").getValue().toString();
                String phase_1_pf= dataSnapshot.child("pf1").getValue().toString();
                String phase_2_pf= dataSnapshot.child("pf2").getValue().toString();
                String phase_3_pf= dataSnapshot.child("pf3").getValue().toString();
                //VOLTAGE STRING//
                String substr_v_1=phase_1_volt.substring(2,6);
                String substr_v_2=phase_2_volt.substring(2,6);
                String substr_v_3=phase_3_volt.substring(2,6);

                float v1 = Float.parseFloat(substr_v_1);
                float v2=Float.parseFloat(substr_v_2);
                float v3=Float.parseFloat(substr_v_3);
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

                float pf1 = Float.parseFloat(substr_pf_1);
                float pf2 =Float.parseFloat(substr_pf_2);
                float pf3 =Float.parseFloat(substr_pf_3);

                float Power_1= v1;
                float Power_2= v2;
                float Power_3= v3;
                float Total_Power = Power_1+Power_2+Power_3;
                //show total power
                String s = Float.toString(Total_Power);

                while(Total_Power>0) {

                    float sum_of_total = Total_Power++;
                    String p = Float.toString(sum_of_total);
                    total_kwh.setText(p);
                }


            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }

        });
    }


}
