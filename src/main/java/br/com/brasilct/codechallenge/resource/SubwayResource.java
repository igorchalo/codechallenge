package br.com.brasilct.codechallenge.resource;

import javax.jws.WebParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
public class SubwayResource {
	
	@GET
	@Path("/get/{origin}/{destination}")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getAnyRoute(@PathParam("origin") @WebParam(name = "origin") String origin,
			@PathParam("destination") @WebParam(name = "destination") String destination) {

		//return Response.status(Status.OK).entity(object).build();
		return null;
	}
	
	@GET
	@Path("/getshortestpath/{origin}/{destination}")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getShortestPath(@PathParam("origin") @WebParam(name = "origin") String origin,
			@PathParam("destination") @WebParam(name = "destination") String destination) {

		//return Response.status(Status.OK).entity(object).build();
		return null;
	}

	@GET
	@Path("/getroutetime/{origin}/{destination}")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getRouteTime(@PathParam("origin") @WebParam(name = "origin") String origin,
			@PathParam("destination") @WebParam(name = "destination") String destination) {

		//return Response.status(Status.OK).entity(object).build();
		return null;
	}
}
