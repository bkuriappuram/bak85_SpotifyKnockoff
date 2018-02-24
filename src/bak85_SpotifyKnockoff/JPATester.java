package bak85_SpotifyKnockoff;

public class JPATester {

	public static void main(String[] args) {
		
		/*Create controllers*/
		SongController sc = new SongController();
		ArtistController ac = new ArtistController();
		AlbumController albController = new AlbumController();
		
		/*Create new song and test update*/
		Song Element = sc.SongCreate("ELEMENT", 3.28, "2017-01-01", "2017-04-14", "Var/Kendrick Lamar/Damn/Element.mp3");
		sc.SongUpdate(Element.getSongID());
		
		/*Create new artist and test update*/
		Artist Kendrick = ac.ArtistCreate("Kendrick", "Lamar", "", "Best Rapper Alive");
		ac.ArtistUpdate(Kendrick.getArtistID());
		
		/*Create new album and test update*/
		Album Damn = albController.AlbumCreate("Damn", "2017-04-14", "Var/AlbumArt/Damn/DamnCoverArt.JPG", "Interscope", 14, "Explicit", 54.54);
		albController.AlbumUpdate(Damn.getAlbumID());
		
		/*Test delete*/
		sc.SongDelete(Element.getSongID());
		ac.ArtistDelete(Kendrick.getArtistID());
		albController.AlbumDelete(Damn.getAlbumID());
		

	}

}
