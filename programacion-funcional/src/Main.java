import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Persona> personas = new ArrayList<>();
        agregarPersonas(personas);

        // EjemploInicial.aproximacionImperativa(personas);
        // EjemploInicial.aproximacionDeclarativa(personas);

        // EjemploFuncionalMap.aproximacionImperativa(personas);
        EjemploFuncionalMap.aproximacionDeclarativa(personas);
    }

    public static void agregarPersonas(List<Persona> personas) {
        personas.add(new Persona(1, "Juan", "Pérez", "juanpe@email.com", 22));
        personas.add(new Persona(2, "Pedro", "Rodríguez", "pedroro@email.com", 36));
        personas.add(new Persona(3, "Lucas", "Martínez", "lucasma@email.com", 43));
        personas.add(new Persona(4, "Mateo", "Fernández", "mateofe@email.com", 54));
        personas.add(new Persona(5, "Marcos", "González", "marcosgo@email.com", 65));
        personas.add(new Persona(6, "María", "López", "marialo@email.com", 17));
        personas.add(new Persona(7, "Sofía", "García", "sofiaga@email.com", 11));
        personas.add(new Persona(8, "Julieta", "Gómez", "julietago@email.com", 12));
        personas.add(new Persona(9, "Ana", "Sánchez", "anasa@email.com", 78));
        personas.add(new Persona(10, "Florencia", "Díaz", "florenciadi@email.com", 9));
    }
}