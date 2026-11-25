package co.edu.sena.inventariopae.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import co.edu.sena.inventariopae.modelo.AlertaStock;
import co.edu.sena.inventariopae.modelo.AlertaVencimiento;
import co.edu.sena.inventariopae.repositorio.InventarioRepositorio;
import co.edu.sena.inventariopae.repositorio.RespuestaCallback;
import co.edu.sena.inventariopae.ui.ItemTarjeta;
import co.edu.sena.inventariopae.util.Mapeadores;

import java.util.List;

/** Módulo Alertas: stock bajo el mínimo y lotes por vencer (HU-06). */
public class AlertasViewModel extends ViewModel {
    public static final int DIAS_VENCIMIENTO = 30;

    private final InventarioRepositorio repositorio;
    private final MutableLiveData<Estado<List<ItemTarjeta>>> stock = new MutableLiveData<>();
    private final MutableLiveData<Estado<List<ItemTarjeta>>> vencimiento = new MutableLiveData<>();

    public AlertasViewModel(InventarioRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public LiveData<Estado<List<ItemTarjeta>>> getStock() {
        return stock;
    }

    public LiveData<Estado<List<ItemTarjeta>>> getVencimiento() {
        return vencimiento;
    }

    public void cargar() {
        stock.setValue(Estado.<List<ItemTarjeta>>cargando());
        vencimiento.setValue(Estado.<List<ItemTarjeta>>cargando());

        repositorio.alertasStock(new RespuestaCallback<List<AlertaStock>>() {
            @Override
            public void onExito(List<AlertaStock> dato) {
                stock.setValue(Estado.exito(Mapeadores.deAlertasStock(dato)));
            }

            @Override
            public void onError(String mensaje) {
                stock.setValue(Estado.<List<ItemTarjeta>>error(mensaje));
            }
        });

        repositorio.alertasVencimiento(DIAS_VENCIMIENTO, new RespuestaCallback<List<AlertaVencimiento>>() {
            @Override
            public void onExito(List<AlertaVencimiento> dato) {
                vencimiento.setValue(Estado.exito(Mapeadores.deAlertasVencimiento(dato)));
            }

            @Override
            public void onError(String mensaje) {
                vencimiento.setValue(Estado.<List<ItemTarjeta>>error(mensaje));
            }
        });
    }
}
