package com.achatenligne.resource;

import java.util.Optional;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import com.achatenligne.model.Produit;
import com.achatenligne.model.exception.ProduitDansCommandeException;
import com.achatenligne.model.exception.ProduitInexistantException;
import com.achatenligne.model.ProduitService;

@Path("/produit/{id}")
public class ProduitResource {
	
	@GET
	@Produces({"application/json", "application/xml"})
	public Response get(@PathParam("id") int id) {
		ProduitService pe = new ProduitService();
		Optional<Produit> op = pe.getById(id);
		Response r;
		if(op.isPresent()) {
			r = Response.ok().entity(op.get()).build();
		}
		else {
			//r = Response.status(Response.Status.NOT_FOUND).build();
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		
		return r;
	}
	
	@DELETE
	public void supprimer (@PathParam("id") int id) {
		ProduitService ps = new ProduitService();
		try {
			ps.supprimer(id);
		}
		catch (ProduitInexistantException e) {
			throw new WebApplicationException(e.getMessage(), Response.Status.NOT_FOUND);
		}
		catch (ProduitDansCommandeException e) {
			throw new WebApplicationException(e.getMessage(), Response.Status.CONFLICT);
		}
		
	}
	
	@PUT
	@Consumes({"application/json", "application/xml"})
	@Produces("application/json")
	public Response modifier(Produit prod, @PathParam("id") int id) {
		ProduitService ps = new ProduitService();
		try {
			prod.setId(id);
			Produit p = ps.modifier(prod);
			return Response.ok().entity(p).build();
		}
		catch (ProduitInexistantException e) {
			throw new WebApplicationException(e.getMessage(), Response.Status.NOT_FOUND);
		}
	}
	
}
