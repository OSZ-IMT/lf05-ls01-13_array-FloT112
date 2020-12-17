import org.junit.After;
import org.junit.Before;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Locale;

import static org.junit.Assert.*;

public class TestBase {

    // backup System.out to restore it later
    protected PrintStream originalOut;
    protected ByteArrayOutputStream bos;

    // backup System.in to restore it later
    protected InputStream sysInBackup;

    //testdata
    protected double[] ticketPreise = new double[]{2.9,3.3,3.6,1.9,8.6,9.0,9.6,23.5,24.3,24.9};
    protected String[] ticketNamen = new String[]{"Einzelfahrschein Berlin AB","Einzelfahrschein Berlin BC","Einzelfahrschein Berlin ABC",
            "Kurzstrecke","Tageskarte Berlin AB","Tageskarte Berlin BC","Tageskarte Berlin ABC","Kleingruppen-Tageskarte Berlin AB","Kleingruppen-Tageskarte Berlin BC","Kleingruppen-Tageskarte Berlin ABC"};

    @Before
    public void InitPrintStream(){
        Locale.setDefault(Locale.GERMANY);
        //bind the system
        originalOut = System.out;
        bos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(bos));

        sysInBackup = System.in; // backup System.in to restore it later
    }

    @After
    public void EndPrintStream(){
        // undo the binding in System
        System.setOut(originalOut);

        // reset System.in to its original
        System.setIn(sysInBackup);
    }

    /**
     * Helpermethod for inputs
     * @param args, string to check
     */
    protected void SetTestData(String... args) {
        ByteArrayInputStream in = new ByteArrayInputStream(String.join(System.lineSeparator(), args).getBytes());
        System.setIn(in);
    }

    /**
     * Helpermethod to check outputs
     * @param check, string to check
     */
    protected void CheckTestData(String check) {
        try {
            assertTrue(bos.toString().contains(check));
        } catch (Throwable t) {
            //print exact program
            assertEquals(check,bos.toString());
        }
    }

    /**
     * Helpermethod to check outputs
     * @param check, string to check
     */
    protected void CheckTestDataNot(String check) {
        try {
            assertFalse(bos.toString().contains(check));
        } catch (Throwable t) {
            //print exact program
            assertEquals(check,bos.toString());
        }
    }

    protected int rnd() {
        return (int) (Math.random()*ticketNamen.length);
    }
}
