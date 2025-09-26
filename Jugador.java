import java.util.ArrayList;
public class Jugador{

    protected String nombre;
    protected int puntos_vida;
    protected int poder_ataque;
    protected ArrayList<Item> item;

    public Jugador(String nombre, int puntos_vida, int poder_ataque, ArrayList<Item> item){
        this.nombre = nombre;
        this.puntos_vida = puntos_vida;
        this.poder_ataque = poder_ataque;
        this.item = item; 
    }

    public String Mensaje(){
    }

    public void turnoGuerrero(){
        
    }
}