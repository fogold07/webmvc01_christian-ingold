package fr.diginamic.webmvc01.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Entity
@Table(name="LIVRE")
public class Livre {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id; 
	
	@NotNull
	@NotBlank
	@Column(name="AUTEUR", length = 50, nullable = false)
	private String auteur; 
	
	@NotNull
	@NotBlank
	@Column(name = "TITRE", length = 255,nullable = false)
	private String titre;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name="COMPO",
	joinColumns = @JoinColumn(name="ID_LIV", referencedColumnName = "ID"),
	inverseJoinColumns = @JoinColumn(name="ID_EMP", referencedColumnName = "ID")
	)
	private Set<Emprunt> empruntLivres;

	public Livre() {
		super();
		empruntLivres = new HashSet<Emprunt>();
	}

	public int getId() {
		return id;
	}

	public String getAuteur() {
		return auteur;
	}

	public String getTitre() {
		return titre;
	}

	public Set<Emprunt> getEmpruntLivres() {
		return empruntLivres;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setAuteur(String auteur) {
		this.auteur = auteur;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public void setEmpruntLivres(Set<Emprunt> empruntLivres) {
		this.empruntLivres = empruntLivres;
	}
	
	@Override
	public String toString() {
		return "Livre [id=" + id + ", auteur=" + auteur + ", titre=" + titre + "]";

	}

	
	
}
