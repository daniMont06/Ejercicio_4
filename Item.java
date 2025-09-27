public class Item{
    private String nommbre;
    private String tipo;
    private int ataque;

    public Item(String nombre, String tipo){
        this.nombre = nombre;
        this.tipo = tipo;
        if ("veneno".equalsIgnoreCase(tipo)) { //es que tengo dos efectos llamados parecidos pero tienen diferente impacto
            this.ataque = 4;
        } else if ("super_veneno".equalsIgnoreCase(tipo)) {
            this.ataque = 5;
        } else {
            this.ataque = 0; 
        }
    }

    public void efecto(Jugador j, Enemigo enemigo){ //Aquí están mis tipos
        if (j == null) return;

        if ("curar".equalsIgnoreCase(tipo)) {
            j.sumarVida(2);
            return;
        }

        if ("fuerza".equalsIgnoreCase(tipo)) {
            j.subirAtaque(4);
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

    public int puntos_efecto(){ //Los puntos que genera cada efecto
        if ("curar".equalsIgnoreCase(tipo)) return 2;
        if ("fuerza".equalsIgnoreCase(tipo)) return 4;
        if ("veneno".equalsIgnoreCase(tipo)) return 4;
        if ("super_veneno".equalsIgnoreCase(tipo)) return 5;
        return 0;
    }

    //Mis getters y setters
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