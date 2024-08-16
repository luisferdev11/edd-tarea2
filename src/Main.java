public class Main{
    public static void main(String[] args) {
        if(args.length == 0){
            Laberinto laberinto = new Laberinto(10, 10);
            laberinto.generarLaberinto();
            laberinto.resolverLaberinto();
            System.out.println(laberinto.toString());
            return; 
        }
        int sizeX = 10;
        int sizeY = 10;
        int firstX = 0;
        int firstY = 0;
        int lastX = 0;
        int lastY = 0;
        boolean changedFirst = false;
        boolean changedLast = false;
        boolean solution = true;
        for(int i = 0; i < args.length; i++){
            Opciones opcion = Opciones.fromString(args[i]);

            if(opcion == Opciones.SIZE){
                try{
                    sizeX = parseInt(args[++i]);
                    sizeY = parseInt(args[++i]);
                    if(sizeX < 1 || sizeY < 1)
                        dimensionesLaberinto();
    
                }catch(IndexOutOfBoundsException e){
                    outOfBounds();
                }
            }else if(opcion == Opciones.UNSOLVED){
                solution = false;
            }else if(opcion == Opciones.FIRST){
                try{
                    firstX = parseInt(args[++i]);
                    firstY = parseInt(args[++i]);
                    if(firstX < 0 || firstY < 0)
                        coordenadasNegativas();

                    changedFirst = true;
                }catch(IndexOutOfBoundsException e){
                    outOfBounds();
                }
            }else if(opcion == Opciones.LAST){
                try{
                    lastX = parseInt(args[++i]);
                    lastY = parseInt(args[++i]);
                    if(lastX < 0 || lastY < 0)
                        coordenadasNegativas();
                    changedLast = true;
                }catch(IndexOutOfBoundsException e){
                    outOfBounds();
                }
            }else{
                help();
            }
        }
        Laberinto laberinto = new Laberinto(sizeX, sizeY);
        laberinto.generarLaberinto();

        if(changedFirst){
            if(firstX >= sizeX || firstY >= sizeY)
                casillaFuera();
            laberinto.setPrimera(firstX, firstY);
        }

        if(changedLast){
            if(lastX >= sizeX || lastY >= sizeY)
                casillaFuera();
            laberinto.setUltima(lastX, lastY);
        }
        if(solution)
            laberinto.resolverLaberinto();
        
        System.out.println(laberinto.toString());
    }
    private static void help(){
        String uso = 
        "Parametros: \n \n" +
        "-help : Ver este mensaje \n" +
        "-unsolved : Crea un laberinto pero no muestra la solucion \n" +
        "-size x y : Indica el tamaño del laberinto \n" +
        "-first x y : Indica la posicion inicial del recorrido \n" +
        "-last x y : Indica la posicion final del recorrido \n \n" +
        "Vease que la posicion en x es de izquierda a derecha " +
        "y la posicion y de arriba a abajo. \n" +
        "Sino se especifican parametros se crea un laberinto por defecto.";
        System.out.println(uso);
        System.exit(0);
    }

    private static int parseInt(String string){
        try{
            return Integer.parseInt(string);
        }catch(Exception e){
            System.out.println("Error: El parametro recibido no es un entero \n");
            System.exit(0);
        }
        return 0;
    }

    private static void outOfBounds(){
        String msg = "Algun parametro esta incompleto \n";
        System.out.println("Error: " + msg);
        help();
    }
    
    private static void casillaFuera(){
        System.out.println("Error: La posicion de alguna de las casillas no es valida \n");
        help();
    }

    private static void coordenadasNegativas(){
        System.out.println("Error: Una casilla no puede tener coordenadas negativas \n");
        help();
    }

    private static void dimensionesLaberinto(){
        System.out.println("Error: Las dimensiones del laberinto deben ser de al menos 1 \n");
        help();
    }
}