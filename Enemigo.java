public class Enemigo{
    protected String nombre;
    protected HabilidadEspecial habilidad_especial;
    protected int puntos_vida;
    protected int ataque;

    public Enemigo(String nombre, int puntos_vida, int ataque, HabilidadEspecial habilidad_especial){
        this.nombre = nombre;
        this.puntos_vida = puntos_vida;
        this.ataque = ataque;
        this.habilidad_especial = habilidad_especial; 
    }

    public String activar_Habilidad(){
        if (habilidad_especial == null) return "";
        if (habilidad_especial.yaFueUsada()) return "";

        if (habilidad_especial.activar()){
            habilidad_especial.uso(this);
            return nombre + " activó " + habilidad_especial.getNombre() +
                " (+" + habilidad_especial.getAtaque() + " ATK).";
        }
        return ""; // sin mensaje cuando no se activa
    }

    public String atacar(Jugador jugador){ //Sé que se ve raro porque no tiene atributo jugador pero eso lo vemos en la clase batalla jajaja
        if (jugador == null) return nombre + " no tiene objetivo."; //Confíen en el proceso
        if (ataque <= 0) return nombre + " intentó atacar pero no hizo daño.";

        jugador.restarVida(ataque);
        return nombre + " atacó a " + jugador.getNombre() + " e infligió " + ataque + " de daño.";
    }

    public String morir(){
        if (puntos_vida <= 0){
            return nombre + " ha muerto.";
        }
        return "";
    }

    public void sumar_vida(int cantidad){
        if (cantidad > 0) this.puntos_vida += cantidad;
    }

    public void restar_vida(int cantidad){
        if (cantidad > 0){
            this.puntos_vida -= cantidad;
            if (this.puntos_vida < 0) this.puntos_vida = 0;
        }
    }

    public void subir_ataque(int cantidad){
        if (cantidad > 0) this.ataque += cantidad;
    }

    public boolean esBoss(){  //No comienza en boss, pero lo voy a sobre escribir
    return false; 
}

    public String getNombre(){ 
        return nombre; 
        }
    public int getPuntos_vida(){ r
    eturn puntos_vida; 
    }
    public int getAtaque(){ 
        return ataque; 
        }
    public HabilidadEspecial getHabilidad(){ 
        return habilidad_especial; 
        }

}