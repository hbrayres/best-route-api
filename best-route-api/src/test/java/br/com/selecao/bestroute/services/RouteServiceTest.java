package br.com.selecao.bestroute.services;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;

import br.com.selecao.bestroute.domain.dto.response.BestRouteResponse;
import br.com.selecao.bestroute.domain.entities.RouteEntity;
import br.com.selecao.bestroute.domain.repositories.RouteRepository;

@RunWith(MockitoJUnitRunner.class)
public class RouteServiceTest {
	
	@Mock
	private RouteRepository repository;
	
	@InjectMocks
	private RouteService service;
	
	private static final String MSG_ERRO = "Route not found";
	
	private static StringBuilder routesUpload = new StringBuilder();
	
	private static List<RouteEntity> routesFound = new ArrayList<RouteEntity>();
	
	@BeforeClass
	public static void init() {
		routesUpload = new StringBuilder();
		routesUpload.append(
				"GRU,BRC,10\r\n" +
				"BRC,SCL,5\r\n" +
				"GRU,CDG,75\r\n" +
				"GRU,SCL,20\r\n" + 
				"GRU,ORL,56\r\n" + 
				"ORL,CDG,5\r\n" + 
				"SCL,ORL,20\r\n" + 
				"ORL,NYC,15\r\n" + 
				"SCL,FRA,38\r\n" + 
				"NYC,FRA,2");
		
		routesFound.add(new RouteEntity(1l, "GRU", "BRC", 10));
		routesFound.add(new RouteEntity(2l, "BRC", "SCL", 5));
		routesFound.add(new RouteEntity(3l, "GRU", "CDG", 75));
		routesFound.add(new RouteEntity(4l, "GRU", "SCL", 20));
		routesFound.add(new RouteEntity(5l, "GRU", "ORL", 56));
		routesFound.add(new RouteEntity(6l, "ORL", "CDG", 5));
		routesFound.add(new RouteEntity(7l, "SCL", "ORL", 20));
		
	}
	
	@Test
	public void uploadSuccessTest() {
		
		final MockMultipartFile file = new MockMultipartFile("routes.csv", RouteServiceTest.routesUpload.toString().getBytes());
		service.uploadRoutes(file);
		
		verify(repository, times(1)).deleteAll();
		verify(repository, times(1)).saveAll(ArgumentMatchers.any());
	}
	
	@Test
	public void uploadErrorTest() {
		
		final StringBuilder routesError = new StringBuilder();
		routesError.append(routesUpload);
		routesError.append("XPTO,XPTO,X");
		
		final MockMultipartFile file = new MockMultipartFile("routes.csv", routesError.toString().getBytes());
		service.uploadRoutes(file);
		
		verify(repository, times(0)).deleteAll();
		verify(repository, times(0)).saveAll(ArgumentMatchers.any());
	}

	@Test
	public void bestRouteTest() {
		
		when(repository.findAll()).thenReturn(routesFound);
		
		final BestRouteResponse bestRoute = service.getBestRoute("GRU", "CDG");
		
		assertNotNull(bestRoute);
		assertEquals(bestRoute.getRoutes(), "GRU - BRC - SCL - ORL - CDG");
		assertEquals(bestRoute.getTotalCust(), "$40");
		
	}
	
	@Test
	public void fromEqualsToTest() {
		
		when(repository.findAll()).thenReturn(routesFound);
		
		final BestRouteResponse bestRoute = service.getBestRoute("GRU", "GRU");
		
		assertNotNull(bestRoute);
		assertEquals(bestRoute.getRoutes(), MSG_ERRO);
		assertNull(bestRoute.getTotalCust());
		
	}
	
	@Test
	public void fromNotFoundTest() {
		
		when(repository.findAll()).thenReturn(routesFound);
		
		final BestRouteResponse bestRoute = service.getBestRoute("NYC", "GRU");
		
		assertNotNull(bestRoute);
		assertEquals(bestRoute.getRoutes(), MSG_ERRO);
	}
	
	@Test
	public void toNotFoundTest() {
		
		when(repository.findAll()).thenReturn(routesFound);
		
		final BestRouteResponse bestRoute = service.getBestRoute("GRU", "NYC");
		
		assertNotNull(bestRoute);
		assertEquals(bestRoute.getRoutes(), MSG_ERRO);
	}
	
	@Test
	public void routeFoundTest() {
		
		when(repository.findAll()).thenReturn(routesFound);
		
		final BestRouteResponse bestRoute = service.getBestRoute("CDG", "GRU");
		
		assertNotNull(bestRoute);
		assertEquals(bestRoute.getRoutes(), MSG_ERRO);
	}
}
