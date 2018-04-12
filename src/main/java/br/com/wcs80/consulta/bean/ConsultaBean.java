package br.com.wcs80.consulta.bean;

import java.util.Objects;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import br.com.wcs80.consulta.bo.ValidaSTIPBO;
import br.com.wcs80.consulta.util.VerifyRecaptcha;
import br.com.wcs80.util.CPF_CNPJ_Util;
import br.com.wcs80.util.DataUtil;

@ManagedBean(name="consultaBean")
@ViewScoped
public class ConsultaBean extends AbstractBean{
	private static final long serialVersionUID = 1L;

	private String cpf;
	
    private String responseCaptcha;
    private Boolean visualizarCaptcha = true;
    private Boolean resultaVerificacaoGoogleCaptcha = false;
    
    
	@EJB
	private ValidaSTIPBO validaSTIPBO;

	@Override
	public void iniciar() {
		
	}
	
	public void pesquisar() {
		try {
			resultaVerificacaoGoogleCaptcha = false;
			
			if(StringUtils.isEmpty(cpf)) {
				getMensagemErro("CPF é obrigatório!");
				return;
			}
			
			cpf = cpf.replace(".", "").replace("-", "");
			if(!CPF_CNPJ_Util.isCPF(cpf)) {
				getMensagemErro("CPF inválido!");
				return;
			}

			//A verificação no Google é feita por requisição, ou seja, cada submit tera um resultado diferente.
            if (resultaVerificacaoGoogleCaptcha == false) {
               resultaVerificacaoGoogleCaptcha = verificaRecaptcha();
            }

            if (resultaVerificacaoGoogleCaptcha) {
               String msn = validaSTIPBO.validaPrestador(cpf, DataUtil.getHoje());
               getMensagemInfo(msn);
            } else {
               setVisualizarCaptcha(true);
               getMensagemErro("Por gentileza, resolva o 'Captcha'.");
            }
			
		} catch (Exception e) {
			getMensagemErro(e.getMessage());
		}
	}
	
	   /**
	    * Permite a renderização do captcha quando o usuário não validar os dados do
	    * captcha ou informar o captcha errado.
	    *
	    * Quando o captcha estiver correto o ajax será true, pois não necessitamos
	    * de visualizar novamente o captcha. Quando o captcha estiver errado o ajax
	    * será false, pois necessitamos visualizar o captcha.
	    *
	    * Motivo de Uso: Fazer funcionar o componente p:blockUI, pois com ajax false
	    * esse componente não funciona.
	    *
	    * @return
	    */
	   public Boolean getRenderizarCaptchaGoogle() {
	      return !visualizarCaptcha;
	   }	

	private Boolean verificaRecaptcha() {
		boolean verify = false;
		try {
			//Chave privada
			//Máquina Wilson (Notebook): 6LfmH0EUAAAAAIrxNHOCd5WNy-XWAoUivoP7CFlO 
			//DFTRANS (Produção)       : 6LdPJ0EUAAAAAAdZqJlUuyFLhnluQRL_V7229KVk
			String hashPrivado = "6LdPJ0EUAAAAAAdZqJlUuyFLhnluQRL_V7229KVk";
			verify = VerifyRecaptcha.verify(getResponseCaptcha(), hashPrivado);
		} catch (Exception e) {
			getMensagemErro("Não foi possível validar o captcha", "");
			verify = false;
		}
		return verify;
	}	

	public void limpar() {
		cpf = null;
		resultaVerificacaoGoogleCaptcha = false;
	}
	
	public Boolean isHabilitaCaptcha() {
		return visualizarCaptcha;
	}
	
	private String getResponseCaptcha() {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		if (Objects.nonNull(request)) {
			responseCaptcha = request.getParameter("g-recaptcha-response");
		}
		System.out.println("Parametro g-recaptcha-response: " + responseCaptcha);
		return responseCaptcha;
	}
	
	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Boolean getVisualizarCaptcha() {
		return visualizarCaptcha;
	}

	public void setVisualizarCaptcha(Boolean visualizarCaptcha) {
		this.visualizarCaptcha = visualizarCaptcha;
	}

}
