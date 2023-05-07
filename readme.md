# Tarea 2: Laberinto

## Equipo:

---

Nombre: Angel Jose Carreon

Cuenta: 319240391

---

Nombre: Miguel Angel Vargas Campos

Cuenta: 423114223

---

## Uso:
Parametros: 
 
-help : Ver este mensaje 

-unsolved : Crea un laberinto pero no muestra la solucion 

-size x y : Indica el tamaño del laberinto 

-first x y : Indica la posicion inicial del recorrido 

-last x y : Indica la posicion final del recorrido 
 
Vease que la posicion en x es de izquierda a derecha y la posicion y de arriba a abajo. 

Sino se especifican parametros se crea un laberinto por defecto.

## Ejemplo:
En la carpeta src:

### Con parametros:

```shell
javac Main.java && java Main -size 11 15 -first 1 1 -last 10 0
```

### Sin parametros:

```shell
javac Main.java && java Main
```
