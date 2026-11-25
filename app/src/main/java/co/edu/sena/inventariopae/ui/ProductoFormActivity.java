package co.edu.sena.inventariopae.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import co.edu.sena.inventariopae.R;
import co.edu.sena.inventariopae.databinding.ActivityProductoFormBinding;
import co.edu.sena.inventariopae.modelo.Producto;
import co.edu.sena.inventariopae.util.Vistas;
import co.edu.sena.inventariopae.viewmodel.Estado;
import co.edu.sena.inventariopae.viewmodel.FabricaViewModel;
import co.edu.sena.inventariopae.viewmodel.ProductoFormViewModel;

/** Formulario para registrar un producto (HU-02). */
public class ProductoFormActivity extends AppCompatActivity {
    private ActivityProductoFormBinding binding;
    private ProductoFormViewModel vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductoFormBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Vistas.ajustarBordes(binding.getRoot());
        binding.toolbar.setNavigationOnClickListener(v -> finish());

        vm = new ViewModelProvider(this, FabricaViewModel.porDefecto()).get(ProductoFormViewModel.class);
        vm.getEstado().observe(this, this::mostrar);

        binding.btnGuardar.setOnClickListener(v -> vm.guardar(
                Vistas.texto(binding.etNombre),
                Vistas.texto(binding.etCategoria),
                Vistas.texto(binding.etUnidad),
                binding.cbPerecedero.isChecked(),
                Vistas.texto(binding.etStockMinimo)));
    }

    private void mostrar(Estado<Producto> estado) {
        if (estado == null) return;
        boolean cargando = estado.getTipo() == Estado.Tipo.CARGANDO;
        binding.progreso.setVisibility(cargando ? View.VISIBLE : View.GONE);
        binding.btnGuardar.setEnabled(!cargando);

        if (estado.getTipo() == Estado.Tipo.ERROR) {
            binding.tvError.setText(estado.getMensaje());
            binding.tvError.setVisibility(View.VISIBLE);
        } else {
            binding.tvError.setVisibility(View.GONE);
        }

        if (estado.getTipo() == Estado.Tipo.EXITO) {
            vm.consumirEstado();
            Toast.makeText(this, R.string.producto_guardado, Toast.LENGTH_LONG).show();
            finish();
        }
    }
}
