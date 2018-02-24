package bak85_SpotifyKnockoff;

import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ArtistController {
	
	
	/**Used to create a new artist using JPA*/
	public Artist ArtistCreate(String firstName, String lastName, String bandName, String bio) {
		
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("bak85_SpotifyKnockoff");
		
		EntityManager emanager = emfactory.createEntityManager();
	
		emanager.getTransaction().begin();
	
		Artist a = new Artist();
		a.setArtistID(UUID.randomUUID().toString());
		a.setFirstName(firstName);
		a.setLastName(lastName);
		a.setBandName(bandName);
		a.setBio(bio);
	
		emanager.persist(a);
		emanager.getTransaction().commit();
	
		emanager.close();
		emfactory.close();
		
		return a;
		
	}
	
	/**Used to update information for an artist using JPA, current implementation updates the bio field*/
	public void ArtistUpdate(String ArtistID) {
		
		String chosenID = ArtistID;
		
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("bak85_SpotifyKnockoff");
		
		EntityManager emanager = emfactory.createEntityManager();
		
		emanager.getTransaction().begin();
		
		Artist a = emanager.find(Artist.class, chosenID);
		
		a.setBio("This bio was changed using JPA");
		
		emanager.persist(a);
		emanager.getTransaction().commit();
		
		emanager.close();
		emfactory.close();
		
	}
	
	/**Used to delete an artist using JPA*/
	public void ArtistDelete(String ArtistID) {
		
		String chosenID = ArtistID;
		
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("bak85_SpotifyKnockoff");
		
		EntityManager emanager = emfactory.createEntityManager();
		
		emanager.getTransaction().begin();
		
		Artist a = emanager.find(Artist.class, chosenID);
		emanager.remove(a);
		
		emanager.getTransaction().commit();
		
		emanager.close();
		emfactory.close();
		
	}

}
