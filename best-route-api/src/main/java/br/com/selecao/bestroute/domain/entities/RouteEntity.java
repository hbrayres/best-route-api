package br.com.selecao.bestroute.domain.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"id", "cust"})
@ToString
@Table(name = "ROUTES")
public class RouteEntity implements Serializable {

	private static final long serialVersionUID = -186887865017675608L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "route_id", unique = true, updatable = false)
	private Long id;
	@Column(name = "origin")
	private String from;
	@Column(name = "destination")
	private String to;
	private Integer cust;
	
	@PrePersist
	public void init() {
		this.from = from.toUpperCase();
		this.to = to.toUpperCase();
	}
}
