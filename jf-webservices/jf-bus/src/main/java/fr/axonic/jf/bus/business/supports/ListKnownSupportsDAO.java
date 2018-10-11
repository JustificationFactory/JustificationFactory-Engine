package fr.axonic.jf.bus.business.supports;

import fr.axonic.jf.engine.support.Support;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListKnownSupportsDAO implements KnownSupportsDAO {

    private List<Support> knownSupports;

    public ListKnownSupportsDAO() {
        knownSupports = new ArrayList<>();
    }

    @Override
    public void saveSupport(Support support) {
        knownSupports.add(support);
    }

    @Override
    public List<Support> loadSupports() {
        return Collections.unmodifiableList(knownSupports);
    }

    @Override
    public void removeAllSupports(List<Support> toBeRemoved) {
        knownSupports.removeAll(toBeRemoved);
    }

    @Override
    public void makeEmpty() {
        knownSupports = new ArrayList<>();
    }
}
