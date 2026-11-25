package co.edu.sena.inventariopae.util;

import android.view.View;
import android.widget.TextView;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

/** Ayudas de interfaz reutilizables por todas las pantallas. */
public final class Vistas {
    private Vistas() { }

    /** Deja el contenido fuera de la barra de estado, la de navegación y el teclado. */
    public static void ajustarBordes(View raiz) {
        ViewCompat.setOnApplyWindowInsetsListener(raiz, (v, insets) -> {
            Insets b = insets.getInsets(WindowInsetsCompat.Type.systemBars()
                    | WindowInsetsCompat.Type.displayCutout()
                    | WindowInsetsCompat.Type.ime());
            v.setPadding(b.left, b.top, b.right, b.bottom);
            return insets;
        });
    }

    public static String texto(TextView vista) {
        return vista.getText() == null ? "" : vista.getText().toString();
    }
}
