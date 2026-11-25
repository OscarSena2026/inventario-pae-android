package co.edu.sena.inventariopae;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import co.edu.sena.inventariopae.databinding.ActivityMainBinding;
import co.edu.sena.inventariopae.ui.AlertasActivity;
import co.edu.sena.inventariopae.ui.ExistenciasActivity;
import co.edu.sena.inventariopae.ui.LoginActivity;
import co.edu.sena.inventariopae.ui.ProductosActivity;
import co.edu.sena.inventariopae.util.SesionLocal;
import co.edu.sena.inventariopae.util.Vistas;

/** Menú principal: entrada a los módulos móviles (Inventario, Productos y Alertas). */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!SesionLocal.haySesion(this)) {
            irAlLogin();
            return;
        }
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Vistas.ajustarBordes(binding.getRoot());

        binding.tvBienvenida.setText(getString(R.string.menu_bienvenida, SesionLocal.usuario(this)));
        binding.cardInventario.setOnClickListener(v ->
                startActivity(new Intent(this, ExistenciasActivity.class)));
        binding.cardProductos.setOnClickListener(v ->
                startActivity(new Intent(this, ProductosActivity.class)));
        binding.cardAlertas.setOnClickListener(v ->
                startActivity(new Intent(this, AlertasActivity.class)));
        binding.btnCerrarSesion.setOnClickListener(v -> {
            SesionLocal.cerrar(this);
            irAlLogin();
        });
    }

    private void irAlLogin() {
        Intent intento = new Intent(this, LoginActivity.class);
        intento.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intento);
        finish();
    }
}
