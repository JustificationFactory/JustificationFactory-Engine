package fr.axonic.jf.instance.reproducibleXP.conclusions;

import fr.axonic.jf.engine.support.conclusion.Conclusion;
import fr.axonic.jf.engine.support.evidence.Document;

public class AccuracyMetricsConclusion extends Conclusion<Document> {

    public AccuracyMetricsConclusion(String name, Document element){
        super(name,element);
    }
    public AccuracyMetricsConclusion(){
        super();
    }

}
