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
        if(item == null){
            this.item = new ArrayList<>();
        } else {
            this.item = item;
        } //Por si acaso no tienen items se crea un nuevo ArrayList en donde se hacen todos estos cambios
    }

    public String Mensaje(){
        return nombre + "entra al combate y esta listo para la batalla"; //Como esta es la clase padre pues solamente puse un mensaje genérico
    }

    public int ataque(){
        return poder_ataque;
    }

    public String pasarTurno(){
        return nombre + " decidió pasar el turno."; //Puse este que solo dice si pasar el turno
    }
    //Este no hace nada solo dice que pasa el turno lo hice porque pense que estaría bonito

    public void turnoGuerrero(){
        // Aquí lo dejo vacio porque los hijos van a decidir
    }

    public void sumarVida(int cantidad){
        if (cantidad > 0) this.puntos_vida += cantidad;
    }

    public void restarVida(int cantidad){
        if (cantidad > 0) {
            this.puntos_vida -= cantidad;
            if (this.puntos_vida < 0) this.puntos_vida = 0;
        }
    } //Añadí estos dos porque no tenía cómo controlar lo de la vida

    public String opcion_menu_batalla(int opcion){ //Los item del menu de items
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

    public String escoger_item(Item nuevo){ //Repite lo de escoger el item
        if (nuevo == null) return "Ítem inválido.";
        if (inventarioLleno()) return "Inventario lleno. No se pudo agregar " + nuevo.getNombre() + ".";
        item.add(nuevo);
        return "Se agregó " + nuevo.getNombre() + " al inventario.";
    }

    protected boolean inventarioLleno(){
        return false;
        } //Pone límite al inventario, con el padre es falso porque es genérico pero se va a especificar con los hijos
    }

    public String uso_item(int index, Enemigo objetivo){ //Si puede usar el item del indice de la lista
        if (index < 0 || index >= item.size()){
            return nombre + " intentó usar un ítem inválido.";
        }

        Item i = item.get(index);
        String resultado;

        if (objetivo == null){
            // Autoconsumo (curar, fuerza). 
            resultado = i.efectoEnMi(this);
        } else {
            // Ofensivo (veneno, super_veneno). 
            resultado = i.efectoEnemigo(objetivo, this);
        }

        // Se consume SIEMPRE tras intentar usarlo, o sea que se va del ArrayList
        item.remove(index);
        return resultado;
    }

    //Getters y Setters
    public String getNombre(){
        return nombre;
    }

    public int getPuntosVida() { 
        return puntos_vida; 
        }
    public int getPoderAtaque() { 
        return poder_ataque; 
        }
    public ArrayList<Item> getItems() { 
        return item; 
        }
    public void setPuntosVida(int nuevosPuntosVida) {
        this.puntos_vida = nuevosPuntosVida;
    }

    public void setPoderAtaque(int nuevoPoderAtaque) {
        this.poder_ataque = nuevoPoderAtaque;
    }
