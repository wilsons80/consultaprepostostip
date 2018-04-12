package br.com.wcs80.consulta.entity;

import br.com.wcs80.arquitetura.entity.IEntity;

/**
 *
 * @author wilson.souza
 * @param <T>
 */
public interface IEntityLocal<T> extends IEntity<T> {

   static final String SCHEMA       = "DFTRANS";
   static final String ID_GENERATOR = "ID_GENERATOR";
   static final String VERSION      = "NR_VERSAO";

}
