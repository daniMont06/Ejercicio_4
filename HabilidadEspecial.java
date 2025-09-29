import java.util.Random;
public class HabilidadEspecial{
    private String nombre;
    private int ataque;        
    private boolean usada; //Este es un atributo que añadí para ver si el enemigo ya la usó o no   

    private Random rng = new Random();

    public HabilidadEspecial(String nombre, int ataque){
        this.nombre = nombre;
        this.ataque = ataque;
        this.usada = false;
    }

    public boolean activar(){
        if (usada) return false;
        return rng.nextBoolean(); 
    }

    public void uso(Enemigo enemigo){
        if (enemigo == null) return;
        if (usada) return;

        enemigo.subir_ataque(ataque);
        usada = true;
    }

    public void ataque(Jugador jugador, Enemigo enemigo){
        if (enemigo == null) return;
        if (usada) return;

        enemigo.subir_ataque(ataque);
        usada = true;
    }

    //Getters y Setters
    public String getNombre(){ 
        return nombre; 
        }
    public int getAtaque(){ 
        return ataque; 
        }
    public boolean yaFueUsada(){ 
        return usada; 
        }
}