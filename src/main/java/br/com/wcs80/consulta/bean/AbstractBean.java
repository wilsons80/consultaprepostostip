package br.com.wcs80.consulta.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.context.RequestContext;

import br.com.wcs80.util.DataUtil;

public abstract class AbstractBean implements Serializable {
  
   public final static String NOME_SISTEMA = "Consulta Preposto STIP";
   private static final long serialVersionUID = 1L;
   
   public AbstractBean() {
      Locale.setDefault(new Locale("pt", "BR"));
   }

   @PostConstruct
   private void inicio() {
	   
   }

   public abstract void iniciar();

   protected FacesContext getContext() {
      return FacesContext.getCurrentInstance();
   }

   protected void getMensagemErro(String mensagem, String descricao) {
      getMensagens(FacesMessage.SEVERITY_ERROR, mensagem, descricao);
   }
   protected void getMensagemErro(String mensagem) {
      getMensagens(FacesMessage.SEVERITY_ERROR, mensagem, "");
   }

   protected void getMensagemInfo(String mensagem, String descricao) {
      getMensagens(FacesMessage.SEVERITY_INFO, mensagem, descricao);
   }
   protected void getMensagemInfo(String mensagem) {
      getMensagens(FacesMessage.SEVERITY_INFO, mensagem, "");
   }

   protected void getMensagemWarn(String mensagem, String descricao) {
      getMensagens(FacesMessage.SEVERITY_WARN, mensagem, descricao);
   }
   protected void getMensagemWarn(String mensagem) {
      getMensagens(FacesMessage.SEVERITY_WARN, mensagem, "");
   }

   private void getMensagens(Severity fm, String msg, String desc) {
      getContext().addMessage(null, new FacesMessage(fm, msg, desc));
   }

   

   public static void popupMensagemErro(String mensagem) {
      popupMensagem(mensagem, FacesMessage.SEVERITY_ERROR);
   }

   public static void popupMensagemWarn(String mensagem) {
      popupMensagem(mensagem, FacesMessage.SEVERITY_WARN);
   }

   public static void popupMensagemInfo(String mensagem) {
      popupMensagem(mensagem, FacesMessage.SEVERITY_INFO);
   }

   public static void popupMensagemFatal(String mensagem) {
      popupMensagem(mensagem, FacesMessage.SEVERITY_FATAL);
   }

   public static void popupMensagem(String mensagem, Severity fm) {
      FacesMessage message = new FacesMessage(fm, NOME_SISTEMA, mensagem);
      RequestContext.getCurrentInstance().showMessageInDialog(message);
   }
   
   protected <T> void setObjetoSessao(String desc, T obj) {
      getContext().getExternalContext().getSessionMap().put(desc, obj);
   }

   @SuppressWarnings("unchecked")
   protected <T> T getObjetoSessao(String desc) {
      return (T) getContext().getExternalContext().getSessionMap().get(desc);
   }

   public String getDescContesto() {
      return getRequeste().getContextPath();
   }

   protected void redirectInterno(String pagina) throws IOException {
      if (pagina.contains("view/")) {
         getContext().getExternalContext().redirect(getDescContesto().concat("/").concat(pagina));
      } else {
         getContext().getExternalContext().redirect(getDescContesto().concat("/").concat(pagina));
      }
   }

   protected void redirectExterno(String pagina) throws IOException {
      getContext().getExternalContext().redirect(pagina);
   }

   public String getPagina() {
      return getContext().getViewRoot().getViewId();
   }

   public HttpServletRequest getRequeste() {
      return (HttpServletRequest) getContext().getExternalContext().getRequest();
   }

   public void putFlash(String key, String value){
	   FacesContext.getCurrentInstance().getExternalContext().getFlash().putNow(key, value);
   }
   
   public String getFlash(String key){
	   return (String) FacesContext.getCurrentInstance().getExternalContext().getFlash().get(key);
   }
   
   public Date getDataHoje(){return DataUtil.getHoje();}
   

	/**
	 * Realiza o update do componente da página JSF de acordo com o comando
	 * informado.
	 *
	 * @param nomeComponente
	 *            Ex: ':formTeste:tabelaTeste'
	 */
	public void updateView(String nomeComponente) {
		RequestContext ct = RequestContext.getCurrentInstance();
		ct.update(nomeComponente);
	}

	public void executeView(String nomeComponente) {
		RequestContext ct = RequestContext.getCurrentInstance();
		ct.execute(nomeComponente);
	}
	
}
