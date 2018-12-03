import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/")
public class Handler {

    static List<Note> listNote = new ArrayList<Note>();

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response forFun() {
        System.out.println("GET METHOD");
        return Response.ok("Hello there !!!", MediaType.TEXT_PLAIN).build();
    }

    @GET
    @Path("/note/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNoteById(@PathParam("id") String id) {
        System.out.println("GET METHOD: Get Note By Id " + id);
        if(id == null || id.trim().length() == 0) {
            return Response.serverError().entity("ID cannot be blank").build();
        }
        for (int i = 0; i < listNote.size(); i++) {
            if (listNote.get(i).getId().equalsIgnoreCase(id)) {
                return Response.ok(listNote.get(i).toString(), MediaType.APPLICATION_JSON).build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Entity not found for ID: " + id).build();
    }

    @GET
    @Path("/note")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNoteList() {
        System.out.println("GET METHOD: Get Note List");
        return Response.ok(new Gson().toJson(listNote), MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/note/search")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchNotesByCreatedBy(
            @DefaultValue("")   @QueryParam("created_by")   String user,
            @DefaultValue("")   @QueryParam("content")      String content,
            @DefaultValue("-1") @QueryParam("from_date")    long from
            ) {
        System.out.println("GET METHOD: Search Notes " + user + " " +  content + " " +from);
        List<Note> result = new ArrayList<Note>();
        for (int i = 0; i < listNote.size(); i++) {
            Note currentNote = listNote.get(i);
            if ((user.trim().length() == 0 || currentNote.getCreated_by().equalsIgnoreCase(user)) &&
                (content.trim().length() == 0 || currentNote.getContent().equalsIgnoreCase(content)) &&
                (currentNote.getCreated_date() > from))
            {
                result.add(listNote.get(i));
            }
        }
        if (result.size() > 0)
            return Response.ok(new Gson().toJson(result), MediaType.APPLICATION_JSON).build();
        else
            return Response.status(Response.Status.NOT_FOUND).entity("Entity not found").build();
    }

    @POST
    @Path("/note")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createNote(String body) {
        System.out.println("POST METHOD: Add Note with Body \n" + body);
        try {
            Gson gson = new Gson();
            Note note;
            note = gson.fromJson(body, Note.class);
            listNote.add(note);
            return Response.ok(new Gson().toJson(note)).build();
        }
        catch (JsonParseException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Bad request: " + body).build();
        }
    }

    @PUT
    @Path("/note/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateNote(@PathParam("id") String id, String body) {
        System.out.println("PUT METHOD: Update Note By Id " + id + " with body \n" + body);
        if(id == null || id.trim().length() == 0) {
            return Response.serverError().entity("ID cannot be blank").build();
        }
        try {
            Gson gson = new Gson();
            Note note;
            note = gson.fromJson(body, Note.class);
            for (int i = 0; i < listNote.size(); i++) {
                if (listNote.get(i).getId().equalsIgnoreCase(id)) {
                    listNote.get(i).updateContent(note.getContent(), note.getCreated_by());
                    return Response.ok(listNote.get(i).toString(), MediaType.APPLICATION_JSON).build();
                }
            }
            return Response.status(Response.Status.NOT_FOUND).entity("Entity not found for ID: " + id).build();
        }
        catch (JsonParseException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Bad request: " + body).build();
        }
    }

    @DELETE
    @Path("/note/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteNote(@PathParam("id") String id) {
        System.out.println("DELETE METHOD: Delete Note By Id" + id);
        if(id == null || id.trim().length() == 0) {
            return Response.serverError().entity("ID cannot be blank").build();
        }
        for (int i = 0; i < listNote.size(); i++) {
            if (listNote.get(i).getId().equalsIgnoreCase(id)) {
                listNote.remove(i);
                return Response.ok("Deleted Note at Id " + id).build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Entity not found for ID: " + id).build();
    }

}