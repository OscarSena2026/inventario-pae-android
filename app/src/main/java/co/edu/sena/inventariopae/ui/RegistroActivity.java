package co.edu.sena.inventariopae.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import co.edu.sena.inventariopae.R;
import co.edu.sena.inventariopae.databinding.ActivityRegistroBinding;
import co.edu.sena.inventariopae.util.Vistas;
import co.edu.sena.inventariopae.viewmodel.Estado;
import co.edu.sena.inventariopae.viewmodel.FabricaViewModel;
import co.edu.sena.inventariopae.viewmodel.RegistroViewModel;

/** Registro de un usuario nuevo (HU-01). */
public class RegistroActivity extends AppCompatActivity {
    private ActivityRegistroBinding binding;
    private RegistroViewModel vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Vistas.ajustarBordes(binding.getRoot());
        binding.toolbar.setNavigationOnClickListener(v -> finish());

        vm = new ViewModelProvider(this, FabricaViewModel.porDefecto()).get(RegistroViewModel.class);
        vm.getEstado().observe(this, this::mostrar);

        binding.btnRegistrar.setOnClickListener(v -> vm.registrar(
                Vistas.texto(binding.etUsuario),
                Vistas.texto(binding.etContrasena),
                Vistas.texto(binding.etConfirmar)));
    }

    private void mostrar(Estado<String> estado) {
        if (estado == null) return;
        boolean cargando = estado.getTipo() == Estado.Tipo.CARGANDO;
        binding.progreso.setVisibility(cargando ? View.VISIBLE : View.GONE);
        binding.btnRegistrar.setEnabled(!cargando);

        if (estado.getTipo() == Estado.Tipo.ERROR) {
            binding.tvError.setText(estado.getMensaje());
            binding.tvError.setVisibility(View.VISIBLE);
        } else {
            binding.tvError.setVisibility(View.GONE);
        }

        if (estado.getTipo() == Estado.Tipo.EXITO) {
            vm.consumirEstado();
            Toast.makeText(this, R.string.registro_ok, Toast.LENGTH_LONG).show();
            finish();
        }
    }
}
