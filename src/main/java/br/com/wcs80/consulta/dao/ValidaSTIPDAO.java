package br.com.wcs80.consulta.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import br.com.wcs80.arquitetura.dao.AbstractDAO;

public class ValidaSTIPDAO extends AbstractDAO {

	public ValidaSTIPDAO() {
	}

	public String validaPrestador(String cpf, Date data) {
		String resultado = "";

		StringBuilder sb = new StringBuilder();
		sb.append(" {? = call DFTRANS.FN_CONSULTA_PRESTADOR_STIP(?,?)}");

		Connection con = null;
		InitialContext ct = null;
		try {

			ct = new InitialContext();
			DataSource dt = (DataSource) ct.lookup("java:jboss/consultastip");
			con = dt.getConnection();

			CallableStatement cs = con.prepareCall(sb.toString());
			cs.registerOutParameter(1, java.sql.Types.CLOB, "RETORNO");
			cs.setString(2, cpf);
			cs.setDate  (3, new java.sql.Date(data.getTime()));

			cs.execute();
			resultado = cs.getString(1);

			return resultado;

		} catch (Exception ex) {
			ex.printStackTrace();
			Logger.getLogger(ValidaSTIPDAO.class.getName()).log(Level.SEVERE,null, ex);
			
			throw new RuntimeException("Houve um problema ao pesquisar o Prestador " + cpf + " - Erro: " + ex.getMessage() );
		} finally {

			try {
				ct.close();
			} catch (NamingException ex) {
				Logger.getLogger(ValidaSTIPDAO.class.getName()).log(Level.SEVERE,null, ex);
			}

			try {
				if (!con.isClosed()) {con.close();
				}
			} catch (SQLException ex) {
				Logger.getLogger(ValidaSTIPDAO.class.getName()).log(Level.SEVERE,null, ex);
			}
		}
	}
	
}
