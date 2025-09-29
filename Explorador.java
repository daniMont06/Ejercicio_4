import java.util.ArrayList;

public class Explorador extends Jugador {

    public Explorador(String nombre){
        super(nombre, 10, 2, new ArrayList<Item>());
    }

    @Override
    public String Mensaje() {
        return nombre + " entra al combate como Explorador, listo para sobrevivir.";
    }

    @Override
    public int ataque() {
        return poder_ataque;
    }

    @Override
    protected boolean inventarioLleno() {
        return item != null && item.size() >= 4;
    }

    @Override
    public void turnoGuerrero() { }
}
