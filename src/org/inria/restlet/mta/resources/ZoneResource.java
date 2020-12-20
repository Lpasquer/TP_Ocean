package org.inria.restlet.mta.resources;

import org.inria.restlet.mta.backend.Backend;
import org.inria.restlet.mta.internals.User;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Delete;
import org.restlet.resource.ServerResource;

/**
 *
 * Resource exposing a user.
 *
 * @author msimonin
 * @author ctedeschi
 *
 */
public class ZoneResource extends ServerResource
{

    /** Backend.*/
    private Backend backend_;

    /** Utilisateur géré par cette resource.*/
    private User user_;

    /**
     * Constructor.
     * Call for every single user request.
     */
    public ZoneResource()
    {
        backend_ = (Backend) getApplication().getContext().getAttributes()
                .get("backend");
    }


    @Get("json")
    public Representation getUser() throws Exception
    {
        String userIdString = (String) getRequest().getAttributes().get("userId");
        int userId = Integer.valueOf(userIdString);

        if (backend_.getDatabase().exists(userId)){
            user_ = backend_.getDatabase().getUser(userId);

            JSONObject userObject = new JSONObject();
            userObject.put("name", user_.getName());
            userObject.put("age", user_.getAge());
            userObject.put("id", user_.getId());
            return new JsonRepresentation(userObject);
        }
        else {
            JSONObject response = new JSONObject();
            response.put("result", "DOES NOT EXIST");
            return new JsonRepresentation(response);
        }

    }


    @Delete("json")
    public Representation deleteUser(JsonRepresentation representation)
        throws Exception
    {

        String userIdString = (String) getRequest().getAttributes().get("userId");
        int userId = Integer.valueOf(userIdString);
        if (backend_.getDatabase().exists(userId)){
            backend_.getDatabase().deleteUser(userId);
            JSONObject response = new JSONObject();
            response.put("result", "DONE");
            return new JsonRepresentation(response);
        }
        else {
            JSONObject response = new JSONObject();
            response.put("result", "DOES NOT EXIST");
            return new JsonRepresentation(response);
        }

    }
}
