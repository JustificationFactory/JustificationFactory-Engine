package fr.axonic.avek.dao;

import fr.axonic.avek.dao.implementations.MongoJustificationSystemsDAO;

public class JustificationSystemsDAOFactory {

    private static final JustificationSystemsDAOFactory INSTANCE = new JustificationSystemsDAOFactory();

    public static JustificationSystemsDAOFactory getInstance() {
        return INSTANCE;
    }

    private JustificationSystemsDAOFactory() {
        // Nothing here.
    }

    public JustificationSystemsDAO makeDAO() {
        return MongoJustificationSystemsDAO.getInstance();
    }
}
