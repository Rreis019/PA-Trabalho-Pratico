package pt.isec.pa.javalife.tests;

import org.junit.Test;
import static org.junit.Assert.*;
import pt.isec.pa.javalife.Main;


public class MainTest {


    @Test
    public void testSomar() {
        Main main = new Main();
        int resultado = main.somar(3, 7);
        assertEquals(10, resultado);
    }
}
