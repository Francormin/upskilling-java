package services;

import dao.TarjetaDAO;
import dao.TarjetaDebitoDAOImpl;
import entities.TarjetaDebito;

/**
 * Servicio para gestionar operaciones de tarjetas de débito.
 * Extiende la clase base TarjetaService.
 */
public class TarjetaDebitoService extends TarjetaService<TarjetaDebito> {

    /**
     * Proporciona la implementación específica de TarjetaDebitoDAO.
     *
     * @return El DAO de TarjetaDebito.
     */
    @Override
    protected TarjetaDAO<TarjetaDebito> getDao() {
        return new TarjetaDebitoDAOImpl();
    }
}