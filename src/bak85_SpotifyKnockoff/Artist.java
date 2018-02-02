package bak85_SpotifyKnockoff;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Artist {

	private String artistID;
	private String firstName;
	private String lastName;
	private String bandName;
	private String bio; 
	
	
	/** Constructor used when adding a new artist to the database*/
	public Artist(String firstName, String lastName, String bandName, String bio) {
		
		this.artistID = UUID.randomUUID().toString();
		this.firstName = firstName;
		this.lastName = lastName;
		this.bandName = bandName;
		this.bio = bio;
		
		String sql = "INSERT INTO artist (artist_id, first_name, last_name, band_name, bio) ";
		sql += "VALUES (?, ?, ?, ?, ?);";
		
		System.out.println(sql);
		
		try {
			DbUtilities db = new DbUtilities();
			Connection conn = db.getConn();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, this.artistID);
			ps.setString(2,  this.firstName);
			ps.setString(3, this.lastName);
			ps.setString(4, this.bandName);
			ps.setString(5, this.bio);
			ps.executeUpdate();
			db.closeDbConnection();
			db = null;
			
		} catch (SQLException e) {
			ErrorLogger.log(e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	
	/** Constructor used to retrieve artist information, given an artistID*/
	public Artist (String artistID) {
		
		String sql = "SELECT * FROM artist WHERE artist_id = '" + artistID + "';";
		System.out.println(sql);
		
		DbUtilities db = new DbUtilities();
		
		try {
			ResultSet rs = db.getResultSet(sql);
			while(rs.next()){
				this.artistID = rs.getString("artist_id");
				System.out.println("Artist ID from database: " + this.artistID);
				this.firstName = rs.getString("first_name");
				this.lastName = rs.getString("last_name");
				this.bandName = rs.getString("band_name");
				this.bio = rs.getString("bio");
				System.out.println("Artist information retrieved from database: " + this.firstName + " " + this.lastName +  " " + this.bandName + " " + this.bio + "\n");
			}
		} catch (SQLException e) {
			ErrorLogger.log(e.getMessage());
			e.printStackTrace();
		}
				
	}
	
	
	/** Deletes an artist from the junction table to enable complete deletion
	 * using the artist ID.
	 * Calls the helper method deleteArtistCompletely() to complete the deletion.*/
	public void deleteArtist (String artistID) {
		
		this.artistID = artistID;
		
		String sql = "DELETE FROM song_artist WHERE fk_artist_id =  ?;";
		System.out.println(sql);
		
		try {
			DbUtilities db = new DbUtilities();
			Connection conn = db.getConn();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, artistID);
			ps.executeUpdate();
			db.closeDbConnection();
			db = null;
			System.out.println("The artist with ID: " + artistID + " was deleted from lookup table. ");
			
		} catch (SQLException e) {
			ErrorLogger.log(e.getMessage());
			e.printStackTrace();
		}
		
		deleteArtistCompletely(artistID);
		
	}
	
	
	/** A method called by deleteArtist to completely delete an artist  
	 * after its instances have been deleted from the junction table.
	 */
	private void deleteArtistCompletely(String artistID) {
		
		this.artistID = artistID;
		
		String sql = "DELETE FROM artist WHERE artist_id =  ?;";
		System.out.println(sql);
		
		try {
			DbUtilities db = new DbUtilities();
			Connection conn = db.getConn();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, artistID);
			ps.executeUpdate();
			db.closeDbConnection();
			db = null;
			System.out.println("The artist with ID: " + artistID + " was completely deleted. ");
			
		} catch (SQLException e) {
			ErrorLogger.log(e.getMessage());
			e.printStackTrace();
		}
		
		
	}
	
	public String getArtistID() {
		return artistID;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getBandName() {
		return bandName;
	}

	public String getBio() {
		return bio;
	}

}
