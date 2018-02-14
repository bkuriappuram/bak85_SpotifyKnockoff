package bak85_SpotifyKnockoff;

import java.util.Map;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Song {
	
	private String songID;
	private String title;
	private double length;
	private String filePath;
	private String releaseDate;
	private String recordDate;
	
	Map<String, Artist> songArtists;
	
	
	
	/** Constructor used to create new entries to the database*/
	public Song(String title, double length, String releaseDate, String recordDate){
		
		this.title = title;
		this.length = length;
		this.releaseDate = releaseDate;
		this.recordDate = recordDate;
		this.songID = UUID.randomUUID().toString();
		
		songArtists = new Hashtable<String, Artist>();
		
		String sql = "INSERT INTO song (song_id,title,length,file_path,release_date,record_date) ";
		sql += "VALUES (?, ?, ?, ?, ?, ?);";
		
		System.out.println(sql);
		
		try {
			DbUtilities db = new DbUtilities();
			Connection conn = db.getConn();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, this.songID);
			ps.setString(2,  this.title);
			ps.setDouble(3, this.length);
			ps.setString(4, "");
			ps.setString(5, this.releaseDate);
			ps.setString(6, this.recordDate);
			ps.executeUpdate();
			db.closeDbConnection();
			db = null;
		} catch (SQLException e) {
			ErrorLogger.log(e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	
	/**Constructor used to retrieve song information, given a songID*/
	public Song(String songID){
		
		String sql = "SELECT * FROM song WHERE song_id = '" + songID + "';";
		System.out.println(sql);
		
		songArtists = new Hashtable<String, Artist>();
		
		DbUtilities db = new DbUtilities();
		try {
			ResultSet rs = db.getResultSet(sql);
			while(rs.next()){
				this.songID = rs.getString("song_id");
				System.out.println("Song ID from database: " + this.songID);
				this.title = rs.getString("title");
				this.releaseDate = rs.getDate("release_date").toString();
				this.recordDate = rs.getDate("record_date").toString();
				this.length = rs.getDouble("length");
				System.out.println("Song information retrieved from database: " + this.title + " " + this.releaseDate + " "
						 + this.recordDate + " " + this.length + "\n");
			}
		} catch (SQLException e) {
			ErrorLogger.log(e.getMessage());
			e.printStackTrace();
		}
				
	}
	
	public Song(String songID, String title, double length, String releaseDate, String recordDate){
		
		this.title = title;
		this.length = length;
		this.releaseDate = releaseDate;
		this.recordDate = recordDate;
		this.songID = songID;
		
		songArtists = new Hashtable<String, Artist>();
	}
	
	
	/** Deletes a song from the junction table using SongID to enable complete deletion.
	 * Calls the helper method deleteSongCompletely() to remove 
	 * the song completely*/
	public void deleteSong (String songID) {
		
		this.songID = songID;
		
		String sql = "DELETE FROM song_artist WHERE fk_song_id =  ?;";
		System.out.println(sql);
		
		try {
			DbUtilities db = new DbUtilities();
			Connection conn = db.getConn();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, songID);
			ps.executeUpdate();
			db.closeDbConnection();
			db = null;
			System.out.println("Song with ID: " + songID + " was deleted from lookup table. ");
			
		} catch (SQLException e) {
			ErrorLogger.log(e.getMessage());
			e.printStackTrace();
		}
		
		deleteSongCompletely(songID);
		
	}
	
	
	/**Called by the deleteSong() method to completely delete a song
	 * from the database*/
	private void deleteSongCompletely(String songID) {
		
		this.songID = songID;
		
		String sql = "DELETE FROM song WHERE song_id =  ?;";
		System.out.println(sql);
		
		try {
			DbUtilities db = new DbUtilities();
			Connection conn = db.getConn();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, songID);
			ps.executeUpdate();
			db.closeDbConnection();
			db = null;
			System.out.println("Song with ID :" + songID + " was completely deleted. ");
			
		} catch (SQLException e) {
			ErrorLogger.log(e.getMessage());
			e.printStackTrace();
		} 
		
		
	}
	
	
	/**Adds an artist to the song using the artist object
	 */
	public void addArtist(Artist artist) {
		
		songArtists.put(artist.getArtistID(), artist);
		String sql = "INSERT INTO song_artist (fk_song_id, fk_artist_id) VALUES (?, ?);";
		
		try {
			DbUtilities db = new DbUtilities();
			Connection conn = db.getConn();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, this.songID);
			ps.setString(2, artist.getArtistID());
			ps.executeUpdate();
			conn.close();
			db = null;
		} catch (SQLException e) {
			ErrorLogger.log(e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	
	/** Deletes an artist from the song using the artistID
	 */
	public void deleteArtist(String artistID){
		
		String sql = "DELETE FROM song_artist WHERE fk_artist_id = ?;";
		String deletedArtistID = artistID;

		try {
			DbUtilities db = new DbUtilities();
			Connection conn = db.getConn();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, artistID);
			ps.executeUpdate();
			conn.close();
			db = null;
			System.out.println("The artist with ID: " + deletedArtistID + " was removed from the song.");
		} catch (SQLException e) {
			ErrorLogger.log(e.getMessage());
			e.printStackTrace();
		}
	
	}
	
	
	/** Deletes an artist from the song using the artist object*/
	public void deleteArtist(Artist artist){
		
		songArtists.remove(artist.getArtistID(), artist);
		String sql = "DELETE FROM song_artist WHERE fk_artist_id = ?;";
		String artistName = artist.getFirstName() + " " + artist.getLastName();
		
		try {
			DbUtilities db = new DbUtilities();
			Connection conn = db.getConn();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, artist.getArtistID());
			ps.executeUpdate();
			conn.close();
			db = null;
			System.out.println(artistName + " was removed from the song.");
		} catch (SQLException e) {
			ErrorLogger.log(e.getMessage());
			e.printStackTrace();
		}
		
	}

	
	/** Allows for the file path for a particular song to be changed after instantiation */
	public void setFilePath(String filePath) {
		
		this.filePath = filePath;
		
		String sql = "UPDATE song SET file_path = ? WHERE song_ID = ?;";
		try {
			DbUtilities db = new DbUtilities();
			Connection conn = db.getConn();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, filePath);
			ps.setString(2, this.songID);
			ps.executeUpdate();
			conn.close();
			db = null;
		} catch (SQLException e) {
			ErrorLogger.log(e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	public Map<String, Artist> getSongArtists() {
		return songArtists;
	}
	
	public String getReleaseDate() {
		return releaseDate;
	}

	public String getSongID() {
		return songID;
	}

	public String getTitle() {
		return title;
	}

	public double getLength() {
		return length;
	}

	public String getFilePath() {
		return filePath;
	}

	public String getRecordDate() {
		return recordDate;
	}

}
