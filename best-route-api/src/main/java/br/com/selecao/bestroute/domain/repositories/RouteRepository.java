package br.com.selecao.bestroute.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.selecao.bestroute.domain.entities.RouteEntity;

public interface RouteRepository extends JpaRepository<RouteEntity, Long> {
}
