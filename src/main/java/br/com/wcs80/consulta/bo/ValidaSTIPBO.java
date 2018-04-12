package br.com.wcs80.consulta.bo;

import java.util.Date;

import javax.ejb.Stateless;

import br.com.wcs80.api.sca.bo.AbstractLocalBO;
import br.com.wcs80.consulta.dao.ValidaSTIPDAO;
import br.com.wcs80.consulta.util.Constantes;


@Stateless
public class ValidaSTIPBO extends AbstractLocalBO {
	private static final long serialVersionUID = 1L;

	
	private ValidaSTIPDAO validaSTIPDAO;
	
	@Override
	protected String getJndiPropertyValue() {return Constantes.PERSISTENCE_JNDI_NAME;}

	@Override
	protected void iniciarFabricaDao() {
		validaSTIPDAO = getDao(ValidaSTIPDAO.class);
	}
	
	public String validaPrestador(String cpf, Date data) {
		return validaSTIPDAO.validaPrestador(cpf, data);
	}
	

}
