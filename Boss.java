import java.util.Random;
public class Boss extends Enemigo{
    private final int vida_max = 15;
    private final Random rng = new Random();

    private boolean usadaTurnoExtra = false;
    private boolean puedeTurnoExtra = false; //Puse flags para las habilidades del Boss, porque son 2 que tengo jejeje

    private boolean usadaMuerteInstantanea = false;
    private boolean muerteInstantaneaPendiente = false; //Este es el segundo

    public Boss(String nombre) {
        
        super(nombre, 15, 5, null); //El ataque y la vida
    }

    @Override
    public boolean esBoss() { //Dice que sí es Boss jaja
        return true; 
    }

    @Override
    public String activar_Habilidad() { //Ver cuando se activa qué habilidad especial
        if (!usadaMuerteInstantanea) {
            muerteInstantaneaPendiente = true;
            usadaMuerteInstantanea = true;
            return nombre + " prepara un golpe letal…";
        }
        if (!usadaTurnoExtra) {
            puedeTurnoExtra = true;
            usadaTurnoExtra = true;
            return nombre + " obtiene un turno extra.";
        }
        return ""; // ya no hay habilidades por usar
    }
    
    @Override
    public String atacar(Jugador jugador) {
        if (jugador == null) return nombre + " no tiene objetivo.";

        if (muerteInstantaneaPendiente) {
            int pv = jugador.getPuntosVida();
            if (pv > 0) jugador.restarVida(pv); // mata al jugador
            muerteInstantaneaPendiente = false; // se consume
            return nombre + " ejecutó un golpe letal. " + jugador.getNombre() + " ha caído.";
        }

        // Ataque normal heredado 
        return super.atacar(jugador);
    }

    public boolean tieneTurnoExtra() { //Si tiene turno extra entonces el boss huega inmediatamente
        if (puedeTurnoExtra) {
            puedeTurnoExtra = false;
            return true;
        }
        return false;
    }

}