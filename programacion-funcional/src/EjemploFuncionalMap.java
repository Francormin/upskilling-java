import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EjemploFuncionalMap {
    public static void aproximacionImperativa(List<Persona> personas) {
        List<PersonaTransfer> personasTransfer = new ArrayList<>();

        for (Persona persona : personas) {
            PersonaTransfer personaTransfer = PersonaTransfer.map(persona);
            personasTransfer.add(personaTransfer);
        }

        for (PersonaTransfer personaTransfer : personasTransfer) {
            System.out.println(personaTransfer);
        }
    }

    public static void aproximacionDeclarativa(List<Persona> personas) {
        List<PersonaTransfer> personasTransfer = personas.stream()
                .map(PersonaTransfer::map)
                .collect(Collectors.toList());

        personasTransfer.forEach(personaTransfer -> System.out.println(personaTransfer));
    }
}