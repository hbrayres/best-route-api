package br.com.selecao.bestroute.domain.dto.response;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorInfo implements Serializable {

	private static final long serialVersionUID = -6416866511269795805L;
	private Integer code;
	private String message;
	
}
