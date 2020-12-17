import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Test141 extends TestBase{


    @Test
    public void test141TicketPreiseLength() {

        // assertion
        assertEquals(ticketPreise.length, Fahrkartenautomat.ticketPreise.length);
    }

    @Test
    public void test141TicketPreiseInhalt() {

        // assertion
        int rnd = rnd();
        assertEquals((float)ticketPreise[rnd], (float)Fahrkartenautomat.ticketPreise[rnd], 0.1f);
    }

    @Test
    public void test141TicketNamenInhalt() {

        // assertion
        int rnd = rnd();
        assertEquals(ticketNamen[rnd], Fahrkartenautomat.ticketNamen[rnd]);
    }

    @Test
    public void test141TicketNamenLength() {

        // assertion
        assertEquals(ticketNamen.length, Fahrkartenautomat.ticketNamen.length);
    }

}
