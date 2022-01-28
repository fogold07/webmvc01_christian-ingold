package fr.diginamic.webmvc01.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller de Home.
 * 
 * @author Christian Ingold
 *
 */
@Controller
@RequestMapping("/")
public class HomeController {

	/**
	 * Renvoi vers la page d'accueil.
	 * @param model
	 * @return
	 */
	@GetMapping
	public String home(Model model) {
		String dtitre = "Welcome ..";
		model.addAttribute("titre", dtitre);
		return "home";
	}
}
