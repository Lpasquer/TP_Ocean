package org.inria.restlet.mta.database.api;

import java.util.Collection;
import java.util.List;

import org.inria.restlet.mta.internals.User;
import org.inria.restlet.mta.internals.Tweet;


/**
 *
 * Interface to the database.
 *
 * @author msimonin
 *
 */
public interface Database
{

    /**
     *
     * Create a new user in the database.
     *
     * @param name      The name of the user
     * @param age       The age of the user
     * @return  the new user.
     */
    User createUser(String name, int age);


    /**
     *
     * Returns the list of users.
     *
     * @return the list of users
     */
    Collection<User> getUsers();


    /**
     *  Returns the user with a given id.
     *
     *  @return the user
     */
    User getUser(int id);

    /**
     *
     *
     *
     */
    void deleteUser(int id);


    Boolean exists(int userId);

    Tweet createTweet(String content);

    Collection<Tweet> getTweets(int id);

}
