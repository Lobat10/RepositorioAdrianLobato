package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class MostrarObraServlet
 */
@WebServlet("/MostrarObra")
public class MostrarObraServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MostrarObraServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext contexto = getServletContext();
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<html><head><meta charset='UTF-8'/></head><body>");

		Connection conn = null;
		Statement sentencia = null;
		String mensajeError="";

		try {
			// Paso 1: Cargar el driver JDBC.
			Class.forName("org.mariadb.jdbc.Driver").newInstance();

			// Paso 2: Conectarse a la Base de Datos utilizando la clase Connection
			String userName = contexto.getInitParameter("usuario");
			String password = contexto.getInitParameter("password");
			String url = contexto.getInitParameter("url");
			conn = DriverManager.getConnection(url, userName, password);

			// Paso 3: Crear sentencias SQL, utilizando objetos de tipo Statement
			sentencia = conn.createStatement();

			// Paso 4: Ejecutar la sentencia SQL a través de los objetos Statement
			if(request.getParameter("id")==null) {
				mensajeError="Error no se ha pasado nigun parametro.";
			}else {
				
			String consulta = "SELECT * FROM obra, autor WHERE "+request.getParameter("id")+"=obra.codigoObra AND obra.codigoAutor=autor.codigoAutor ";

				ResultSet rset = sentencia.executeQuery(consulta);
			
			// Paso 5: Mostrar resultados
			if (!rset.isBeforeFirst()) {
				out.println("<h3>No hay resultados</p>");
			}
			out.println("<table>");
			out.println("<tr style='background-color: lightgreen'>");
			out.println("<th>Codigo obra</th>");
			out.println("<th>Nombre obra</th>");
			out.println("<th>Duracion obra</th>");
			out.println("<th>Imagen obra</th>");
			out.println("<th>Nombre autor</th>");
			out.println("<th>Imagen autor</th>");
			out.println("</tr>");		
			while (rset.next()) {
				
			
				out.println("<tr style='background-color: lightblue'>");
				out.println("<td>" + rset.getString("codigoObra") + "</td>");
				out.println("<td>" + rset.getString("nombreObra") + "</td>");
				out.println("<td>" + rset.getString("duracion") + "</td>");
				out.println("<td><img src='img/" + rset.getString("imagen") +"'></td>");
				out.println("<td>" + rset.getString("nombreAutor") + "</td>");
				out.println("<td><img src='img/" + rset.getString("foto") +"'></td>");
				out.println("</tr>");
			}
			out.println("</table>");
			// Paso 6: Desconexión
			if (sentencia != null)
				sentencia.close();
			if (conn != null)
				conn.close();
			
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(mensajeError!="") {
		out.println("<p>Error: "+mensajeError+" </p>");
		out.println("<p><a href='./MostrarCatalogo'>Volver</p>");
		}
		out.println("<p><a href='./MostrarCatalogo'>Volver</p>");

		out.println("</body></html>");
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
