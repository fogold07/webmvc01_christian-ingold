package fr.diginamic.webmvc01.exceptions;

/**
 * Création d'une exception fonctionnelle pour gérer les erreurs sur les Clients
 * @author Christian Ingold
 *
 */
public class ErreurClient extends Exception {

	public ErreurClient() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ErreurClient(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public ErreurClient(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public ErreurClient(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public ErreurClient(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}



}
