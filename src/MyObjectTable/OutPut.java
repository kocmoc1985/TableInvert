/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyObjectTable;

import supplementary.HelpA;


/**
 *
 * @author mcab
 */
public class OutPut implements ShowMessage {

    @Override
    public void showMessage(String str) {
        String msg = "[" + HelpA.get_proper_date_time_same_format_on_all_computers() + "] " + "  " + str + "\n";
        System.out.print(msg);
//        PROD_PLAN.jTextArea1Console.append(msg);
    }

}
