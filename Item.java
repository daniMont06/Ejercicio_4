
public class Item {
    private String nombre;      // "Curar", "Fuerza", "Veneno", "Super Veneno"
    private String tipo;        // "curar", "fuerza", "veneno", "super_veneno"
    private int ataque;         // 4 para veneno, 5 para super_veneno, 0 en curar/fuerza

    public Item(String nombre, String tipo){
        this.nombre = nombre;
        this.tipo = tipo;
        if ("veneno".equalsIgnoreCase(tipo)) {
            this.ataque = 4;
        } else if ("super_veneno".equalsIgnoreCase(tipo)) {
            this.ataque = 5;
        } else {
            this.ataque = 0;
        }
    }

    public void efecto(Jugador j, Enemigo enemigo){ //Son los items que escogí
        if (j == null) return;

        if ("curar".equalsIgnoreCase(tipo)) {
            j.sumarVida(2);
            return;
        }
        if ("fuerza".equalsIgnoreCase(tipo)) {
            j.setPoderAtaque(j.getPoderAtaque() + 4); 
            return;
        }
        if ("veneno".equalsIgnoreCase(tipo)) {
            if (enemigo != null && !enemigo.esBoss()) {
                enemigo.restar_vida(4);
            }
            return;
        }
        if ("super_veneno".equalsIgnoreCase(tipo)) {
            if (enemigo != null && enemigo.esBoss()) {
                enemigo.restar_vida(5);
            }
            return;
        }
    }

    public int puntos_efecto(){
        if ("curar".equalsIgnoreCase(tipo)) return 2;
        if ("fuerza".equalsIgnoreCase(tipo)) return 4;
        if ("veneno".equalsIgnoreCase(tipo)) return 4;
        if ("super_veneno".equalsIgnoreCase(tipo)) return 5;
        return 0;
    }

    public String getNombre(){ 
        return nombre; 
        }
    public String getTipo(){ 
        return tipo; 
        }
    public int getAtaque(){ 
        return ataque; 
        }
}
