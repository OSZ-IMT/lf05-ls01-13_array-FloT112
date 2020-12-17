import java.util.Scanner;

class Fahrkartenautomat {

    /**
     * Erfasst die Bestellung
     * @return Gesamter Ticketpreis
     */
    public static double fahrkartenbestellungErfassen() {
        int anzahlTickets;
        double ticketPreis;
        Scanner tastatur = new Scanner(System.in);

        System.out.print("Ticketpreis (EURO,Cent): ");
        ticketPreis = tastatur.nextDouble();

        System.out.print("Anzahl der Tickets: ");
        anzahlTickets = tastatur.nextInt();

        if (ticketPreis <= 0){
            ticketPreis = 1;
        }

        if (anzahlTickets < 1 || anzahlTickets > 10){
            anzahlTickets = 1;
        }

        return ticketPreis * anzahlTickets;
    }

    /**
     * Erhält den zu zahlenden Betrag und fragt den User ab, bis er den Betrag bezahlt hat
     * @param zuZahlenderBetrag, Betrag der zuzahlen ist
     * @return Rückgeld, wie viel überbezahlt wurde
     */
    public static double fahrkartenBezahlen(double zuZahlenderBetrag) {
        double eingeworfenemuenze;
        double eingezahlterGesamtbetrag = 0.0;
        Scanner tastatur = new Scanner(System.in);

        while (eingezahlterGesamtbetrag < zuZahlenderBetrag) {
            System.out.format("Noch zu zahlen: %4.2f € %n", (zuZahlenderBetrag - eingezahlterGesamtbetrag));
            System.out.print("Eingabe (mind. 5Ct, höchstens 2 Euro): ");
            eingeworfenemuenze = tastatur.nextDouble();
            eingezahlterGesamtbetrag += eingeworfenemuenze;
        }
        return eingezahlterGesamtbetrag - zuZahlenderBetrag;
    }

    /**
     * Gibt animiert die Meldung aus, das bezahlt wurde
     */
    public static void fahrkartenAusgeben() {
        System.out.println("\nFahrschein wird ausgegeben");
        for (int i = 0; i < 8; i++) {
            System.out.print("=");
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("\n\n");
    }

    /**
     * Erhält dem Rückgabebetrag und zahlt diesen in passenden Münzen aus
     */
    public static void rueckgeldAusgeben(double rueckgabebetrag) {

        if (rueckgabebetrag > 0.0) {
            System.out.format("Der Rückgabebetrag in Höhe von %4.2f € %n", rueckgabebetrag);
            System.out.println("wird in folgenden Münzen ausgezahlt:");

            while (rueckgabebetrag >= 2.0) {// 2 EURO-muenzen
                System.out.println("2 EURO");
                rueckgabebetrag -= 2.0;
            }
            while (rueckgabebetrag >= 1.0) {// 1 EURO-muenzen
                System.out.println("1 EURO");
                rueckgabebetrag -= 1.0;
            }
            while (rueckgabebetrag >= 0.5) // 50 CENT-muenzen
            {
                System.out.println("50 CENT");
                rueckgabebetrag -= 0.5;
            }
            while (rueckgabebetrag >= 0.2) // 20 CENT-muenzen
            {
                System.out.println("20 CENT");
                rueckgabebetrag -= 0.2;
            }
            while (rueckgabebetrag >= 0.1) // 10 CENT-Müzen
            {
                System.out.println("10 CENT");
                rueckgabebetrag -= 0.1;
            }
            while (rueckgabebetrag >= 0.05)// 5 CENT-muenzen
            {
                System.out.println("5 CENT");
                rueckgabebetrag -= 0.05;
            }
        }
    }

    /**
     * Main Methode, die alles aufruft
     * @param args, Startargumente, werden ignoriert
     */
    public static void main(String[] args) {

        double zuZahlenderBetrag;
        double rueckgabebetrag;

        zuZahlenderBetrag = fahrkartenbestellungErfassen();
        rueckgabebetrag = fahrkartenBezahlen(zuZahlenderBetrag);
        fahrkartenAusgeben();
        rueckgeldAusgeben(rueckgabebetrag);

        System.out.println("\nVergessen Sie nicht, den Fahrschein\n" + "vor Fahrtantritt entwerten zu lassen!\n"
                + "Wir wünschen Ihnen eine gute Fahrt.");
    }
}