import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * 
 */
public class Model {

	private String usuari, contrasenya;

	Model() {
		this.usuari = "";
		this.contrasenya = "";
	}

	public String getUsuari() {
		return usuari;
	}

	public void setUsuari(String usuari) {
		this.usuari = usuari;
	}

	public String getContrasenya() {
		return contrasenya;
	}

	public void setContrasenya(String contrasenya) {
		this.contrasenya = contrasenya;
	}

	public void conexioBD() {

	}

	/**
	 * Métode de verificació d'usuari
	 * 
	 * @param user   Nom del usuari
	 * @param contra Contrasenya del usuari
	 * @return boolean
	 */
	public boolean comprobarLogin(String user, String contra) {
		boolean confirmar = false;
		String query = "SELECT * FROM users WHERE login = ? AND password = ?";

		try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/population", "root", "");
				PreparedStatement pstmt = con.prepareStatement(query)) {
			pstmt.setString(1, user);
			pstmt.setString(2, CifradoHashMD5(contra));

			try (ResultSet rs = pstmt.executeQuery()) {
				confirmar = rs.next();
			}

		} catch (Exception e) {
			System.out.println("Error al conectar con la base de datos: " + e.getMessage());
			e.printStackTrace();
		}

		return confirmar;
	}

	/**
	 * Métode que comproba si el usuari es admin
	 * 
	 * @param user   nom del usuari
	 * @param contra contrasenya del usuari
	 * @return boolean
	 */
	public boolean comprobarAdmin(String user, String contra) {
		boolean confirmar = false;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/population", "root", "");
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM users where type='admin'");
			while (rs.next()) {
				this.usuari = rs.getString(2);
				this.contrasenya = rs.getString(3);
				if (usuari.equals(user) && contrasenya.equals(CifradoHashMD5(contra))) {
					confirmar = true;
				}
			}
		} catch (Exception e) {
			System.out.println("Error al conectar con la base de datos: " + e.getMessage());
			e.printStackTrace();
		}

		return confirmar;
	}

	/**
	 * Métode que compara les contrasenyes
	 * 
	 * @param contrasenya          contrasenya introduida
	 * @param confirmarContrasenya confirmació de contrasenya
	 * @return boolean
	 */
	public boolean comprobarContrasenya(String contrasenya, String confirmarContrasenya) {
		if (contrasenya.equals(confirmarContrasenya)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Métode que cifra la contrasenya
	 * 
	 * @param contrasenya contrasenya a cifrar
	 * @return Contrasenya cifrada en MD5
	 */
	public String CifradoHashMD5(String contrasenya) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] contraBytes = md.digest(contrasenya.getBytes());
			StringBuilder hexString = new StringBuilder();

			for (byte b : contraBytes) {
				String hex = Integer.toHexString(0xff & b);
				if (hex.length() == 1) {
					hexString.append('0');
				}
				hexString.append(hex);
			}
			return hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Error: MD5 algorithm not found", e);
		}
	}

	/**
	 * Métode que inserta un nou usuari a la taula users de la BD.
	 * 
	 * @param usuari      Nom del usuari introduit
	 * @param contrasenya Contrasenya no cifrada
	 */
	public void insertarUsuari(String usuari, String contrasenya) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/population", "root", "");
			Statement stmt = con.createStatement();

			String sqlInsert = "INSERT INTO users (login, password, type) VALUES (?, ?, ?)";
			PreparedStatement psInsertar = con.prepareStatement(sqlInsert);
			psInsertar.setString(1, usuari);
			psInsertar.setString(2, CifradoHashMD5(contrasenya));
			psInsertar.setString(3, "client");
			psInsertar.executeUpdate();
			stmt.close();
			con.close();
		} catch (Exception e) {
			System.out.println("Error al conectar con la base de datos: " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Métode que llegeix un fitxer CSV, crea la taula population en la BD, borra la
	 * tabla population existent y genera un XML
	 * 
	 * @param ruta Ruta del fitxer CSV
	 * @return Dades en format XML
	 */
	public String crearTablaPopulation(String ruta) {
		String contenidoXML = "";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/population", "root", "");
			Statement stmt = con.createStatement();
			if (comprobarTablaExistente(con, "population")) {
				StringBuilder borrarTabla = new StringBuilder("DELETE FROM population");
				stmt.execute(borrarTabla.toString());
			}
			BufferedReader reader = new BufferedReader(new FileReader(ruta));
			String linea;
			boolean primeraLinea = true;
			String[] datosPrimeraLinea = null;
			String[] campos = null;
			while ((linea = reader.readLine()) != null) {
				campos = linea.split(";");

				if (primeraLinea) {
					datosPrimeraLinea = campos;
					StringBuilder sql = new StringBuilder("CREATE TABLE IF NOT EXISTS population (");
					for (String dato : datosPrimeraLinea) {
						sql.append(dato).append(" varchar(30), ");

					}

					sql.setLength(sql.length() - 2);
					sql.append(");");
					stmt.execute(sql.toString());
					primeraLinea = false;
				} else {
					StringBuilder sql = new StringBuilder("INSERT INTO  population (");
					StringBuilder placeholders = new StringBuilder("VALUES (");
					for (String dato : datosPrimeraLinea) {
						sql.append(dato).append(", ");
						placeholders.append("?, ");

					}
					sql.setLength(sql.length() - 2);
					placeholders.setLength(placeholders.length() - 2);
					sql.append(") ").append(placeholders).append(");");

					try (PreparedStatement pstmt = con.prepareStatement(sql.toString())) {
						for (int i = 0; i < campos.length; i++) {
							pstmt.setString(i + 1, campos[i].trim());

						}
						pstmt.executeUpdate();

					}
					contenidoXML += "-------------------\n";
					contenidoXML += writeXmlFile(campos, datosPrimeraLinea) + "\n";
					contenidoXML += "-------------------\n";
				}

			}

			reader.close();
			stmt.close();

		} catch (IOException | SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Error al conectar con la base de datos: " + e.getMessage());
			e.printStackTrace();
		}
		return contenidoXML;
	}

	public boolean comprobarTablaExistente(Connection conexion, String nombreTabla) {
		boolean validar = false;
		try {
			DatabaseMetaData metaData = conexion.getMetaData();

			ResultSet rs = metaData.getTables(null, null, nombreTabla, new String[] { "TABLE" });

			if (rs.next()) {
				validar = true;
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println("Error al consultar la base de datos: " + e.getMessage());
		}
		return validar;
	}

	/**
	 * Métode que genera una taula HTML a partir del resultat d'una consulta SQL
	 * 
	 * @param consulta consulta realitzada
	 * @return Taula HTML en String
	 */
	public String crearTabla(String consulta) {
		String tabla = "";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/population", "root", "");
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(consulta);
			tabla += "<html>";
			tabla += "<head>";
			tabla += "<style>";
			tabla += "table { border-collapse: collapse; width: 100%; }";
			tabla += "th, td { padding: 8px; text-align: left; }";
			tabla += "th { background-color: #f2f2f2; }";
			tabla += "</style>";
			tabla += "</head>";
			tabla += "<body>";
			tabla += "<table>";
			tabla += "<tr>";
			for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
				tabla += "<th>" + rs.getMetaData().getColumnName(i) + "</th> ";
			}
			tabla += "</tr>";
			while (rs.next()) {
				tabla += "<tr>";
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					tabla += "<td>" + rs.getString(i) + "</td>";
				}
				tabla += "</tr>";
			}
			tabla += "</table>";
			tabla += "</body>";
			tabla += "</html>";
			rs.close();
			stmt.close();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return tabla;
	}

	/**
	 * AQUEST METODE FUNCIONA CORRECTAMENT, PERO HI HAN PROBLEMES DE CONFLICTES ENTRE IMPORTS (document.w i document.jsoup)
	 * Métode que converteix una taula HTML a un fitxer CSV i la guarda al disc
	 * 
	 * @param contingut Codi HTML amb una taula que es processarà
	 */
