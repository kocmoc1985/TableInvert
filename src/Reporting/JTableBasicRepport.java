/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Reporting;

import static net.sf.dynamicreports.report.builder.DynamicReports.*;
import java.util.ArrayList;
import javax.swing.JTable;
import net.sf.dynamicreports.examples.Templates;
import net.sf.dynamicreports.report.builder.column.ColumnBuilder;
import net.sf.dynamicreports.report.constant.PageOrientation;
import net.sf.dynamicreports.report.constant.PageType;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;
import supplementary.HelpA;

/**
 *
 * @author KOCMOC
 */
public class JTableBasicRepport {

    private ColumnBuilder[] COL_CONF;
    private String[] COLUMNS;
    private boolean PAGE_ORIENTATION_LANDSCAPE = false;

    public JTableBasicRepport(JTable table, boolean landscape) {
        PAGE_ORIENTATION_LANDSCAPE = landscape;
        buildColumns(table);
        build(table);
    }

    private void buildColumns(JTable table) {
        //
        ArrayList<String> colNames = HelpA.getVisibleColumnsNames(table);
        //
        COL_CONF = new ColumnBuilder[colNames.size()];
        COLUMNS = new String[colNames.size()];
        //
        for (int i = 0; i < colNames.size(); i++) {
            COL_CONF[i] = col.column(colNames.get(i), colNames.get(i), type.stringType());
            COLUMNS[i] = colNames.get(i);
        }
        //
        //

    }

    private void build(JTable table) {
        try {

            JasperReportBuilderM report = new JasperReportBuilderM();
            //
            report.setTemplate(Templates.reportTemplate);
            report.setPageFormat(PageType.A4, pageOrient());
            report.columns(COL_CONF);
            report.title(cmp.text("MCRecipe"));//shows report title
            report.pageFooter(cmp.pageXofY());//shows number of page at page footer
            report.setDataSource(createDataSource(table));//set datasource
            report.show(false);
            //
        } catch (DRException e) {
            e.printStackTrace();
        }
    }

    private net.sf.dynamicreports.report.constant.PageOrientation pageOrient() {
        if (PAGE_ORIENTATION_LANDSCAPE) {
            return PageOrientation.LANDSCAPE;
        } else {
            return PageOrientation.PORTRAIT;
        }
    }

    private JRDataSource createDataSource(JTable table) {
        DRDataSource dataSource = new DRDataSource(COLUMNS);

        for (int x = 0; x < table.getRowCount(); x++) {
            //
            Object[] values = new String[COLUMNS.length];
            //
            ArrayList rowValues = HelpA.getLineValuesVisibleColsOnly(table, x);

            for (int i = 0; i < rowValues.size(); i++) {
                values[i] = rowValues.get(i);
            }
            //
            dataSource.add(values);

        }

        return dataSource;

    }
//    private JRDataSource createDataSource() {
//        DRDataSource dataSource = new DRDataSource("item", "quantity", "unitprice");
//        dataSource.add("Notebook", "1", "");
//        dataSource.add("DVD", "5", "5");
//        dataSource.add("DVD", "11", "12");
//        return dataSource;
//
//    }
}
