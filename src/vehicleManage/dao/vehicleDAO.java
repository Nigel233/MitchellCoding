package vehicleManage.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vehicleManage.model.Vehicle;

/**
 * AbstractDAO.java This DAO class provides CRUD database operations for the
 * table vehicles in the database.
 *
 */

public class vehicleDAO {

	private String jdbcURL = "jdbc:mysql://localhost:3306/demo?useSSL=false&allowPublicKeyRetrieval=true";
	private String jdbcVehiclename = "root";
	// here needs to be changed according to local MySQL database
	private String jdbcPassword = "651104Lyz";

	private static final String INSERT_VEHICLES_SQL = "INSERT INTO vehicles" + "  (year, make, model) VALUES "
			+ " (?, ?, ?);";

	private static final String SELECT_VEHICLE_BY_ID = "select id, year, make, model from vehicles where id =?";
	private static final String SELECT_ALL_VEHICLES = "select * from vehicles";
	private static final String DELETE_VEHICLES_SQL = "delete from vehicles where id = ?;";
	private static final String UPDATE_VEHICLES_SQL = "update vehicles set year = ?, make = ?, model =? where id = ?;";

	public vehicleDAO() {}

	protected Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(jdbcURL, jdbcVehiclename, jdbcPassword);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return connection;
	}

	public void insertVehicle(Vehicle vehicle) throws SQLException {
		System.out.println(INSERT_VEHICLES_SQL);
		// try-with-resource statement will auto close the connection.
		try (Connection connection = getConnection();
				PreparedStatement pS = connection.prepareStatement(INSERT_VEHICLES_SQL)) {
			pS.setString(1, vehicle.getYear());
			pS.setString(2, vehicle.getMake());
			pS.setString(3, vehicle.getModel());
			System.out.println(pS);
			pS.executeUpdate();
		} catch (SQLException e) {
			printSQLException(e);
		}
	}

	public Vehicle selectVehicle(int id) {
		Vehicle vehicle = null;
		// establishing connection
		try (Connection connection = getConnection();
				
				// get statement
				PreparedStatement pS = connection.prepareStatement(SELECT_VEHICLE_BY_ID);) {
			
			pS.setInt(1, id);
			System.out.println(pS);
			
			// execute query
			ResultSet rs = pS.executeQuery();

			while (rs.next()) {
				String year = rs.getString("year");
				String make = rs.getString("make");
				String model = rs.getString("model");
				vehicle = new Vehicle(id, year, make, model);
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return vehicle;
	}

	public List<Vehicle> selectAllVehicles() {

		List<Vehicle> vehicles = new ArrayList<>();

		// establish connection
		try (Connection connection = getConnection();

			// get statement
			PreparedStatement pS = connection.prepareStatement(SELECT_ALL_VEHICLES);) {
			System.out.println(pS);
			
			// execute query
			ResultSet rs = pS.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("id");
				String year = rs.getString("year");
				String make = rs.getString("make");
				String model = rs.getString("model");
				vehicles.add(new Vehicle(id, year, make, model));
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return vehicles;
	}

	public boolean deleteVehicle(int id) throws SQLException {
		boolean rowDeleted;
		try (Connection connection = getConnection();
				PreparedStatement pS = connection.prepareStatement(DELETE_VEHICLES_SQL);) {
			pS.setInt(1, id);
			rowDeleted = pS.executeUpdate() > 0;
		}
		return rowDeleted;
	}

	public boolean updateVehicle(Vehicle vehicle) throws SQLException {
		boolean rowUpdated;
		try (Connection connection = getConnection();
				PreparedStatement pS = connection.prepareStatement(UPDATE_VEHICLES_SQL);) {
			pS.setString(1, vehicle.getYear());
			pS.setString(2, vehicle.getMake());
			pS.setString(3, vehicle.getModel());
			pS.setInt(4, vehicle.getId());

			rowUpdated = pS.executeUpdate() > 0;
		}
		return rowUpdated;
	}

	private void printSQLException(SQLException ex) {
		for (Throwable e : ex) {
			if (e instanceof SQLException) {
				e.printStackTrace(System.err);
				System.err.println("SQLState: " + ((SQLException) e).getSQLState());
				System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
				System.err.println("Message: " + e.getMessage());
				Throwable t = ex.getCause();
				while (t != null) {
					System.out.println("Cause: " + t);
					t = t.getCause();
				}
			}
		}
	}

}
