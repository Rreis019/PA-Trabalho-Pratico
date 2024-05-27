package pt.isec.pa.javalife.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;

import org.junit.Before;
import org.junit.Test;

import pt.isec.pa.javalife.model.EcosystemManager;
import pt.isec.pa.javalife.model.data.Area;
import pt.isec.pa.javalife.model.data.elements.Element;
import pt.isec.pa.javalife.model.data.elements.Fauna;
import pt.isec.pa.javalife.model.data.elements.Flora;
import pt.isec.pa.javalife.model.fsm.FaunaState;
import pt.isec.pa.javalife.model.gameengine.GameEngine;


public class FaunaStateMachineTest {

	EcosystemManager model;
	Fauna fauna = null;

    @Before
    public void setUp() {
    	model = new EcosystemManager();
        model.setWidth(300);
        model.setHeight(300);
        fauna = (Fauna)model.addElementToRandomFreePosition(Element.FAUNA);
        System.out.println(fauna);
    }

    @Test
    public void testMoveState() {
        // Verifica se a força diminui após o movimento
        double initialStrength = fauna.getStrength();
        fauna.getFSM().execute();
        assertTrue(fauna.getStrength() < initialStrength);
    }

    
    @Test
    public void testIfFaunaDies() throws NoSuchFieldException, SecurityException {
        fauna.setStrength(0);
        model.evolve();

        assertEquals(model.getElements().size(),0);
    }
    

    @Test
    public void testSearchFoodState() throws NoSuchFieldException, SecurityException {
        fauna.setStrength(33);
        model.addElementToRandomFreePosition(Element.FLORA);
        model.evolve();

        assertEquals(fauna.getState(),FaunaState.SEARCH_FOOD);
    }

    @Test
    public void testEating() throws NoSuchFieldException, SecurityException {
        fauna.setStrength(33);
        model.addElementToRandomFreePosition(Element.FLORA);
        model.addElementToRandomFreePosition(Element.INANIMATE);
        model.addElementToRandomFreePosition(Element.INANIMATE);
        model.addElementToRandomFreePosition(Element.INANIMATE);


        boolean startsEating = false;
        for (int i = 0; i < 50;i++ ) {
            model.evolve();
            if(fauna.getState() == FaunaState.EATING){startsEating = true;}
        }

        assertTrue(startsEating);
    }

    @Test
    public void testAttackingState() throws NoSuchFieldException, SecurityException {
        Area area = fauna.getArea();
        Fauna someFauna  = (Fauna)model.addElementToRandomFreePosition(Element.FAUNA);
        fauna.setStrength(33);
        someFauna.setStrength(22);

        model.evolve();
        someFauna.setPosition(area.left(), area.top());
        model.evolve();

        assertEquals(someFauna.getState(),FaunaState.ATTACKING);
    }

    @Test
    public void testFaunaMakeChild() throws NoSuchFieldException, SecurityException {
        Area area = fauna.getArea();
        Fauna someFauna = (Fauna)model.addElementToRandomFreePosition(Element.FAUNA);
        someFauna.setPosition(area.left(), area.top());

        fauna.setStrength(100);
        someFauna.setStrength(100);


        for (int i = 0; i < 15 ;i++) {
            model.evolve();
        }

        assertEquals(model.getElements().size(),4);
    }


    @Test
    public void testFaunaDontMakeChild() throws NoSuchFieldException, SecurityException {
        Area area = fauna.getArea();
        Fauna someFauna = (Fauna)model.addElementToRandomFreePosition(Element.FAUNA);
        someFauna.setPosition(area.left(), area.top());

        fauna.setStrength(100);
        someFauna.setStrength(100);


        for (int i = 0; i < 5 ;i++) {
            model.evolve();
        }

        assertEquals(model.getElements().size(),2);
    }
}