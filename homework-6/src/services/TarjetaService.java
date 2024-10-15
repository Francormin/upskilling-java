package services;

import dao.TarjetaDAO;
import dto.TarjetaDTO;
import entities.Tarjeta;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Clase abstracta que define la lógica de negocio para el manejo de tarjetas,
 * ya sean de crédito o débito. Esta clase proporciona métodos comunes para
 * operar con tarjetas, como obtener, insertar, actualizar y eliminar.
 *
 * <p>Los métodos de esta clase dependen de una implementación específica de
 * {@link TarjetaDAO}, que es responsable de las operaciones de acceso a datos.</p>
 *
 * @param <T> El tipo de tarjeta (TarjetaCredito o TarjetaDebito) que extiende
 * de la clase base Tarjeta.
 */
public abstract class TarjetaService<T extends Tarjeta> {

    /**
     * Método que debe ser implementado por subclases para obtener el DAO específico.
     *
     * @return El DAO correspondiente para la tarjeta.
     */
    protected abstract TarjetaDAO<T> getDao();

    /**
     * Obtiene una tarjeta por su ID.
     * Este método delega la operación al método correspondiente en el DAO.
     * Ver {@link TarjetaDAO#obtenerTarjetaPorId(int)}.
     *
     * @param id El ID de la tarjeta a obtener.
     * @return La tarjeta correspondiente al ID, o null si no se encuentra.
     */
    protected T obtenerTarjetaPorId(int id) {
        return getDao().obtenerTarjetaPorId(id);
    }

    /**
     * Obtiene todas las tarjetas.
     * Este método delega la operación al método correspondiente en el DAO.
     * Ver {@link TarjetaDAO#obtenerTodasLasTarjetas()}.
     *
     * @return Una lista de todas las tarjetas disponibles.
     */
    protected List<T> obtenerTodasLasTarjetas() {
        return getDao().obtenerTodasLasTarjetas();
    }

    /**
     * Inserta una nueva tarjeta.
     * Este método delega la operación al método correspondiente en el DAO.
     * Ver {@link TarjetaDAO#insertarTarjeta(T)}.
     *
     * @param tarjeta La tarjeta a insertar.
     */
    public void insertarTarjeta(T tarjeta) {
        getDao().insertarTarjeta(tarjeta);
    }

    /**
     * Actualiza una tarjeta existente.
     * Este método delega la operación al método correspondiente en el DAO.
     * Ver {@link TarjetaDAO#actualizarTarjeta(T)}.
     *
     * @param tarjeta La tarjeta a actualizar.
     */
    public void actualizarTarjeta(T tarjeta) {
        getDao().actualizarTarjeta(tarjeta);
    }

    /**
     * Elimina una tarjeta por su ID.
     * Este método delega la operación al método correspondiente en el DAO.
     * Ver {@link TarjetaDAO#eliminarTarjeta(int)}.
     *
     * @param id El ID de la tarjeta a eliminar.
     */
    public void eliminarTarjeta(int id) {
        getDao().eliminarTarjeta(id);
    }

    /**
     * Convierte un DTO (Data Transfer Object) en una entidad Tarjeta,
     * usando el ID proporcionado.
     *
     * @param id El ID de la tarjeta a actualizar.
     * @param tarjetaDTO El DTO de la tarjeta a convertir.
     * @return La entidad Tarjeta correspondiente.
     */
    public T convertirTarjetaDTOATarjeta(int id, TarjetaDTO tarjetaDTO) {
        T tarjeta = getDao().obtenerTarjetaPorId(id);
        if (tarjeta != null) {
            tarjeta.setNumero(tarjetaDTO.getNumero());
            tarjeta.setTitular(tarjetaDTO.getTitular());
        }
        return tarjeta;
    }

    /**
     * Convierte una tarjeta en su DTO (Data Transfer Object) correspondiente.
     *
     * @param tarjeta La tarjeta a convertir.
     * @return El DTO de la tarjeta.
     */
    protected TarjetaDTO convertirTarjetaATarjetaDTO(T tarjeta) {
        return new TarjetaDTO(tarjeta.getNumero(), tarjeta.getTitular());
    }

    /**
     * Obtiene una tarjeta por su ID y la convierte en DTO.
     *
     * @param id El ID de la tarjeta.
     * @return El DTO de la tarjeta correspondiente.
     */
    public TarjetaDTO obtenerTarjetaDTOPorId(int id) {
        T tarjeta = obtenerTarjetaPorId(id);
        return tarjeta != null ? convertirTarjetaATarjetaDTO(tarjeta) : null;
    }

    /**
     * Obtiene todas las tarjetas y las convierte a su DTO correspondiente.
     *
     * @return Una lista de tarjetas convertidas a DTO.
     */
    public List<TarjetaDTO> obtenerTodasLasTarjetasDTO() {
        List<T> tarjetas = obtenerTodasLasTarjetas();
        return tarjetas.stream()
                .map(this::convertirTarjetaATarjetaDTO)
                .collect(Collectors.toList());
    }
}