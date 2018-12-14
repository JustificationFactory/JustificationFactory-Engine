package fr.axonic.verification;


import fr.axonic.base.AContinuousNumber;
import fr.axonic.base.engine.AVar;
import fr.axonic.base.format.BoundedNumberFormat;
import fr.axonic.validation.exception.ErrorVerifyException;
import fr.axonic.validation.exception.RuntimeVerificationException;
import fr.axonic.validation.exception.VerificationException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by cduffau on 05/07/16.
 */
public class ANumberVerifierTest {

    @Test
    public void testGoodRangeVerifier() throws VerificationException {
        AContinuousNumber aNumber= (AContinuousNumber) AVar.create(new BoundedNumberFormat());
        aNumber.setMax(10);
        aNumber.setMin(1);

        aNumber.setValue(5);
    }

    @Test(expected = RuntimeVerificationException.class)
    public void testWrongRangeVerifier() throws VerificationException {
        AContinuousNumber aNumber= (AContinuousNumber) AVar.create(new BoundedNumberFormat());
        aNumber.setMax(10);
        aNumber.setMin(1);
        aNumber.setValue(0);
    }
    @Test
    public void testGoodVerifier() throws VerificationException {
        AContinuousNumber aNumber= (AContinuousNumber) AVar.create(new BoundedNumberFormat());
        aNumber.setMax(10);
        aNumber.setMin(1);

        aNumber.setValue(5);
        aNumber.verify(false);
    }

    @Test(expected = ErrorVerifyException.class)
    public void testWrongVerifier() throws VerificationException {
        AContinuousNumber aNumber= (AContinuousNumber) AVar.create(new BoundedNumberFormat());
        try {
            aNumber.setMax(10);
            aNumber.setMin(1);
        } catch (VerificationException e) {
            fail();
        }
        aNumber.verify(true);
    }

    @Test
    public void testGoodVerifier2() throws VerificationException {
        AContinuousNumber aNumber= (AContinuousNumber) AVar.create(new BoundedNumberFormat());
        aNumber.setCode("test");
        aNumber.setPath("test.test");
        aNumber.setMax(10);
        aNumber.setMin(1);
        aNumber.setDefaultValue(2);
        assertEquals(aNumber.getValue().intValue(),2);
        aNumber.setValue(5);
        assertEquals(aNumber.getValue().intValue(),5);
        aNumber.verify(true);
    }

    @Test
    public void testGoodVerifier3() throws VerificationException {
        AContinuousNumber aNumber= (AContinuousNumber) AVar.create(new BoundedNumberFormat());
        aNumber.setCode("test");
        aNumber.setPath("test.test");
        aNumber.setMax(10);
        aNumber.setMin(1);
        aNumber.setValue(5);
        assertEquals(aNumber.getValue().intValue(),5);
        aNumber.setDefaultValue(2);
        assertEquals(aNumber.getValue().intValue(),5);
        aNumber.verify(true);
    }
}
