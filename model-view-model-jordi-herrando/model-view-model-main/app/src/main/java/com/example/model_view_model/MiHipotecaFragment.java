package com.example.model_view_model;




import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.model_view_model.databinding.FragmentMiHipotecaBinding;


public class MiHipotecaFragment extends Fragment {
    private FragmentMiHipotecaBinding binding;
    private MiHipotecaViewModel miHipotecaViewModel;
    private double capital;
    private int plazo;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentMiHipotecaBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        miHipotecaViewModel = new ViewModelProvider(this).get(MiHipotecaViewModel.class);

        binding.calcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean error = false;

                try {
                    capital = Double.parseDouble(binding.capital.getText().toString());
                } catch (Exception e){
                    binding.capital.setError("Introduzca un número");
                    error = true;
                }

                try {
                    plazo = Integer.parseInt(binding.plazo.getText().toString());
                } catch (Exception e){
                    binding.plazo.setError("Introduzca un número");
                    error = true;
                }

                if (!error) {
                    miHipotecaViewModel.calcular(capital, plazo);
                }
            }
        });

        miHipotecaViewModel.cuota.observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double cuota) {
                binding.cuota.setText(String.format("%.2f",cuota));
            }
        });

        miHipotecaViewModel.calculando.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean calculando) {
                if (calculando) {
                    binding.calculando.setVisibility(View.VISIBLE);
                    binding.cuota.setVisibility(View.GONE);
                } else {
                    binding.calculando.setVisibility(View.GONE);
                    binding.cuota.setVisibility(View.VISIBLE);
                }
            }
        });

        miHipotecaViewModel.errorCapital.observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double capitalMinimo) {
                if (capitalMinimo != null) {
                    binding.capital.setError("El capital no puede ser inferor a " + capitalMinimo);
                } else {
                    binding.capital.setError(null);
                }
            }
        });

        miHipotecaViewModel.errorPlazos.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer plazoMinimo) {
                if (plazoMinimo != null) {
                    binding.plazo.setError("El plazo no puede ser inferior a " + plazoMinimo + " años");
                } else {
                    binding.plazo.setError(null);
                }
            }
        });
    }
}