package com.achatenligne.model.exception;

public class ProduitExistantException extends Exception {

	private static final long serialVersionUID = 1L;

	public ProduitExistantException(String message) {
		super(message);
	}

}
