package bak85_SpotifyKnockoff;


import java.awt.Color;
import java.awt.EventQueue;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SpotifyGUI {

	private static JFrame frame;
	private JTextField txtSearch;
	private JRadioButton radShowAlbums;
	private JRadioButton radShowArtists;
	private JRadioButton radShowSongs;
	private JTable tblData;
	private DefaultTableModel musicData;

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
		
		frame = new JFrame("Spotify Knockoff");
		frame.setBounds(100, 100, 1000, 600);
		frame.getContentPane().setLayout(null);
		
		JLabel lblViewSelector = new JLabel("Select View");
		lblViewSelector.setBounds(20, 30, 99, 16);
		frame.getContentPane().add(lblViewSelector);
		
		radShowAlbums = new JRadioButton("Albums");
		radShowAlbums.setBounds(40, 60, 150, 25);
		radShowAlbums.setSelected(true);
		frame.getContentPane().add(radShowAlbums);
		radShowAlbums.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(radShowAlbums.isSelected()){
					musicData = getSongData("");
					tblData.setModel(musicData);
				}
			}
		});
		
		radShowArtists = new JRadioButton("Artists");
		radShowArtists.setBounds(40, 85, 150, 25);
		frame.getContentPane().add(radShowArtists);
		radShowArtists.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(radShowArtists.isSelected()){
					musicData = getSongData("");
					tblData.setModel(musicData);
				}
			}
		});
		
		radShowSongs = new JRadioButton("Songs");
		radShowSongs.setBounds(40, 110, 150, 25);
		frame.getContentPane().add(radShowSongs);
		radShowSongs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(radShowSongs.isSelected()){
					musicData = getSongData("");
					tblData.setModel(musicData);
					
				}
			}
		});
		
		ButtonGroup selectorGroup = new ButtonGroup(); //a button group allows for only one button to be clicked at a time
		selectorGroup.add(radShowAlbums);
		selectorGroup.add(radShowArtists);
		selectorGroup.add(radShowSongs);
		
		JLabel lblSearch = new JLabel("Search");
		lblSearch.setBounds(20, 290, 100, 20);
		frame.getContentPane().add(lblSearch);
		
		frame.getContentPane().add(lblViewSelector);
		txtSearch = new JTextField();
		txtSearch.setBounds(20, 315, 200, 30);
		frame.getContentPane().add(txtSearch);
		txtSearch.setColumns(10);
		
		musicData = getSongData("");
		tblData = new JTable(musicData);
		tblData.setBounds(299, 45, 600, 400);
		tblData.setFillsViewportHeight(true);
		tblData.setShowGrid(true);
		tblData.setGridColor(Color.BLACK);
		frame.getContentPane().add(tblData);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(radShowSongs.isSelected()){
					musicData = getSongData(txtSearch.getText());
					tblData.setModel(musicData);
				}
				if(radShowAlbums.isSelected()) {
					musicData = getSongData(txtSearch.getText());
					tblData.setModel(musicData);
				}
				if(radShowArtists.isSelected()) {
					musicData = getSongData(txtSearch.getText());
					tblData.setModel(musicData);
				}
			}
		});
		
		
		btnSearch.setBounds(103, 357, 117, 29);
		
		frame.getContentPane().add(btnSearch);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static JFrame getFrame () {
		return frame;
	}
	
	
	private DefaultTableModel getSongData(String searchTerm){
		
		DefaultTableModel result = Spotify.searchAlbums(searchTerm);
		
		if(radShowSongs.isSelected()) {
			result = Spotify.searchSongs(searchTerm);
			return result;
		}
		if(radShowAlbums.isSelected()) {
			result = Spotify.searchAlbums(searchTerm);
			return result;
		}
		if(radShowArtists.isSelected()) {
			result = Spotify.searchArtists(searchTerm);
			return result;
		}
		
		return null;
		
	}
	
	
}