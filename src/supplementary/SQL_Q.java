/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package supplementary;

/**
 *
 * @author KOCMOC
 */
public class SQL_Q {

    public static String basic_combobox_query(String parameter, String tableName) {
        return "SELECT DISTINCT " + parameter
                + " FROM " + tableName
                + " ORDER BY " + parameter + " ASC";
    }

    public static String basic_combobox_query_double_param(String parameter1, String parameter2, String tableName) {
        return "SELECT DISTINCT " + parameter1 + "," + parameter2
                + " FROM " + tableName
                + " ORDER BY " + parameter1 + " ASC";
    }
    
    public static String select_query_1() {
        return "SELECT top 5 VENDOR.Vendor_ID,dbo.VENDOR.VendorNo, dbo.VENDOR.VendorName, dbo.VENDOR.Adress, dbo.VENDOR.ZipCode, dbo.VENDOR.City, dbo.VENDOR.Country,\n"
                + " dbo.VENDOR.Phone, dbo.VENDOR.Fax, dbo.VENDOR.Website, dbo.VENDOR.FreeInfo, dbo.VENDOR.Status, dbo.Vendor_Contact.ContactName,\n"
                + " dbo.Vendor_Contact.position, dbo.Vendor_Contact.Phone AS Person_Phone, dbo.Vendor_Contact.Email AS Person_email,\n"
                + " dbo.Vendor_Contact.Status AS Person_Status\n"
                + " FROM dbo.VENDOR LEFT OUTER JOIN\n"
                + " dbo.Vendor_Contact ON dbo.VENDOR.VENDOR_ID = dbo.Vendor_Contact.VENDOR_ID";
    }
}
