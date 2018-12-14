package fr.axonic.base;


import static org.junit.Assert.fail;

import org.junit.Test;

import fr.axonic.TestEnum;
import fr.axonic.base.engine.AVar;
import fr.axonic.base.format.BooleanFormat;
import fr.axonic.base.format.BoundedDateFormat;
import fr.axonic.base.format.BoundedNumberFormat;
import fr.axonic.base.format.DateFormat;
import fr.axonic.base.format.EnumFormat;
import fr.axonic.base.format.NumberFormat;
import fr.axonic.base.format.RangedEnumFormat;
import fr.axonic.base.format.RangedStringFormat;
import fr.axonic.base.format.StringFormat;

/**
 * Created by cduffau on 07/07/16.
 */
public class AVarTest {

    @Test
    public void testCreateAVar(){

        if(!(AVar.create(new NumberFormat()) instanceof ANumber)){
            fail();
        }
        if(!(AVar.create(new BooleanFormat()) instanceof ABoolean)){
            fail();
        }
        if(!(AVar.create(new StringFormat()) instanceof AString)){
            fail();
        }
        if(!(AVar.create(new DateFormat()) instanceof ADate)){
            fail();
        }
        if(!(AVar.create(new EnumFormat<TestEnum>(TestEnum.class)) instanceof AEnum)){
            fail();
        }
        if(!(AVar.create(new RangedEnumFormat<TestEnum>(TestEnum.class)) instanceof ARangedEnum)){
            fail();
        }
        if(!(AVar.create(new BoundedNumberFormat()) instanceof AContinuousNumber)){
            fail();
        }
        if(!(AVar.create(new BoundedDateFormat()) instanceof AContiniousDate)){
            fail();
        }
        if(!(AVar.create(new RangedStringFormat()) instanceof ARangedString)){
            fail();
        }



        if(!(AVar.create(new BoundedDateFormat()) instanceof ADate)){
            fail();
        }
        if(!(AVar.create(new BoundedNumberFormat()) instanceof ANumber)){
            fail();
        }
        if(!(AVar.create(new RangedEnumFormat<TestEnum>(TestEnum.class)) instanceof AEnum)){
            fail();
        }
        if(!(AVar.create(new RangedStringFormat()) instanceof AString)){
            fail();
        }
    }

}
