import java.util.ArrayList;
import java.util.Scanner;
class Fahrkartenautomat {
    //Passwort zum aufrufen des Administrationsmenues: Nuklearsprengcode_aktivieren:_40°_43′_N_74°_0′_W
    //Antwort zur Frage 7.1:Durch die Nutzung von Arrays kann man schneller Werte im Menue hinzufuegen, was die Performance und die Programmierung   von neuen Tickets schneller macht.
    public static void main(String[] args) {
        Scanner tastatur = new Scanner(System.in);
        String[] answer = {"Ja", "Yes", "ja", "jo", "Jo", "Ja ich will", "yes", "Klar Diggah", "klar diggah", "Die Magische Miesmuschel sagt ja", "YES WE CAN"};
        kasse(tastatur, answer);
    }
    protected static void kasse(Scanner tastatur,String[] answer) {
        double warenkorbGesamt = 0, warenkorbSumme, fahrkarte = 0;
        int ticketGesamt = 0, ticket = 0, ticketZwischenSumme;
        boolean bestellung = true, dauerlauf = true;
        ArrayList<Double> bestellungen = new ArrayList<>();
        ArrayList<Integer> ticketszahl = new ArrayList<>();
        int[] kasseBestand = new int[8];
        kasseBestand[0] = 30;kasseBestand[1] = 30;kasseBestand[2] = 30;kasseBestand[3] = 30;
        kasseBestand[4] = 30;kasseBestand[5] = 20;kasseBestand[6] = 0;kasseBestand[7] = 15;//7 = ticketbestand
        while (dauerlauf) {
            while (bestellung) {
                while (fahrkarte == 0 ^ fahrkarte == -5000) {
                    fahrkarte = fahrkartenbestellungErfassen(tastatur);
                    if (fahrkarte == -5000) {
                        adminMenue(tastatur, kasseBestand);
                    }
                }
                while (ticket == 0 ^ ticket == -5000) {
                    ticket = ticketanzahl(tastatur, answer, kasseBestand,ticketGesamt);
                    if (ticket == -5000) {
                        adminMenue(tastatur, kasseBestand);
                    }
                }
                warenkorbSumme = fahrkarte * ticket;
                bestellungen.add(warenkorbSumme);
                ticketszahl.add(ticket);
                ticketGesamt += ticket;
                warenkorbGesamt += warenkorbSumme;
                ticketZwischenSumme=(kasseBestand[7]-ticketGesamt);
                if (ticketZwischenSumme<0){ticketZwischenSumme=0;}
                System.out.printf("\nSie fuegen dem Warenkorb %.2f€ zu.\n\n", warenkorbSumme);
                System.out.printf("Im Warenkorb befindet sich ein Wert von: %.2f€\n", warenkorbGesamt);
                System.out.println("Sie haben "+ticketGesamt+" Tickets im Warenkorb und koennen noch "+ticketZwischenSumme+" Tickets hinzufuegen.\n");
                System.out.println("Moechten Sie eine weitere Bestellung hinzufuegen? ");
                fahrkarte=0;ticket=0;
                bestellung = frageAntwort(tastatur, answer);
                if (ticketZwischenSumme==0&&bestellung){System.out.println("Sie haben bereits das Maximum an Tickets erreicht. \nSie werden einige Ticketbestellungen loeschen muessen oder das Personal beten den Ticketbestand aufzufuellen.");warte(300);}
            }
            while (ticketGesamt > kasseBestand[7]) {
                System.out.printf("Das System hat nur noch %d Fahrkartenrohlinge zum drucken verfuegbar.\n", kasseBestand[7]);
                System.out.printf("Ihre aktuelle Ticketbestellung: %d\n", ticketGesamt);
                System.out.println("Bitte entfernen Sie eine Ticketbestellung.");
                for (int i = 0, j = 1; i < bestellungen.size(); i++, j++) {
                    System.out.printf("[%d] Bestellung %.2f mit %d Tickets\n", j, bestellungen.get(i), ticketszahl.get(i));
                }
                System.out.println("Bitte geben Sie die Nummer der zu löschenden Bestellung ein " + "oder Bestätigen Sie mit Ja, um den gesamten Kaufvorgang abzubrechen. " +
                        "\n Sie können auch das Personal bitten die Ticketrohlinge aufzufüllen.");
                System.out.print("Eingabe: ");
                try {
                    String zahl = tastatur.next();
                    if (zahl.equals("Nuklearsprengcode_aktivieren:_40°_43′_N_74°_0′_W")) {
                        adminMenue(tastatur, kasseBestand);
                    }
                    for (String s : answer) {
                        if (zahl.equals(s)) {
                            ticketGesamt = 0;fahrkarte=0;bestellungen.clear();
                            warenkorbGesamt = 0;ticket=0;ticketszahl.clear();break;
                        }
                    }
                    int r = Integer.parseInt(zahl);
                    ticketGesamt -= ticketszahl.get(-1 + r);
                    System.out.printf("\n\n[%d] mit %.2f und %d Tickets wurde aus dem Warenkorb entfernt.\n\n", r, bestellungen.get(-1 + r), ticketszahl.get(-1 + r));
                    ticketszahl.remove(-1 + r);
                    bestellungen.remove(-1 + r);
                } catch (NumberFormatException e) {
                    System.out.println("Die Eingabe war falsch. Geben Sie eine Zahl aus der Bestellliste ein.\n");
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.print("Die Zahl ist nicht im Menue vorhanden. Bitte geben Sie eine Zahl aus der Bestellliste ein.\n");
                }
            }
            System.out.println("Der Bezahlvorgang wird nun eingeleitet.\n");
            kasseBestand[7] -= ticketGesamt;
            double bezahlt = fahrkartenBezahlen(tastatur, warenkorbGesamt, kasseBestand);
            if (bezahlt == -5000) {
                adminMenue(tastatur, kasseBestand);
            }
            fahrkartenAusgeben(ticketGesamt);
            rueckgeldAusgabe(bezahlt, warenkorbGesamt, kasseBestand);
            System.out.println("\nVergessen Sie nicht, den Fahrschein\n" + "vor Fahrtantritt entwerten zu lassen!\n" + "Wir wünschen Ihnen eine gute Fahrt.\n\n");
            warte(1000);
            System.out.println("Das Programm wird neugestartet!");
            for (int i = 0; i < 20; i++) {
                System.out.print("=");
                warte(250);
            }
            System.out.println("\n\n");bestellung = true;
            ticketGesamt = 0;fahrkarte=0;bestellungen.clear();
            warenkorbGesamt = 0;ticket=0;ticketszahl.clear();
        }
    }
    private static int[] adminMenue(Scanner tastatur, int[] kassebestand) {
        String[] menue = {"Kassenbestand anzeigen", "Wechselgeld auf 30 Muenzen je Fach zuruecksetzen", "Ticketbestand auffuellen",
                "Geldscheinfach leeren", "Kasse komplett leeren", "Wechselgeld hinzufügen", "Menue verlassen"};
        double[] geldwert = {2, 1, 0.50, 0.20, 0.10, 0.05};
        System.out.println("\nAdministrationsmenue:");
        boolean boole = true, schleife = true;
        while (schleife) {
            for (int i = 0; i < menue.length; i++) { System.out.println("[" + (i+1) + "]" + menue[i]);warte(400); }
            System.out.print("Eingabe: ");
            String gibMirMoney = tastatur.next();
            System.out.println();
            switch (gibMirMoney) {
                case "1" -> {System.out.println("Kassenbestand:");
                    for (int i = 1; i < 7; i++) {
                        System.out.printf("%d x %.2f€\n", kassebestand[i - 1], geldwert[i - 1]);warte(400);
                    }
                    System.out.println();
                    warte(750);
                }
                case "2" -> {
                    System.out.println("Das Restgeld wird ausgeworfen!");
                    for (int i = 0; i < geldwert.length; i++) {
                        kassebestand[i] = 30;
                        System.out.println("*raschel* *kling* Geldauswurf *kling* *kling*");
                        System.out.printf("%.2f€ zurueckgesetzt auf 30 Stueck\n", geldwert[i]);
                        warte(400);
                    }
                    System.out.println();
                    warte(750);
                }
                case "3" -> {
                    System.out.println("500 Tickets aufgefuellt.");
                    kassebestand[7] = 500;
                    System.out.println();
                    warte(750);
                }
                case "4" -> {
                    kassebestand[6] = 0;
                    System.out.println("Geldscheine ausgeworfen, bitte alle Scheine entnehmen!");
                    System.out.println();
                    warte(750);
                }
                case "5" -> {
                    for (int i = 0; i < geldwert.length; i++) {
                        kassebestand[i] = 0;
                        System.out.println("*raschel* *kling* Geldauswurf *kling* *kling*");
                        warte(400);
                    }
                    kassebestand[7] = 0;
                    System.out.println("wwwt wwwt wwwt wwwt");
                    System.out.println("Geld ausgeworfen! Die Kasse ist nun komplett entleert.");
                    System.out.println();
                    warte(750);
                }
                case "6" -> {
                    System.out.println("Wechselgeldmenue:");
                    for (int i = 0; i < geldwert.length; i++) {
                        System.out.printf("[%d] %.2f€ Bestand: %d\n", (i + 1), geldwert[i], kassebestand[i]);warte(400);
                    }
                    while (boole) {
                        System.out.print("Welche Muenze soll aufgefuellt werden?" + "\nEingabe: ");
                        String auswahl = tastatur.next();
                        try {
                            int wahl = Integer.parseInt(auswahl);
                            System.out.printf("\nSie haben %.2f€ ausgewaehlt.\n", geldwert[wahl]);
                            System.out.println("\nEs passen bis zu 60 Muenzen ins Muenzfach!");
                            System.out.print("Wie viele Muenzen fuegen Sie hinzu?" + "\nEingabe: ");
                            int menge=tastatur.nextInt();
                            kassebestand[wahl - 1] += menge;
                            if (kassebestand[wahl-1] > 60 ^ kassebestand[wahl-1] < 0) {
                                System.out.println("Fehler: Maximale Muenzenmenge ueberschritten!");
                                kassebestand[wahl - 1] -= menge;
                                System.out.println("Das hinzufuegte Geld wurde wieder ausgeworfen.");
                            }
                            boole = false;
                        } catch (NumberFormatException e) {
                            System.out.println("Die Eingabe war falsch. Geben Sie eine Zahl aus der Liste ein.\n");
                        } catch (ArrayIndexOutOfBoundsException e) {
                            System.out.print("Die Zahl ist nicht in der Liste vorhanden. Bitte geben Sie eine Zahl aus der Liste ein.\n");
                        }
                    }
                    System.out.println();boole=true;
                    warte(750);
                }
                case "7" -> {
                    System.out.println("Das Menue wird geschlossen.");
                    warte(500);
                    schleife = false;
                }
            }
        }
        return kassebestand;
    }
    private static double  fahrkartenbestellungErfassen(Scanner tastatur) {
        double[] ticktetPreis = {2.90, 3.30, 3.60, 1.90, 8.60, 9.00, 9.60, 23.50, 24.30, 24.90};
        String[] ticketNamen = {"Einzelfahrschein Berlin AB", "Einzelfahrschein Berlin BC", "Einzelfahrschein Berlin ABC",
                "Kurzstrecke", "Tageskarte Berlin AB", "Tageskarte Berlin BC", "Tageskarte Berlin ABC", "Tageskarte Berlin AB",
                "Tageskarte Berlin", "Tageskarte Berlin ABC"};
        System.out.println("Fahrkarten Menue:");
        double ausgabe = -1;
        for (int i = 0; i < ticketNamen.length; i++) {
            System.out.print("["+(i+1)+"]");
            System.out.printf(" %s - %.2f€\n", ticketNamen[i], ticktetPreis[i]);
        }
        System.out.print("Fahrkarte: ");
        boolean input = true;
        while (input) {
            try {
                String eins = tastatur.next();
                if (eins.equals("Nuklearsprengcode_aktivieren:_40°_43′_N_74°_0′_W")){ausgabe=-5000;input=false;break;}
                int r = Integer.parseInt(eins);
                ausgabe = ticktetPreis[(r-1)];
                System.out.printf("\nSie haben %s - %.2f€ ausgewählt\n\n",ticketNamen[(r-1)],ausgabe);
                input = false;
            }catch (NumberFormatException e) {
                System.out.print("Die Eingabe war falsch. Bitte geben Sie eine Zahl aus dem Menue ein.\n"+"Bitte die Eingabe wiederholen: ");
            }catch (ArrayIndexOutOfBoundsException e){
                System.out.print("Die Eingabe ist nicht im Menue vorhanden. Bitte geben Sie eine Zahl aus dem Menue ein.\n"+"Bitte die Eingabe wiederholen: ");}
        }
        return ausgabe;
    }
    private static int ticketanzahl(Scanner tastatur, String[] answer,int[]kasseBestand,int ticketGesamt) {
        boolean input = true;
        int tickets = 0;
        while (input) {
            try {
                System.out.print("Es sind noch "+kasseBestand[7]+" Ticketrohlinge vorhanden.\n");
                if(ticketGesamt>0){System.out.println("Sie haben bereits "+ticketGesamt+" Tickets im Warenkorb."+"\n");}
                if (ticketGesamt>kasseBestand[7]){System.out.println((ticketGesamt-kasseBestand[7])+" muessen entfernt werden.\n");}
                System.out.print("Sie können bis zu 10 Tickets fuer ein Fahrschein auswaehlen. \n"+"Bitte geben Sie die gewuenschte Anzahl der Tickets ein. \n"+"Ticketanzahl: ");
                String eins = tastatur.next();
                if (eins.equals("Nuklearsprengcode_aktivieren:_40°_43′_N_74°_0′_W")){tickets=-5000;input=false;break;}
                tickets = Integer.parseInt(eins);
                if (tickets < 1) {
                    System.out.println("Minimale Anzahl an Tickets wurde unterschritten! Ticketanzahl: \n" + tickets+"\nMoechten Sie mit einem Ticket fortfahren?\n");
                    input= frageAntwort(tastatur,answer);
                    if (input){tickets=1;input=false;}
                } else if (tickets > 10) {
                    System.out.println("Maximale Anzahl an Tickets wurde ueberschritten! Ticketanzahl: \n" + tickets+"Moechten Sie mit einem Ticket fortfahren?");
                    input = frageAntwort(tastatur, answer);
                    if (input) { tickets = 1;input = false;}
                }else {input=false;}
            } catch (NumberFormatException e) {
                System.out.print("Die Eingabe war falsch. Bitte eine Ticketanzahl zwischen 1-10 eingeben.\n"+"Bitte die Eingabe wiederholen: ");
            }
        }
        return tickets;
    }
    private static double fahrkartenBezahlen(Scanner tastatur, double pwarenkorbGesamt,int[]kasseBestand) {
        double eingezahlterGesamtbetrag = 0.0;
        System.out.println("Folgende Scheine und Muenzen werden akteptiert.");
        System.out.println("Scheine: 5€, 10€, 20€, 50€, 100€, 200€, 500€");
        System.out.println("Muenzen: 2€, 1€, 50Cent, 20Cent, 10Cent, 5Cent");
        System.out.printf("Zu zahlender Betrag: %.2f€\n", (pwarenkorbGesamt - eingezahlterGesamtbetrag));
        String[] geld = {"0.05", "0.10", "0.20", "0.50", "1", "2", "5", "10", "20", "50", "100", "200", "500"};
        //Es werden nur Münzen ausgegeben, weil nur die Münzen grafisch dargestellt werden :)
        double kassenwert=kasseBestand[0]*2+kasseBestand[1]+kasseBestand[2]*0.50+kasseBestand[3]*0.20+kasseBestand[4]*0.10+kasseBestand[5]*0.05;
        while (eingezahlterGesamtbetrag < pwarenkorbGesamt) {
            boolean input = true;
            double muenzEingabe;
            System.out.print("Geldeinwurf: ");
            while (input) {
                String zahl = tastatur.next();
                if (zahl.equals("Nuklearsprengcode_aktivieren:_40°_43′_N_74°_0′_W")){eingezahlterGesamtbetrag=-5000;input=false;break;}
                try {
                    for (String s : geld) {
                        if (zahl.equals(s)) {
                            muenzEingabe = Double.parseDouble(zahl);
                            for (int j : kasseBestand) {
                                if (j == 60) {
                                    System.out.println("Der Muenzbestand ist voll. Bitte werfen Sie eine andere Muenze ein oder bitten das Personal die Kasse zu leeren. ");
                                    muenzEingabe = 0;
                                }
                            }
                            eingezahlterGesamtbetrag += muenzEingabe;
                            if (kassenwert<eingezahlterGesamtbetrag){System.out.println("Es ist nicht genug Wechselgeld vorhanden."+"Ihr eingeworfenes Geld wir ausgeworfen. Bitte werfen Sie das Geld passend ein. ");eingezahlterGesamtbetrag=0.0;}
                            if (muenzEingabe==2){kasseBestand[0]+=1;}else if(muenzEingabe==1){kasseBestand[1]+=1;}
                            else if(muenzEingabe==0.50){kasseBestand[2]+=1;} else if(muenzEingabe==0.20){kasseBestand[3]+=1;}
                            else if(muenzEingabe==0.10){kasseBestand[4]+=1;} else if(muenzEingabe==0.05){kasseBestand[5]+=1;}else if(muenzEingabe>2){kasseBestand[6]+=1;}
                            if ((pwarenkorbGesamt - eingezahlterGesamtbetrag)>0.00){
                                System.out.printf("Noch zu zahlen: %.2f€\n", (pwarenkorbGesamt - eingezahlterGesamtbetrag));}
                            input = false;
                            break;
                        }
                        else{input=true;}
                    }if (input) {
                        System.out.print("Das eingeworfene Geld entspricht nicht dem aktzeptierten Geld. Bitte wiederholen Sie den Geldeinwurf: ");
                    }
                }catch(NumberFormatException e){
                    System.out.println("Die Eingabe war falsch.\n");
                } catch(ArrayIndexOutOfBoundsException e){
                    System.out.print("Das eingeworfene Geld entspricht nicht dem aktzeptierten Geld. Bitte wiederholen Sie die Eingabe\n");
                }
            }
        }
        return eingezahlterGesamtbetrag;
    }
    private static void fahrkartenAusgeben(int panzahlFahrkarten) {
        int fahrscheine = 0;
        if (panzahlFahrkarten > 0) {
            while (panzahlFahrkarten >= 1) {
                panzahlFahrkarten -= 1;System.out.println("\n" + (fahrscheine +=1) + "." + "Fahrschein wird ausgegeben ");
                for (int i = 0; i < 20; i++) { System.out.print("=");warte(250);}
            }
        }
    }
    private static void rueckgeldAusgabe(double peingezahlterGesamtbetrag, double pzuZahlenderBetrag,int[] kasseBestand) {
        double rueckgeldAusgeben = (peingezahlterGesamtbetrag * 100) - (pzuZahlenderBetrag * 100);
        double rueckgabeBetrag = (rueckgeldAusgeben / 100);
        System.out.printf("\nDer Rueckgabebetrag in Höhe von %.2f€", rueckgabeBetrag);
        System.out.println(" wird in folgenden Münzen ausgezahlt:");
        ArrayList<Integer> muenzen = new ArrayList<>();
        ArrayList<String> waehrung=new ArrayList<>();
        while (rueckgeldAusgeben >= 200.0&&kasseBestand[0]>0) // 2 EURO-Münzen
        { rueckgeldAusgeben -= 200.0;muenzen.add(2);waehrung.add("EURO");kasseBestand[0]-=1; }
        while (rueckgeldAusgeben >= 100.0&&kasseBestand[1]>0) // 1 EURO-Münzen
        { rueckgeldAusgeben -= 100.0;muenzen.add(1);waehrung.add("EURO");kasseBestand[1]-=1; }
        while (rueckgeldAusgeben >= 50.0&&kasseBestand[2]>0) // 50 CENT-Münzen
        { rueckgeldAusgeben -= 50.0;muenzen.add(50);waehrung.add("CENT");kasseBestand[2]-=1; }
        while (rueckgeldAusgeben >= 20.0&&kasseBestand[3]>0) // 20 CENT-Münzen
        { rueckgeldAusgeben -= 20.0;muenzen.add(20);waehrung.add("CENT");kasseBestand[3]-=1; }
        while (rueckgeldAusgeben >= 10.0&&kasseBestand[4]>0) // 10 CENT-Münzen
        { rueckgeldAusgeben -= 10.0;muenzen.add(10);waehrung.add("CENT");kasseBestand[4]-=1; }
        while (rueckgeldAusgeben >= 5.0&&kasseBestand[5]>0)// 5 CENT-Münzen
        { rueckgeldAusgeben -= 5.0;muenzen.add(5);waehrung.add("CENT");kasseBestand[5]-=1; }
        muenzeAusgeben(muenzen,waehrung);
    }
    private static void muenzeAusgeben(ArrayList<Integer> betrag, ArrayList<String> einheit) {
        int muenzenFor;
        for (int i = 0; i < betrag.size(); i += 3) {
            if (i + 3 <= betrag.size()) { muenzenFor = 3;}
            else { muenzenFor = betrag.size() % 3;}
            String[] muenzenTemplate = {"   * * *   ", "  *     *  ", " *   %-2s  * ", " *  %4s * ", "  *     *  ", "   * * *   "};
            for (int j = 0; j < muenzenTemplate.length; j++) {
                warte(250);
                for (int z = 0; z < muenzenFor; z++) {
                    int zahl = betrag.get(i + z);String einheit2 = einheit.get(i + z);
                    if (j == 2) { System.out.printf(muenzenTemplate[j], zahl);}
                    else if (j == 3) { System.out.printf(muenzenTemplate[j], einheit2);}
                    else { System.out.print(muenzenTemplate[j]);}
                }
                System.out.println();
            }
        }
    }
    static void warte(int pmillisekunde) {
        try {
            Thread.sleep(pmillisekunde);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static boolean frageAntwort(Scanner tastatur,String[]answer){
        String input = tastatur.next();
        boolean bestellung=true;
        for (String s : answer) {
            if (input.equals(s)) {
                bestellung = true;
                break;
            } else {
                bestellung = false;
            }
        }
        return bestellung;
    }
}
