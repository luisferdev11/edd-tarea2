import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class Laberinto {
    private class Casilla{
        // Si es verdadero significa que la casilla tiene una parde en esa direccion
        private boolean izquierda, abajo, recorrida;
        private int x, y;
        // private ArrayList<Casilla> vecinosVisitados;

        private Casilla(int x, int y){
            izquierda = true;
            abajo = true;
            recorrida = false;
            this.x = x;
            this.y = y;
            // vecinosVisitados = new ArrayList<>();
        }
    }

    private enum Direccion{
        IZQ,
        DER,
        ARR,
        ABJ;

        @Override
        public String toString(){
            return
                this == IZQ ? "Izquierda" :
                this == DER ? "Derecha" :
                this == ARR ? "Arriba" :
                this == ABJ ? "Abajo" :
                "";
        }
    }

    private Casilla[][] laberinto;
    private int x, y;
    private static final Random random = new Random();

    private Laberinto(){}

    public Laberinto(int x, int y){
        this.x = x;
        this.y = y;
        laberinto = new Casilla[y][x];
        for(int i = 0; i < y; i++){
            for(int j = 0; j < x; j++){
                laberinto[i][j] = new Casilla(j, i);
            }
        }
    }

    private Direccion obtenerVecino(Casilla casilla, ArrayList<Casilla> visitados){
        ArrayList<Direccion> opciones = new ArrayList<>();
        try{
            Casilla aux = laberinto[casilla.y - 1][casilla.x];
            if(!visitados.contains(aux))
                opciones.add(Direccion.ARR);
        }catch(Exception e){}
        try{
            Casilla aux = laberinto[casilla.y + 1][casilla.x];
            if(!visitados.contains(aux))
                opciones.add(Direccion.ABJ);
        }catch(Exception e){}
        try{
            Casilla aux = laberinto[casilla.y][casilla.x - 1];
            if(!visitados.contains(aux))
                opciones.add(Direccion.IZQ);
        }catch(Exception e){}
        try{
            Casilla aux = laberinto[casilla.y][casilla.x + 1];
            if(!visitados.contains(aux))
                opciones.add(Direccion.DER);
        }catch(Exception e){}

        if(opciones.size() == 0) return null;
        Direccion seleccionada = opciones.get(random.nextInt(opciones.size()));
        visitados.add(getRelative(casilla, seleccionada));
        return seleccionada;
    }

    private String underline(String string){
        return "\033[4m" + string + "\033[0m";
    }

    private Casilla getRelative(Casilla casilla, Direccion dir){
        return
            dir == Direccion.ARR ? laberinto[casilla.y - 1][casilla.x] :
            dir == Direccion.ABJ ? laberinto[casilla.y + 1][casilla.x] :
            dir == Direccion.IZQ ? laberinto[casilla.y][casilla.x - 1] :
            dir == Direccion.DER ? laberinto[casilla.y][casilla.x + 1] :
            null;
    }

    @Override
    public String toString(){
        String salida = " ";
        for(int i = 0; i < x; i++){
            salida += underline(" ") + " ";
        }
        salida += "\n";
        for(int i = 0; i < y; i++){
            for(int j = 0; j < x; j++){
                salida += laberinto[i][j].izquierda ? "|" : " ";
                String aux = laberinto[i][j].recorrida ? "X" : " ";
                salida += laberinto[i][j].abajo ? underline(aux) : aux;
            }
            salida += "| \n";
        }
        return salida;
    }

    public void generarLaberinto(){
        resetLaberinto();
        Stack<Casilla> pila = new Stack<>();
        ArrayList<Casilla> visitados = new ArrayList<>();
        int actualX = 0;
        int actualY = 0;
        // deberiamos añadir la actual a las vistaiadas??
        Casilla actual = laberinto[actualY][actualX];
        pila.push(actual);
        while(!pila.empty()){
            Direccion dirSiguiente = obtenerVecino(actual, visitados);
            if(dirSiguiente == null){
                actual = pila.pop();
                continue;
            }
            // System.out.println(dirSiguiente.toString());
            Casilla casillaSiguiente = getRelative(actual, dirSiguiente);
            pila.push(casillaSiguiente);
            borrarPared(actual, casillaSiguiente, dirSiguiente);
            actual = pila.peek();
        }
    }

    private void borrarPared(Casilla padre, Casilla hija, Direccion dir){
        if(dir == Direccion.ARR){
            hija.abajo = false;
        }else if(dir == Direccion.ABJ){
            padre.abajo = false;
        }else if(dir == Direccion.IZQ){
            padre.izquierda = false;
        }else if(dir == Direccion.DER){
            hija.izquierda = false;
        }
    }

    public void resetLaberinto(){
        for(int i = 0; i < y; i++){
            for(int j = 0; j < x; j++){
                laberinto[i][j] = new Casilla(j, i);
            }
        }
    }
}
