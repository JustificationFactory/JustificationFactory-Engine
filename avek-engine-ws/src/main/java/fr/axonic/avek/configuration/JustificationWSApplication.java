package fr.axonic.avek.configuration;

import org.glassfish.jersey.server.ResourceConfig;

public class JustificationWSApplication extends ResourceConfig {

    public JustificationWSApplication() {
        register(new JustificationWSBinder());
    }
}
