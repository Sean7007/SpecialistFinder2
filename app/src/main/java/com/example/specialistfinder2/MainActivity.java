package com.example.specialistfinder2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    //Button declaration
    private Button customer, specialist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //
        customer = (Button) findViewById(R.id.customer);
        specialist = (Button) findViewById(R.id.specialist);

        //Set onClickListener of the buttons on Customer screen
        customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CustomerLoginActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        });

        //Set onClickListener of the buttons on Specialist screen
        customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SpecialistLoginActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        });
    }
}
