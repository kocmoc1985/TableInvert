/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MyObjectTableInvert;

import java.util.HashMap;

/**
 *
 * @author KOCMOC
 */
public class FakeValuesMaps {
    
    public static final HashMap<String, String> COMBO_BOX_FAKE_VALUES_MAP = new HashMap<String, String>();
    //
    static {
        //STATUS -> RECIPE_DETAILED -> TABLE_INVERT
        COMBO_BOX_FAKE_VALUES_MAP.put("352980126", "FAKE VALUE EXAMPLE COMBO BOX");
    }
    //
    public static final HashMap<String, String> TEXT_FIELD_FAKE_VALUES_MAP = new HashMap<String, String>();
    //
    static {
        TEXT_FIELD_FAKE_VALUES_MAP.put("186", "FAKE VALUE EXAMPLE TEXTFIELD");
    }
}
