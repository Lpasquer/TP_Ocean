
package org.inria.restlet.mta.resources;

import java.util.ArrayList;
import java.util.Collection;

import org.inria.restlet.mta.backend.Backend;
import org.inria.restlet.mta.internals.User;
import org.inria.restlet.mta.internals.Tweet;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Delete;
import org.restlet.resource.ServerResource;

/**
 * Resource exposing the users
 *
 * @author ctedeschi
 * @author msimonin
 *
 */
public class ZonesResource extends ServerResource
{

    /** Backend. */
    private Backend backend_;

    /**
     * Constructor.
     * Call for every single user request.
     */
    public ZonesResource()
    {
        super();
        backend_ = (Backend) getApplication().getContext().getAttributes()
            .get("backend");
    }

    /**
     *
     * Returns the list of all the tweets for a given user
     *
     * @return  JSON representation of the tweets
     * @throws JSONException
     */
    @Get("json")
    public Representation getTweets() throws JSONException
    {

        String userIdString = (String) getRequest().getAttributes().get("userId");
        int userId = Integer.valueOf(userIdString);


        Collection<Tweet> tweets = backend_.getDatabase().getTweets(userId);
        Collection<JSONObject> jsonUsers = new ArrayList<JSONObject>();

        for (Tweet tweet : tweets)
        {
            JSONObject current = new JSONObject();
            current.put("content", tweet.getContent());
            current.put("date", tweet.getDate());
            jsonUsers.add(current);

        }
        JSONArray jsonArray = new JSONArray(jsonUsers);
        return new JsonRepresentation(jsonArray);
    }

    @Post("json")
    public Representation createTweet(JsonRepresentation representation)
        throws Exception
    {

        String userIdString = (String) getRequest().getAttributes().get("userId");
        int userId = Integer.valueOf(userIdString);
        
        JSONObject object = representation.getJsonObject();
        String content = object.getString("content");

        // Save the tweet
        Tweet t = new Tweet(content);
        backend_.getDatabase().getUser(userId).addTweet(t);

        // generate result
        return new JsonRepresentation(new JSONObject());
    }

}
