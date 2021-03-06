package com.techelevator.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;


public class Inventory {
    private ArrayList<VendingMachineSnack> snackArrayList= new ArrayList();

    public Inventory(){

        readInventory();

    }

    public VendingMachineSnack ifCodeMatchesReturnSnack(String code) {

        for (int i = 0; i < snackArrayList.size(); i++) {
            if(snackArrayList.get(i).getIdentifier().equals(code)){
                //check inventory
                if(snackArrayList.get(i).getInventory()>0){
                    return snackArrayList.get(i);
                }
                else{ //matches but no inventory
                    System.out.println("Product sold out (inventory=0)");
                    return null;
                }
            }
        }
        System.out.println("Product Not Found");
        return null;

    }


    public void dispense(VendingMachineSnack temp){

        System.out.println(temp.dispenseMessage());
        temp.subtractInventory();
        temp.addToAmountBought();
        System.out.println(temp.getName()+" "+ temp.getPrice()+ " Remaining Inventory: "+temp.getInventory());

    }


    public void readInventory(){

        String filepath=
                "C:\\Users\\leeda\\Desktop\\MeritAmerica\\Module 1 Capstone Vending Machine\\capstone\\vendingmachine.csv";
        File dataFile = new File(filepath);


        try(Scanner fin = new Scanner(dataFile);){
            while(fin.hasNextLine()){
                String lineOfInput=fin.nextLine();
                String[] lineOfInputToArray=lineOfInput.split("\\|");

                VendingMachineSnack tempSnack=
                        new VendingMachineSnack(lineOfInputToArray[0],lineOfInputToArray[1],Double.parseDouble(lineOfInputToArray[2]),lineOfInputToArray[3]);

                snackArrayList.add(tempSnack);

            }
        }
        catch (FileNotFoundException ex){
            System.out.println("File not opened");

        }


    }

    //Prints each snack in snack array list using toString
    public void printInventory(){

        for (VendingMachineSnack s: snackArrayList) {
            System.out.println(s.toString());
        }

    }

    public String printInventoryForSalesReport(){

        String returnString="";
        double totalSales=0;
        for (VendingMachineSnack s: snackArrayList) {
            totalSales+=(s.getAmountBought())*s.getPrice();
            returnString+=s.toStringForSalesReport();

        }
        DecimalFormat df = new DecimalFormat("0.00");

        returnString+="\n"+ "TOTAL SALES: "+ df.format(totalSales) +"$";
        return returnString;

    }






}
