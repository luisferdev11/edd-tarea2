public enum Opciones {
    
    HELP,
    SIZE,
    FIRST,
    LAST,
    UNSOLVED;

    public static Opciones fromString(String string){
        if(string == null) return null;
        return
            string.equals("-help") ? HELP :
            string.equals("-size") ? SIZE :
            string.equals("-last") ? LAST :
            string.equals("-first") ? FIRST :
            string.equals("-unsolved") ? UNSOLVED :
            null;
    }
}
