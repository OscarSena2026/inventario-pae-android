package co.edu.sena.inventariopae.ui;

import androidx.lifecycle.ViewModelProvider;

import co.edu.sena.inventariopae.R;
import co.edu.sena.inventariopae.viewmodel.ExistenciasViewModel;
import co.edu.sena.inventariopae.viewmodel.FabricaViewModel;
import co.edu.sena.inventariopae.viewmodel.ListaViewModel;

/** Módulo Inventario: consulta de existencias (HU-05). */
public class ExistenciasActivity extends ListaBaseActivity {

    @Override
    protected int titulo() {
        return R.string.titulo_existencias;
    }

    @Override
    protected ListaViewModel<?> crearViewModel() {
        return new ViewModelProvider(this, FabricaViewModel.porDefecto()).get(ExistenciasViewModel.class);
    }
}
