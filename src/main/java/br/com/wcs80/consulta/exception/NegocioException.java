package br.com.wcs80.consulta.exception;

import javax.ejb.EJBException;

public class NegocioException extends EJBException{
	private static final long serialVersionUID = 1L;

	public NegocioException(String msn) {
		super(msn);
	}

}
