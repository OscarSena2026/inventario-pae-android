package co.edu.sena.inventariopae.modelo;

/** Cuerpo JSON de POST /api/auth/login. */
public class CredencialesRequest {
    private final String usuario;
    private final String contrasena;

    public CredencialesRequest(String usuario, String contrasena) {
        this.usuario = usuario;
        this.contrasena = contrasena;
    }

    public String getUsuario() { return usuario; }
    public String getContrasena() { return contrasena; }
}
