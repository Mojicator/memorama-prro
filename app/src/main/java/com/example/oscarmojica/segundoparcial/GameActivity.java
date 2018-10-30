package com.example.oscarmojica.segundoparcial;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class GameActivity extends AppCompatActivity {

    ImageButton primero;
    int card1, card2;
    boolean bloqueo = false;

    ImageButton restart, exit;

    TextView textViewScore;
    String SCORE_STRING = "Puntuacion: ";
    TextView textViewLifes;
    String LIFES_STRING = "Vidas: ";

    int backCard;
    int images[];
    ImageButton [] btnImagenes = new ImageButton[16];
    ArrayList<Integer> arrayShuffled;

    int hits = 0;
    int score = 0;

    int difficulty = 1000;
    int DELAY_ERROR = 500;
    int lifes = 0;
    int restart_life;

    final Handler handler = new Handler();

    public void cargaImagenes(){
        backCard = R.drawable.back;
        images = new int[]{
                R.drawable.imagen0,
                R.drawable.imagen1,
                R.drawable.imagen2,
                R.drawable.imagen3,
                R.drawable.imagen4,
                R.drawable.imagen5,
                R.drawable.imagen6,
                R.drawable.imagen7
        };
    }

    public void cargaBotones(){
        btnImagenes[0] = (ImageButton) findViewById(R.id.iBtn00);
        btnImagenes[1] = (ImageButton) findViewById(R.id.iBtn01);
        btnImagenes[2] = (ImageButton) findViewById(R.id.iBtn02);
        btnImagenes[3] = (ImageButton) findViewById(R.id.iBtn03);
        btnImagenes[4] = (ImageButton) findViewById(R.id.iBtn10);
        btnImagenes[5] = (ImageButton) findViewById(R.id.iBtn11);
        btnImagenes[6] = (ImageButton) findViewById(R.id.iBtn12);
        btnImagenes[7] = (ImageButton) findViewById(R.id.iBtn13);
        btnImagenes[8] = (ImageButton) findViewById(R.id.iBtn20);
        btnImagenes[9] = (ImageButton) findViewById(R.id.iBtn21);
        btnImagenes[10] = (ImageButton) findViewById(R.id.iBtn22);
        btnImagenes[11] = (ImageButton) findViewById(R.id.iBtn23);
        btnImagenes[12] = (ImageButton) findViewById(R.id.iBtn30);
        btnImagenes[13] = (ImageButton) findViewById(R.id.iBtn31);
        btnImagenes[14] = (ImageButton) findViewById(R.id.iBtn32);
        btnImagenes[15] = (ImageButton) findViewById(R.id.iBtn33);

        textViewScore = (TextView) findViewById(R.id.puntuacion);
        textViewScore.setHint(SCORE_STRING + Integer.toString(score));
        textViewLifes = (TextView) findViewById(R.id.vidas);
        textViewLifes.setHint(LIFES_STRING + Integer.toString(lifes));
    }

    public ArrayList<Integer> barajas(int longitud){
        ArrayList resultA = new ArrayList<Integer>();
        for (int i = 0; i < longitud; i++){
            resultA.add(i % longitud / 2);
        }
        Collections.shuffle(resultA);
        return resultA;
    }

    public void controlesMenu(){
        restart = (ImageButton) findViewById(R.id.reiniciar);
        exit = (ImageButton) findViewById(R.id.salir);

        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciar();
            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void comprobar(int i, final ImageButton imgBtn){
        if (primero == null){
            //obtenemos la primera carta pulsada
            primero = imgBtn;
            primero.setScaleType(ImageView.ScaleType.CENTER_CROP);
            primero.setImageResource(images[arrayShuffled.get(i)]);
            //bloqueamos la carta
            primero.setEnabled(false);
            //obtenemos el valor de la imagen de la carta pulsada
            card1 = arrayShuffled.get(i);
        } else {
            //aqui ya se pulso la segunda carta
            bloqueo = true;
            imgBtn.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imgBtn.setImageResource(images[arrayShuffled.get(i)]);
            imgBtn.setEnabled(false);
            //valor de la segunda imagen pulsada
            card2 = arrayShuffled.get(i);
            if (card1 == card2){
                primero = null;
                bloqueo = false;
                hits++;
                score = score + 10;
                textViewScore.setHint(SCORE_STRING + Integer.toString(score));
                textViewLifes.setHint(LIFES_STRING + Integer.toString(lifes));
                //para ganar
                if (hits == 8){
                    Toast toast = Toast.makeText(getApplicationContext(),"Has ganado", Toast.LENGTH_LONG);
                    toast.show();
                }
            } else {
                //si son diferentes los valores, se vuelven a ocultar las cartas
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        primero.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        primero.setImageResource(R.drawable.back);
                        imgBtn.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        imgBtn.setImageResource(R.drawable.back);

                        primero.setEnabled(true);
                        imgBtn.setEnabled(true);

                        primero = null;
                        bloqueo = false;

                        if (score > 0 && lifes >= 0){
                            score = score - 1;
                            lifes--;
                            textViewScore.setHint(SCORE_STRING + Integer.toString(score));
                            textViewLifes.setHint(LIFES_STRING + Integer.toString(lifes));
                        } else if (score == 0){
                            lifes--;
                            textViewLifes.setHint(LIFES_STRING + Integer.toString(lifes));
                        }
                        if (lifes < 0){
                            textViewLifes.setHint(LIFES_STRING + Integer.toString(lifes));
                            Toast toast = Toast.makeText(getApplicationContext(),"Has perdido", Toast.LENGTH_LONG);
                            toast.show();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    iniciar();
                                }
                            }, DELAY_ERROR);
                        }
                    }
                }, DELAY_ERROR);
            }
        }
    }

    public void cargaDificultad(){
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if (extras != null){
            difficulty = extras.getInt("DIFFICULTY");
            lifes = extras.getInt("LIFES");
            restart_life = lifes;
        }
    }

    public void iniciar(){
        arrayShuffled = barajas(images.length * 2);
        cargaBotones();

        for (int i = 0; i < btnImagenes.length; i++){
            btnImagenes[i].setScaleType(ImageView.ScaleType.CENTER_CROP);
            btnImagenes[i].setImageResource(images[arrayShuffled.get(i)]);
        }

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < btnImagenes.length; i++){
                    btnImagenes[i].setScaleType(ImageView.ScaleType.CENTER_CROP);
                    btnImagenes[i].setImageResource(backCard);
                }
            }
        }, difficulty);

        for (int i = 0; i < arrayShuffled.size(); i++){
            final int j = i;
            btnImagenes[i].setEnabled(true);
            btnImagenes[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!bloqueo){
                        comprobar(j, btnImagenes[j]);
                    }
                }
            });
        }
        hits = 0;
        score = 0;
        lifes = restart_life;
        textViewScore.setHint(SCORE_STRING + Integer.toString(score));
        textViewLifes.setHint(LIFES_STRING + Integer.toString(lifes));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        cargaDificultad();
        cargaImagenes();
        controlesMenu();
        iniciar();
    }
}
