package bak85_SpotifyKnockoff;

import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class SongController {
	
	
		/**Used to create a new song using JPA*/
		public Song SongCreate(String title, double length, String recordDate, String releaseDate, String filePath) {
		
			EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("bak85_SpotifyKnockoff");
		
			EntityManager emanager = emfactory.createEntityManager();
		
			emanager.getTransaction().begin();
		
			Song s = new Song();
			s.setSongID(UUID.randomUUID().toString());
			s.setTitle(title);
			s.setLength(length);
			s.setRecordDate(recordDate);
			s.setReleaseDate(releaseDate);
			s.setFilePath(filePath);
		
			emanager.persist(s);
			emanager.getTransaction().commit();
		
			emanager.close();
			emfactory.close();
			
			return s;
		
		}
		
		
		/**Used to update information for a song using JPA, current implementation updates the file path field*/
		public void SongUpdate(String SongID) {
			
			String chosenID = SongID;
			
			EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("bak85_SpotifyKnockoff");
			
			EntityManager emanager = emfactory.createEntityManager();
			
			emanager.getTransaction().begin();
			
			Song s = emanager.find(Song.class, chosenID);
			
			s.setFilePath("This file path was changed using JPA");
			
			emanager.persist(s);
			emanager.getTransaction().commit();
			
			emanager.close();
			emfactory.close();
			
		}
		
		
		/**Used to delete a song using JPA */
		public void SongDelete(String SongID) {
			
			String chosenID = SongID;
			
			EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("bak85_SpotifyKnockoff");
			
			EntityManager emanager = emfactory.createEntityManager();
			
			emanager.getTransaction().begin();
			
			Song s = emanager.find(Song.class, chosenID);
			emanager.remove(s);
			
			emanager.getTransaction().commit();
			
			emanager.close();
			emfactory.close();
			
		}

}
