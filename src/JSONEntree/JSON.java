/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JSONEntree;

import Files.FileReader;
import main.Main;

/**
 *
 * @author Hamza
 */
public class JSON {

    public static String getJsonInfo() throws Exception {
        String jsonTxt = FileReader.loadFileIntoString(Main.tmp[0], "UTF-8");//Main.tmp[0]
        return jsonTxt;
    }
    public static String getJsonData() throws Exception {
        String jsonTxt = FileReader.loadFileIntoString("json/bd.json", "UTF-8");
        return jsonTxt;
    }
}
