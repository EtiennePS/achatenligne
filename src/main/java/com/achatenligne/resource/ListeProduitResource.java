package com.achatenligne.resource;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.achatenligne.model.Produit;
import com.achatenligne.model.ProduitService;
import com.achatenligne.model.exception.ProduitExistantException;

@Path("/produit")
public class ListeProduitResource {
	
	@GET
	@Produces({"application/json", MediaType.APPLICATION_XML})
	public List<Produit> getAll() {
		ProduitService pe = new ProduitService();
		return pe.getAll();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({"application/json"})
	public Response create(Produit produit, @Context UriInfo uriInfo) {
		ProduitService produitService = new ProduitService();
	    try {
	        Optional<Produit> p = produitService.ajouterProduit(produit);
	        if(p.isPresent()) {
	        	URI uri = uriInfo.getRequestUriBuilder().path(String.valueOf(p.get().getId())).build();
		        return Response.created(uri).entity(p.get()).build();
	        }
	        else {
	        	throw new WebApplicationException(
	        			"Aucune erreur n'a été détecté à la création du produit, mais il n'a pas été créé", 
	        			Response.Status.INTERNAL_SERVER_ERROR);
	        }
	        
	    }catch(ProduitExistantException e){
	    	throw new WebApplicationException(e.getMessage(), Response.Status.CONFLICT);
	    }
	}
}
