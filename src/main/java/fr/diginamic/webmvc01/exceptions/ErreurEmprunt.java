package fr.diginamic.webmvc01.exceptions;

/**
 * Création d'une exception fonctionnelle pour gérer les erreurs sur les Emprunts
 * @author Christian Ingold
 *
 */
public class ErreurEmprunt extends Exception {

	public ErreurEmprunt() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ErreurEmprunt(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public ErreurEmprunt(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public ErreurEmprunt(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public ErreurEmprunt(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
