package fr.axonic.avek.model.base;


import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class ADate extends AVar<Date>{

    public ADate() {
        this(null);
    }

    public ADate(Date value) {
        super(new Format(FormatType.DATE),value);
    }
    public ADate(String label, Date value) {
        super(label,new Format(FormatType.DATE), value);
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
}
