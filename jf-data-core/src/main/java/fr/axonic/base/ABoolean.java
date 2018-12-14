package fr.axonic.base;



import fr.axonic.base.engine.AVar;
import fr.axonic.base.format.BooleanFormat;
import fr.axonic.visitor.AVisitor;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ABoolean extends AVar<Boolean> {

    public ABoolean() {
        this(null);
    }

    public ABoolean(String label, Boolean value) {
        super(label, new BooleanFormat(),value);
    }

    public ABoolean(Boolean value) {
        this("",value);
    }

    /**public boolean booleanValue(){
     Boolean bValue= (Boolean) this.getValue();
     if(bValue==null){
     return false;
     }
     return bValue;
     }*/

    @Override
    public void accept(AVisitor v){
        v.visit(this);
    }
}
