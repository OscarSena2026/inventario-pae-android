package co.edu.sena.inventariopae.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import co.edu.sena.inventariopae.red.ClienteRetrofit;
import co.edu.sena.inventariopae.repositorio.InventarioRepositorio;
import co.edu.sena.inventariopae.repositorio.InventarioRepositorioRemoto;

/** Fábrica que entrega a cada ViewModel el repositorio que necesita (inyección manual). */
public class FabricaViewModel implements ViewModelProvider.Factory {
    private final InventarioRepositorio repositorio;

    public FabricaViewModel(InventarioRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public static FabricaViewModel porDefecto() {
        return new FabricaViewModel(new InventarioRepositorioRemoto(ClienteRetrofit.api()));
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> clase) {
        if (clase.isAssignableFrom(LoginViewModel.class)) return (T) new LoginViewModel(repositorio);
        if (clase.isAssignableFrom(RegistroViewModel.class)) return (T) new RegistroViewModel(repositorio);
        if (clase.isAssignableFrom(ExistenciasViewModel.class)) return (T) new ExistenciasViewModel(repositorio);
        if (clase.isAssignableFrom(ProductosViewModel.class)) return (T) new ProductosViewModel(repositorio);
        if (clase.isAssignableFrom(ProductoFormViewModel.class)) return (T) new ProductoFormViewModel(repositorio);
        if (clase.isAssignableFrom(AlertasViewModel.class)) return (T) new AlertasViewModel(repositorio);
        throw new IllegalArgumentException("ViewModel desconocido: " + clase.getName());
    }
}
