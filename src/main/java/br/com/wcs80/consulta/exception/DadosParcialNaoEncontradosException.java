package br.com.wcs80.consulta.exception;

public class DadosParcialNaoEncontradosException extends NegocioException{
	private static final long serialVersionUID = 1L;

	public DadosParcialNaoEncontradosException(String msn) {
		super(msn);
	}

}
