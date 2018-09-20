package fr.axonic.jf.instance.clinical.evidence;

import fr.axonic.jf.engine.support.evidence.Element;
import fr.axonic.base.AString;
import fr.axonic.base.engine.AList;

public class Result extends Element {

    private AList<AString> filePaths;

    public Result(AList<AString> filePaths) {
        this.filePaths = filePaths;
    }

    public AList<AString> getFilePaths() {
        return filePaths;
    }

    @Override
    public String toString() {
        return "Result{" +
                "filePaths=" + filePaths +
                '}';
    }
}
