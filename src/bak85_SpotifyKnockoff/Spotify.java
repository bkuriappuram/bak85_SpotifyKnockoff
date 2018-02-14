package bak85_SpotifyKnockoff;

import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Spotify {
	
	
	/** A method for searching for songs in the Database
	 *  @param searchTerm - the string to be searched for, if this is an empty string, all songs will be displayed
	 * */
	public static DefaultTableModel searchSongs (String searchTerm) {
		
		String sql = "SELECT song_id, title, length, release_date, record_date FROM song ";
		
		if(!searchTerm.equals("")){
				sql += " WHERE title LIKE '%" + searchTerm + "%';";
		}
		
		try {
			DbUtilities db = new DbUtilities();
			String[] columnNames = {"Song ID", "Title", "Length", "Release Date", "Record Date"};
			return db.getDataTable(sql, columnNames);
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(SpotifyGUI.getFrame(), "Unable to connect to database");
			ErrorLogger.log(e.getMessage());
		}
		
		return null;	
		
	}
	
	/** A method for searching for artists in the Database
	 *  @param searchTerm - the string to be searched for, if this is an empty string, all artists will be displayed
	 * */
	public static DefaultTableModel searchArtists (String searchTerm) {
		
		String sql = "SELECT artist_id, first_name, last_name, band_name, bio FROM artist ";
		
		if(!searchTerm.equals("")){
				sql += " WHERE first_name LIKE '%" + searchTerm + "%' OR last_name LIKE '%" + searchTerm + "%' OR band_name LIKE '%" + searchTerm + "%';";
		}
		
		try {
			DbUtilities db = new DbUtilities();
			String[] columnNames = {"Song ID", "Title", "Length", "Release Date", "Record Date"};
			return db.getDataTable(sql, columnNames);
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(SpotifyGUI.getFrame(), "Unable to connect to database");
			ErrorLogger.log(e.getMessage());
		}
		
		return null;	
		
	}
	
	
	/** A method for searching for albums in the Database
	 *  @param searchTerm - the string to be searched for, if this is an empty string, all albums will be displayed
	 * */
	public static DefaultTableModel searchAlbums (String searchTerm) {
		
		String sql = "SELECT album_id, title, release_date, cover_image_path, recording_company_name, number_of_tracks, pmrc_rating, length FROM album ";
		
		if(!searchTerm.equals("")){
				sql += " WHERE title LIKE '%" + searchTerm + "%';";
		}
		
		try {
			DbUtilities db = new DbUtilities();
			String[] columnNames = {"Album ID", "Title", "Release Date", "Cover Image Path", "Recording Company", "Number of Tracks", "PMRC Rating", "Length"};
			return db.getDataTable(sql, columnNames);
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(SpotifyGUI.getFrame(), "Unable to connect to database");
			ErrorLogger.log(e.getMessage());
		}
		
		return null;	
		
	}

}
