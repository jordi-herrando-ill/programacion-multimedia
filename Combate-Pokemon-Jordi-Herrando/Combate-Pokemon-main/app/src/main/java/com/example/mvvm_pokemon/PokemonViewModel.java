package com.example.mvvm_pokemon;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Random;

public class PokemonViewModel extends ViewModel {
    private MutableLiveData<Pokemon> pokemon1 = new MutableLiveData<>();
    private MutableLiveData<Pokemon> pokemon2 = new MutableLiveData<>();
    private MutableLiveData<String> mensaje = new MutableLiveData<>();
    private boolean turnoPokemon1 = true;

    public MutableLiveData<Pokemon> getPokemon1() {
        return pokemon1;
    }

    public MutableLiveData<Pokemon> getPokemon2() {
        return pokemon2;
    }

    public MutableLiveData<String> getMensaje() {
        return mensaje;
    }

    public void iniciarCombate(String nombre1, int hp1, int ataque1, int defensa1, int ataqueEsp1, int defensaEsp1,
                               String nombre2, int hp2, int ataque2, int defensa2, int ataqueEsp2, int defensaEsp2) {
        pokemon1.setValue(new Pokemon(nombre1, hp1, ataque1, defensa1, ataqueEsp1, defensaEsp1));
        pokemon2.setValue(new Pokemon(nombre2, hp2, ataque2, defensa2, ataqueEsp2, defensaEsp2));
        mensaje.setValue("¡Comienza el combate!");
    }

    public void realizarAtaque() {
        Pokemon atacante, atacado;

        if (turnoPokemon1) {
            atacante = pokemon1.getValue();
            atacado = pokemon2.getValue();
        } else {
            atacante = pokemon2.getValue();
            atacado = pokemon1.getValue();
        }

        // Verificar si el Pokémon atacado está debilitado
        if (atacado.getHp() <= 0) {
            mensaje.setValue(atacado.getNombre() + " está debilitado y no puede atacar.");
            return;
        }

        // Calcula el daño basado en una fórmula ficticia
        int dano = calcularDanio(atacante, atacado);

        // Aplica el daño al Pokémon atacado
        atacado.setHp(atacado.getHp() - dano);

        // Modifica las estadísticas con una probabilidad del 20%
        if (Math.random() < 0.2) {
            modificarEstadisticas(atacante);
        }

        if (Math.random() < 0.2) {
            modificarEstadisticas(atacado);
        }

        // Actualiza los Pokémon y el mensaje
        pokemon1.setValue(pokemon1.getValue());
        pokemon2.setValue(pokemon2.getValue());

        if (atacado.getHp() <= 0) {
            mensaje.setValue(atacado.getNombre() + " ha sido derrotado!");
        } else if (atacado.getHp() < 20) {
            mensaje.setValue("¡" + atacado.getNombre() + " está debilitado!");
        } else if (dano > 50) {
            mensaje.setValue("¡Ataque crítico! " + atacado.getNombre() + " ha recibido un gran daño.");
        } else if (dano > 20) {
            mensaje.setValue("¡" + atacado.getNombre() + " ha recibido un ataque fuerte!");
        } else if (atacado.getHp() < 50) {
            mensaje.setValue("¡" + atacado.getNombre() + " tiene poca vida!");
        } else {
            mensaje.setValue(atacante.getNombre() + " atacó a " + atacado.getNombre() + "!");
        }

        // Cambia el turno
        turnoPokemon1 = !turnoPokemon1;
    }

    public boolean isPokemon1Derrotado() {
        return pokemon1.getValue() != null && pokemon1.getValue().getHp() <= 0;
    }

    public boolean isPokemon2Derrotado() {
        return pokemon2.getValue() != null && pokemon2.getValue().getHp() <= 0;
    }

    private int calcularDanio(Pokemon atacante, Pokemon atacado) {
        // Fórmula ficticia para calcular el daño
        Random random = new Random();
        int factorAtaque = random.nextInt(10) + 1;
        int factorDefensa = random.nextInt(10) + 1;

        // Cálculo del daño físico
        int danioFisico = (atacante.getAtaque() * factorAtaque) - (atacado.getDefensa() / 2);

        // Cálculo del daño especial
        int danioEspecial = (atacante.getAtaqueEspecial() * factorAtaque) - (atacado.getDefensaEspecial() / 2);

        // Selecciona aleatoriamente el tipo de ataque (físico o especial)
        int tipoAtaque = random.nextInt(2);

        // Ajusta el daño según la defensa del Pokémon atacado
        int danioFinal = tipoAtaque == 0 ? Math.max(danioFisico, 0) : Math.max(danioEspecial, 0);

        // Asegúrate de que el daño final no sea negativo
        return Math.max(danioFinal, 0);
    }


    private void modificarEstadisticas(Pokemon pokemon) {
        // Modifica las estadísticas con una probabilidad del 20%
        Random random = new Random();

        if (Math.random() < 0.2) {
            int nuevoAtaque = pokemon.getAtaque() + random.nextInt(10) - 5;
            pokemon.setAtaque(Math.min(Math.max(nuevoAtaque, 0), 999));
        }

        if (Math.random() < 0.2) {
            int nuevaDefensa = pokemon.getDefensa() + random.nextInt(10) - 5;
            pokemon.setDefensa(Math.min(Math.max(nuevaDefensa, 0), 999));
        }

        if (Math.random() < 0.2) {
            int nuevoAtaqueEspecial = pokemon.getAtaqueEspecial() + random.nextInt(10) - 5;
            pokemon.setAtaqueEspecial(Math.min(Math.max(nuevoAtaqueEspecial, 0), 999));
        }

        if (Math.random() < 0.2) {
            int nuevaDefensaEspecial = pokemon.getDefensaEspecial() + random.nextInt(10) - 5;
            pokemon.setDefensaEspecial(Math.min(Math.max(nuevaDefensaEspecial, 0), 999));
        }
    }


}
