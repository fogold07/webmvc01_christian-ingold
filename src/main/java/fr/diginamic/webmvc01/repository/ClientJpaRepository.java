package fr.diginamic.webmvc01.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import fr.diginamic.webmvc01.entities.Client;
import fr.diginamic.webmvc01.entities.Emprunt;


public interface ClientJpaRepository extends CrudRepository<Client, Integer> {
	
	@Query("SELECT emp FROM Emprunt emp  WHERE emp.clientE.id = :id")
	public Iterable<Emprunt> getEmpruntByClient(Integer id);

}
