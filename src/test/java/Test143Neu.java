import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Test143Neu extends TestBase{

    private String backupTicketNamen;
    private double backupTicketPreise;

    @Before
    public void backupTicket(){
        backupTicketNamen = Fahrkartenautomat.ticketNamen[1];
        backupTicketPreise = Fahrkartenautomat.ticketPreise[1];

        Fahrkartenautomat.ticketNamen[1] = "test143test143";
        Fahrkartenautomat.ticketPreise[1] = 99f;
    }

    @After
    public void restoreTicket(){
        Fahrkartenautomat.ticketNamen[1] = backupTicketNamen;
        Fahrkartenautomat.ticketPreise[1] = (float) backupTicketPreise;
    }

    @Test
    public void test143BerechnungNeu() {
        SetTestData("2","1");

        // action
        double amount = Fahrkartenautomat.fahrkartenbestellungErfassen();

        // assertion
        assertEquals(99,amount,0.1);
    }

    @Test
    public void test143AusgabeErfassenNeu() {

        SetTestData("2","1");

        // action
        double amount = Fahrkartenautomat.fahrkartenbestellungErfassen();
        System.out.println(amount);

        // assertion
        CheckTestData("test143test143");
    }

}
