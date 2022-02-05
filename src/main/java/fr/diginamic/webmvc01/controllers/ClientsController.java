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
import fr.diginamic.webmvc01.exceptions.ErreurClient;
import fr.diginamic.webmvc01.repository.ClientJpaRepository;
import fr.diginamic.webmvc01.repository.EmpruntJpaRepository;
import fr.diginamic.webmvc01.repository.LivreJpaRepository;

/**
 * Controller de Client.
 * 
 * @author Christian Ingold
 *
 */
@Controller
@RequestMapping("/client") // donne le chemin d'accès
public class ClientsController {

	@Autowired
	ClientJpaRepository grc;

	@Autowired
	EmpruntJpaRepository gre;

	@Autowired
	LivreJpaRepository grl;

	private String message;

	/**
	 * Renvoi à la page avec la liste de tous les clients.
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping("/clients")
	public String findAll(Model model) {
		model.addAttribute("grc", grc);		
		model.addAttribute("clients", (List<Client>) grc.findAll());
		model.addAttribute("emprunts", gre);
		model.addAttribute("titre", "Liste des clients");

		return "clients/listeClient";
	}


	/**
	 * Renvoi vers la page d'ajout d'un nouveau client.
	 * @param model
	 * @return
	 */
	@GetMapping("/add")
	public String addT(Model model) {
		model.addAttribute("clientForm", new Client());
		model.addAttribute("titre", "Ajout d'un nouveau client");
		return "clients/addClient";
	}

	/**
	 * Traite les données issues du formunlaire et les enregistre en BdD.
	 * @param model
	 * @param clientForm
	 * @param result
	 * @return
	 * @throws ErreurClient
	 */
	@PostMapping("/add")
	public String add(Model model, @Valid @ModelAttribute("clientForm") Client clientForm, BindingResult result) throws ErreurClient {
		manageBindingResult(result);
		grc.save(clientForm);
		return "redirect:/client/clients";

	}

	/**
	 * Supprime le client suivant l'id.
	 * 
	 * @param pid
	 * @throws ErreurClient
	 */
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") Integer pid) throws ErreurClient {

		/**
		 * Gérer la suppression de "façon automatique" des emprunts avec un requete JPQL
		 */
		grc.getEmpruntByClient(pid).forEach(e -> {
			// suppression des liens avec compo
			gre.findByLivre(e).forEach(l -> {
				// pour chaque livre ayant un emprunt remove l'emprunt e
				l.getEmpruntLivres().remove(e);
				grl.save(l);
			});
			// supprimer de l'emprunt possible
			gre.delete(e);
		});
		// supprime du client possible
		grc.deleteById(pid);
		return "redirect:/client/clients";
	}


	/**
	 * Renvoi vers le formulaire de mise à jour des informations d'un client.
	 * @param pid
	 * @param model
	 * @return
	 */
	@GetMapping("/update/{id}")
	public String updateT(@PathVariable("id") Integer pid, Model model) {
		Client client = grc.findById(pid).get();
		model.addAttribute("clientForm", client);
		model.addAttribute("titre", "Mise à jour des données du client");
		return "clients/updateClient";

	}

	/**
	 * Traite les données mises à jour et l'enregistre en BdD.
	 * @param clientForm
	 * @param result
	 * @return
	 * @throws ErreurClient
	 */
	@PostMapping("/update")
	public String update(@Valid @ModelAttribute("clientForm") Client clientForm, BindingResult result) throws ErreurClient {
		manageBindingResult(result);
		grc.save(clientForm);
		
		return "redirect:/client/clients";

	}
	
	/**
	 * Méthode qui renvoie un message d'erreur si les propriétés de @Valid ne sont
	 * pas respectées.
	 * 
	 * @param result
	 * @throws ErreurClient
	 */
	private void manageBindingResult(BindingResult result) throws ErreurClient {
		// TODO Auto-generated method stub
		if (result.hasErrors()) {
			message = "";

			result.getFieldErrors().forEach(e -> {
				message += e.getField() + " - " + e.getDefaultMessage() + " * ";
			});
			throw new ErreurClient(message);
		}

	}
}
