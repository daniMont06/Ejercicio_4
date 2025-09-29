import java.util.ArrayList; //Esta clase tiene los métodos del diseño, pero les añadí más porque tengo que hacer varias validaciones
import java.util.List; //No quite nada del diseño, pero añadí varios métodos, tengan piedad por favor :D
import java.util.Random;

public class Batalla {
    private Guerrero guerrero;
    private Explorador explorador;

    // 3 enemigos por ronda 
    private final ArrayList<Enemigo> enemigos = new ArrayList<>();

    // Guardamos solo los últimos 3 mensajes
    private final ArrayList<String> historial = new ArrayList<>();

    private final Random rng = new Random(); //es el random para que generen a los enemigos de manera aleatoria

    //  Validar que haya 1 Guerrero y 1 Explorador por lo menos
    public boolean validarJugadores(Guerrero g, Explorador e) { //Este lo añadí porque no tenía una validación
        if (g == null || e == null) return false;
        this.guerrero = g;
        this.explorador = e;
        return true;
    }

    //  Crear exactamente 3 enemigos, tipos aleatorios, así es divertido
    public void definirEnemigos() {
        enemigos.clear();
        String[] nombresBoss = {"Boss A", "Boss B", "Boss C", "Boss D"};
        String[] nombresNorm = {"Goblin", "Orco", "Bandido", "Lobo"};

        for (int i = 0; i < 3; i++) {
            boolean tipoBoss = rng.nextBoolean();
            if (tipoBoss) {
                enemigos.add(new Boss(nombresBoss[rng.nextInt(nombresBoss.length)]));
            } else {
                enemigos.add(new EnemigoNormal(nombresNorm[rng.nextInt(nombresNorm.length)]));
            }
        }
    }

    //  Cargar ítems antes de pelear  
    public List<String> elegirItemsBatalla(Jugador jugador, int[] opciones) {
        ArrayList<String> mensajes = new ArrayList<>();
        if (jugador == null || opciones == null) return mensajes;

        for (int op : opciones) {
            String r = jugador.opcion_menu_batalla(op);
            mensajes.add(r);
            guardarHistorial(r);
        }
        return mensajes;
    }

    // Vaciar inventarios cuando termine la batalla
    public void limpiarInventarios() {
        if (guerrero != null && guerrero.getItems() != null) guerrero.getItems().clear();
        if (explorador != null && explorador.getItems() != null) explorador.getItems().clear();
    }

    //  Menú por turno del jugador, pregunta si quiere atacar tomar un item o pasar el turno
    public String menuBatalla(Jugador jugador, String accion, Integer indiceItem, Enemigo enemigoObjetivo) {
        if (jugador == null || accion == null) return "";
        String accionMayus = accion.trim().toUpperCase(); //Añadí este método para que escojan la acción el personaje
        String mensaje;

        if (accionMayus.equals("ATACAR")) {
            if (enemigoObjetivo == null) {
                mensaje = jugador.getNombre() + " no tiene enemigo objetivo.";
            } else {
                int dano = jugador.ataque();
                enemigoObjetivo.restar_vida(dano);
                mensaje = jugador.getNombre() + " atacó a " + enemigoObjetivo.getNombre()
                        + " e infligió " + dano + " de daño.";
            }
        } else if (accionMayus.equals("USAR_ITEM")) {
            if (indiceItem == null) {
                mensaje = jugador.getNombre() + " intentó usar un ítem sin indicar cuál.";
            } else {
                mensaje = jugador.uso_item(indiceItem, enemigoObjetivo); // consume el ítem
            }
        } else { // pasar o algo más
            mensaje = jugador.pasarTurno();
        }

        guardarHistorial(mensaje);
        return mensaje;
    }

    // Ejecuta ronda en orden
    // Pasa la acción de cada paso y el índice del enemigo objetivo (0,1,2).
    public List<String> ejecutarRondaSimple(
            String accionJ1_A, Integer itemJ1_A, int idxE1,
            String accionJ2_B, Integer itemJ2_B, int idxE2,
            String accionJ1_C, Integer itemJ1_C, int idxE3) {

        ArrayList<String> mensajes = new ArrayList<>();

        if (!estadoListo()) {
            String s = "Batalla no preparada (faltan jugadores o enemigos).";
            mensajes.add(s); guardarHistorial(s);
            return mensajes;
        }

        Enemigo e1 = enemigoVivo(idxE1);
        Enemigo e2 = enemigoVivo(idxE2);
        Enemigo e3 = enemigoVivo(idxE3);

        // J1 a E1
        String a1 = menuBatalla(guerrero, accionJ1_A, itemJ1_A, e1);
        mensajes.add(estadoTurno(guerrero.getNombre(), a1));
        if (termino(mensajes)) return mensajes;
        if (e1 != null) {
            mensajes.addAll(turnoEnemigo(e1, guerrero));
            if (termino(mensajes)) return mensajes;
        }

        // J2 a E2
        String a2 = menuBatalla(explorador, accionJ2_B, itemJ2_B, e2);
        mensajes.add(estadoTurno(explorador.getNombre(), a2));
        if (termino(mensajes)) return mensajes;
        if (e2 != null) {
            mensajes.addAll(turnoEnemigo(e2, explorador));
            if (termino(mensajes)) return mensajes;
        }

        // J1 a E3
        String a3 = menuBatalla(guerrero, accionJ1_C, itemJ1_C, e3);
        mensajes.add(estadoTurno(guerrero.getNombre(), a3));
        if (termino(mensajes)) return mensajes;
        if (e3 != null) {
            mensajes.addAll(turnoEnemigo(e3, guerrero));
        }

        return mensajes;
    }

