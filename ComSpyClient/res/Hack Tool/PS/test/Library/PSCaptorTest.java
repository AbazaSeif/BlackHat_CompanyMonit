/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Library;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Administrator
 */
public class PSCaptorTest {
    
    public PSCaptorTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getdevlist method, of class PSCaptor.
     */
    @Test
    public void testGetdevlist() {
        System.out.println("getdevlist");
        String[] expResult = null;
        String[] result = PSCaptor.getdevlist();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of startUpdating method, of class PSCaptor.
     */
    @Test
    public void testStartUpdating() {
        System.out.println("startUpdating");
        PSCaptor instance = new PSCaptor();
        instance.startUpdating();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of stopUpdating method, of class PSCaptor.
     */
    @Test
    public void testStopUpdating() {
        System.out.println("stopUpdating");
        PSCaptor instance = new PSCaptor();
        instance.stopUpdating();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of startSesUpdating method, of class PSCaptor.
     */
    @Test
    public void testStartSesUpdating() {
        System.out.println("startSesUpdating");
        PSCaptor instance = new PSCaptor();
        instance.startSesUpdating();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of stopSesUpdating method, of class PSCaptor.
     */
    @Test
    public void testStopSesUpdating() {
        System.out.println("stopSesUpdating");
        PSCaptor instance = new PSCaptor();
        instance.stopSesUpdating();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of startSesThread method, of class PSCaptor.
     */
    @Test
    public void testStartSesThread() {
        System.out.println("startSesThread");
        PSCaptor instance = new PSCaptor();
        instance.startSesThread();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of stopSesThread method, of class PSCaptor.
     */
    @Test
    public void testStopSesThread() {
        System.out.println("stopSesThread");
        PSCaptor instance = new PSCaptor();
        instance.stopSesThread();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of main method, of class PSCaptor.
     */
    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        PSCaptor.main(args);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
