
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Vistas.Vista;
import Vistas.VistaMenuPrincipal;
import Vistas.VistaCrearUsuari;

/**
 * 
 */
public class Controlador {

	private Model model;
	private Vista vista;
	private VistaMenuPrincipal vistaPrincipal;
	private VistaCrearUsuari vistaCrearUsuari;
	private ActionListener actionListener_Login;
	private ActionListener actionListener_CerrarSessio;
	private ActionListener actionListener_MenuCrearUsuari;
	private ActionListener actionListener_CrearUsuari;
	private ActionListener actionListener_InsertarDatos;
	private ActionListener actionListener_CrearTabla;
	private ActionListener actionListener_SalirUsuari;
	private ActionListener actionListener_GuardarCsv;

	Controlador(Model model, Vista vista, VistaMenuPrincipal vPrincipal, VistaCrearUsuari vCrearUsuari) {
		this.model = model;
		this.vista = vista;
		vistaPrincipal = vPrincipal;
		vistaCrearUsuari = vCrearUsuari;
		login();
		cerrarSessio();
		CrearUsuari();
		menuCrearUsuari();
		insertarTabla();
		crearTabla();
		salirUsuari();
		//guardarCsv();
	}

	/**
	 * Métode per iniciar sessió
	 */
	public void login() {
		actionListener_Login = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String usuariVista = vista.getTxtUsuari().getText();
				String contra = conversorChar(vista.getTxtContrasenya().getPassword());

				if (usuariVista.equals("") || contra.equals("")) {
					vista.mostrarMensaje("S'han detectat camps buits");
				} else {
					if (model.comprobarLogin(usuariVista, contra)) {
						vistaPrincipal.setVisible(true);
						vista.setVisible(false);
					}
				}
			}
		};
		vista.getLogin().addActionListener(actionListener_Login);
	}

	/**
	 * Métode per a tancar sessió (retorna al login)
	 */
	public void cerrarSessio() {

		actionListener_CerrarSessio = new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				if (e.getSource() == vistaPrincipal.getCerrarSesio()) {
					vistaPrincipal.setVisible(false);
					vista.setVisible(true);
					vistaPrincipal.limpiarCampos();
					vista.limpiarCampos();
					
				}
			}
		};
		vistaPrincipal.getCerrarSesio().addActionListener(actionListener_CerrarSessio);
	}

	
	/**
	 * mètode per a obrir la finestra de creació d'un usuari
	 */
	public void menuCrearUsuari() {
		actionListener_MenuCrearUsuari = new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String contra = conversorChar(vista.getTxtContrasenya().getPassword());

				if (model.comprobarAdmin(vista.getTxtUsuari().getText(), contra)) {
					if (e.getSource() == vistaPrincipal.getCrearUsuari()) {
						vistaCrearUsuari.setVisible(true);
						vistaCrearUsuari.limpiarCampos();
					}
				} else {
					vistaPrincipal.mostrarMensaje("No tens permisos d'administraor");
				}
			}
		};
		vistaPrincipal.getCrearUsuari().addActionListener(actionListener_MenuCrearUsuari);
	}

	
	/**
	 * Métode per a crear un usuari (verifica que no exista)
	 */
	public void CrearUsuari() {
		actionListener_CrearUsuari = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String usuari = vistaCrearUsuari.getUsuari().getText();
				String contrasenya = conversorChar(vistaCrearUsuari.getContrasenya().getPassword());
				String repetirContra = conversorChar(vistaCrearUsuari.getConfirmarContra().getPassword());

				if (usuari.equals("") || contrasenya.equals("") || repetirContra.equals("")) {
					vistaCrearUsuari.mostrarMensaje("S'han detectat camps buits");
				} else {
					if (model.comprobarContrasenya(contrasenya, repetirContra)) {
						if (!model.comprobarLogin(usuari, contrasenya)) {
							model.insertarUsuari(usuari, contrasenya);
							System.out.println(usuari + "/" + contrasenya);
							vistaCrearUsuari.mostrarMensaje("Usuari creat correctament");
							vistaCrearUsuari.setVisible(false);
						} else {
							vistaCrearUsuari.mostrarMensaje("Usuari ja existent");
						}
					} else {
						vistaCrearUsuari.mostrarMensaje("Les contrasenyes no coincidixen");
					}
				}
			}
		};
		vistaCrearUsuari.getRegistrar().addActionListener(actionListener_CrearUsuari);
	}

	/**
	 * Métode per a tancar el menú de creació d'usuari (retorna al menú principal)
	 */
	public void salirUsuari() {
		actionListener_SalirUsuari = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vistaCrearUsuari.setVisible(false);
				vistaCrearUsuari.limpiarCampos();
			}
		};
		vistaCrearUsuari.getCancelar().addActionListener(actionListener_SalirUsuari);
	}

	/**
	 * Métode que importa el fitxer CSV
	 */
	public void insertarTabla() {
		actionListener_InsertarDatos = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String ruta = "./datosTabla.csv";
				String contra = conversorChar(vista.getTxtContrasenya().getPassword());

				if (model.comprobarAdmin(vista.getTxtUsuari().getText(), contra)) {
					vistaPrincipal.setTxtrXmlData(model.crearTablaPopulation(ruta));

				} else {
					vistaPrincipal.mostrarMensaje("No tens permisos d'administraor");
				}
			}
		};
		vistaPrincipal.getImportData().addActionListener(actionListener_InsertarDatos);
	}

	/**
	 * Métode que crea la tabla per a mostrarla en vista
	 */
	public void crearTabla() {
		actionListener_CrearTabla = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("funciona");
				String contra = conversorChar(vista.getTxtContrasenya().getPassword());

				if (model.comprobarAdmin(vista.getTxtUsuari().getText(), contra)) {
					vistaPrincipal.modificarTabla(model.crearTabla(vistaPrincipal.gettxtfConsultas()));
				} else {
					if (vistaPrincipal.gettxtfConsultas().contains("population")) {
						vistaPrincipal.modificarTabla(model.crearTabla(vistaPrincipal.gettxtfConsultas()));
					} else {
						vistaPrincipal.mostrarMensaje(
								"Els usuaris nomes poden realitzar consultas sobre la taula population");
					}
				}
			}
		};
		vistaPrincipal.getConsultasSql().addActionListener(actionListener_CrearTabla);
	}

	/**
	 * Métode que guarda el CVS
	 */
//	public void guardarCsv() {
//		actionListener_GuardarCsv = new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				model.guardarCSV(model.crearTabla(vistaPrincipal.gettxtfConsultas()));
//			}
//		};
//		vistaPrincipal.getExportacioCSV().addActionListener(actionListener_GuardarCsv);
//	}

	/**
	 * Métode conversor de contrasenya a string
	 * @param contrasenya contrasenya en char
	 * @return contrasenya en string
	 */
	private String conversorChar(char[] contrasenya) {
		String contra = "";
		for (char c : contrasenya) {
			contra += c;
		}
		return contra;
	}
}