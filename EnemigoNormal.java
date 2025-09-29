public class EnemigoNormal extends Enemigo {
    private final int vida_max = 10; //La vida máxima del normal

    public EnemigoNormal(String nombre) { //COnstructor
        super(
            nombre,
            10,                            // puntos_vida
            5,                             // ataque
            new HabilidadEspecial("Curación total", 0) // ataque=0 solo para marcar usada
        );
    }

    @Override
    public String activar_Habilidad() {
        if (habilidad_especial == null) return nombre + " no tiene habilidad especial.";
        if (habilidad_especial.yaFueUsada()) return nombre + " ya usó su habilidad especial.";

        // Intento de activación aleatoria, que se active la habilidad en momentos aleatorios.
        if (habilidad_especial.activar()) {
            // Curarse al 100 casi que jajaja
            this.puntos_vida = vida_max;

            // Marcar la habilidad como usada
            habilidad_especial.uso(this);

            return nombre + " activó " + habilidad_especial.getNombre() + " y recuperó toda su vida.";
        }

        // No se activó en este turno
        return "";
    }
}