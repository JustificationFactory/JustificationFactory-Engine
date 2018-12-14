package fr.axonic.base;




import fr.axonic.base.engine.AVar;
import fr.axonic.base.format.DateFormat;
import fr.axonic.visitor.AVisitor;
import fr.axonic.validation.exception.VerificationException;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.datatype.XMLGregorianCalendar;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;

@XmlRootElement
public class ADate extends AVar<GregorianCalendar> {

    public ADate() {
        this(null);
    }

    public ADate(GregorianCalendar value) {
        super(new DateFormat(),value);
    }
    public ADate(String label, GregorianCalendar value) {
        super(label,new DateFormat(), value);
    }

    /**
     * Returns the internal JAVA value in the form of a double.
     *
     * @return internal JAVA value of this ANumber (double)
     **/
    /**public GregorianCalendar gregorianCalendarValue() {
     GregorianCalendar gregorianCalendar;
     try{
     gregorianCalendar = (GregorianCalendar) this.getValue();
     return gregorianCalendar;
     }
     catch (ClassCastException e){
     // TODO : handle that
     //XMLGregorianCalendar calendar= (XMLGregorianCalendar) this.getValue();
     //gregorianCalendar=calendar.toGregorianCalendar();
     }

     }*/
    @Override
    protected void setObject(Object value) throws VerificationException {
        // This condition serves to solve the problem of the JAXB serialization mechanism of GregorianCalendar
        if (value instanceof XMLGregorianCalendar) {
            setValue(((XMLGregorianCalendar) value).toGregorianCalendar());
        }
        else if(value instanceof Long){
            Timestamp timestamp = new Timestamp((Long) value);
            GregorianCalendar calendar = (GregorianCalendar) GregorianCalendar.getInstance();
            calendar.setTimeInMillis(timestamp.getTime());
            setValue(calendar);
        }
        else {
            setValue((GregorianCalendar) value);
        }
    }

    @Override
    public String toString() {
        return "ADate{"+super.toString()+"}";
    }

    @Override
    public void accept(AVisitor v){
        v.visit(this);
    }
}
