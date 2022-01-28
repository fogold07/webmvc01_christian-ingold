package fr.diginamic.webmvc01.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import fr.diginamic.webmvc01.entities.Emprunt;
import fr.diginamic.webmvc01.entities.Livre;


public interface EmpruntJpaRepository extends CrudRepository<Emprunt, Integer> {

	/**
	 * Ajout une requete me retournant la liste des livres d'un Emprunt
	 * Utilise MEMBER OF l.empruntLivres car c'est un objet Set
	 * @param emp
	 * @return
	 */
	@Query("SELECT l FROM Livre l WHERE :emp MEMBER OF l.empruntLivres ")
	public Iterable<Livre> findByLivre(Emprunt emp);
}
