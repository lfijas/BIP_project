package Controller;
import org.junit.Test;

import Controller.RetrieveNutritionalData;
import Model.Product;
import static org.junit.Assert.*;

public class Testing {

    @Test
    public void testConcatenate() {
        RetrieveNutritionalData myUnit = new RetrieveNutritionalData();

        Product result = myUnit.getProductInfo("8410199272393");
        assertEquals(result.getName(), "Doritos Nacho cheese");
        
        result = myUnit.getProductInfo("");
        assertNull(result);
        
        result = myUnit.getProductInfo("grawgewcwc");
        assertNull(result);
        
        result = myUnit.getProductInfo("235345213");
        assertNull(result);

    }
}