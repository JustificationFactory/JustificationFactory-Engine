package fr.axonic.jf.bus.business.supports;

import fr.axonic.jf.engine.support.Support;
import fr.axonic.jf.instance.redmine.RedmineDocument;
import fr.axonic.jf.instance.redmine.RedmineDocumentEvidence;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.util.Collections;

import static org.junit.Assert.*;

@Ignore
public class MongoKnownSupportsDAOIT {

    private MongoKnownSupportsDAO dao;

    @Before
    public void initialize() throws IOException {
        dao = new MongoKnownSupportsDAO();

        dao.makeEmpty();
    }

    @Test
    public void shouldAddAndListSupports() throws IOException {
        Support first = new RedmineDocumentEvidence("ABC", new RedmineDocument("http://a-url.com"));
        Support second = new RedmineDocumentEvidence("DEF", new RedmineDocument("http://another-url.com"));

        assertEquals(0, dao.loadSupports().size());

        dao.saveSupport(first);
        dao.saveSupport(second);

        assertEquals(2, dao.loadSupports().size());
    }

    @Test
    public void shouldRemoveSupports() throws IOException {
        Support first = new RedmineDocumentEvidence("ABC", new RedmineDocument("http://a-url.com"));
        Support second = new RedmineDocumentEvidence("DEF", new RedmineDocument("http://another-url.com"));

        dao.saveSupport(first);
        dao.saveSupport(second);

        dao.removeAllSupports(Collections.singletonList(first));

        assertEquals(1, dao.loadSupports().size());
    }
}
