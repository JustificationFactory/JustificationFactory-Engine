package fr.axonic.jf.instance.ValidXp.evidence;

import fr.axonic.jf.engine.support.evidence.Evidence;
import fr.axonic.jf.instance.ValidXp.documents.XpDocument;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class XpEvidence extends Evidence<XpDocument> {
    public static final String EVIDENCE_NAME = "XP_E";

    public XpEvidence(XpDocument element) {
        super(EVIDENCE_NAME, element);
    }

    public XpEvidence() {
    }
}