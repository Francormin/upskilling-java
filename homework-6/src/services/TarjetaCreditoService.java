package services;

import dao.TarjetaCreditoDAOImpl;
import dao.TarjetaDAO;
import entities.TarjetaCredito;

/**
 * Servicio para gestionar operaciones de tarjetas de crédito.
 * Extiende la clase base TarjetaService.
 */
public class TarjetaCreditoService extends TarjetaService<TarjetaCredito> {

    /**
     * Proporciona la implementación específica de TarjetaCreditoDAO.
     *
     * @return El DAO de TarjetaCredito.
     */
    @Override
    protected TarjetaDAO<TarjetaCredito> getDao() {
        return new TarjetaCreditoDAOImpl();
    }
}