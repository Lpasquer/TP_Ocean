package org.inria.restlet.mta.database.api.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.inria.restlet.mta.database.api.Ocean;
import org.inria.restlet.mta.internals.User;
import org.inria.restlet.mta.internals.Tweet;

/**
 *
 * In-memory database
 *
 * @author Pasquereau
 * @author Leduc
 *
 */
public class OceanImpl implements Ocean
{

    /** User count (next id to give).*/
    private int userCount_;

    /** User Hashmap. */
    Map<Integer, User> users_;


    public OceanImpl()
    {
        users_ = new HashMap<Integer, User>();
    }

	@Override
	public int getNbRequins() {
		
		return 0;
	}

	@Override
	public int getNbSardines() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void startRequin() {
		// TODO Auto-generated method stub
		
	}

}
