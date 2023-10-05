package com.example.the_game_of_pig_herrando_jordi;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;


public class MainActivity extends AppCompatActivity {

    // Declarar las vistas
    private Button buttonJugar;
    private Button buttonPasaTurno;
    private TextView textScoreJugador1;
    private TextView textScoreJugador2;
    private TextView textGanador;

    private TextView umbral1;

    private TextView umbral2;
    private Button buttonReinicio;

    // Declarar variables de estado
    private int scoreJugador1 = 0;
    private int scoreJugador2 = 0;

    private int umbralJugador1 = 20;

    private int umbralJugador2 = 20;
    private boolean turnoJugador1 = true;
    private boolean juegoTerminado = false;

    private int jugador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);

        // Inicializar las vistas
        buttonJugar = findViewById(R.id.buttonJugar);
        buttonPasaTurno = findViewById(R.id.buttonPasaTurno);
        textScoreJugador1 = findViewById(R.id.textScoreJugador1);
        textScoreJugador2 = findViewById(R.id.textScoreJugador2);
        textGanador = findViewById(R.id.textGanador);
        buttonReinicio = findViewById(R.id.buttonReinicio);


        buttonJugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jugar();
            }
        });

        buttonPasaTurno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pasaTurno();
            }
        });

        buttonReinicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reinicio();
            }
        });
    }

    private void jugar()
    {
        Random random = new Random();
        int jugarValue =random.nextInt(6)+1;

        if (jugador== 1){
            scoreJugador1 +=jugarValue;
        } else {
            scoreJugador2 +=jugarValue;
        }
        actualizaInterfaz();
        }

        private void pasaTurno() {

        switchPlayer();

        actualizaInterfaz();

        compruebaGanador();
        }

        private void switchPlayer()
    {
        jugador= (jugador==1) ? 2:1;

    }

    private void reinicio()
    {
        scoreJugador1=0;
        scoreJugador2=0;


        actualizaInterfaz();

        textGanador.setText("");
    }

    private void actualizaInterfaz() {
sacaGanador("Jugador 1");
        textScoreJugador1.setText("Puntuación jugador 1: "+scoreJugador1);
        umbral1.setText("Umbral jugador 1:"+umbralJugador1);
        textScoreJugador2.setText("Puntuación jugador 2: "+scoreJugador2);
        umbral2.setText("Umbral jugador 2:"+umbralJugador2);
    }

    private void compruebaGanador() {
        if (scoreJugador1 >= umbralJugador1) {

            sacaGanador("Jugador 1");

        } else if (scoreJugador2 >= umbralJugador2) {
            sacaGanador("Jugador 2");
        }
    }
        private void sacaGanador(String winner) {

            textGanador.setText("El ganador es "+winner);
        }
    }











