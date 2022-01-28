package fr.diginamic.webmvc01.controllers.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import fr.diginamic.webmvc01.exceptions.ErreurClient;
import fr.diginamic.webmvc01.exceptions.ErreurEmprunt;
import fr.diginamic.webmvc01.exceptions.ErreurLivre;

/**
 * Controleur général qui catch les exceptions de mon serveur API REST, grace à
 * l'annotation @RestControllerAdvice
 *
 */
@RestControllerAdvice
public class ErrorController {

	@ExceptionHandler(value = { Exception.class })
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public String errorGeneralException(Exception e) {
		String message = "Il y a une erreur : " + e.getMessage();

		return message;
	}

	/**
	 * Erreur liée à un client non trouvé.
	 * @param e
	 * @return
	 */
	@ExceptionHandler(value = { ErreurClient.class })
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public String errorClientException(ErreurClient e) {
		String message = "Erreur liée au Client : " + e.getMessage();

		return message;
	}
	
	/**
	 * Erreur liée à un livre non trouvé.
	 * @param e
	 * @return
	 */
	@ExceptionHandler(value = { ErreurLivre.class })
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public String errorLivreException(ErreurLivre e) {
		String message = "Erreur liée au Livre : " + e.getMessage();

		return message;
	}
	
	/**
	 * Erreur liée à un emprunt non trouvé.
	 * @param e
	 * @return
	 */
	@ExceptionHandler(value = { ErreurEmprunt.class })
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public String errorEmpruntException(ErreurEmprunt e) {
		String message = "Erreur liée à l'emprunt : " + e.getMessage();

		return message;
	}
}
