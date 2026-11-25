package co.edu.sena.inventariopae.ui;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import co.edu.sena.inventariopae.databinding.ActivityAlertasBinding;
import co.edu.sena.inventariopae.util.Vistas;
import co.edu.sena.inventariopae.viewmodel.AlertasViewModel;
import co.edu.sena.inventariopae.viewmodel.Estado;
import co.edu.sena.inventariopae.viewmodel.FabricaViewModel;

import java.util.List;

/** Módulo Alertas: stock bajo el mínimo y lotes por vencer (HU-06). */
public class AlertasActivity extends AppCompatActivity {
    private ActivityAlertasBinding binding;
    private AlertasViewModel vm;
    private final TarjetaAdapter adaptadorStock = new TarjetaAdapter();
    private final TarjetaAdapter adaptadorVenc = new TarjetaAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAlertasBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Vistas.ajustarBordes(binding.getRoot());
        binding.toolbar.setNavigationOnClickListener(v -> finish());

        binding.listaStock.setLayoutManager(new LinearLayoutManager(this));
        binding.listaStock.setAdapter(adaptadorStock);
        binding.listaVenc.setLayoutManager(new LinearLayoutManager(this));
        binding.listaVenc.setAdapter(adaptadorVenc);

        vm = new ViewModelProvider(this, FabricaViewModel.porDefecto()).get(AlertasViewModel.class);
        vm.getStock().observe(this, estado -> {
            mostrar(estado, adaptadorStock, binding.tvVacioStock);
            actualizarProgreso();
        });
        vm.getVencimiento().observe(this, estado -> {
            mostrar(estado, adaptadorVenc, binding.tvVacioVenc);
            actualizarProgreso();
        });

        binding.btnActualizar.setOnClickListener(v -> vm.cargar());
    }

    @Override
    protected void onResume() {
        super.onResume();
        vm.cargar();
    }

    private void mostrar(Estado<List<ItemTarjeta>> estado, TarjetaAdapter adaptador, View vacio) {
        if (estado == null) return;
        if (estado.getTipo() == Estado.Tipo.EXITO) {
            adaptador.actualizar(estado.getDatos());
            vacio.setVisibility(estado.getDatos().isEmpty() ? View.VISIBLE : View.GONE);
            ocultarErrorSiNoHay();
        } else if (estado.getTipo() == Estado.Tipo.ERROR) {
            binding.tvError.setText(estado.getMensaje());
            binding.tvError.setVisibility(View.VISIBLE);
        }
    }

    private void ocultarErrorSiNoHay() {
        Estado<List<ItemTarjeta>> a = vm.getStock().getValue();
        Estado<List<ItemTarjeta>> b = vm.getVencimiento().getValue();
        boolean hayError = (a != null && a.getTipo() == Estado.Tipo.ERROR)
                || (b != null && b.getTipo() == Estado.Tipo.ERROR);
        if (!hayError) binding.tvError.setVisibility(View.GONE);
    }

    private void actualizarProgreso() {
        Estado<List<ItemTarjeta>> a = vm.getStock().getValue();
        Estado<List<ItemTarjeta>> b = vm.getVencimiento().getValue();
        boolean cargando = (a != null && a.getTipo() == Estado.Tipo.CARGANDO)
                || (b != null && b.getTipo() == Estado.Tipo.CARGANDO);
        binding.progreso.setVisibility(cargando ? View.VISIBLE : View.GONE);
        binding.btnActualizar.setEnabled(!cargando);
    }
}
