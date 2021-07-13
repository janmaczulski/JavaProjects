package com.jdbcapp.dao;

import java.util.Scanner;

import static com.jdbcapp.dao.Dao.*;

public class UI extends Thread{
    private static void printActions() {
        System.out.println("Akcje:");
        System.out.println("1. Zarzadzaj danymi");
        System.out.println("2. Przegladaj wplaty/naleznosci");
        System.out.println("3. Rejestracja wplat/naleznosci");
        System.out.println("4. Wyjdz");

    }

    private static void printViews()
    {
        System.out.println("Przegladaj:");
        System.out.println("1. Wszystkie naleznosci");
        System.out.println("2. Wplaty");
        System.out.println("3. Powrot");
        System.out.println("4. Wyjdz");
    }

    private static void printRegisterPayments()
    {
        System.out.println("Zarejestruj:");
        System.out.println("1. Wplate");
        System.out.println("2. Naleznosc");
        System.out.println("3. Skoryguj wplate");
        System.out.println("4. Skoryguj naleznosc");
        System.out.println("5. Powrot");
        System.out.println("6. Wyjdz");
    }

    private static void printManageOptions()
    {
        System.out.println("Opcje:");
        System.out.println("1. Klienci");
        System.out.println("2. Instalacje");
        System.out.println("3. Cennik");
        System.out.println("4. Powrot");
        System.out.println("5. Wyjdz");
    }

    private static void printClientManageOptions()
    {
        System.out.println("Opcje:");
        System.out.println("1. Dodaj klienta");
        System.out.println("2. Usun klienta");
        System.out.println("3. Zmien klienta");
        System.out.println("4. Powrot");
        System.out.println("5. Wyjdz");
    }

    private static void printInstalationManageOptions()
    {
        System.out.println("Opcje:");
        System.out.println("1. Dodaj instalacje");
        System.out.println("2. Usun instalacje");
        System.out.println("4. Powrot");
        System.out.println("5. Wyjdz");
    }

    private static void printPricesManageOptions()
    {
        System.out.println("Opcje:");
        System.out.println("1. Dodaj cene uslugi");
        System.out.println("2. Usun cene usluge");
        System.out.println("3. Zmien cene usluge");
        System.out.println("4. Powrot");
        System.out.println("5. Wyjdz");
    }

    private boolean running = true;

    public UI()
    {

    }

