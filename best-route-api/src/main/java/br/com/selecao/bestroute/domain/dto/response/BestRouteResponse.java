package br.com.selecao.bestroute.domain.dto.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "BestRouteResponse", description = "DTO BestRouteResponse")
public class BestRouteResponse implements Serializable {

	private static final long serialVersionUID = 1623902410782687733L;

	@ApiModelProperty(position = 1, example = "GRU - SPO - NYC")
	private String routes;
	
	@ApiModelProperty(position = 2, example = "$31")
	private String totalCust;
}
