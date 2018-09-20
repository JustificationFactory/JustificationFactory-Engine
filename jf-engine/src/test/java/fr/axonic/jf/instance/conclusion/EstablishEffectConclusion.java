package fr.axonic.jf.instance.conclusion;

import fr.axonic.jf.engine.support.conclusion.Conclusion;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * Created by cduffau on 09/08/16.
 */
@XmlRootElement
@XmlSeeAlso(EstablishedEffect.class)
public class EstablishEffectConclusion extends Conclusion<EstablishedEffect>{
    public EstablishEffectConclusion() {
    }

    public EstablishEffectConclusion(String name, EstablishedEffect element) {
        super(name, element);
    }
}
