package co.edu.sena.inventariopae.modelo;

/** Cuerpo JSON de POST /api/auth/registro. */
public class RegistroRequest {
    private final String usuario;
    private final String contrasena;
    private final String rol;

    public RegistroRequest(String usuario, String contrasena) {
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.rol = "USUARIO"; // la app móvil nunca crea administradores
    }

    public String getUsuario() { return usuario; }
    public String getContrasena() { return contrasena; }
    public String getRol() { return rol; }
}
