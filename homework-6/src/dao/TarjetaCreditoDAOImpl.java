package dao;

import config.JdbcConfiguration;
import entities.TarjetaCredito;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación de TarjetaDAO para tarjetas de crédito.
 */
public class TarjetaCreditoDAOImpl implements TarjetaDAO<TarjetaCredito> {
    @Override
    public TarjetaCredito obtenerTarjetaPorId(int id) {
        String query = "SELECT * FROM tarjetas_credito WHERE id = ?";

        try (Connection connection = JdbcConfiguration.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new TarjetaCredito(
                        resultSet.getInt("id"),
                        resultSet.getString("numero"),
                        resultSet.getString("titular")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<TarjetaCredito> obtenerTodasLasTarjetas() {
        String query = "SELECT * FROM tarjetas_credito";
        List<TarjetaCredito> tarjetas = new ArrayList<>();

        try (Connection connection = JdbcConfiguration.getDBConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                TarjetaCredito tarjeta = new TarjetaCredito(
                        resultSet.getInt("id"),
                        resultSet.getString("numero"),
                        resultSet.getString("titular")
                );
                tarjetas.add(tarjeta);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tarjetas;
    }

    @Override
    public void insertarTarjeta(TarjetaCredito tarjeta) {
        String insertSQL = "INSERT INTO tarjetas_credito (numero, titular) VALUES (?, ?)";

        try (Connection connection = JdbcConfiguration.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {

            preparedStatement.setString(1, tarjeta.getNumero());
            preparedStatement.setString(2, tarjeta.getTitular());
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println(
                        "Tarjeta de crédito insertada correctamente: " + tarjeta.getNumero() + " - " + tarjeta.getTitular()
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actualizarTarjeta(TarjetaCredito tarjeta) {
        String updateSQL = "UPDATE tarjetas_credito SET numero = ?, titular = ? WHERE id = ?";

        try (Connection connection = JdbcConfiguration.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {

            preparedStatement.setString(1, tarjeta.getNumero());
            preparedStatement.setString(2, tarjeta.getTitular());
            preparedStatement.setInt(3, tarjeta.getId());
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println(
                        "Tarjeta de crédito actualizada correctamente: " + tarjeta.getNumero() + " - " + tarjeta.getTitular()
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminarTarjeta(int id) {
        String deleteSQL = "DELETE FROM tarjetas_credito WHERE id = ?";

        try (Connection connection = JdbcConfiguration.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {

            preparedStatement.setInt(1, id);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Tarjeta de crédito con ID " + id + " eliminada correctamente.");
            } else {
                System.out.println("Tarjeta de crédito con ID " + id + " no encontrada.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}