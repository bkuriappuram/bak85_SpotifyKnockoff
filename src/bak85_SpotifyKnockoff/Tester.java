package bak85_SpotifyKnockoff;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Map;

public class Tester {
	
	public static void main(String[] args){
		
		System.out.println("Populating database... \n");
		
		Song DNA = new Song("DNA", 3.06, "2017-04-14", "2016-03-10"); //create new song 
		Song Humble = new Song("Humble", 2.57, "2017-03-30", "2016-03-05");
		DNA.setFilePath("Var/Kendrick Lamar/Damn/DNA.mp3"); //test setFilePath
		Artist Kendrick_Lamar = new Artist("Kendrick", "Lamar","", "Best Rapper Alive"); //create new artist
		DNA.addArtist(Kendrick_Lamar); //add artist to song
		Humble.addArtist(Kendrick_Lamar);
		Album Damn = new Album("Damn", "2017-04-14", "Path", "Interscope", 14, "Explicit", 54.54); //create new album
		Damn.setCoverImagePath("Var/AlbumArt/Damn/DamnCoverArt.JPG"); //test setCoverImagePath
		Damn.addSong(DNA); //add song to album
		Damn.addSong(Humble);
		
		System.out.println("\nRetrieving song info...\n");
		
		Song dnaInfo = new Song(DNA.getSongID()); //uses SongID, ArtistID, and AlbumID to retrieve information
		Song humbleInfo = new Song(Humble.getSongID());
		Artist kendrickInfo = new Artist(Kendrick_Lamar.getArtistID());
		Album damnInfo = new Album(Damn.getAlbumID());
		
		System.out.println("\nTesting delete artist from song... \n");
		
		DNA.deleteArtist(Kendrick_Lamar); //deletes artist from song by artist object
		Humble.deleteArtist(Kendrick_Lamar.getArtistID()); //deletes artist from song by ArtistID
		
		System.out.println("\nTesting delete song... \n");
		
		DNA.deleteSong(DNA.getSongID()); //deletes song from database by SongID
		
		System.out.println("\nTesting delete artist... \n");
		
		Kendrick_Lamar.deleteArtist(Kendrick_Lamar.getArtistID()); //deletes artist from database by ArtistID
		
		System.out.println("\nTesting delete song from album... \n");
		
		Damn.deleteSong(DNA); //deletes a song from an album using the song object
		Damn.deleteSong(Humble.getSongID()); //deletes a song from an album by SongID
		
		System.out.println("\nTesting delete album... \n");
		
		Damn.deleteAlbum(Damn.getAlbumID()); //deletes an album from the database by AlbumID
		
	}
}