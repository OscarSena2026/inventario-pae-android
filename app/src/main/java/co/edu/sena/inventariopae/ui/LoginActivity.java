package co.edu.sena.inventariopae.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import co.edu.sena.inventariopae.MainActivity;
import co.edu.sena.inventariopae.databinding.ActivityLoginBinding;
import co.edu.sena.inventariopae.util.SesionLocal;
import co.edu.sena.inventariopae.util.Vistas;
import co.edu.sena.inventariopae.viewmodel.Estado;
import co.edu.sena.inventariopae.viewmodel.FabricaViewModel;
import co.edu.sena.inventariopae.viewmodel.LoginViewModel;

/** Pantalla de inicio: inicio de sesión (HU-01). */
public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private LoginViewModel vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (SesionLocal.haySesion(this)) {
            irAlMenu();
            return;
        }
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Vistas.ajustarBordes(binding.getRoot());

        vm = new ViewModelProvider(this, FabricaViewModel.porDefecto()).get(LoginViewModel.class);
        vm.getEstado().observe(this, this::mostrar);

        binding.btnIngresar.setOnClickListener(v ->
                vm.iniciarSesion(Vistas.texto(binding.etUsuario), Vistas.texto(binding.etContrasena)));
        binding.btnIrRegistro.setOnClickListener(v ->
                startActivity(new Intent(this, RegistroActivity.class)));
    }

    private void mostrar(Estado<String> estado) {
        if (estado == null) return;
        boolean cargando = estado.getTipo() == Estado.Tipo.CARGANDO;
        binding.progreso.setVisibility(cargando ? View.VISIBLE : View.GONE);
        binding.btnIngresar.setEnabled(!cargando);

        if (estado.getTipo() == Estado.Tipo.ERROR) {
            binding.tvError.setText(estado.getMensaje());
            binding.tvError.setVisibility(View.VISIBLE);
        } else {
            binding.tvError.setVisibility(View.GONE);
        }

        if (estado.getTipo() == Estado.Tipo.EXITO) {
            SesionLocal.guardar(this, estado.getDatos());
            vm.consumirEstado();
            irAlMenu();
        }
    }

    private void irAlMenu() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
