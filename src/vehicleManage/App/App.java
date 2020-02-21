package vehicleManage.App;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import vehicleManage.dao.vehicleDAO;
import vehicleManage.model.Vehicle;

/**
 * This servlet acts as a page controller for the application, handling all
 * requests.
 */

@WebServlet("/")
public class App  extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private vehicleDAO vch;
	
	public void init() {
		vch = new vehicleDAO();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getServletPath();

		try {
			switch (action) {
			case "/new":
				showNewForm(request, response);
				break;
			case "/insert":
				insertVehicle(request, response);
				break;
			case "/delete":
				deleteVehicle(request, response);
				break;
			case "/edit":
				showEditForm(request, response);
				break;
			case "/update":
				updateVehicle(request, response);
				break;
			default:
				listVehicle(request, response);
				break;
			}
		} catch (SQLException ex) {
			throw new ServletException(ex);
		}
	}

	private void listVehicle(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		List<Vehicle> listVehicle = vch.selectAllVehicles();
		request.setAttribute("listVehicle", listVehicle);
		RequestDispatcher dispatcher = request.getRequestDispatcher("vehicle-list.jsp");
		dispatcher.forward(request, response);
	}

	private void showNewForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("vehicle-form.jsp");
		dispatcher.forward(request, response);
	}

	private void showEditForm(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		Vehicle existingVehicle = vch.selectVehicle(id);
		RequestDispatcher dispatcher = request.getRequestDispatcher("vehicle-form.jsp");
		request.setAttribute("vehicle", existingVehicle);
		dispatcher.forward(request, response);

	}

	private void insertVehicle(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException {
		String year = request.getParameter("year");
		String make = request.getParameter("make");
		String model = request.getParameter("model");
		Vehicle newVehicle = new Vehicle(year, make, model);
		vch.insertVehicle(newVehicle);
		response.sendRedirect("list");
	}

	private void updateVehicle(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		String year = request.getParameter("year");
		String make = request.getParameter("make");
		String model = request.getParameter("model");

		Vehicle book = new Vehicle(id, year, make, model);
		vch.updateVehicle(book);
		response.sendRedirect("list");
	}

	private void deleteVehicle(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		vch.deleteVehicle(id);
		response.sendRedirect("list");

	}

}
