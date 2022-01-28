package fr.diginamic.webmvc01.repository;

import org.springframework.data.repository.CrudRepository;

import fr.diginamic.webmvc01.entities.Livre;

public interface LivreJpaRepository extends CrudRepository<Livre, Integer> {


}
