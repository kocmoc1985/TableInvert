/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Reporting;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author KOCMOC
 */
public class JasperReportBuilderM extends JasperReportBuilder{
    
    @Override
      public JasperReportBuilder show(boolean exitOnClose) throws DRException {
         JasperViewer jview = new JasperViewer(toJasperPrint(), exitOnClose, null);
         jview.setTitle("MCRecipe");
                                          //below is standart icon - can change it
         //jview.setIconImage(new javax.swing.ImageIcon(getClass().getResource("/net/sf/jasperreports/view/images/jricon.GIF")).getImage());
         jview.setVisible(true);
         return this;
      }
    
}
