package fr.diginamic.webmvc01.exceptions;

/**
 * Création d'une exception fonctionnelle pour gérer les erreurs sur les Livres
 * @author Christian Ingold
 *
 */
public class ErreurLivre extends Exception {

	public ErreurLivre() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ErreurLivre(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public ErreurLivre(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public ErreurLivre(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public ErreurLivre(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
