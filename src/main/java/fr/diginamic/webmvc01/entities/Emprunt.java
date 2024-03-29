package fr.diginamic.webmvc01.entities;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;


@Entity
@Table(name="EMPRUNT")
public class Emprunt {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	@Column(name = "date_debut")
	private Date datedebut;
	
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	@Column(name = "date_fin")
	private Date datefin;
	
	@NotNull
	private int delai;
	
	@ManyToOne
	@JoinColumn(name ="ID_CLIENT")
	private Client clientE;
	
	@ManyToMany(mappedBy="empruntLivres", fetch = FetchType.EAGER)
//	@Transient
	private Set<Livre> livresE;
	
	
	public Set<Livre> getLivresE() {
		return livresE;
	}


	public void setLivresE(Set<Livre> livresE) {
		this.livresE = livresE;
	}


	public Emprunt() {
		super();
	}


	public int getId() {
		return id;
	}


	public Date getDatedebut() {
		return datedebut;
	}


	public Date getDatefin() {
		return datefin;
	}


	public int getDelai() {
		return delai;
	}


	public Client getClientE() {
		return clientE;
	}


	public void setId(int id) {
		this.id = id;
	}


	public void setDatedebut(Date datedebut) {
		this.datedebut = datedebut;
	}


	public void setDatefin(Date datefin) {
		this.datefin = datefin;
	}


	public void setDelai(int delai) {
		this.delai = delai;
	}


	public void setClientE(Client clientE) {
		this.clientE = clientE;
	}

	
	@Override
	public String toString() {
		return "[datedebut=" + datedebut + ", datefin=" + datefin+"]";
	}
	
}
