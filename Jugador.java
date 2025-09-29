import java.util.ArrayList;

public class Jugador {

    protected String nombre;
    protected int puntos_vida;
    protected int poder_ataque;
    protected ArrayList<Item> item;

    public Jugador(String nombre, int puntos_vida, int poder_ataque, ArrayList<Item> item){
        this.nombre = nombre;
        this.puntos_vida = puntos_vida;
        this.poder_ataque = poder_ataque;
        if (item == null){
            this.item = new ArrayList<>();
        } else {
            this.item = item;
        }
    }

    public String Mensaje(){
        return nombre + " entra al combate y está listo para la batalla";
    }

    public int ataque(){
        return poder_ataque;
    }

    public String pasarTurno(){
        return nombre + " decidió pasar el turno.";
    }

    public void turnoGuerrero(){
        // los hijos deciden
    }

    public void sumarVida(int cantidad){ //Estos los añadí, porque si no no tenía cómo hacer lo de la suma de puntos
        if (cantidad > 0) this.puntos_vida += cantidad;
    }

    public void restarVida(int cantidad){
        if (cantidad > 0) {
            this.puntos_vida -= cantidad;
            if (this.puntos_vida < 0) this.puntos_vida = 0;
        }
    } 

    public String opcion_menu_batalla(int opcion){
        Item nuevo;
        if (opcion == 1){
            nuevo = new Item("Curar", "curar");
        } else if (opcion == 2){
            nuevo = new Item("Fuerza", "fuerza");
        } else if (opcion == 3){
            nuevo = new Item("Veneno", "veneno");
        } else if (opcion == 4){
            nuevo = new Item("Super Veneno", "super_veneno");
        } else {
            return "Opción inválida.";
        }
        return escoger_item(nuevo);
    }

    public String escoger_item(Item nuevo){
        if (nuevo == null) return "Ítem inválido.";
        if (inventarioLleno()) return "Inventario lleno. No se pudo agregar " + nuevo.getNombre() + ".";
        item.add(nuevo);
        return "Se agregó " + nuevo.getNombre() + " al inventario.";
    }

    protected boolean inventarioLleno(){ //también lo añadí para ver lo del inventario de los personajes con los items
        return false;
    }

    public String uso_item(int index, Enemigo objetivo){
        if (index < 0 || index >= item.size()){
            return nombre + " intentó usar un ítem inválido.";
        }
        Item i = item.get(index);

        
        i.efecto(this, objetivo);

        // Se consume siempre
        item.remove(index);

        
        return nombre + " usó " + i.getNombre() + ".";
    }

    // Getters y Setters
    public String getNombre(){ 
        return nombre; 
        }
    public int getPuntosVida(){ 
        return puntos_vida; 
        }
    public int getPoderAtaque(){ 
        return poder_ataque; 
        }
    public ArrayList<Item> getItems(){ 
        return item; 
        }

    public void setPuntosVida(int nuevosPuntosVida){ 
        this.puntos_vida = nuevosPuntosVida; 
        }
    public void setPoderAtaque(int nuevoPoderAtaque){ 
    this.poder_ataque = nuevoPoderAtaque; 
    }
}
