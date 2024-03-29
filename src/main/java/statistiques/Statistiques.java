package statistiques;

import Files.FileWriter1;
import jsonInput.JSON_Input;
import jsonOutput.JSON_Output;
import java.io.IOException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

public class Statistiques {

    JSON_Output unjson = new JSON_Output();
    JSON_Input jsonin = new JSON_Input();
    JSONObject unObjet;

    public Statistiques() {
        unObjet = new JSONObject();
    }

    public boolean existeJson() {
        boolean res = true;
        try {
            if (jsonin.validerStats()) {
                unObjet = (JSONObject) JSONSerializer.toJSON(jsonin.getJsonStats());
            } else {
                res = false;
            }
        } catch (Exception e) {
            res = false;
        }
        return res;
    }

    public void initialiserNombresMarques1(JSONObject o[]) {
        o[0].put("marque", "Porsche");
        o[0].put("nombre", 0);
        o[1].put("marque", "Maserati");
        o[1].put("nombre", 0);
        o[2].put("marque", "Ferrari");
        o[2].put("nombre", 0);
        o[3].put("marque", "Lamborghini");
        o[3].put("nombre", 0);
        o[4].put("marque", "Koenigsegg");
        o[4].put("nombre", 0);
    }

    public void initialiserNombresMarques2(JSONObject o[]) {
        o[5].put("marque", "Aston Martin");
        o[5].put("nombre", 0);
        o[6].put("marque", "Pagani");
        o[6].put("nombre", 0);
        o[7].put("marque", "Bugatti");
        o[7].put("nombre", 0);
        o[8].put("marque", "W Motors");
        o[8].put("nombre", 0);
        o[9].put("marque", "Ducati");
        o[9].put("nombre", 0);
    }

    public void initialiserNombresMarques(JSONObject o[]) {
        initialiserNombresMarques1(o);
        initialiserNombresMarques2(o);
    }

    public JSONArray getVehiculeParMarque() {
        JSONObject o[] = new JSONObject[10];
        JSONArray tab = new JSONArray();
        for (int i = 0; i < 10; i++) {
            o[i] = new JSONObject();
        }
        initialiserNombresMarques(o);
        for (int i = 0; i < 10; i++) {
            tab.add(o[i]);
        }
        return tab;
    }

    public void initialiserStatistiques() {
        unObjet.put("nombre_de_soumissions", 0);
        unObjet.put("nombre_de_soumissions_non_assurables", 0);
        unObjet.put("nombre_de_soumissions_assurables", 0);
        unObjet.put("nombre_de_soumissions_hommes", 0);
        unObjet.put("nombre_de_soumissions_femmes", 0);
        unObjet.put("nombre_de_vehicules", 0);
        unObjet.put("nombre_de_voitures_assurables", 0);
        unObjet.put("nombre_de_motos_assurables", 0);
        unObjet.put("nombre_vehicules_demi_million", 0);
        unObjet.put("nombre_vehicules_million", 0);
        unObjet.put("vehicules_par_marque", getVehiculeParMarque());
    }

    public JSONObject creerUnJsonStat() throws IOException, Exception {
        initialiserStatistiques();
        unObjet = unjson.getStats();
        FileWriter1.ecrire("json/tmpstats.json", unObjet);
        return unObjet;
    }

    public JSONArray retournerMarques(JSONObject ob) {
        JSONArray tab = ob.getJSONArray("vehicules_par_marque");
        JSONArray tab2 = new JSONArray();
        for (int i = 0; i < tab.size(); i++) {
            JSONObject ob1 = tab.getJSONObject(i);
            if (ob1.getInt("nombre") != 0) {
                tab2.add(ob1);
            }
        }
        return tab2;
    }

    public JSONObject copier1(JSONObject ob, JSONObject ob1) {
        ob1.put("nombre_de_soumissions", ob.getInt("nombre_de_soumissions"));
        ob1.put("nombre_de_soumissions_non_assurables", ob.getInt("nombre_de_soumissions_non_assurables"));
        ob1.put("nombre_de_soumissions_assurables", ob.getInt("nombre_de_soumissions_assurables"));
        ob1.put("nombre_de_soumissions_hommes", ob.getInt("nombre_de_soumissions_hommes"));
        ob1.put("nombre_de_soumissions_femmes", ob.getInt("nombre_de_soumissions_femmes"));
        ob1.put("nombre_de_vehicules", ob.getInt("nombre_de_vehicules"));

        return ob1;
    }

    public JSONObject copier() {
        JSONObject ob = new JSON_Input().getJsonStats(), ob1 = new JSONObject();
        ob1 = copier1(ob, ob1);
        ob1.put("nombre_de_voitures_assurables", ob.getInt("nombre_de_voitures_assurables"));
        ob1.put("nombre_de_motos_assurables", ob.getInt("nombre_de_motos_assurables"));
        ob1.put("nombre_vehicules_demi_million", ob.getInt("nombre_vehicules_demi_million"));
        ob1.put("nombre_vehicules_million", ob.getInt("nombre_vehicules_million"));
        ob1.put("vehicules_par_marque", retournerMarques(ob));
        return ob1;
    }

    public boolean ecrireStats(String path) throws IOException, Exception {
        JSONObject ob = new JSONObject();
        boolean res = false;
        try {
            ob = copier();
            FileWriter1.ecrire(path, ob);
            res = true;
        } catch (Exception e) {}
        if (ob.isEmpty()) {
            FileWriter1.ecrire(path, creerUnJsonStat());
        }
        return res;
    }
}
