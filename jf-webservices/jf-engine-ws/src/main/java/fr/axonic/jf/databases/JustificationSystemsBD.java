package fr.axonic.jf.databases;

import fr.axonic.jf.engine.JustificationSystem;

import java.util.Map;

public interface JustificationSystemsBD {

    Map<String, JustificationSystem> getJustificationSystems();
}
