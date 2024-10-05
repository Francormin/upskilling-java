import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EjemploInicial {
    public static void aproximacionImperativa(List<Persona> personas) {
        List<Persona> personasMenores = new ArrayList<>();
        int contador = 0;
        int limite = 3;

        for (Persona persona : personas) {
            if (persona.getEdad() < 18) {
                personasMenores.add(persona);
                contador++;
                if (contador == limite) {
                    break;
                }
            }
        }

        for (Persona personaMenor : personasMenores) {
            System.out.println(personaMenor);
        }
    }

    public static void aproximacionDeclarativa(List<Persona> personas) {
        List<Persona> personasMenores = personas.stream()
                .filter(persona -> persona.getEdad() < 18)
                .limit(3)
                .collect(Collectors.toList());

        personasMenores.forEach(personaMenor -> System.out.println(personaMenor));
    }
}