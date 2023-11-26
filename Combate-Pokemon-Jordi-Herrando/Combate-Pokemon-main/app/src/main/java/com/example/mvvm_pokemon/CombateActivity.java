package com.example.mvvm_pokemon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CombateActivity extends AppCompatActivity {

    private PokemonViewModel viewModel;
    private TextView tvPokemon1, tvPokemon2, tvMensaje;
    private Button btnAtaque;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combate);

        // Inicializar ViewModel
        viewModel = new ViewModelProvider(this).get(PokemonViewModel.class);

        // Obtener referencias de la interfaz de usuario
        tvPokemon1 = findViewById(R.id.tvPokemon1);
        tvPokemon2 = findViewById(R.id.tvPokemon2);
        tvMensaje = findViewById(R.id.tvMensaje);
        btnAtaque = findViewById(R.id.btnCombate);

        // Observadores para actualizar la interfaz de usuario cuando cambian los datos
        viewModel.getPokemon1().observe(this, pokemon -> tvPokemon1.setText(pokemon.toString()));
        viewModel.getPokemon2().observe(this, pokemon -> tvPokemon2.setText(pokemon.toString()));
        viewModel.getMensaje().observe(this, mensaje -> {
            tvMensaje.setText(mensaje);

            // Verificar si algún Pokémon ha sido derrotado
            if (viewModel.isPokemon1Derrotado() || viewModel.isPokemon2Derrotado()) {
                // Desactivar el botón de ataque si algún Pokémon ha sido derrotado
                btnAtaque.setEnabled(false);
            }
        });

        // Configurar el botón de ataque
        btnAtaque.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Verificar si algún Pokémon ha sido derrotado
                if (!viewModel.isPokemon1Derrotado() && !viewModel.isPokemon2Derrotado()) {
                    // Realizar el ataque solo si ningún Pokémon ha sido derrotado
                    viewModel.realizarAtaque();
                } else {
                    // Mostrar un mensaje indicando que no se puede atacar
                    Toast.makeText(CombateActivity.this, "No se puede atacar, un Pokémon ha sido derrotado", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Iniciar el combate con los datos recibidos de FormularioActivity
        iniciarCombate();
    }

    private void iniciarCombate() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String nombre1 = extras.getString("nombre1");
            int hp1 = extras.getInt("hp1");
            int ataque1 = extras.getInt("ataque1");
            int defensa1 = extras.getInt("defensa1");
            int ataqueEsp1 = extras.getInt("ataqueEsp1");
            int defensaEsp1 = extras.getInt("defensaEsp1");

            String nombre2 = extras.getString("nombre2");
            int hp2 = extras.getInt("hp2");
            int ataque2 = extras.getInt("ataque2");
            int defensa2 = extras.getInt("defensa2");
            int ataqueEsp2 = extras.getInt("ataqueEsp2");
            int defensaEsp2 = extras.getInt("defensaEsp2");

            // Iniciar el combate con los datos recibidos
            viewModel.iniciarCombate(nombre1, hp1, ataque1, defensa1, ataqueEsp1, defensaEsp1,
                    nombre2, hp2, ataque2, defensa2, ataqueEsp2, defensaEsp2);
        } else {
            // Mostrar un mensaje de error si no se recibieron datos
            Toast.makeText(this, "Error: No se recibieron datos de Pokémon", Toast.LENGTH_SHORT).show();
            finish(); // Cerrar la actividad si no hay datos
        }
    }
}
