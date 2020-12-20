

package org.inria.restlet.mta.internals;

import java.util.Date;
import java.util.ArrayList;

/**
 *
 * User
 *
 * @author ctedeschi
 * @author msimonin
 *
 */
public class Tweet
{

    /** Name of the user.*/
    private String content_;

    /** Age of the user.*/
    private Date date_;

    public Tweet(String content)
    {
        content_ = content;
        date_ = new Date();
    }


    public String getContent()
    {
        return content_;
    }

    public String getDate()
    {
        return date_.toString();
    }
}


