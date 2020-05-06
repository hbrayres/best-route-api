package br.com.selecao.bestroute.domain.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "cust")
@JsonPropertyOrder(value = {"from", "to", "cust"})
public class Route implements Serializable {

	private static final long serialVersionUID = -1943457379612642051L;

	private String from;
	private String to;
	private Integer cust;
}
