import java.util.ArrayList; //Todo esto me costó mucho tiempo y ayuda pls sean lindos conmigo me costó mucho :D
import java.util.List;
import java.util.Scanner;

public class Main{
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Juego de Batalla! Tu y un amigo vs monstruos!\n");

        //  Nombres de jugadores
        System.out.print("Nombre del Guerrero: ");
        String nombreGuerrero = scanner.nextLine();
        System.out.print("Nombre del Explorador: ");
        String nombreExplorador = scanner.nextLine();

        Guerrero guerrero = new Guerrero(nombreGuerrero);
        Explorador explorador = new Explorador(nombreExplorador);

        //  Preparar batalla 
        Batalla batalla = new Batalla();
        boolean jugadoresValidos = batalla.validarJugadores(guerrero, explorador);
        if (!jugadoresValidos) {
            System.out.println("Error: faltan jugadores.");
            scanner.close();
            return;
        }

        batalla.definirEnemigos();

        // Intro especial con nombres de los tres enemigos para que sepan con quién pelean
        ArrayList<Enemigo> enemigosIntro = batalla.getEnemigos();
        String nombreE0 = enemigosIntro.get(0).getNombre();
        String nombreE1 = enemigosIntro.get(1).getNombre();
        String nombreE2 = enemigosIntro.get(2).getNombre();

        System.out.println("\n¡Entraron " + guerrero.getNombre() + " el guerrero y "
                + explorador.getNombre() + " el explorador! "
                + "¡Oh no! ¡Han llegado " + nombreE0 + ", " + nombreE1 + " y " + nombreE2 + " a pelear!");

        System.out.println(batalla.estadoPersonajes());

        //  Elegir ítems! Guerrero solo puede 2, Explorador solo 4
        System.out.println("\n" + guerrero.getNombre() + ", puedes elegir hasta 2 ítems.");
        System.out.println("Menú de ítems: 1) Curar  2) Fuerza  3) Veneno  4) Super Veneno");
        int[] itemsSeleccionadosGuerrero = new int[2];
        for (int i = 0; i < 2; i++) {
            System.out.print("Elige ítem #" + (i+1) + " (1-4) o 0 para no agregar más: ");
            int opcionItem = scanner.nextInt(); scanner.nextLine();
            if (opcionItem == 0) { itemsSeleccionadosGuerrero = java.util.Arrays.copyOf(itemsSeleccionadosGuerrero, i); break; }
            itemsSeleccionadosGuerrero[i] = opcionItem;
        }
        List<String> mensajesItemsGuerrero = batalla.elegirItemsBatalla(guerrero, itemsSeleccionadosGuerrero);
        for (int i=0; i<mensajesItemsGuerrero.size(); i++) System.out.println("  " + mensajesItemsGuerrero.get(i));

        System.out.println("\n" + explorador.getNombre() + ", puedes elegir hasta 4 ítems.");
        System.out.println("Menú de ítems: 1) Curar  2) Fuerza  3) Veneno  4) Super Veneno");
        int[] itemsSeleccionadosExplorador = new int[4];
        for (int i = 0; i < 4; i++) {
            System.out.print("Elige ítem #" + (i+1) + " (1-4) o 0 para no agregar más: ");
            int opcionItem = scanner.nextInt(); scanner.nextLine();
            if (opcionItem == 0) { itemsSeleccionadosExplorador = java.util.Arrays.copyOf(itemsSeleccionadosExplorador, i); break; }
            itemsSeleccionadosExplorador[i] = opcionItem;
        }
        List<String> mensajesItemsExplorador = batalla.elegirItemsBatalla(explorador, itemsSeleccionadosExplorador);
        for (int i=0; i<mensajesItemsExplorador.size(); i++) System.out.println("  " + mensajesItemsExplorador.get(i));

