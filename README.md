## Generador de Archivo ARFF

Programa en Java que genera un Archivo ARFF en el cual se simulan los partidos de liga de Béisbol entre los meses abril a octubre (4 - 10), siendo el esquema del archivo el siguiente:

| Atributos          | Tipo de datos | Valores             |
|--------------------|---------------|---------------------|
| Mes                | Numérico      | 4-10                |
| Día                | Numérico      | 1-30                |
| Liga               | Nominal       | Nacional, Americana |
| Equipo local       | Nominal       | Se define abajo     |
| Equipo visitante   | Nominal       | Se define abajo     |
| Equipo ganador     | Nominal       | Se define abajo     |
| Carreras local     | Numérico      | 0-10                |
| Carreras Visitante | Numérico      | 0-10                |

La MLB se conforma de dos ligas y cada liga tiene  equipos de beisbol.

| Nacional   | Americana    |
|------------|--------------|
| Dodgers    | Yankees      |
| Bravos     | Astros       |
| Cardenales | Piratas      |
| Gigantes   | Medias Rojas |
| Cachorros  | Cerveceros   |

### Ejemplo de datos generados:

| Día | Mes |   Liga   |   Equipo 1  |   Equipo 2   |    Ganador    | Carrera Equipo 1 | Carrera Equipo 2 |
|-----|-----|----------|-------------|--------------|---------------|------------------|------------------|
|   31|   10| Americana| Medias rojas|      Gigantes|       Gigantes|                0 |                6 |
|   31|   10| Americana|       Astros|       Dodgers|         Astros|                2 |                0 |
|   31|   10| Americana|      Piratas|    Cardenales|        Piratas|               10 |                6 |
|   31|   10| Americana|      Yankees|     Cachorros|        Yankees|                4 |                3 |
|   31|   10| Americana|   Cerveceros|        Bravos|     Cerveceros|                8 |                6 |

### Reglas para la generación de los ejemplos
* Se debe de considerar de abril a octubre.
* Cada serie es de tres juegos realizados en días sucesivos y se descansa al siguiente día.
* Considere los meses que tienen 31 días.
* Las series deben de ser válidas, por ejemplo, un equipo no puede enfrentarse a sí mismo. Tampoco dentro del mismo periodo puede estar jugando más de una serie.

### Librerías utilizadas
Se utilizó la [librería](https://waikato.github.io/weka-wiki/formats_and_processing/creating_arff_file/) de [Weka](https://www.cs.waikato.ac.nz/~ml/index.html) para realizar la estructura del archivo ARFF

### Curso
Programa realizado para la materia de **Temas Selectos de Bases de Datos**

Profesor: Dr. Clemente García Gerardo

Estudiante: Jesús Ramiro Valenzuela Rivas