    public void run(){

        Scanner in = new Scanner(System.in);

        while(running==true){
            printActions();

            String s = in.nextLine();
            boolean actionSelected = true;
            if(s.equals("1"))
            {
                while(actionSelected)
                {
                    printManageOptions();
                    s = in.nextLine();
                    if(s.equals("1"))
                    {
                        printClientManageOptions();
                        s = in.nextLine();
                        if(s.equals("1"))
                        {
                            System.out.println("Imie:");
                            String newName = in.nextLine();
                            System.out.println("Nazwisko:");
                            String newLastName = in.nextLine();
                            Dao.addClient(newName,newLastName);
                            Dao.selectClients();
                        }
                        else if(s.equals("2"))
                        {
                            Dao.selectClients();
                            System.out.println("Id klienta:");
                            int deletionId = Integer.parseInt(in.nextLine());
                            Dao.deleteClient(deletionId);
                        }
                        else if(s.equals("3"))
                        {
                            Dao.selectClients();
                            System.out.println("Id klienta do zmiany:");
                            int id = Integer.parseInt(in.nextLine());
                            System.out.println("Nowe imie:");
                            String newName = in.nextLine();
                            System.out.println("Nowe nazwisko:");
                            String newLastName = in.nextLine();
                            Dao.alterClient(newName,newLastName,id);
                        }
                        else
                        {
                            actionSelected=false;
                        }
                    }
                    else if(s.equals("2"))
                    {
                        printInstalationManageOptions();
                        s = in.nextLine();
                        if(s.equals("1"))
                        {
                            selectClients();
                            System.out.println("Id klienta");
                            int idklienta = Integer.parseInt(in.nextLine());
                            System.out.println("Adres:");
                            String address = in.nextLine();
                            System.out.println("Typ uslugi");
                            String type = in.nextLine();
                            Dao.addInstallation(address,type,idklienta);
                            Dao.selectInstallations();
                        }
                        else if(s.equals("2"))
                        {
                            Dao.selectInstallations();
                            System.out.println("Id routera:");
                            int deletionId = Integer.parseInt(in.nextLine());
                            Dao.deleteInstallation(deletionId);
                        }
                        else if(s.equals("3"))
                        {
                            Dao.selectInstallations();
                            System.out.println("Id instalacji do zmiany:");
                            int id = Integer.parseInt(in.nextLine());
                            System.out.println("Nowy adres:");
                            String address = in.nextLine();
                            System.out.println("Nowy typ uslugi:");
                            String type = in.nextLine();
                            Dao.alterClient(address,type,id);
                        }
                        else
                        {
                            actionSelected=false;
                        }
                    }
                    else if(s.equals("3"))
                    {
                        printPricesManageOptions();
                        s = in.nextLine();
                        if(s.equals("1"))
                        {
                            System.out.println("Typ uslugi:");
                            String typ = in.nextLine();
                            System.out.println("Cena:");
                            double cena = Double.parseDouble(in.nextLine());
                            Dao.addCena(typ,cena);
                            Dao.selectCeny();
                        }
                        else if(s.equals("2"))
                        {
                            Dao.selectCeny();
                            System.out.println("Typ uslugi:");
                            String typ = in.nextLine();
                            Dao.deleteCena(typ);
                            Dao.selectCeny();
                        }
                        else if(s.equals("3"))
                        {
                            Dao.selectCeny();
                            System.out.println("Typ uslugi:");
                            String typ = in.nextLine();
                            System.out.println("Nowa cena:");
                            double cena = Double.parseDouble(in.nextLine());
                            Dao.alterCena(typ,cena);
                            Dao.selectCeny();
                        }
                        else
                        {
                            actionSelected=false;
                        }
                    }
                    else if(s.equals("4"))
                    {
                        actionSelected=false;
                    }
                    else
                    {
                        actionSelected=false;
                        running=false;
                    }
                }
            }
            else if(s.equals("2"))
            {
                while(actionSelected) {
                    printViews();
                    s = in.nextLine();

                    if(s.equals("1"))
                    {
                        System.out.println("Naleznosci:\n");
                        Dao.getNaleznosci();
                    }
                    else if(s.equals("2"))
                    {
                        System.out.println("Wplaty:\n");
                        getWplaty();
                    }
                    else if(s.equals("3"))
                    {
                        actionSelected=false;
                    }
                    else
                    {
                        actionSelected=false;
                        running=false;
                    }
                }
            }
            else if(s.equals("3"))
            {
                while(actionSelected) {
                    printRegisterPayments();
                    s = in.nextLine();

                    if(s.equals("1"))
                    {
                        System.out.println("Rachunki:\n");
                        getRachunki();
                        System.out.println("Wybierz rachunek: ");
                        int rachunek = Integer.parseInt(in.nextLine());
                        System.out.println("Podaj kwote wplaty: ");
                        double kwota = Double.parseDouble(in.nextLine());
                        addWplata(rachunek,kwota);
                    }
                    else if(s.equals("2"))
                    {

                        System.out.println("Klienci\n");
                        System.out.println("Imie" + "\t" + "Nazwisko" + "\t" + "Id Klienta\n");
                        selectClients();
                        System.out.println("\nPodaj kwote do zaplaty: ");
                        double kwota = Double.parseDouble(in.nextLine());
                        System.out.println("Podaj id klienta: ");
                        int id = Integer.parseInt(in.nextLine());
                        System.out.println("Podaj termin platnosci (YYYY-MM-DD): ");
                        String termin = in.nextLine() + " 23:59:59";
                        addNaleznosc(id, kwota, termin);
                    }
                    else if(s.equals("3"))
                    {
                        getWplaty();
                        System.out.println("\nPodaj id wplaty: ");
                        int idwplaty = Integer.parseInt(in.nextLine());
                        System.out.println("Podaj nowa kwote: ");
                        double kwota = Double.parseDouble(in.nextLine());
                        System.out.println("Podaj nowy termin wplaty (YYYY-MM-DD GG:MM:SS): ");
                        String termin = in.nextLine();
                        updateWplata(idwplaty,kwota,termin);
                    }
                    else if(s.equals("4"))
                    {
                        getRachunki();
                        System.out.println("\nPodaj id rachunku: ");
                        int idrachunku = Integer.parseInt(in.nextLine());
                        System.out.println("Podaj nowa kwote: ");
                        double kwota = Double.parseDouble(in.nextLine());
                        System.out.println("Podaj nowy termin wplaty (YYYY-MM-DD GG:MM:SS): ");
                        String termin = in.nextLine();
                        System.out.println("Czy oplacone?(1 - tak, 0 - nie)");
                        int oplacone = Integer.parseInt(in.nextLine());
                        updateNaleznosc(kwota,termin,idrachunku,oplacone);
                    }
                    else if(s.equals("5"))
                    {
                        actionSelected=false;
                    }
                    else
                    {
                        actionSelected=false;
                        running=false;
                    }
                }
            }
            else
            {
                running=false;
            }
        }
    }
}

