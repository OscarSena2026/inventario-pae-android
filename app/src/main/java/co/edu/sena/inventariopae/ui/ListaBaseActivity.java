package co.edu.sena.inventariopae.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import co.edu.sena.inventariopae.R;
import co.edu.sena.inventariopae.databinding.ActivityListaBinding;
import co.edu.sena.inventariopae.util.Vistas;
import co.edu.sena.inventariopae.viewmodel.Estado;
import co.edu.sena.inventariopae.viewmodel.ListaViewModel;

import java.util.ArrayList;
import java.util.List;

/** Pantalla de lista reutilizable (buscador, actualizar, vacío/error). Las hijas solo la configuran. */
public abstract class ListaBaseActivity extends AppCompatActivity {
    protected ActivityListaBinding binding;
    private ListaViewModel<?> vm;
    private final TarjetaAdapter adaptador = new TarjetaAdapter();

    protected abstract int titulo();

    protected abstract ListaViewModel<?> crearViewModel();

    protected boolean permiteCrear() {
        return false;
    }

    protected void alCrear() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Vistas.ajustarBordes(binding.getRoot());

        binding.toolbar.setTitle(titulo());
        binding.toolbar.setNavigationOnClickListener(v -> finish());
        binding.lista.setLayoutManager(new LinearLayoutManager(this));
        binding.lista.setAdapter(adaptador);

        vm = crearViewModel();
        vm.getEstado().observe(this, this::mostrar);

        binding.btnActualizar.setOnClickListener(v -> vm.cargar());
        binding.etBuscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                vm.filtrar(s.toString());
            }
        });

        binding.fabNuevo.setVisibility(permiteCrear() ? View.VISIBLE : View.GONE);
        binding.fabNuevo.setOnClickListener(v -> alCrear());
    }

    @Override
    protected void onResume() {
        super.onResume();
        vm.cargar();
    }

    private void mostrar(Estado<List<ItemTarjeta>> estado) {
        if (estado == null) return;
        boolean cargando = estado.getTipo() == Estado.Tipo.CARGANDO;
        binding.progreso.setVisibility(cargando ? View.VISIBLE : View.GONE);
        binding.btnActualizar.setEnabled(!cargando);

        if (estado.getTipo() == Estado.Tipo.EXITO) {
            List<ItemTarjeta> items = estado.getDatos();
            adaptador.actualizar(items);
            binding.tvVacio.setText(R.string.lista_vacia);
            binding.tvVacio.setVisibility(items.isEmpty() ? View.VISIBLE : View.GONE);
        } else if (estado.getTipo() == Estado.Tipo.ERROR) {
            adaptador.actualizar(new ArrayList<ItemTarjeta>());
            binding.tvVacio.setText(estado.getMensaje());
            binding.tvVacio.setVisibility(View.VISIBLE);
        }
    }
}
