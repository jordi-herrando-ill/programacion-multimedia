package com.example.mvvm_pokemon;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class FormularioActivity extends AppCompatActivity {

    private EditText etNombre1, etHp1, etAtaque1, etDefensa1, etAtaqueEsp1, etDefensaEsp1;
    private EditText etNombre2, etHp2, etAtaque2, etDefensa2, etAtaqueEsp2, etDefensaEsp2;
    private Button btnCombate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        etNombre1 = findViewById(R.id.etNombre1);
        etHp1 = findViewById(R.id.etHp1);
        etAtaque1 = findViewById(R.id.etAtaque1);
        etDefensa1 = findViewById(R.id.etDefensa1);
        etAtaqueEsp1 = findViewById(R.id.etAtaqueEsp1);
        etDefensaEsp1 = findViewById(R.id.etDefensaEsp1);

        etNombre2 = findViewById(R.id.etNombre2);
        etHp2 = findViewById(R.id.etHp2);
        etAtaque2 = findViewById(R.id.etAtaque2);
        etDefensa2 = findViewById(R.id.etDefensa2);
        etAtaqueEsp2 = findViewById(R.id.etAtaqueEsp2);
        etDefensaEsp2 = findViewById(R.id.etDefensaEsp2);

        btnCombate = findViewById(R.id.btnCombate);

        btnCombate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validar y pasar a la siguiente actividad
                try {
                    if (validarDatos()) {
                        lanzarActividadCombate();
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(FormularioActivity.this, "Ingrese valores numéricos válidos", Toast.LENGTH_SHORT).show();
                } catch (IllegalArgumentException e) {
                    Toast.makeText(FormularioActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validarDatos() {
        // Validar que todos los campos estén completos y tengan valores válidos
        return validarCampo(etNombre1) &&
                validarCampo(etHp1, 0, 999) &&
                validarCampo(etAtaque1, 0, 999) &&
                validarCampo(etDefensa1, 0, 999) &&
                validarCampo(etAtaqueEsp1, 0, 999) &&
                validarCampo(etDefensaEsp1, 0, 999) &&
                validarCampo(etNombre2) &&
                validarCampo(etHp2, 0, 999) &&
                validarCampo(etAtaque2, 0, 999) &&
                validarCampo(etDefensa2, 0, 999) &&
                validarCampo(etAtaqueEsp2, 0, 999) &&
                validarCampo(etDefensaEsp2, 0, 999);
    }

    private boolean validarCampo(EditText editText) {
        // Validar que el campo no esté vacío
        if (TextUtils.isEmpty(editText.getText().toString())) {
            throw new IllegalArgumentException("Por favor, complete todos los campos");
        }
        return true;
    }

    private boolean validarCampo(EditText editText, int min, int max) {
        // Validar que el campo no esté vacío y tenga un valor dentro del rango
        if (TextUtils.isEmpty(editText.getText().toString())) {
            throw new IllegalArgumentException("Por favor, complete todos los campos");
        }

        int valor = Integer.parseInt(editText.getText().toString());
        if (valor < min || valor > max) {
            throw new IllegalArgumentException("Ingrese un valor entre " + min + " y " + max + " para " + editText.getHint());
        }

        return true;
    }

    private void lanzarActividadCombate() {
        Intent intent = new Intent(this, CombateActivity.class);

        // Enviar datos a la siguiente actividad
        intent.putExtra("nombre1", etNombre1.getText().toString());
        intent.putExtra("hp1", Integer.parseInt(etHp1.getText().toString()));
        intent.putExtra("ataque1", Integer.parseInt(etAtaque1.getText().toString()));
        intent.putExtra("defensa1", Integer.parseInt(etDefensa1.getText().toString()));
        intent.putExtra("ataqueEsp1", Integer.parseInt(etAtaqueEsp1.getText().toString()));
        intent.putExtra("defensaEsp1", Integer.parseInt(etDefensaEsp1.getText().toString()));

        intent.putExtra("nombre2", etNombre2.getText().toString());
        intent.putExtra("hp2", Integer.parseInt(etHp2.getText().toString()));
        intent.putExtra("ataque2", Integer.parseInt(etAtaque2.getText().toString()));
        intent.putExtra("defensa2", Integer.parseInt(etDefensa2.getText().toString()));
        intent.putExtra("ataqueEsp2", Integer.parseInt(etAtaqueEsp2.getText().toString()));
        intent.putExtra("defensaEsp2", Integer.parseInt(etDefensaEsp2.getText().toString()));

        startActivity(intent);
    }
}
