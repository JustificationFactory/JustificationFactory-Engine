package fr.axonic.avek.databases;

import fr.axonic.avek.engine.JustificationSystem;

import java.util.Map;

public interface JustificationSystemsBD {

    Map<String, JustificationSystem> getJustificationSystems();
}
