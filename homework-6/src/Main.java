import config.DatabaseInitializer;
import dto.TarjetaDTO;
import entities.TarjetaCredito;
import entities.TarjetaDebito;
import services.TarjetaCreditoService;
import services.TarjetaDebitoService;

public class Main {
    public static void main(String[] args) {
        // Inicializar la base de datos con sus tablas y registros de ejemplo
         DatabaseInitializer.inicializarBD();

        // ----- Utilizar los servicios para ambos tipos de Tarjeta ----- //

        TarjetaCreditoService tarjetaCreditoService = new TarjetaCreditoService();
        TarjetaDebitoService tarjetaDebitoService = new TarjetaDebitoService();

        // Obtener una tarjeta de crédito por su ID y mostrarla como DTO
        TarjetaDTO tarjetaCreditoDTO = tarjetaCreditoService.obtenerTarjetaDTOPorId(2);
        if (tarjetaCreditoDTO != null) {
            System.out.println("Tarjeta de crédito: " + tarjetaCreditoDTO);
        } else {
            System.out.println("No se encontró la tarjeta de crédito con ID 2.");
        }

        // Obtener una tarjeta de débito por su ID y mostrarla como DTO
        TarjetaDTO tarjetaDebitoDTO = tarjetaDebitoService.obtenerTarjetaDTOPorId(1);
        if (tarjetaDebitoDTO != null) {
            System.out.println("Tarjeta de débito: " + tarjetaDebitoDTO);
        } else {
            System.out.println("No se encontró la tarjeta de débito con ID 1.");
        }

        // Obtener todas las tarjetas de crédito disponibles en la base de datos y mostrarlas como DTO
        System.out.println("Lista de todas las tarjetas de crédito:");
        tarjetaCreditoService.obtenerTodasLasTarjetasDTO().forEach(System.out::println);

        // Obtener todas las tarjetas de débito disponibles en la base de datos y mostrarlas como DTO
        System.out.println("Lista de todas las tarjetas de débito:");
        tarjetaDebitoService.obtenerTodasLasTarjetasDTO().forEach(System.out::println);

        // Insertar una nueva tarjeta de crédito
        tarjetaCreditoService.insertarTarjeta(new TarjetaCredito(
                "1231231231231231",
                "John Doe"
        ));

        // Insertar una nueva tarjeta de débito
        tarjetaDebitoService.insertarTarjeta(new TarjetaDebito(
                "3213213213213213",
                "Jane Doe"
        ));

        // --- Actualizar tarjeta de crédito (cambiar el titular) --- //

        // Obtener el DTO de la tarjeta de crédito por su ID
        TarjetaDTO tarjetaCreditoDTOAActualizar = tarjetaCreditoService.obtenerTarjetaDTOPorId(1);

        if (tarjetaCreditoDTOAActualizar != null) {
            // Crear un nuevo DTO con el nuevo titular
            tarjetaCreditoDTOAActualizar = new TarjetaDTO(tarjetaCreditoDTOAActualizar.getNumero(), "John Smith");

            // Convertir el DTO a la entidad Tarjeta, proporcionando el ID explícitamente
            TarjetaCredito tarjetaCreditoActualizada = tarjetaCreditoService.convertirTarjetaDTOATarjeta(1, tarjetaCreditoDTOAActualizar);

            // Actualizar la tarjeta
            tarjetaCreditoService.actualizarTarjeta(tarjetaCreditoActualizada);

            // Confirmar la actualización mostrando la tarjeta actualizada
            System.out.println("Tarjeta de crédito actualizada: " + tarjetaCreditoService.obtenerTarjetaDTOPorId(1));
        } else {
            System.out.println("No se encontró la tarjeta de crédito con ID 1.");
        }

        // --- Actualizar tarjeta de débito (cambiar el titular) --- //

        // Obtener el DTO de la tarjeta de débito por su ID
        TarjetaDTO tarjetaDebitoDTOAActualizar = tarjetaDebitoService.obtenerTarjetaDTOPorId(1);

        if (tarjetaDebitoDTOAActualizar != null) {
            // Crear un nuevo DTO con el nuevo titular
            tarjetaDebitoDTOAActualizar = new TarjetaDTO(tarjetaDebitoDTOAActualizar.getNumero(), "Jane Smith");

            // Convertir el DTO a la entidad Tarjeta, proporcionando el ID explícitamente
            TarjetaDebito tarjetaDebitoActualizada = tarjetaDebitoService.convertirTarjetaDTOATarjeta(1, tarjetaDebitoDTOAActualizar);

            // Actualizar la tarjeta
            tarjetaDebitoService.actualizarTarjeta(tarjetaDebitoActualizada);

            // Confirmar la actualización mostrando la tarjeta actualizada
            System.out.println("Tarjeta de débito actualizada: " + tarjetaDebitoService.obtenerTarjetaDTOPorId(1));
        } else {
            System.out.println("No se encontró la tarjeta de débito con ID 1.");
        }

        // Eliminar una tarjeta de crédito por su ID
        tarjetaCreditoService.eliminarTarjeta(4);

        // Eliminar una tarjeta de débito por su ID
        tarjetaDebitoService.eliminarTarjeta(4);
    }
}