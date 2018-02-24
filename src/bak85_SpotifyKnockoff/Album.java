package bak85_SpotifyKnockoff;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Map;
import java.util.UUID;
import javax.persistence.*;


@Entity
@Table (name = "album")
public class Album {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	
	@Column (name = "album_id")
	private String albumID;
	
	@Column (name = "title")
	private String title;
	
	@Column (name = "release_date")
	private String releaseDate;
	
	@Column (name = "cover_image_path")
	private String coverImagePath;
	
	@Column (name = "recording_company_name")
	private String recordingCompany;
	
	@Column (name = "number_of_tracks")
	private int numberOfTracks;
	
	@Column (name = "PMRC_rating")
	private String pmrcRating;
	
	@Column (name = "length")
	private double length;
	
	@Transient
	Map <String, Song> albumSongs;
	
	public Album() {
		super();
	}
	
	
	/**Constructor used when creating a new Album in the database*/
	public Album (String title, String releaseDate, String coverImagePath, String recordingCompany, int numberOfTracks, String pmrcRating, double length) {
		
		this.title = title;
		this.releaseDate = releaseDate;
		this.coverImagePath = coverImagePath;
		this.recordingCompany = recordingCompany;
		this.numberOfTracks = numberOfTracks;
		this.pmrcRating = pmrcRating;
		this.length = length; 
		this.albumID = UUID.randomUUID().toString();
		
		albumSongs = new Hashtable <String, Song>();
		
		String sql = "INSERT INTO album (album_id, title, release_date, cover_image_path, recording_company_name, number_of_tracks, PMRC_rating, length ) ";
		sql += "VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
		
		System.out.println(sql);
		
		try {
			DbUtilities db = new DbUtilities();
			Connection conn = db.getConn();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, this.albumID);
			ps.setString(2,  this.title);
			ps.setString(3, this.releaseDate);
			ps.setString(4, this.coverImagePath);
			ps.setString(5, this.recordingCompany);
			ps.setInt(6, this.numberOfTracks);
			ps.setString(7,this.pmrcRating);
			ps.setDouble(8, this.length);
			ps.executeUpdate();
			db.closeDbConnection();
			db = null;
		} catch (SQLException e) {
			ErrorLogger.log(e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	
	/**Constructor used to retrieve album information, given an ArtistID*/
	public Album(String albumID) {
		
		String sql = "SELECT * FROM album WHERE album_id = '" + albumID + "';";
		System.out.println(sql);
		
		albumSongs = new Hashtable<String, Song>();
		
		DbUtilities db = new DbUtilities();
		
		try {
			ResultSet rs = db.getResultSet(sql);
			while(rs.next()){
				this.albumID = rs.getString("album_id");
				System.out.println("Album ID from database: " + this.albumID);
				this.title = rs.getString("title");
				this.releaseDate = rs.getDate("release_date").toString();
				this.coverImagePath = rs.getString("cover_image_path").toString();
				this.recordingCompany = rs.getString("recording_company_name");
				this.numberOfTracks = rs.getInt("number_of_tracks");
				this.pmrcRating = rs.getString("PMRC_rating");
				this.length = rs.getDouble("length");
				System.out.println("Album information retrieved from database: " + this.title + " " + this.releaseDate + " "
						+ this.coverImagePath + " " + this.recordingCompany + " " + this.numberOfTracks + " " + this.numberOfTracks
						+ " " + this.length + "\n");
			}
		} catch (SQLException e) {
			ErrorLogger.log(e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	/**Deletes an album from the junction table to enable complete deletion.
	 * Calls helper method deleteAlbumCompletely() to complete the deletion.
	 */
	public void deleteAlbum (String albumID) {
		
		this.albumID = albumID;
		
		String sql = "DELETE FROM album_song WHERE fk_album_id =  ?;";
		System.out.println(sql);
		
		try {
			DbUtilities db = new DbUtilities();
			Connection conn = db.getConn();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, albumID);
			ps.executeUpdate();
			db.closeDbConnection();
			db = null;
			System.out.println("The album with ID: " + albumID + " was deleted from lookup table. ");
			
			
		} catch (SQLException e) {
			ErrorLogger.log(e.getMessage());
			e.printStackTrace();
		}
		
		deleteAlbumCompletely(albumID);
		
	}
	
	
	/**Method called by deleteAlbum() to complete the deletion of
	 * an album, given an albumID */
	private void deleteAlbumCompletely(String albumID) {
		
		this.albumID = albumID;
		
		String sql = "DELETE FROM album WHERE album_id =  ?;";
		System.out.println(sql);
		
		try {
			DbUtilities db = new DbUtilities();
			Connection conn = db.getConn();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, albumID);
			ps.executeUpdate();
			db.closeDbConnection();
			db = null;
			System.out.println("The album with ID: " + albumID + " was completely deleted. ");
			albumID = null;
			
		} catch (SQLException e) {
			ErrorLogger.log(e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	
	/**Method used to add a song to an album using the song object*/
	public void addSong(Song song) {
		
		albumSongs.put(song.getSongID(), song);
		String sql = "INSERT INTO album_song (fk_album_id, fk_song_id) VALUES (?, ?);";
		
		try {
			DbUtilities db = new DbUtilities();
			Connection conn = db.getConn();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, this.albumID);
			ps.setString(2, song.getSongID());
			ps.executeUpdate();
			conn.close();
			db = null;
		} catch (SQLException e) {
			ErrorLogger.log(e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	
	/**Method used to delete a song from album using the songID*/
	public void deleteSong(String songID) {
		
		String deletedSongID = songID;
		String sql = "DELETE FROM album_song WHERE fk_song_id = ?;";

		try {
			DbUtilities db = new DbUtilities();
			Connection conn = db.getConn();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, songID);
			ps.executeUpdate();
			conn.close();
			db = null;
			System.out.println("Song with ID: " + deletedSongID + " was deleted from the album." );
			songID = null;
		} catch (SQLException e) {
			ErrorLogger.log(e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	
	/**Method used to delete song from an album using the song object*/
	public void deleteSong(Song song) {
		
		albumSongs.remove(song.getSongID(), song);
		String sql = "DELETE FROM album_song WHERE fk_song_id = ?;";
		String songTitle = song.getTitle();
		
		try {
			DbUtilities db = new DbUtilities();
			Connection conn = db.getConn();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, song.getSongID());
			ps.executeUpdate();
			conn.close();
			db = null;
			System.out.println(songTitle + " was removed from the album.");
			song = null;
			
		} catch (SQLException e) {
			ErrorLogger.log(e.getMessage());
			e.printStackTrace();
		}

	}
	
	
	/**Method that allows for the path to the cover image to be set after instantiation*/
	public void setCoverImagePath(String coverImagePath) {
		
		this.coverImagePath = coverImagePath;
		
		String sql = "UPDATE album SET cover_image_path = ? WHERE album_ID = ?;";
		try {
			DbUtilities db = new DbUtilities();
			Connection conn = db.getConn();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, coverImagePath);
			ps.setString(2, this.albumID);
			ps.executeUpdate();
			conn.close();
			db = null;
		} catch (SQLException e) {
			ErrorLogger.log(e.getMessage());
			e.printStackTrace();
		}
	}

	public Map<String, Song> getAlbumSongs() {
		return albumSongs;
	}

	public String getAlbumID() {
		return albumID;
	}

	public String getTitle() {
		return title;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public String getCoverImagePath() {
		return coverImagePath;
	}

	public String getRecordingCompany() {
		return recordingCompany;
	}

	public int getNumberOfTracks() {
		return numberOfTracks;
	}

	public String getPmrcRating() {
		return pmrcRating;
	}

	public double getLength() {
		return length;
	}


	public void setAlbumID(String albumID) {
		this.albumID = albumID;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}


	public void setRecordingCompany(String recordingCompany) {
		this.recordingCompany = recordingCompany;
	}


	public void setNumberOfTracks(int numberOfTracks) {
		this.numberOfTracks = numberOfTracks;
	}


	public void setPmrcRating(String pmrcRating) {
		this.pmrcRating = pmrcRating;
	}


	public void setLength(double length) {
		this.length = length;
	}


	public void setAlbumSongs(Map<String, Song> albumSongs) {
		this.albumSongs = albumSongs;
	}
	
	

}
