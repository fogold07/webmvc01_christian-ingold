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

import fr.diginamic.webmvc01.entities.Client;
import fr.diginamic.webmvc01.entities.Emprunt;
import fr.diginamic.webmvc01.entities.Livre;
import fr.diginamic.webmvc01.exceptions.ErreurEmprunt;
import fr.diginamic.webmvc01.repository.ClientJpaRepository;
import fr.diginamic.webmvc01.repository.EmpruntJpaRepository;
import fr.diginamic.webmvc01.repository.LivreJpaRepository;

/**
 * Controller de Emprunt.
 * 
 * @author Christian Ingold
 *
 */
@Controller 
@RequestMapping("/emprunt") // donne le chemin d'accès
public class EmpruntsController {

	@Autowired
	ClientJpaRepository grc;
	
	@Autowired
	EmpruntJpaRepository gre;
	
	@Autowired
	LivreJpaRepository grl;


	/**
	 * Renvoi à la page avec la liste de tous les emprunts.
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping("/emprunts")
	public String findAll(Model model) {
		List<Emprunt> emprunts = (List<Emprunt>) gre.findAll();
		model.addAttribute("gre", gre);
		model.addAttribute("emprunts", emprunts);
		model.addAttribute("titre", "Liste des emprunts");
		return "emprunts/listeEmprunt";
	}

	/**
	 * Renvoi vers la page d'ajout d'un nouvel emprunt.
	 * @param model
	 * @return
	 */
	@GetMapping("/add")
	public String addT(Model model) {
		List<Client> listeClients = (List<Client>) grc.findAll();
		List<Livre> listeLivres = (List<Livre>) grl.findAll();
		model.addAttribute("empruntForm", new Emprunt());
		model.addAttribute("listeClients", listeClients);
		model.addAttribute("listeLivres", listeLivres);
		model.addAttribute("titre", "Ajout d'un nouvel emprunt");
		return "emprunts/addEmprunt";
	}

	/**
	 * Traite les données issues du formunlaire et les enregistre en BdD. 
	 * @param model
	 * @param empruntForm
	 * @param result
	 * @return
	 * @throws ErreurEmprunt
	 */
	@PostMapping("/add")
	public String add(Model model, @Valid @ModelAttribute("empruntForm") Emprunt empruntForm, BindingResult result) throws ErreurEmprunt {
		manageBindingResult(result);
		gre.save(empruntForm);
		empruntForm.getLivresE().forEach(l -> {
			l.getEmpruntLivres().add(empruntForm);
			grl.save(l);
		});
		return "redirect:/emprunt/emprunts";
	}


	/**
	 * Supprime de l'emprunt suivant l'id.
	 * 
	 * @param pid
	 * @throws ErreurEmprunt
	 */
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") Integer pid) throws ErreurEmprunt {

		/**
		 * suppression de l'emprunt dans la table COMPO
		 */
		
		Emprunt emp = gre.findById(pid).get();
		gre.findByLivre(emp).forEach(l -> {
			l.getEmpruntLivres().remove(emp);
			grl.save(l);
		});
		//suppression de l'emprunt
		gre.deleteById(pid);
		return "redirect:/emprunt/emprunts";
	}

	/**
	 * Renvoi vers le formulaire de mise à jour des informations d'un emprunt.
	 * @param pid
	 * @param model
	 * @return
	 */
	@GetMapping("/update/{id}")
	public String updateT(@PathVariable("id") Integer pid, Model model) {
		Emprunt emprunt = gre.findById(pid).get();
		List<Client> listeClients = (List<Client>) grc.findAll();
		List<Livre> listeLivres = (List<Livre>) grl.findAll();
		model.addAttribute("listeClients", listeClients);
		model.addAttribute("listeLivres", listeLivres);
		model.addAttribute("empruntForm", emprunt);
		model.addAttribute("titre", "Mise à jour des informations de l'emprunt");
		return "emprunts/updateEmprunt";

	}


	/**
	 * Traite les données mises à jour et l'enregistre en BdD.
	 * @param empruntForm
	 * @param result
	 * @return
	 * @throws ErreurEmprunt
	 */
	@PostMapping("/update")
	public String update(@Valid @ModelAttribute("empruntForm") Emprunt empruntForm, BindingResult result) throws ErreurEmprunt {
		manageBindingResult(result);
		gre.save(empruntForm);
		empruntForm.getLivresE().forEach(l->{
			l.getEmpruntLivres().add(empruntForm);
			grl.save(l);
		});
		return "redirect:/emprunt/emprunts";

	}
private String message;

	/**
	 * Méthode qui renvoie un message d'erreur si les propriétés de @Valid ne sont
	 * pas respectées.
	 * @param result
	 * @throws ErreurEmprunt
	 */
	private void manageBindingResult(BindingResult result) throws ErreurEmprunt {
		if(result.hasErrors()) {
			message="";
			result.getFieldErrors().forEach(e -> {
				message += e.getField() + " - " + e.getDefaultMessage() +" * ";
			});
			throw new ErreurEmprunt(message);
		}
	}
}
