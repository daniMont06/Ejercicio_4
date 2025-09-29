import java.util.ArrayList;

public class Guerrero extends Jugador{
    public Guerrero(String nombre) { //Le cambie la vida, el ataque y pues el espacio de items.
        super(nombre, 15, 5, new ArrayList<Item>());
    }

    @Override
    public String Mensaje() {
        return nombre + " entra al combate como Guerrero, listo para ganar!";
    }

    @Override
    public int ataque() {
        return poder_ataque; // aquí vale 5 por el constructor
    }

    @Override
    protected boolean inventarioLleno() { //Solo 2 items
        return item != null && item.size() >= 2;
    }

    @Override
    public void turnoGuerrero() {}
}