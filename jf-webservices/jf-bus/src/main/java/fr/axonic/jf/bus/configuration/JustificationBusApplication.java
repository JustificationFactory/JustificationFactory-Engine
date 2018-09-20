package fr.axonic.jf.bus.configuration;

import org.glassfish.jersey.server.ResourceConfig;

public class JustificationBusApplication extends ResourceConfig {

    public JustificationBusApplication() {
        register(new JustificationBusBinder());
    }
}
