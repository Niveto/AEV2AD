package Vistas;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Color;
import javax.swing.UIManager;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;

public class VistaMenuPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtfConsultas;
	private JButton btnRegistrarUsuari;
	private JButton btnCerrarSesio;
	private JButton btnImportData;
	private JEditorPane tablaHtml;
	private JButton btnConsultaSql;
	private JTextArea txtrXmlData;
	private JButton btnExportacioCSV;
	/**
	 * Launch the application.
	 */
	/*
	 * public static void main(String[] args) { EventQueue.invokeLater(new
	 * Runnable() { public void run() { try { VistaMenuPrincipal frame = new
	 * VistaMenuPrincipal(); frame.setVisible(true); } catch (Exception e) {
	 * e.printStackTrace(); } } }); }
	 * 
	 * /** Create the frame.
	 */
	public VistaMenuPrincipal() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 731, 645);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.window);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		btnRegistrarUsuari = new JButton("Registar nou usuari");
		btnRegistrarUsuari.setBackground(SystemColor.activeCaption);
		btnRegistrarUsuari.setForeground(SystemColor.desktop);
		btnRegistrarUsuari.setFont(new Font("Times New Roman", Font.BOLD, 12));
		btnRegistrarUsuari.setBounds(10, 30, 144, 45);
		contentPane.add(btnRegistrarUsuari);

		btnImportData = new JButton("Import data");
		btnImportData.setForeground(SystemColor.desktop);
		btnImportData.setFont(new Font("Times New Roman", Font.BOLD, 12));
		btnImportData.setBackground(SystemColor.activeCaption);
		btnImportData.setBounds(10, 86, 144, 45);
		contentPane.add(btnImportData);

		btnCerrarSesio = new JButton("Cerrar sesio");
		btnCerrarSesio.setForeground(SystemColor.desktop);
		btnCerrarSesio.setFont(new Font("Times New Roman", Font.BOLD, 12));
		btnCerrarSesio.setBackground(SystemColor.activeCaption);
		btnCerrarSesio.setBounds(10, 142, 144, 45);
		contentPane.add(btnCerrarSesio);

		txtrXmlData = new JTextArea();
		txtrXmlData.setEditable(false);
		txtrXmlData.setText("XML DATA...");
		txtrXmlData.setBounds(186, 30, 517, 157);

		
		JScrollPane scrollPaneXml = new JScrollPane(txtrXmlData);
		scrollPaneXml.setBounds(186, 30, 517, 157);
		contentPane.add(scrollPaneXml);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(112, 128, 144));
		panel.setBounds(10, 208, 693, 10);
		contentPane.add(panel);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(112, 128, 144));
		panel_1.setBounds(10, 227, 693, 4);
		contentPane.add(panel_1);

		txtfConsultas = new JTextField();
		txtfConsultas.setBounds(10, 242, 528, 45);
		contentPane.add(txtfConsultas);
		txtfConsultas.setColumns(10);

		btnConsultaSql = new JButton("Consulta SQL");
		btnConsultaSql.setForeground(SystemColor.desktop);
		btnConsultaSql.setFont(new Font("Times New Roman", Font.BOLD, 12));
		btnConsultaSql.setBackground(SystemColor.activeCaption);
		btnConsultaSql.setBounds(559, 242, 144, 45);
		contentPane.add(btnConsultaSql);

		tablaHtml = new JEditorPane();
		tablaHtml.setEditable(false);
		tablaHtml.setContentType("text/html");
		tablaHtml.setBounds(10, 298, 693, 224);

		JScrollPane scrollPaneConsulta = new JScrollPane(tablaHtml);
		scrollPaneConsulta.setBounds(10, 298, 693, 224);
		contentPane.add(scrollPaneConsulta);

		btnExportacioCSV = new JButton("Exportació CSV");
		btnExportacioCSV.setForeground(SystemColor.desktop);
		btnExportacioCSV.setFont(new Font("Times New Roman", Font.BOLD, 12));
		btnExportacioCSV.setBackground(SystemColor.activeCaption);
		btnExportacioCSV.setBounds(10, 533, 693, 45);
		contentPane.add(btnExportacioCSV);

	}

	public void limpiarCampos() {
		txtfConsultas.setText("");
		tablaHtml.setText("");
		txtrXmlData.setText("");
	}
	
	public void mostrarMensaje(String mensaje) {
		JOptionPane.showMessageDialog(this, mensaje, "Mensaje", JOptionPane.INFORMATION_MESSAGE);
	}

	public void modificarTabla(String contenido) {
		tablaHtml.setText(contenido);
	}
	
	public void setTxtrXmlData(String contenido) {
		txtrXmlData.setText(contenido);
	}

	public JButton getCrearUsuari() {
		return btnRegistrarUsuari;
	}

	public JButton getImportData() {
		return btnImportData;
	}

	public JButton getCerrarSesio() {
		return btnCerrarSesio;
	}

	public String gettxtfConsultas() {
		return txtfConsultas.getText();
	}

	public JButton getConsultasSql() {
		return btnConsultaSql;
	}
	
	public JButton getExportacioCSV() {
		return btnExportacioCSV;
	}
}
