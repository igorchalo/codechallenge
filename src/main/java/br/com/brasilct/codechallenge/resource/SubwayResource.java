package br.com.brasilct.codechallenge.resource;

import java.net.UnknownHostException;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import br.com.brasilct.codechallenge.repository.MongoDbRepository;

@Path("/")
public class SubwayResource {
	
	private MongoDbRepository mongoDbRepository = new MongoDbRepository();
	
	@GET
	@Path("{key}")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getAnyRoute(@PathParam("key") UUID key) {
		try {
			mongoDbRepository.getMongoDb();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Event event = repository.find(key);
		//return Response.status(Status.OK).entity(event).build();
		return null;
	}

}