        //  Rondas, hasta que mueras jejeje
        int maxRondas = 10;
        for (int numeroDeRonda = 1; numeroDeRonda <= maxRondas; numeroDeRonda++) {
            System.out.println("\n--------- RONDA " + numeroDeRonda + " ---------");
            System.out.println(batalla.estadoPersonajes());

            // Mostrar enemigos con índice (0,1,2)
            System.out.println("Enemigos:");
            ArrayList<Enemigo> listaEnemigos = batalla.getEnemigos();
            for (int i=0; i<listaEnemigos.size(); i++) {
                Enemigo enemigo = listaEnemigos.get(i);
                System.out.println("  " + i + ": " + enemigo.getNombre() + " (PV=" + enemigo.getPuntos_vida() + ", Boss=" + enemigo.esBoss() + ")");
            }

            //  J1 (Guerrero) contra el primer enemigo
            System.out.println("\nTurno de " + guerrero.getNombre() + " (1=Atacar, 2=Usar ítem, 3=Pasar)");
            int opcionAccionA = scanner.nextInt(); scanner.nextLine();
            String accionGuerreroPasoA = (opcionAccionA==1) ? "ATACAR" : (opcionAccionA==2) ? "USAR_ITEM" : "PASAR";
            Integer indiceItemGuerreroPasoA = null;
            int indiceEnemigoPasoA = 0;
            if (accionGuerreroPasoA.equals("ATACAR")) {
                System.out.print("Elige enemigo (0-2): ");
                indiceEnemigoPasoA = scanner.nextInt(); scanner.nextLine();
                String nombreEnemigoA = (indiceEnemigoPasoA>=0 && indiceEnemigoPasoA<listaEnemigos.size())
                        ? listaEnemigos.get(indiceEnemigoPasoA).getNombre() : "enemigo";
                System.out.println("Atacaste a " + nombreEnemigoA + "! Ahora es turno del enemigo!");
            } else if (accionGuerreroPasoA.equals("USAR_ITEM")) {
                System.out.println("1) En mí (Curar/Fuerza)  2) Enemigo (Veneno/Super)");
                int objetivoItem = scanner.nextInt(); scanner.nextLine();
                System.out.print("Índice del ítem en tu inventario (0..): ");
                indiceItemGuerreroPasoA = scanner.nextInt(); scanner.nextLine();
                if (objetivoItem == 1) {
                    System.out.println("Usaste un ítem en ti. ¡Ahora es turno del enemigo!");
                } else {
                    System.out.print("Elige enemigo (0-2): ");
                    indiceEnemigoPasoA = scanner.nextInt(); scanner.nextLine();
                    String nombreEnemigoA = (indiceEnemigoPasoA>=0 && indiceEnemigoPasoA<listaEnemigos.size())
                            ? listaEnemigos.get(indiceEnemigoPasoA).getNombre() : "enemigo";
                    System.out.println("Usaste un ítem contra " + nombreEnemigoA + "! Ahora es turno del enemigo!");
                }
            } else {
                System.out.println("Decidiste pasar. ¡Ahora es turno del enemigo!");
            }

            //  J2 (Explorador) contra el segundo enemigo
            System.out.println("\nTurno de " + explorador.getNombre() + " (1=Atacar, 2=Usar ítem, 3=Pasar)");
            int opcionAccionB = scanner.nextInt(); scanner.nextLine();
            String accionExploradorPasoB = (opcionAccionB==1) ? "ATACAR" : (opcionAccionB==2) ? "USAR_ITEM" : "PASAR";
            Integer indiceItemExploradorPasoB = null;
            int indiceEnemigoPasoB = 1;
            if (accionExploradorPasoB.equals("ATACAR")) {
                System.out.print("Elige enemigo (0-2): ");
                indiceEnemigoPasoB = scanner.nextInt(); scanner.nextLine();
                String nombreEnemigoB = (indiceEnemigoPasoB>=0 && indiceEnemigoPasoB<listaEnemigos.size())
                        ? listaEnemigos.get(indiceEnemigoPasoB).getNombre() : "enemigo";
                System.out.println("Atacaste a " + nombreEnemigoB + "! Ahora es turno del enemigo!");
            } else if (accionExploradorPasoB.equals("USAR_ITEM")) {
                System.out.println("1) En mí (Curar/Fuerza)  2) Enemigo (Veneno/Super)");
                int objetivoItem = scanner.nextInt(); scanner.nextLine();
                System.out.print("Índice del ítem en tu inventario (0..): ");
                indiceItemExploradorPasoB = scanner.nextInt(); scanner.nextLine();
                if (objetivoItem == 1) {
                    System.out.println("Usaste un ítem en ti. ¡Ahora es turno del enemigo!");
                } else {
                    System.out.print("Elige enemigo (0-2): ");
                    indiceEnemigoPasoB = scanner.nextInt(); scanner.nextLine();
                    String nombreEnemigoB = (indiceEnemigoPasoB>=0 && indiceEnemigoPasoB<listaEnemigos.size())
                            ? listaEnemigos.get(indiceEnemigoPasoB).getNombre() : "enemigo";
                    System.out.println("Usaste un ítem contra " + nombreEnemigoB + "! Ahora es turno del enemigo!");
                }
            } else {
                System.out.println("Decidiste pasar. ¡Ahora es turno del enemigo!");
            }

            //  J1 (Guerrero) contra el tercer enemigo
            System.out.println("\nTurno de " + guerrero.getNombre() + " (1=Atacar, 2=Usar ítem, 3=Pasar)");
            int opcionAccionC = scanner.nextInt(); scanner.nextLine();
            String accionGuerreroPasoC = (opcionAccionC==1) ? "ATACAR" : (opcionAccionC==2) ? "USAR_ITEM" : "PASAR";
            Integer indiceItemGuerreroPasoC = null;
            int indiceEnemigoPasoC = 2;
            if (accionGuerreroPasoC.equals("ATACAR")) {
                System.out.print("Elige enemigo (0-2): ");
                indiceEnemigoPasoC = scanner.nextInt(); scanner.nextLine();
                String nombreEnemigoC = (indiceEnemigoPasoC>=0 && indiceEnemigoPasoC<listaEnemigos.size())
                        ? listaEnemigos.get(indiceEnemigoPasoC).getNombre() : "enemigo";
                System.out.println("Atacaste a " + nombreEnemigoC + "! Ahora es turno del enemigo!");
            } else if (accionGuerreroPasoC.equals("USAR_ITEM")) {
                System.out.println("1) En mí (Curar/Fuerza)  2) Enemigo (Veneno/Super)");
                int objetivoItem = scanner.nextInt(); scanner.nextLine();
                System.out.print("Índice del ítem en tu inventario (0..): ");
                indiceItemGuerreroPasoC = scanner.nextInt(); scanner.nextLine();
                if (objetivoItem == 1) {
                    System.out.println("Usaste un ítem en ti. ¡Ahora es turno del enemigo!");
                } else {
                    System.out.print("Elige enemigo (0-2): ");
                    indiceEnemigoPasoC = scanner.nextInt(); scanner.nextLine();
                    String nombreEnemigoC = (indiceEnemigoPasoC>=0 && indiceEnemigoPasoC<listaEnemigos.size())
                            ? listaEnemigos.get(indiceEnemigoPasoC).getNombre() : "enemigo";
                    System.out.println("Usaste un ítem contra " + nombreEnemigoC + "! Ahora es turno del enemigo!");
                }
            } else {
                System.out.println("Decidiste pasar. ¡Ahora es turno del enemigo!");
            }

            // Ejecutar ronda en orden, esto me costó mucho tiempo :D en realidad todo me costó jajaja
            List<String> mensajesDeRonda = batalla.ejecutarRondaSimple(
                    accionGuerreroPasoA, indiceItemGuerreroPasoA, indiceEnemigoPasoA,
                    accionExploradorPasoB, indiceItemExploradorPasoB, indiceEnemigoPasoB,
                    accionGuerreroPasoC, indiceItemGuerreroPasoC, indiceEnemigoPasoC
            );

            // Mostrar lo que pasó, porque resulta que es aburrido si no explico qué pasa jajaja
            for (int i=0; i<mensajesDeRonda.size(); i++) {
                System.out.println(mensajesDeRonda.get(i));
            }

            // Comprobar fin 
            boolean existenEnemigosVivos = false;
            for (int i=0; i<batalla.getEnemigos().size(); i++) {
                if (batalla.getEnemigos().get(i).getPuntos_vida() > 0) {
                    existenEnemigosVivos = true;
                }
            }
            boolean hayAlgunJugadorVivo = (guerrero.getPuntosVida() > 0) || (explorador.getPuntosVida() > 0);

            if (!existenEnemigosVivos) {
                System.out.println("¡Victoria! Todos los enemigos han caído.");
                batalla.limpiarInventarios();
                break;
            }
            if (!hayAlgunJugadorVivo) {
                System.out.println("Derrota… los jugadores han caído.");
                batalla.limpiarInventarios();
                break;
            }
        }

        scanner.close();
    }
}
