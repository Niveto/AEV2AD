package Vistas;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Vista extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtUsuari;
	private JLabel lblDrets;
	private JPasswordField pswContrasenya;
	private JFrame jfrMissatje;
	private JButton btnLogin;
	
	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					Vista frame = new Vista();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public Vista() {
		setTitle("AEV2");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 715, 280);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblLogin = new JLabel("Login");
		lblLogin.setBounds(298, 20, 83, 37);
		lblLogin.setFont(new Font("Tahoma", Font.BOLD, 30));
		contentPane.add(lblLogin);
		
		JLabel lblUsuari = new JLabel("Usuari:");
		lblUsuari.setBounds(228, 88, 71, 25);
		lblUsuari.setFont(new Font("Tahoma", Font.PLAIN, 20));
		contentPane.add(lblUsuari);
		
		JLabel lblContrasenya = new JLabel("Contrasenya:");
		lblContrasenya.setBounds(173, 113, 127, 25);
		lblContrasenya.setFont(new Font("Tahoma", Font.PLAIN, 20));
		contentPane.add(lblContrasenya);
		
		txtUsuari = new JTextField();
		txtUsuari.setBounds(309, 95, 145, 19);
		contentPane.add(txtUsuari);
		txtUsuari.setColumns(10);
		
		lblDrets = new JLabel("Fet per Javi Armero i Nico Vega");
		lblDrets.setFont(new Font("Tahoma", Font.ITALIC, 12));
		lblDrets.setBounds(523, 220, 178, 23);
		contentPane.add(lblDrets);
		
		pswContrasenya = new JPasswordField();
		pswContrasenya.setBounds(309, 120, 145, 19);
		contentPane.add(pswContrasenya);
		
		JButton btnEsborrar = new JButton("Esborrar");
		btnEsborrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpiarCampos();
			}
		});
		btnEsborrar.setBounds(354, 141, 100, 21);
		contentPane.add(btnEsborrar);
		
		btnLogin = new JButton("Login");
		btnLogin.setBounds(354, 165, 100, 21);
		contentPane.add(btnLogin);
		
		this.setVisible(true);
	}
	
	public JTextField getTxtUsuari() {
		return txtUsuari;
	}
	public JPasswordField getTxtContrasenya() {
		return pswContrasenya;
	}
	public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Mensaje", JOptionPane.INFORMATION_MESSAGE);
    }
	public JButton getLogin() {
		return btnLogin;
	}
	
	public void limpiarCampos() {
	    txtUsuari.setText("");
	    pswContrasenya.setText("");
	}
	
}
