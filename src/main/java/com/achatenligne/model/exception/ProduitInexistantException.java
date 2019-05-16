package com.achatenligne.model.exception;

public class ProduitInexistantException extends Exception {

	private static final long serialVersionUID = 1L;

	public ProduitInexistantException(String message) {
		super(message);
	}

}
