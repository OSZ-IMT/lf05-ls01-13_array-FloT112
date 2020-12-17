import org.junit.Test;

import static org.junit.Assert.*;

public class Test143 extends TestBase{

    @Test
    public void test143BerechnungErfassen() {

        int rnd = rnd();
        int anzahl = rnd()+1;
        SetTestData(String.valueOf(rnd+1),String.valueOf(anzahl));

        // action
        double price = Fahrkartenautomat.fahrkartenbestellungErfassen();

        // assertion
        assertEquals(ticketPreise[rnd]*anzahl,price,0.1);
    }

    @Test
    public void test143FehlerhafteEingabe() {

        SetTestData(String.valueOf(Fahrkartenautomat.ticketPreise.length+2),"2","2");

        // action
        double price = Fahrkartenautomat.fahrkartenbestellungErfassen();

        // assertion
        assertEquals(ticketPreise[1]*2, Fahrkartenautomat.ticketPreise[1]*2,0.1);
    }

    @Test
    public void test143AusgabeErfassen() {

        int rnd = rnd();
        SetTestData(String.valueOf(rnd+1),"1");

        // action
        Fahrkartenautomat.fahrkartenbestellungErfassen();

        // assertion
        CheckTestData(ticketNamen[rnd]);
    }

}
