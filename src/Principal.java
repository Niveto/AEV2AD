import Vistas.Vista;
import Vistas.VistaMenuPrincipal;
import Vistas.VistaCrearUsuari;

public class Principal {

	public static void main(String[] args) {

		Model m = new Model();
		Vista v = new Vista();
		VistaMenuPrincipal vPrincipal = new VistaMenuPrincipal();
		VistaCrearUsuari vCrearUsuari = new VistaCrearUsuari();
		Controlador c = new Controlador(m, v, vPrincipal, vCrearUsuari);
	}

}
