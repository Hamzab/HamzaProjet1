package main1;

import Files.FileReader;
import Files.FileWriter1;
import jsonOutput.JSON_Output;
import statistiques.ListeVehicule;
import statistiques.Statistiques;

public class Main {

    public static String tmp[] = new String[3];

    public static boolean validerArgs(String[] args) {
        boolean estValide = false;
        if (args.length == 2) {
            estValide = true;
        }
        return estValide;
    }

    public static boolean signerContrat(String path){
        boolean log=true;
        try{
        Statistiques s = new Statistiques();
        JSON_Output unjson = new JSON_Output();
        unjson.initialiser();
        FileWriter1.ecrire(path, unjson.retournerUnJSON());
        s.creerUnJsonStat();
        }catch(Exception e){
            log=false;
        }
        return log;
    }

    public static void appliquer(String[] args) throws Exception {
        if (!args[0].equals("-L") && !args[0].equals("-S")) {
            if (!FileReader.loadFileIntoString(args[0], "UTF-8").equals("")) {
                signerContrat(args[1]);
            }
        } else if (args[0].equals("-L")) {
            new ListeVehicule().ecrireListe(args[1]);
        } else if (args[0].equals("-S")) {
            new Statistiques().ecrireStats(args[1]);
        }
    }

    public static void main(String[] args) throws Exception {
      tmp = args;
        if (validerArgs(args)) {
            appliquer(args);
        } else {
            System.out.println("Erreur, ce programme doit accepter 2 arguments.");
        }
    }
}
