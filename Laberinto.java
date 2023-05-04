import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class Laberinto {
    private class Casilla{ //subclase de casilla
        // Si es verdadero significa que la casilla tiene una parde en esa direccion
        private boolean izquierda, abajo, recorrida, solucion; //atributos de casilla
        private int x, y; //atributos de casilla
        // private ArrayList<Casilla> vecinosVisitados;

        private Casilla(int x, int y){ // contructor de casilla, recibe x y y como parametros y los asigna a los atributos de casilla  
            izquierda = true; 
            abajo = true;
            recorrida = false;
            solucion = false;
            this.x = x; // posicion de la casilla en x
            this.y = y; //posicion de la casilla en y
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

    private Casilla[][] laberinto; //se crea un arreglo de casillas llamado laberinto
    private int x, y; // atributos de laberinto
    private static final Random random = new Random();

    private Laberinto(){}

    public Laberinto(int x, int y){ //constructor de laberinto, recibe x y y como parametros y los asigna a los atributos de laberinto
        this.x = x;
        this.y = y;
        laberinto = new Casilla[y][x]; // se crea un arreglo de casillas con las dimensiones de x y y
        for(int i = 0; i < y; i++){
            for(int j = 0; j < x; j++){
                laberinto[i][j] = new Casilla(j, i); // se crea una casilla en cada posicion del arreglo
            }
        }
    }

    private Direccion obtenerVecino(Casilla casilla){ // metodo que recibe una casilla como parametro y regresa una direccion si es que hay una casilla vecina que no ha sido visitada
        ArrayList<Direccion> opciones = new ArrayList<>();
        try{
            Casilla aux = laberinto[casilla.y - 1][casilla.x];
            if(!aux.recorrida)
                opciones.add(Direccion.ARR);
        }catch(Exception e){}
        try{
            Casilla aux = laberinto[casilla.y + 1][casilla.x];
            if(!aux.recorrida)
                opciones.add(Direccion.ABJ);
        }catch(Exception e){}
        try{
            Casilla aux = laberinto[casilla.y][casilla.x - 1];
            if(!aux.recorrida)
                opciones.add(Direccion.IZQ);
        }catch(Exception e){}
        try{
            Casilla aux = laberinto[casilla.y][casilla.x + 1];
            if(!aux.recorrida)
                opciones.add(Direccion.DER);
        }catch(Exception e){}

        if(opciones.size() == 0) return null;
        Direccion seleccionada = opciones.get(random.nextInt(opciones.size()));
        Casilla visitada = getRelative(casilla, seleccionada);
        visitada.recorrida = true;
        return seleccionada;
    }

    private String underline(String string){
        return "\033[4m" + string + "\033[0m";
    }

    private Casilla getRelative(Casilla casilla, Direccion dir){ // metodo que recibe una casilla y una direccion y regresa la casilla vecina en esa direccion
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
                String aux = laberinto[i][j].solucion ? "X" : " ";
                salida += laberinto[i][j].abajo ? underline(aux) : aux;
            }
            salida += "| \n";
        }
        return salida;
    }

    public void generarLaberinto(){
        resetLaberinto();
        Stack<Casilla> pila = new Stack<>();
        int actualX = 0;
        int actualY = 0;
        // deberiamos añadir la actual a las vistaiadas?? Si
        Casilla actual = laberinto[actualY][actualX];
        actual.recorrida = true;
        pila.push(actual);
        while(!pila.empty()){
            Direccion dirSiguiente = obtenerVecino(actual);
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

    public void resolverLaberinto(){
        resetSolucion();
        Stack<Casilla> pila = new Stack<>();
        int actualX = 0;
        int actualY = 0;
        Casilla actual = laberinto[actualY][actualX];
        actual.recorrida = true;
        Casilla meta = laberinto[y - 1][x - 1];
        pila.push(actual);
        while(actual != meta){
            Direccion dirSiguiente = obtenerVecinoAccesible(actual);
            if(dirSiguiente == null){
                pila.pop();
                actual = pila.peek();
                continue;
            }
            Casilla casillaSiguiente = getRelative(actual, dirSiguiente);
            pila.push(casillaSiguiente);
            actual = pila.peek();
        }
        for(int i = 0; i < pila.size(); i++){
            Casilla aux = pila.get(i);
            aux.solucion = true;
        }
    }    
    public void resetSolucion(){
        for(int i = 0; i < y; i++){
            for(int j = 0; j < x; j++){
                laberinto[i][j].solucion = false;
                laberinto[i][j].recorrida = false;
            }
        }
    }
    private Direccion obtenerVecinoAccesible(Casilla casilla){
        ArrayList<Direccion> opciones = new ArrayList<>();
        try{
            Casilla aux = laberinto[casilla.y - 1][casilla.x];
            if((!aux.recorrida) && aux.abajo == false)
                opciones.add(Direccion.ARR);
        }catch(Exception e){}
        try{
            Casilla aux = laberinto[casilla.y + 1][casilla.x];
            if((!aux.recorrida) && casilla.abajo == false)
                opciones.add(Direccion.ABJ);
        }catch(Exception e){}
        try{
            Casilla aux = laberinto[casilla.y][casilla.x - 1];
            if((!aux.recorrida) && casilla.izquierda == false)
                opciones.add(Direccion.IZQ);
        }catch(Exception e){}
        try{
            Casilla aux = laberinto[casilla.y][casilla.x + 1];
            if((!aux.recorrida) && aux.izquierda == false)
                opciones.add(Direccion.DER);
        }catch(Exception e){}
        // System.out.println("x" + casilla.x);
        // System.out.println("y" + casilla.y);
        // System.out.println(opciones.size());
        if(opciones.size() == 0)
            return null;
        Direccion seleccionada = opciones.get(random.nextInt(opciones.size()));
        Casilla regreso = getRelative(casilla, seleccionada);
        // visitados.add(regreso);
        regreso.recorrida = true;
        // regreso.recorrida = true;
        return seleccionada;
    }
}