    //  Estado de turno y de personajes 
    public String estadoTurno(String quien, String quePaso) {
        String s = "[Turno de " + quien + "] " + quePaso;
        guardarHistorial(s);
        return s;
    }

    public String estadoPersonajes() { //El estado de los personajes
    String s = "[Estado] ";

    if (guerrero != null) {
        s = s + "Guerrero(Puntos de vida =" + guerrero.getPuntosVida()
            + ", Ataque =" + guerrero.getPoderAtaque() + ") ";
    }
    if (explorador != null) {
        s = s + "Explorador(Puntos de vida=" + explorador.getPuntosVida()
            + ", Ataque =" + explorador.getPoderAtaque() + ") ";
    }

    s = s + "| Enemigos: ";
    for (int i = 0; i < enemigos.size(); i++) {
        Enemigo en = enemigos.get(i);
        s = s + "#" + i + ":" + en.getNombre()
            + "(PV=" + en.getPuntos_vida()
            + ", Boss=" + en.esBoss() + ") ";
    }

    guardarHistorial(s);
    return s;
}

    //  este es el método que puse como especial() en el diseño pero lo cambie a activarHabilidad con los enemigos, así es más específico
    public String activarHabilidad(Enemigo e) {
        if (e == null) return "";
        String m = e.activar_Habilidad();   // cada enemigo define qué hace
        guardarHistorial(m);
        return m;
    }

    
    public ArrayList<Enemigo> getEnemigos() { 
        return enemigos; 
        }
    public Guerrero getGuerrero() { 
        return guerrero; 
        }
    public Explorador getExplorador() { 
        return explorador; 
        }
    public List<String> getHistorial() { 
        return new ArrayList<>(historial); 
    }

    
    private void guardarHistorial(String s) { //Guarda el historial de las rondas
    if (s == null || s.isEmpty()) return;
    historial.add(s);

    // Dejar solo los últimos 3 mensajes
    if (historial.size() > 3) {
        int quitar = historial.size() - 3;
        for (int i = 0; i < quitar; i++) {
            historial.remove(0);
            }
        }
    }

    private boolean estadoListo() { //Que el juego este listo
        return guerrero != null && explorador != null && enemigos.size() == 3;
    }

    private Enemigo enemigoVivo(int idx) { //Mira si se mueren los enemigos, así se acaba el juego o se va el enemigo
        if (idx < 0 || idx >= enemigos.size()) return null;
        Enemigo e = enemigos.get(idx);
        if (e.getPuntos_vida() <= 0) return null;
        return e;
    }

    private boolean todosEnemigosMuertos() { //Les quita los puntos de vida si se mueren y se acaba el juego
        for (Enemigo e : enemigos) {
            if (e.getPuntos_vida() > 0) return false;
        }
        return true;
    }

    private boolean hayJugadoresVivos() { //Valida si todavía hay jugadores vivos para seguir con la ronda
        boolean gVivo = guerrero != null && guerrero.getPuntosVida() > 0;
        boolean eVivo = explorador != null && explorador.getPuntosVida() > 0;
        return gVivo || eVivo; // sigue si al menos uno vive
    }

    private boolean termino(ArrayList<String> mensajes) { //Es el final, dice si ganamos nosotros o perdimos
        if (todosEnemigosMuertos()) {
            String s = "¡Victoria! Todos los enemigos han caído.";
            mensajes.add(s); guardarHistorial(s);
            limpiarInventarios();
            return true;
        }
        if (!hayJugadoresVivos()) {
            String s = "Noooooo los jugadores han caído.";
            mensajes.add(s); guardarHistorial(s);
            limpiarInventarios();
            return true;
        }
        return false;
    }

    // Turno del enemigo contra un jugador concreto 
    private List<String> turnoEnemigo(Enemigo e, Jugador j) {
        ArrayList<String> ms = new ArrayList<>();
        if (e == null || e.getPuntos_vida() <= 0) {
            String s = "El enemigo no puede actuar.";
            ms.add(s); guardarHistorial(s);
            return ms;
        }

        String h = activarHabilidad(e);
        if (h != null && !h.isEmpty()) {
            ms.add(estadoTurno(e.getNombre(), h));
        }

        String a = e.atacar(j);
        ms.add(estadoTurno(e.getNombre(), a));

        // Si es Boss y tiene turno extra, que pase de una vez
        if (e instanceof Boss) {
            Boss b = (Boss) e;
            if (b.tieneTurnoExtra() && j != null && j.getPuntosVida() > 0) {
                String h2 = activarHabilidad(b);
                if (h2 != null && !h2.isEmpty()) {
                    ms.add(estadoTurno(b.getNombre(), h2));
                }
                String a2 = b.atacar(j);
                ms.add(estadoTurno(b.getNombre(), a2));
            }
        }
        return ms;
    }
}
