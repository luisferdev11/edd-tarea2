public class Main{
    public static void main(String[] args) {
        Laberinto laberinto = new Laberinto(10, 10);
        System.out.println(laberinto.toString());
        laberinto.generarLaberinto();
        System.out.println(laberinto.toString());
    }
}