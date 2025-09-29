import java,util.ArrayList;

public class Explorador extends Jugador{

    public Explorador(String nombre) {  //Lindo constructor
        super(nombre, 10, 2, new ArrayList<Item>());
    }

    @Override
    public String Mensaje() { //El mensaje personalizado por el explorador
        return nombre + " entra al combate como Explorador, listo para sobrevivir.";
    }

    @Override
    public int ataque() {
        return poder_ataque; // aquí vale 2 por el constructor
    }

    @Override
    protected boolean inventarioLleno() {
        return item != null && item.size() >= 4;
    } //Es la capacidad de items

    @Override
    public void turnoGuerrero() {} //Se queda vacío, lo vemos en Batalla



}