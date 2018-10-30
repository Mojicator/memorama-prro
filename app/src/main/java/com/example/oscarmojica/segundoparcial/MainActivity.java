package com.example.oscarmojica.segundoparcial;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button easy, medium, hard, perro;

    int EASY_TIME = 3000;
    int EASY_LIFES = 10;
    int MEDIUM_TIME = 2000;
    int MEDIUM_LIFES = 8;
    int HARD_TIME = 1000;
    int HARD_LIFES = 6;
    int PERRO_TIME = 500;
    int PERRO_LIFES = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        easy = (Button) findViewById(R.id.btnEasy);
        easy.setOnClickListener(this);
        medium = (Button) findViewById(R.id.btnMedium);
        medium.setOnClickListener(this);
        hard = (Button) findViewById(R.id.btnHard);
        hard.setOnClickListener(this);
        perro = (Button) findViewById(R.id.btnPerro);
        perro.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent juego = new Intent(MainActivity.this, GameActivity.class);
        switch (v.getId()){
            case R.id.btnEasy:
                juego.putExtra("DIFFICULTY", EASY_TIME);
                juego.putExtra("LIFES", EASY_LIFES);
                break;
            case R.id.btnMedium:
                juego.putExtra("DIFFICULTY", MEDIUM_TIME);
                juego.putExtra("LIFES", MEDIUM_LIFES);
                break;
            case R.id.btnHard:
                juego.putExtra("DIFFICULTY", HARD_TIME);
                juego.putExtra("LIFES", HARD_LIFES);
                break;
            case R.id.btnPerro:
                juego.putExtra("DIFFICULTY", PERRO_TIME);
                juego.putExtra("LIFES", PERRO_LIFES);
                break;
        }
        startActivity(juego);
    }
}
