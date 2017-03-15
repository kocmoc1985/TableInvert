/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyObjectTableInvert;

/**
 *
 * @author mcab
 */
public class HeaderInvert {

    private final Object header;
    private boolean unitHeader = false;
    private String realColName;
    private String tableName;

    public HeaderInvert(String header,String realColName,String tableName) {
        this.header = header;
        this.realColName = realColName;
        this.tableName = tableName;
    }
    
    public HeaderInvert(Object header,boolean unitHeader) {
        this.header = header;
        this.unitHeader = unitHeader;
    }

    public Object getHeader() {
        return header;
    }
    
    public boolean isUnitHeader(){
        return unitHeader;
    }

    public String getRealColName() {
        return realColName;
    }

    public String getTableName() {
        return tableName;
    }
    
}
