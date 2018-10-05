package fr.axonic.jf.bus.business.supports;

import fr.axonic.jf.engine.support.Support;

import java.io.IOException;
import java.util.List;

public interface KnownSupportsDAO {

    void saveSupport(Support support) throws IOException;
    List<Support> loadSupports() throws IOException;
    void removeAllSupports(List<Support> toBeRemoved) throws IOException;
    void makeEmpty() throws IOException;
}
