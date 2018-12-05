package fr.axonic.jf.instance.reproducibleXP.conclusions;

import fr.axonic.jf.engine.support.conclusion.Conclusion;
import fr.axonic.jf.engine.support.evidence.Document;

public class TotalTimeMetricsConclusion extends Conclusion<Document> {

    public TotalTimeMetricsConclusion(String name, Document element){
        super(name,element);
    }

    public TotalTimeMetricsConclusion(){
        super();
    }

}