//	public static void guardarCSV(String contingut) {
//		try {
//			Document doc = Jsoup.parse(contingut);
//			Element table = doc.select("table").first();
//
//			FileWriter writer = new FileWriter("datostabla.csv");
//
//			Elements rows = table.select("tr");
//			for (Element row : rows) {
//				Elements cells = row.select("th, td");
//				for (int i = 0; i < cells.size(); i++) {
//
//					writer.append(cells.get(i).text());
//					if (i < cells.size() - 1) {
//						writer.append(":");
//					}
//				}
//				writer.append("\n");
//			}
//
//			writer.flush();
//			writer.close();
//
//			System.out.println("Tabla guardada exitosamente ");
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * Métode que genera un arxiu XML
	 * 
	 * @param campo  Un array amb els valors que es volen incloure en el fitxer XML
	 * @param titulo Un array amb els noms dels elements XML que correspondran als
	 *               valors de campo
	 * @return El contingut XML generat com a cadena de text
	 */

	public static String writeXmlFile(String[] campo, String[] titulo) {
		String tituloFichero = campo[0];
		String contenidoXML = null;
		try {
			DocumentBuilderFactory dFact = DocumentBuilderFactory.newInstance();
			DocumentBuilder build = dFact.newDocumentBuilder();
			Document doc = build.newDocument();
			Element raiz = doc.createElement("dataUnit");
			doc.appendChild(raiz);

			for (int i = 0; i < campo.length; i++) {
				Element fichero = doc.createElement(titulo[i]);
				fichero.appendChild(doc.createTextNode(campo[i]));
				raiz.appendChild(fichero);
			}

			TransformerFactory tranFactory = TransformerFactory.newInstance();
			Transformer aTransformer = tranFactory.newTransformer();
			aTransformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
			aTransformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			aTransformer.setOutputProperty(OutputKeys.INDENT, "yes");

			DOMSource source = new DOMSource(doc);
			try (FileWriter fw = new FileWriter("./src/xml/" + tituloFichero + ".xml");
					StringWriter sw = new StringWriter()) {

				StreamResult fileResult = new StreamResult(fw);
				aTransformer.transform(source, fileResult);

				StreamResult stringResult = new StreamResult(sw);
				aTransformer.transform(source, stringResult);

				contenidoXML = sw.toString();
			}
		} catch (TransformerException ex) {
			System.out.println("Error escribiendo el documento XML: " + ex.getMessage());
		} catch (ParserConfigurationException ex) {
			System.out.println("Error construyendo el documento XML: " + ex.getMessage());
		} catch (IOException ex) {
			System.out.println("Error al guardar el archivo XML: " + ex.getMessage());
		} catch (IllegalArgumentException ex) {
			System.out.println("Error de datos: " + ex.getMessage());
		}
		return contenidoXML.toString();
	}
}
