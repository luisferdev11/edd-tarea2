
public class Main{
    public static void main(String[] args) {
        if(args.length<2){
            System.err.println("Necesitas ingresar dos valores");
            System.exit(1);
        }
        
       
        int x =Integer.parseInt(args[0]);
        int y = Integer.parseInt(args[1]);
       
        

        Laberinto laberinto = new Laberinto(x, y);
        System.out.println(laberinto.toString());
        laberinto.generarLaberinto();
        System.out.println(laberinto.toString());
        laberinto.resolverLaberinto();
        System.out.println(laberinto.toString());
    }
}