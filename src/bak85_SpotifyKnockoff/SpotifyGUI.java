package bak85_SpotifyKnockoff;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JTable;

public class SpotifyGUI {

	private JFrame frame;
	private JTextField textField;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SpotifyGUI window = new SpotifyGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SpotifyGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Spotify");
		frame.setBounds(100, 100, 1000, 600);
		frame.getContentPane().setLayout(null);
		
		JLabel lblViewSelector = new JLabel("Select View");
		lblViewSelector.setBounds(12, 30, 79, 16);
		frame.getContentPane().add(lblViewSelector);
		
		JRadioButton radShowAlbums = new JRadioButton("Albums");
		radShowAlbums.setBounds(37, 55, 127, 25);
		frame.getContentPane().add(radShowAlbums);
		
		textField = new JTextField();
		textField.setBounds(27, 336, 116, 22);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		table = new JTable();
		table.setBounds(325, 45, 600, 400);
		frame.getContentPane().add(table);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
