package br.com.brasilct.codechallenge.resource;

import java.util.List;

import javax.jws.WebParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import br.com.brasilct.codechallenge.domain.Edge;
import br.com.brasilct.codechallenge.domain.GraphSearch;

@Path("/")
public class SubwayResource {
	
	//TODO- Singleton to init()
	private GraphSearch graphSearch = new GraphSearch();
	
	@GET
	@Path("/get/{origin}/{destination}")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getAnyRoute(@PathParam("origin") @WebParam(name = "origin") String origin,
			@PathParam("destination") @WebParam(name = "destination") String destination) {

		List<Edge> result = graphSearch.getShortestPath(origin, destination);
		return Response.status(Status.OK).entity(result).build();
	}
	
	@GET
	@Path("/getshortestpath/{origin}/{destination}")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getShortestPath(@PathParam("origin") @WebParam(name = "origin") String origin,
			@PathParam("destination") @WebParam(name = "destination") String destination) {

		List<Edge> result = graphSearch.getShortestPath(origin, destination);
		return Response.status(Status.OK).entity(result).build();
	}

	@GET
	@Path("/getroutetime/{origin}/{destination}")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getRouteTime(@PathParam("origin") @WebParam(name = "origin") String origin,
			@PathParam("destination") @WebParam(name = "destination") String destination) {

		String time = graphSearch.getRouteTime(origin, destination);
		return Response.status(Status.OK).entity(time).build();
	}
}
