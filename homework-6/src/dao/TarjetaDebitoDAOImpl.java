package dao;

import config.JdbcConfiguration;
import entities.TarjetaDebito;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación de TarjetaDAO para tarjetas de débito.
 */
public class TarjetaDebitoDAOImpl implements TarjetaDAO<TarjetaDebito> {
    @Override
    public TarjetaDebito obtenerTarjetaPorId(int id) {
        String query = "SELECT * FROM tarjetas_debito WHERE id = ?";

        try (Connection connection = JdbcConfiguration.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new TarjetaDebito(
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
    public List<TarjetaDebito> obtenerTodasLasTarjetas() {
        String query = "SELECT * FROM tarjetas_debito";
        List<TarjetaDebito> tarjetas = new ArrayList<>();

        try (Connection connection = JdbcConfiguration.getDBConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                TarjetaDebito tarjeta = new TarjetaDebito(
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
    public void insertarTarjeta(TarjetaDebito tarjeta) {
        String insertSQL = "INSERT INTO tarjetas_debito (numero, titular) VALUES (?, ?)";

        try (Connection connection = JdbcConfiguration.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {

            preparedStatement.setString(1, tarjeta.getNumero());
            preparedStatement.setString(2, tarjeta.getTitular());
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println(
                        "Tarjeta de débito insertada correctamente: " + tarjeta.getNumero() + " - " + tarjeta.getTitular()
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void actualizarTarjeta(TarjetaDebito tarjeta) {
        String updateSQL = "UPDATE tarjetas_debito SET numero = ?, titular = ? WHERE id = ?";

        try (Connection connection = JdbcConfiguration.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {

            preparedStatement.setString(1, tarjeta.getNumero());
            preparedStatement.setString(2, tarjeta.getTitular());
            preparedStatement.setInt(3, tarjeta.getId());
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println(
                        "Tarjeta de débito actualizada correctamente: " + tarjeta.getNumero() + " - " + tarjeta.getTitular()
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminarTarjeta(int id) {
        String deleteSQL = "DELETE FROM tarjetas_debito WHERE id = ?";

        try (Connection connection = JdbcConfiguration.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {

            preparedStatement.setInt(1, id);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Tarjeta de débito con ID " + id + " eliminada correctamente.");
            } else {
                System.out.println("Tarjeta de débito con ID " + id + " no encontrada.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}