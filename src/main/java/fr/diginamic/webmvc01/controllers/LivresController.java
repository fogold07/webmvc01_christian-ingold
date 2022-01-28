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
 * Controller de Client, API Rest Mapping api/livres.
 * 
 * @author Christian
 *
 */
@Controller
@RequestMapping("/livre") // donne le chemin d'accès
public class LivresController {
	
	@Autowired
	LivreJpaRepository grl;

	private String message;
	

	/**
	 * localhost:8090/livre/livres
	 * 
	 * 
	 */
	@GetMapping("/livres")
	public String findAll(Model model) {
		model.addAttribute("titre","Liste des livres");
		model.addAttribute("livres",(List<Livre>) grl.findAll());
		return "livres/listeLivre";
	}


	/**
	 * api/livres/id(1)
	 * 
	 * @param pid
	 * @return
	 * @throws ErreurLivre
	 */
	@GetMapping("/add")
	public String addT(Model model) {
		model.addAttribute("titre", "Ajout livre");
		model.addAttribute("livreForm", new Livre());
		return "livres/addLivre";
	}

	/**
	 * Post suite à la validation du formulaire.
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
	 * @param pid
	 * @param model
	 * @return
	 */
	@GetMapping("/update/{id}")
	public String updateT(@PathVariable("id") Integer pid, Model model) {
		Livre livre = grl.findById(pid).get();
		model.addAttribute("livreForm", livre);
		model.addAttribute("titre", "Update livre");
		return "livres/updateLivre";

	}
	
	/**
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
