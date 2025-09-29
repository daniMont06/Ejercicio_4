import java.util.ArrayList;

public class Guerrero extends Jugador {

    public Guerrero(String nombre){
        super(nombre, 15, 5, new ArrayList<Item>());
    }

    @Override
    public String Mensaje() {
        return nombre + " entra al combate como Guerrero, listo para ganar!";
    }

    @Override
    public int ataque() {
        return poder_ataque;
    }

    @Override
    protected boolean inventarioLleno() {
        return item != null && item.size() >= 2;
    }

    @Override
    public void turnoGuerrero() { }
}
