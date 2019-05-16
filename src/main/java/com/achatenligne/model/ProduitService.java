package com.achatenligne.model;

import java.util.List;
import java.util.Optional;

import com.achatenligne.dao.ProduitDao;
import com.achatenligne.model.exception.ProduitDansCommandeException;
import com.achatenligne.model.exception.ProduitExistantException;
import com.achatenligne.model.exception.ProduitInexistantException;

public class ProduitService {
	
	public List<Produit> getAll() {
		ProduitDao produitDao = new ProduitDao();
		return produitDao.getAll();
	}
	
	public Optional<Produit> getById(int id) {
		ProduitDao produitDao = new ProduitDao();
		return produitDao.getById(id);
	}
	
	public Optional<Produit> getByCode(String code) {
        ProduitDao produitDao = new ProduitDao();
        return produitDao.getByCode(code);
    }

	public Commande creerCommande(int...idProduits) {
		Commande commande = new Commande();
		for (int idProduit : idProduits) {
			ajouter(commande, idProduit);
		}
		return commande;
	}

	private void ajouter(Commande commande, int idProduit) {
		ProduitDao produitDao = new ProduitDao();
		Optional<Produit> produit = produitDao.getById(idProduit);
		
		if (produit.isPresent()) {
			commande.add(produit.get());
		}
	}
	
	public Optional<Produit> ajouterProduit(Produit p) throws ProduitExistantException {
        ProduitDao produitDao = new ProduitDao();
        Optional<Produit> prod = getByCode(p.getCode());
        if (prod.isPresent()) {
            throw new ProduitExistantException("Produit existant");
        }
        else {
            produitDao.create(p);
            return produitDao.getByCode(p.getCode());
        }
    }
	
	public void supprimer(int id) throws ProduitInexistantException, ProduitDansCommandeException {
		ProduitDao produitDao = new ProduitDao();
		Optional<Produit> op = getById(id);
		if(op.isPresent()) {
			produitDao.delete(op.get());
		}
		else {
			throw new ProduitInexistantException("Impossible de supprimer le produit car il n'existe pas");
		}
	}
	
	public Produit modifier(Produit p) throws ProduitInexistantException {
		ProduitDao produitDao = new ProduitDao();
		if(getById(p.getId()).isPresent()) {
			produitDao.update(p);
			Optional<Produit> op = produitDao.getById(p.getId());
			if(op.isPresent()) {
				return op.get();
			}
			else {
				throw new RuntimeException();
			}
		}
		else {
			throw new ProduitInexistantException("Le produit ne peut être mis à jour car il n'existe pas!");
		}
		
	}
}
