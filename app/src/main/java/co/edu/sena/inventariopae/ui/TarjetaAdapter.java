package co.edu.sena.inventariopae.ui;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import co.edu.sena.inventariopae.R;
import co.edu.sena.inventariopae.databinding.ItemTarjetaBinding;

import java.util.ArrayList;
import java.util.List;

/** Patrón Adapter: convierte una lista de ItemTarjeta en tarjetas dentro de un RecyclerView. */
public class TarjetaAdapter extends RecyclerView.Adapter<TarjetaAdapter.TarjetaVH> {
    private final List<ItemTarjeta> items = new ArrayList<>();

    public void actualizar(List<ItemTarjeta> nuevos) {
        items.clear();
        if (nuevos != null) items.addAll(nuevos);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TarjetaVH onCreateViewHolder(@NonNull ViewGroup padre, int tipo) {
        ItemTarjetaBinding b = ItemTarjetaBinding.inflate(LayoutInflater.from(padre.getContext()), padre, false);
        return new TarjetaVH(b);
    }

    @Override
    public void onBindViewHolder(@NonNull TarjetaVH holder, int posicion) {
        holder.enlazar(items.get(posicion));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class TarjetaVH extends RecyclerView.ViewHolder {
        private final ItemTarjetaBinding b;

        TarjetaVH(ItemTarjetaBinding b) {
            super(b.getRoot());
            this.b = b;
        }

        void enlazar(ItemTarjeta item) {
            b.tvTitulo.setText(item.getTitulo());
            b.tvSubtitulo.setText(item.getSubtitulo());
            b.tvDetalle.setText(item.getDetalle());
            int color = R.color.estado_normal;
            if (item.getNivel() == ItemTarjeta.Nivel.ADVERTENCIA) color = R.color.estado_advertencia;
            if (item.getNivel() == ItemTarjeta.Nivel.CRITICO) color = R.color.estado_critico;
            b.tvDetalle.setTextColor(ContextCompat.getColor(b.getRoot().getContext(), color));
        }
    }
}
