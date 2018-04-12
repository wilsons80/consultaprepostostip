package br.com.wcs80.consulta.exception;

public class QueryException extends NegocioException{
	private static final long serialVersionUID = 1L;

	public QueryException(String msn) {
		super(msn);
	}

}
