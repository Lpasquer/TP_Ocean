package org.inria.restlet.mta.application;


import org.inria.restlet.mta.resources.RequinResource;
import org.inria.restlet.mta.resources.RequinsResource;
import org.inria.restlet.mta.resources.ZoneResource;
import org.inria.restlet.mta.resources.ZonesResource;
import org.restlet.Application;
import org.restlet.Context;
import org.restlet.Restlet;
import org.restlet.routing.Router;

/**
 *
 * Application.
 *
 * @author Pasquereau
 * @author Leduc
 *
 */
public class MyOceanApplication extends Application
{

    public MyOceanApplication(Context context)
    {
        super(context);
    }

    @Override
    public Restlet createInboundRoot()
    {
        Router router = new Router(getContext());
        router.attach("/zones/{zoneId}", ZoneResource.class);
        router.attach("/sharks", RequinsResource.class);
        router.attach("/sharks/", RequinsResource.class);
        router.attach("/sharks/{sharkId}", RequinResource.class);
        router.attach("/tunas", ZonesResource.class);
        
        return router;
    }
}
