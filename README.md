# Calculadora básica en Prolog

Adaptación del proyecto Calculadora-derivadas-integrales. Conserva la estructura
Maven, los paquetes Java, la interfaz Swing, el tema oscuro y los componentes
del repositorio original. Las reglas de derivadas e integrales se sustituyeron
por suma, resta, multiplicación y división.

## Estructura

```text
Calculadora/
├── pom.xml
└── src/main/
    ├── java/org/example/
    │   ├── Main.java
    │   ├── Connection.java
    │   └── screen/
    │       ├── Window.java
    │       ├── Theme.java
    │       └── components/
    │           ├── Buttons.java
    │           ├── PanelResult.java
    │           └── TextField.java
    └── resources/
        └── calculo.pl
```

## Ejecutar la interfaz

Requisitos: JDK 21 o superior y Maven, o un IDE con soporte Maven.
La dependencia tuProlog 3.3.0 es la misma del proyecto original.
Maven o el IDE la descarga al importar el proyecto; la aplicación Java no necesita
una instalación independiente de SWI-Prolog.

En IntelliJ IDEA, abrí `Calculadora/pom.xml` como proyecto Maven, seleccioná
un JDK 21 o superior y ejecutá `org.example.Main`.

También podés ejecutar desde esta carpeta, con Maven instalado:

```powershell
cd Calculadora
mvn compile exec:java
```

Ingresá dos números y presioná **Sumar**, **Restar**, **Multiplicar** o **Dividir**.
El orden de resta y división es primer número menos/dividido entre segundo número.
Se aceptan negativos y decimales con punto o coma, por ejemplo `-5`, `2.5` y `2,5`.
Enter realiza la suma. Los ejemplos rellenan ambos campos y **Copiar** copia el resultado.
Los campos vacíos, el texto no numérico y la división entre cero muestran un mensaje.

## Cómo funciona

1. `Window.java` recibe los dos números y la operación elegida.
2. `Connection.java` valida las entradas y consulta el motor tuProlog.
3. `calculo.pl` calcula el resultado con `is/2` y lo devuelve en la variable `R`.
4. La ventana muestra el resultado devuelto por Prolog.

Por ejemplo, el botón Sumar genera una consulta como `suma(8, 2, R).`.
`number/1` comprueba que ambos operandos sean números. En la división,
`B =\= 0` comprueba que el divisor sea distinto de cero y el factor `1.0`
permite obtener un cociente decimal en tuProlog.

## Probar solo las reglas en Prolog

Si tenés SWI-Prolog instalado, desde esta carpeta:

```powershell
swipl -s Calculadora/src/main/resources/calculo.pl
```

Consultas de ejemplo (en Prolog los decimales usan punto):

```prolog
?- suma(8, 2, R).
R = 10.

?- resta(-5, 3, R).
R = -8.

?- multiplicacion(2.5, 4, R).
R = 10.0.

?- division(7, 2, R).
R = 3.5.

?- division(10, 0, R).
false.
```

## Verificación realizada

Se compiló el código Java con compatibilidad Java 21 y se ejecutaron 12 casos
de integración con tuProlog: las cuatro operaciones, negativos, fracciones,
coma decimal, numerador cero, división entre cero entero y decimal, campos
vacíos, texto inválido, intento de insertar una consulta y operación desconocida.
Las reglas también se probaron por separado con SWI-Prolog.
La compilación se verificó con `javac`; el comando Maven no se ejecutó porque
Maven no está instalado en el entorno de trabajo. No se realizó una prueba visual
de la ventana.
