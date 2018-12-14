package fr.axonic.base;

import fr.axonic.base.engine.AVar;
import fr.axonic.base.engine.AVarProperty;
import fr.axonic.base.format.BoundedNumberFormat;
import fr.axonic.base.format.NumberFormat;
import fr.axonic.observation.binding.BindingTypesException;
import fr.axonic.validation.exception.VerificationException;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by cduffau on 07/07/16.
 */
public class ANumberTest {

    @Test
    public void testValue() throws VerificationException {
        ANumber aNumber = (ANumber) AVar.create(new NumberFormat());
        aNumber.setValue(1.0);
        assertEquals(aNumber.getValue().intValue(), 1);
        assertEquals(aNumber.getValue().floatValue(), 1.0f, 0);
        assertEquals(aNumber.getValue().doubleValue(), 1.0d, 0);
        assertEquals(aNumber.getValue().longValue(), 1L);
    }

    @Test
    public void testVerifiableProperties() throws VerificationException {
        AContinuousNumber aNumber = (AContinuousNumber) AVar.create(new BoundedNumberFormat());
        aNumber.setValue(1.0);
        assertTrue(aNumber.isPropertyVerifiable(AVarProperty.MIN.name()));
        assertTrue(aNumber.isPropertyVerifiable(AVarProperty.MAX.name()));
        assertTrue(aNumber.isPropertyVerifiable(AVarProperty.DEFAULT_VALUE.name()));
        assertTrue(aNumber.isPropertyVerifiable(AVarProperty.VALUE.name()));
    }

    @Test
    public void testClone() throws VerificationException, CloneNotSupportedException {
        AVar aNumber = AVar.create(new NumberFormat());
        aNumber.setValue(1.0);
        AVar aNumber2 = aNumber.clone();
        assertEquals(aNumber, aNumber2);
    }

    @Test
    public void testBind() throws BindingTypesException {
        ANumber num = new ANumber();
        ANumber num1 = new ANumber();
        ANumber num2 = new ANumber();

        // num.addListener(change -> {
        // System.out.println("num : " + change);
        // });
        //
        // num1.addListener(change -> {
        // System.out.println("num1 : " + change);
        // });

        num.bind(num1);

        assertTrue(num.isBound());
        assertTrue(!num1.isBound());

        assertTrue(num.isBindWith(num1));
        assertTrue(!num1.isBindWith(num));

        num.unbind();

        // num1.unsafeSetValue(200);

        assertTrue(!num.isBound());
        assertTrue(!num1.isBound());

        assertTrue(!num.isBindWith(num1));
        assertTrue(!num1.isBindWith(num));

        num.bind(num1);
        num1.bind(num2);

        assertTrue(num.isBindWith(num1));
        assertTrue(num1.isBindWith(num2));
        assertTrue(!num.isBindWith(num2));

        // num2.unsafeSetValue(200);

        assertTrue(num.isBound());
        assertTrue(num1.isBound());

        num1.unbind();

        // num2.unsafeSetValue(200);

        System.out.println("1. " + System.identityHashCode(num));
        System.out.println("2. " + System.identityHashCode(num1));
        System.out.println("3. " + System.identityHashCode(num2));

        System.out.println("1. bound with " + System.identityHashCode(num.getBindElement()));
        System.out.println("2. bound with " + System.identityHashCode(num1.getBindElement()));
        System.out.println("3. bound with " + System.identityHashCode(num2.getBindElement()));

        assertTrue(num.isBindWith(num1));
        assertTrue(!num1.isBindWith(num2));

        assertTrue(!num.isBindWith(num2));

    }

}
