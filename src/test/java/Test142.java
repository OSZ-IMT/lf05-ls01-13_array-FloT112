import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Test142 extends TestBase {

    @Test
    public void test142TicketPreiseBeispiel() {

        // assertion
        int rnd = rnd();
        assertEquals((float) ticketPreise[rnd], (float) Fahrkartenautomat.ticketPreise[rnd], 0.1f);
    }

    @Test
    public void test142TicketNamenBeispiel() {

        // assertion
        int rnd = rnd();
        assertEquals(ticketNamen[rnd], Fahrkartenautomat.ticketNamen[rnd]);
    }
}
