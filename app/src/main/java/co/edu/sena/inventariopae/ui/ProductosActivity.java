package co.edu.sena.inventariopae.ui;

import android.content.Intent;

import androidx.lifecycle.ViewModelProvider;

import co.edu.sena.inventariopae.R;
import co.edu.sena.inventariopae.viewmodel.FabricaViewModel;
import co.edu.sena.inventariopae.viewmodel.ListaViewModel;
import co.edu.sena.inventariopae.viewmodel.ProductosViewModel;

/** Módulo Productos: listado con acceso al formulario de registro (HU-02). */
public class ProductosActivity extends ListaBaseActivity {

    @Override
    protected int titulo() {
        return R.string.titulo_productos;
    }

    @Override
    protected ListaViewModel<?> crearViewModel() {
        return new ViewModelProvider(this, FabricaViewModel.porDefecto()).get(ProductosViewModel.class);
    }

    @Override
    protected boolean permiteCrear() {
        return true;
    }

    @Override
    protected void alCrear() {
        startActivity(new Intent(this, ProductoFormActivity.class));
    }
}
