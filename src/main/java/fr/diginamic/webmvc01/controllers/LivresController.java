package fr.diginamic.webmvc01.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.diginamic.webmvc01.entities.Livre;
import fr.diginamic.webmvc01.exceptions.ErreurLivre;
import fr.diginamic.webmvc01.repository.LivreJpaRepository;

/**
 * Controller du Livre.
 * 
 * @author Christian Ingold
 *
 */
@Controller
@RequestMapping("/livre") // donne le chemin d'accès
public class LivresController {
	
	@Autowired
	LivreJpaRepository grl;

	private String message;
	
	/**
	 * Renvoi à la page avec la liste de tous les livres.
	 * @param model
	 * @return
	 */
	@GetMapping("/livres")
	public String findAll(Model model) {
		model.addAttribute("titre","Liste des livres");
		model.addAttribute("livres",(List<Livre>) grl.findAll());
		return "livres/listeLivre";
	}

	
	/**
	 * Renvoi vers la page d'ajout d'un nouveau livre.
	 * @param model
	 * @return
	 */
	@GetMapping("/add")
	public String addT(Model model) {
		model.addAttribute("titre", "Ajout d'un nouveau livre");
		model.addAttribute("livreForm", new Livre());
		return "livres/addLivre";
	}

	/**
	 * Traite les données issues du formunlaire et les enregistre en BdD.
	 * @param model
	 * @param livreForm
	 * @param result
	 * @return
	 * @throws ErreurLivre
	 */
	@PostMapping("/add")
	public String add(Model model, @Valid @ModelAttribute("livreForm") Livre livreForm, BindingResult result) throws ErreurLivre {
		manageBindingResult(result);
		grl.save(livreForm);
		return "redirect:/livre/livres";
	}

	/**
	 * suppression du livre suivant l'id.
	 * 
	 * @param pid
	 * @throws ErreurLivre
	 */
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") Integer pid) throws ErreurLivre {

		//clear le set EmpruntLivres
		Livre livreToDelete = grl.findById(pid).get(); 
		livreToDelete.getEmpruntLivres().clear();
		grl.save(livreToDelete);
					
		//supprime du livre possible
		grl.deleteById(pid);
		return "redirect:/livre/livres";
	}


	/**
	 * Renvoi vers le formulaire de mise à jour des informations d'un livre.
	 * @param pid
	 * @param model
	 * @return
	 */
	@GetMapping("/update/{id}")
	public String updateT(@PathVariable("id") Integer pid, Model model) {
		Livre livre = grl.findById(pid).get();
		model.addAttribute("livreForm", livre);
		model.addAttribute("titre", "Mise à jour des informations du livre");
		return "livres/updateLivre";

	}
	
	/**
	 * Traite les données mises à jour et l'enregistre en BdD.
	 * @param livreForm
	 * @param result
	 * @return
	 * @throws ErreurLivre
	 */
	@PostMapping("/update")
	public String update(@Valid @ModelAttribute("livreForm") Livre livreForm, BindingResult result) throws ErreurLivre {
		manageBindingResult(result);
		grl.save(livreForm);
		return "redirect:/livre/livres";
	}
	
	/**
	 * Méthode qui renvoie un message d'erreur si les propriétés de @Valid ne sont
	 * pas respectées.
	 * @param result
	 * @throws ErreurLivre
	 */
	private void manageBindingResult(BindingResult result) throws ErreurLivre {
		// TODO Auto-generated method stub
		if(result.hasErrors()) {
			message = "";

			result.getFieldErrors().forEach(e -> {
				message += e.getField() + " - " + e.getDefaultMessage() + " * ";
			}); throw new ErreurLivre(message);
		}
	}

}
