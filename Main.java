
import java.util.ArrayList; //Esto me costó mucho, por fis tengan piedad :DD
import java.util.List;
import java.util.Scanner;

public class Main{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);

        System.out.println(" Juego de Batalla (dos jugadores vs consola) ===\n");

        // Nombres
        System.out.print("Nombre del Guerrero: ");
        Guerrero guerrero = new Guerrero(sc.nextLine());
        System.out.print("Nombre del Explorador: ");
        Explorador explorador = new Explorador(sc.nextLine());

        // Preparar batalla
        Batalla batalla = new Batalla();
        if (!batalla.validarJugadores(guerrero, explorador)) {
            System.out.println("Error: faltan jugadores.");
            sc.close(); return;
        }
        batalla.definirEnemigos();
        ArrayList<Enemigo> enemigos = batalla.getEnemigos();
        System.out.println("\n¡Entraron " + guerrero.getNombre() + " el guerrero y "
                + explorador.getNombre() + " el explorador! ¡Oh no! ¡Han llegado "
                + enemigos.get(0).getNombre() + ", " + enemigos.get(1).getNombre()
                + " y " + enemigos.get(2).getNombre() + " a pelear!");
        System.out.println(batalla.estadoPersonajes());

        // Elegir ítems 
        System.out.println("\n" + guerrero.getNombre() + " elige hasta 2 ítems (1:Curar 2:Fuerza 3:Veneno 4:SuperVeneno, 0=fin):");
        int[] gItems = new int[2];
        for (int i=0;i<2;i++){ System.out.print("#"+(i+1)+": "); int op=sc.nextInt(); sc.nextLine(); if(op==0){gItems=java.util.Arrays.copyOf(gItems,i);break;} gItems[i]=op; }
        for(String s: batalla.elegirItemsBatalla(guerrero,gItems)) System.out.println("  "+s);

        System.out.println("\n" + explorador.getNombre() + " elige hasta 4 ítems (1:Curar 2:Fuerza 3:Veneno 4:SuperVeneno, 0=fin):");
        int[] eItems = new int[4];
        for (int i=0;i<4;i++){ System.out.print("#"+(i+1)+": "); int op=sc.nextInt(); sc.nextLine(); if(op==0){eItems=java.util.Arrays.copyOf(eItems,i);break;} eItems[i]=op; }
        for(String s: batalla.elegirItemsBatalla(explorador,eItems)) System.out.println("  "+s);

        //  Rondas 
        int maxRondas = 8;
        for (int ronda=1; ronda<=maxRondas; ronda++){
            System.out.println("\n--------- RONDA " + ronda + " ---------");
            System.out.println(batalla.estadoPersonajes());
            System.out.println("Enemigos:");
            for(int i=0;i<enemigos.size();i++){
                Enemigo en = enemigos.get(i);
                System.out.println("  " + i + ": " + en.getNombre() + " (PV=" + en.getPuntos_vida() + ", Boss=" + en.esBoss() + ")");
            }

            // secuencia a usar
            Jugador[] actores = new Jugador[]{guerrero, explorador, guerrero};
            int[] idxEnemigoPorPaso = new int[]{0,1,2};

            for (int paso=0; paso<3; paso++){
                Jugador actor = actores[paso];
                int idxDefault = idxEnemigoPorPaso[paso];

                // acción del jugador
                System.out.println("\nTurno de " + actor.getNombre() + " (1=Atacar, 2=Usar ítem, 3=Pasar)");
                int op = sc.nextInt(); sc.nextLine();
                String accion = (op==1)?"ATACAR":(op==2)?"USAR_ITEM":"PASAR";
                Integer idxItem = null;
                int idxEnemigo = idxDefault;
                Enemigo enemigoObjetivo = null;

                if ("ATACAR".equals(accion)) {
                    System.out.print("Elige enemigo (0-2): ");
                    idxEnemigo = sc.nextInt(); sc.nextLine();
                    if (idxEnemigo>=0 && idxEnemigo<enemigos.size()) enemigoObjetivo = enemigos.get(idxEnemigo);
                    System.out.println("Atacaste a " + (enemigoObjetivo!=null?enemigoObjetivo.getNombre():"enemigo") + "! Ahora es turno del enemigo!");
                    System.out.println(batalla.menuBatalla(actor, "ATACAR", null, enemigoObjetivo));
                } else if ("USAR_ITEM".equals(accion)) {
                    System.out.println("¿A quién? 1) A mí  2) A enemigo");
                    int tgt = sc.nextInt(); sc.nextLine();
                    System.out.print("Índice del ítem (0..): ");
                    idxItem = sc.nextInt(); sc.nextLine();
                    if (tgt==2){
                        System.out.print("Elige enemigo (0-2): ");
                        idxEnemigo = sc.nextInt(); sc.nextLine();
                        if (idxEnemigo>=0 && idxEnemigo<enemigos.size()) enemigoObjetivo = enemigos.get(idxEnemigo);
                        System.out.println("Usaste un ítem contra " + (enemigoObjetivo!=null?enemigoObjetivo.getNombre():"enemigo") + "! Ahora es turno del enemigo!");
                    } else {
                        System.out.println("Usaste un ítem en ti. ¡Ahora es turno del enemigo!");
                    }
                    System.out.println(batalla.menuBatalla(actor, "USAR_ITEM", idxItem, enemigoObjetivo));
                } else {
                    System.out.println("Decidiste pasar. ¡Ahora es turno del enemigo!");
                    System.out.println(batalla.menuBatalla(actor, "PASAR", null, null));
                }

                //  respuesta del enemigo 
                if (idxEnemigo>=0 && idxEnemigo<enemigos.size()){
                    Enemigo enemigoTurno = enemigos.get(idxEnemigo);
                    if (enemigoTurno.getPuntos_vida() > 0 && actor.getPuntosVida() > 0){
                        String h = batalla.activarHabilidad(enemigoTurno);
                        if (h!=null && !h.isEmpty()) System.out.println(h);
                        System.out.println(enemigoTurno.atacar(actor));
                        if (enemigoTurno instanceof Boss){
                            Boss b = (Boss) enemigoTurno;
                            if (b.tieneTurnoExtra() && actor.getPuntosVida() > 0){
                                String h2 = batalla.activarHabilidad(b);
                                if (h2!=null && !h2.isEmpty()) System.out.println(h2);
                                System.out.println(b.atacar(actor));
                            }
                        }
                    }
                }

                //  estado tras la respuesta del enemigo 
                System.out.println(batalla.estadoPersonajes());

                // fin temprano si alguien muere
                boolean enemigosVivos=false;
                for (Enemigo e: enemigos) if (e.getPuntos_vida()>0) enemigosVivos=true;
                boolean jugadoresVivos = guerrero.getPuntosVida()>0 || explorador.getPuntosVida()>0;
                if (!enemigosVivos){ System.out.println("¡Victoria!"); batalla.limpiarInventarios(); sc.close(); return; }
                if (!jugadoresVivos){ System.out.println("Derrota…"); batalla.limpiarInventarios(); sc.close(); return; }
            }
        }

        sc.close();
    }
}
