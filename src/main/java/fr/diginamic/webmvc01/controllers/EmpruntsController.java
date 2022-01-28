package fr.diginamic.webmvc01.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.diginamic.webmvc01.entities.Client;
import fr.diginamic.webmvc01.entities.Emprunt;
import fr.diginamic.webmvc01.entities.Livre;
import fr.diginamic.webmvc01.exceptions.ErreurClient;
import fr.diginamic.webmvc01.exceptions.ErreurEmprunt;
import fr.diginamic.webmvc01.repository.ClientJpaRepository;
import fr.diginamic.webmvc01.repository.EmpruntJpaRepository;
import fr.diginamic.webmvc01.repository.LivreJpaRepository;

/**
 * Controller de Emprunt, API Rest Mapping api/emprunts.
 * 
 * @author Christian
 *
 */
@Controller // devient une api REST
@RequestMapping("/emprunt") // donne le chemin d'accès
public class EmpruntsController {

	@Autowired
	ClientJpaRepository grc;
	
	@Autowired
	EmpruntJpaRepository gre;
	
	@Autowired
	LivreJpaRepository grl;


	@GetMapping("/emprunts")
	public String findAll(Model model) {
		List<Emprunt> emprunts = (List<Emprunt>) gre.findAll();
		model.addAttribute("emprunts", emprunts);
		model.addAttribute("titre", "Liste des emprunts");
		return "emprunts/listeEmprunt";
	}

	/**
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
		model.addAttribute("titre", "Ajout emprunt");
		return "emprunts/addEmprunt";
	}

	@PostMapping("/add")
	public String add(Model model, @ModelAttribute("empruntForm") Emprunt empruntForm) {
		gre.save(empruntForm);
		empruntForm.getLivresE().forEach(l -> {
			l.getEmpruntLivres().add(empruntForm);
			grl.save(l);
		});
		return "redirect:/emprunt/emprunts";
	}


	/**
	 * suppression de l'emprunt suivant l'id.
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

	@GetMapping("/update/{id}")
	public String updateT(@PathVariable("id") Integer pid, Model model) {
		Emprunt emprunt = gre.findById(pid).get();
		List<Client> listeClients = (List<Client>) grc.findAll();
		List<Livre> listeLivres = (List<Livre>) grl.findAll();
		model.addAttribute("listeClients", listeClients);
		model.addAttribute("listeLivres", listeLivres);
		model.addAttribute("empruntForm", emprunt);
		model.addAttribute("titre", "Update emprunt");
		return "emprunts/updateEmprunt";

	}


	@PostMapping("/update")
	public String update(@Valid @ModelAttribute("empruntForm") Emprunt empruntForm) {
		
		gre.save(empruntForm);
		empruntForm.getLivresE().forEach(l->{
			l.getEmpruntLivres().add(empruntForm);
			grl.save(l);
		});
		return "redirect:/emprunt/emprunts";

	}

//TODO : Gestion de l'exception ErreurEmprunt dans le BindingResult
	private void manageBindingResult(BindingResult result) throws ErreurEmprunt {
		
	}
}
