package dao;

import entities.Tarjeta;

import java.util.List;

/**
 * Interfaz que define las operaciones CRUD (Crear, Leer, Actualizar, Eliminar)
 * para el manejo de tarjetas en el sistema. Esta interfaz debe ser implementada
 * por las clases que manejan la persistencia de datos para distintos tipos de tarjetas.
 *
 * <p>Las implementaciones de esta interfaz son responsables de realizar las operaciones
 * de acceso a datos necesarias, como la conexión a la base de datos y la ejecución
 * de consultas.</p>
 *
 * @param <T> Tipo de tarjeta, que debe extender de la clase base Tarjeta.
 */
public interface TarjetaDAO<T extends Tarjeta> {

    /**
     * Obtiene una tarjeta por su ID.
     *
     * @param id El ID de la tarjeta a obtener.
     * @return La tarjeta correspondiente al ID, o null si no se encuentra.
     */
    T obtenerTarjetaPorId(int id);

    /**
     * Obtiene todas las tarjetas.
     *
     * @return Una lista de todas las tarjetas.
     */
    List<T> obtenerTodasLasTarjetas();

    /**
     * Inserta una nueva tarjeta.
     *
     * @param tarjeta La tarjeta a insertar.
     */
    void insertarTarjeta(T tarjeta);

    /**
     * Actualiza una tarjeta existente.
     *
     * @param tarjeta La tarjeta a actualizar.
     */
    void actualizarTarjeta(T tarjeta);

    /**
     * Elimina una tarjeta por su ID.
     *
     * @param id El ID de la tarjeta a eliminar.
     */
    void eliminarTarjeta(int id);
}