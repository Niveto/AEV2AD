package Vistas;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.FlowLayout;
import javax.swing.JTextField;
import net.miginfocom.swing.MigLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.JPasswordField;

public class VistaCrearUsuari extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtUsuari;
	private JButton btnRegistrar;
	private JPasswordField pswContrasenya;
	private JPasswordField pswConfirmarContra;
	private JButton btnCancelar;

	public VistaCrearUsuari() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 419, 413);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		
		JLabel lblRegistrarUsuari = new JLabel("Registrar Usuari");
		lblRegistrarUsuari.setBounds(110, 27, 214, 25);
		lblRegistrarUsuari.setFont(new Font("Tahoma", Font.BOLD, 20));
		
		txtUsuari = new JTextField();
		txtUsuari.setBounds(215, 82, 150, 19);
		txtUsuari.setColumns(10);
		
		JLabel lblUsuari = new JLabel("Usuari:");
		lblUsuari.setBounds(152, 83, 45, 13);
		lblUsuari.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JLabel lblContrasenya = new JLabel("Contrasenya:");
		lblContrasenya.setBounds(110, 111, 87, 25);
		lblContrasenya.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JLabel lblConfirmarContra = new JLabel("Confirmar contrasenya:");
		lblConfirmarContra.setBounds(51, 142, 146, 33);
		lblConfirmarContra.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		btnRegistrar = new JButton("Registrar");
		btnRegistrar.setBounds(278, 216, 87, 21);
		contentPane.setLayout(null);
		contentPane.add(lblRegistrarUsuari);
		contentPane.add(txtUsuari);
		contentPane.add(lblUsuari);
		contentPane.add(lblContrasenya);
		contentPane.add(lblConfirmarContra);
		contentPane.add(btnRegistrar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(278, 190, 87, 21);
		contentPane.add(btnCancelar);
		
		pswContrasenya = new JPasswordField();
		pswContrasenya.setBounds(215, 115, 150, 20);
		contentPane.add(pswContrasenya);
		
		pswConfirmarContra = new JPasswordField();
		pswConfirmarContra.setBounds(215, 150, 150, 20);
		contentPane.add(pswConfirmarContra);
	}
	
	public JTextField getUsuari() {
		return txtUsuari;
	}
	
	public JPasswordField getContrasenya() {
        return pswContrasenya;
    }

    public JPasswordField getConfirmarContra() {
        return pswConfirmarContra;
    }
	
	public JButton getRegistrar() {
		return btnRegistrar;
	}
	public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Mensaje", JOptionPane.INFORMATION_MESSAGE);
    }
	public void limpiarCampos() {
	    txtUsuari.setText("");
	    pswContrasenya.setText("");
	    pswConfirmarContra.setText("");
	}
	public JButton getCancelar() {
		return btnCancelar;
	}
}
