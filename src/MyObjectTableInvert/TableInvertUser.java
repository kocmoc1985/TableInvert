/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MyObjectTableInvert;

/**
 *
 * @author KOCMOC
 */
public interface TableInvertUser extends SaveIndicator.SaveIndicatorIF{
    
    
    public abstract RowDataInvert[] getConfigTableInvert();
    
    public abstract void showTableInvert();

}
