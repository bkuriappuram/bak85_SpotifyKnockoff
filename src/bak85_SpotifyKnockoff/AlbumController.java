package bak85_SpotifyKnockoff;

import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class AlbumController {
	
	
	/**Used to create an album using JPA*/
	public Album AlbumCreate(String title, String releaseDate, String CoverImagePath, String recordingCompany, 
			int numberOfTracks, String pmrcRating, double length) {
		
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("bak85_SpotifyKnockoff");
		
		EntityManager emanager = emfactory.createEntityManager();
	
		emanager.getTransaction().begin();
	
		Album a = new Album();
		a.setAlbumID(UUID.randomUUID().toString());
		a.setTitle(title);
		a.setReleaseDate(releaseDate);
		a.setCoverImagePath(CoverImagePath);
		a.setRecordingCompany(recordingCompany);
		a.setNumberOfTracks(14);
		a.setPmrcRating(pmrcRating);
		a.setLength(54.54);
		
		emanager.persist(a);
		emanager.getTransaction().commit();
	
		emanager.close();
		emfactory.close();
		
		return a;
		
	}
	
	
	/**Used to update information for an album using JPA, current implementation updates the cover
	 *  image path field*/
	public void AlbumUpdate(String AlbumID) {
		
		String chosenID = AlbumID;
		
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("bak85_SpotifyKnockoff");
		
		EntityManager emanager = emfactory.createEntityManager();
		
		emanager.getTransaction().begin();
		
		Album a = emanager.find(Album.class, chosenID);
		
		a.setCoverImagePath("This cover image path was changed with JPA");
		
		emanager.persist(a);
		emanager.getTransaction().commit();
		
		emanager.close();
		emfactory.close();
		
	}
	
	
	/**Used to delete an album using JPA*/
	public void AlbumDelete(String AlbumID) {
		
		String chosenID = AlbumID;
		
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("bak85_SpotifyKnockoff");
		
		EntityManager emanager = emfactory.createEntityManager();
		
		emanager.getTransaction().begin();
		
		Album a = emanager.find(Album.class, chosenID);
		emanager.remove(a);
		
		emanager.getTransaction().commit();
		
		emanager.close();
		emfactory.close();
	}
	
	


}
