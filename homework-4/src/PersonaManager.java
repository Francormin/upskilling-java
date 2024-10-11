import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PersonaManager {
    public static void main(String[] args) {
        List<Persona> personas = List.of(
                new Persona("Juan", 26, "caminar y programar"),
                new Persona("Pedro", 52, "leer"),
                new Persona("María", 15, "cocinar"),
                new Persona("Ana", 46, "jugar"),
                new Persona("Julio", 37, "limpiar"),
                new Persona("Lorena", 64, "estudiar"),
                new Persona("Pablo", 78, "bailar y programar"),
                new Persona("Camila", 9, "pintar"),
                new Persona("Sofía", 18, "cantar y programar"),
                new Persona("Marcelo", 32, "correr")
        );

        // Filtrando los elementos para mostrar solo aquellos que cumplen dos condiciones determinadas
        System.out.println("Personas mayores de 18 años que tienen a la programación como hobby:");
        personas.stream()
                .filter(persona -> persona.getEdad() > 18 &&persona.getHobby().contains("programar"))
                .forEach(System.out::println);

        // Mapeando (transformando) los elementos para mostrar solo una propiedad o atributo de los mismos
        System.out.println("\nLista que consiste solo en los nombres de las personas:");
        personas.stream()
                .map(Persona::getNombre)
                .forEach(System.out::println);

        // Limitando el número de elementos a mostrar
        System.out.println("\nLista con las primeras 5 personas encontradas:");
        personas.stream()
                .limit(5)
                .forEach(System.out::println);

        // Generando un diccionario (Map) que tenga el Nombre como key y el Hobby como value
        System.out.println("\nMapa de clave-valor con los nombres y hobbies de las personas:");
        personas.stream()
                .collect(Collectors.toMap(Persona::getNombre, Persona::getHobby))
                .forEach((clave, valor) -> System.out.println(clave + ": " + valor));

        // Buscando cualquier persona cuyo hobby sea "programar"
        System.out.println("\nPrimera persona encontrada que tenga como hobby programar:");
        Optional<Persona> resultadoOpcional = personas.stream()
                .filter(persona -> persona.getHobby().contains("programar"))
                .findAny();

        System.out.println(resultadoOpcional);
    }
}