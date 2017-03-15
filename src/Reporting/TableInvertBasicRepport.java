/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Reporting;

import static net.sf.dynamicreports.report.builder.DynamicReports.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import net.sf.dynamicreports.examples.Templates;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
//import net.sf.dynamicreports.examples.Templates;
import net.sf.dynamicreports.report.builder.FieldBuilder;
import net.sf.dynamicreports.report.builder.column.ComponentColumnBuilder;
import net.sf.dynamicreports.report.builder.component.VerticalListBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.builder.subtotal.AggregationSubtotalBuilder;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.constant.PageType;
import net.sf.dynamicreports.report.constant.VerticalAlignment;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.view.JasperViewer;

/**
 * @author Ricardo Mariaca (r.mariaca@dynamicreports.org)
 */
public class TableInvertBasicRepport {

    private VerticalListBuilder nameList;
    private VerticalListBuilder valueList;
    private ComponentColumnBuilder nameColumn;
    private ComponentColumnBuilder valueColumn;
    private FieldBuilder[] FIELDS;
    private String[] COLUMNS;

    public TableInvertBasicRepport(ArrayList<InvertTableRow> tableRowsList) {
        go(tableRowsList);
    }

    private void go(ArrayList<InvertTableRow> tableRowsList) {
        extractColumns(tableRowsList);
        buildNameList(COLUMNS);
        buildValueList(COLUMNS);
        build(tableRowsList);
    }

    private void extractColumns(ArrayList<InvertTableRow> tableRowsList) {
        COLUMNS = new String[tableRowsList.size()];
        //
        for (int i = 0; i < tableRowsList.size(); i++) {
            COLUMNS[i] = tableRowsList.get(i).getColumnName();
        }

    }

    private void buildNameList(String[] colNames) {
        nameList = cmp.verticalList();
        StyleBuilder nameStyle = stl.style().bold();
        //
        for (int i = 0; i < colNames.length; i++) {
            nameList.add(cmp.text(colNames[i]).setStyle(nameStyle));
        }
        //
        nameColumn = col.componentColumn(nameList);
    }

    private void buildValueList(String[] colNames) {
        valueList = cmp.verticalList();
        StyleBuilder valueStyle = stl.style().setHorizontalAlignment(HorizontalAlignment.LEFT);
        //
        FIELDS = new FieldBuilder[colNames.length];
        //
        for (int i = 0; i < colNames.length; i++) {
            FieldBuilder<String> field = field(colNames[i], type.stringType());
            //
            FIELDS[i] = field;
            //
            valueList.add(cmp.text(field).setStyle(valueStyle));
        }
        //
        valueColumn = col.componentColumn(valueList);
    }

    private void build(ArrayList<InvertTableRow> tableRowsList) {
//        AggregationSubtotalBuilder<BigDecimal> unitPriceSum = sbt.sum(unitPriceField, valueColumn)
//                .setLabel("Unit price sum =");

        StyleBuilder titleStyle = stl.style()
                .setVerticalAlignment(VerticalAlignment.TOP)
                .setFontSize(15);

        try {

            JasperReportBuilderM report = new JasperReportBuilderM();

            report.setTemplate(Templates.reportTemplate)
                    .setPageFormat(PageType.A4)
                    .fields(FIELDS)
                    .columns(nameColumn, valueColumn)
                    //                    .subtotalsAtSummary(unitPriceSum)
                    .title(//shows report title
                    cmp.horizontalList()
                    .add(
                    //                    cmp.image(getClass().getResourceAsStream("../images/dynamicreports.png")).setFixedDimension(80, 80),
                    cmp.text("MCRecipe").setStyle(titleStyle).setHorizontalAlignment(HorizontalAlignment.LEFT),
                    cmp.text("Repport").setStyle(titleStyle).setHorizontalAlignment(HorizontalAlignment.RIGHT))
                    .newRow()
                    .add(cmp.filler().setStyle(stl.style().setTopBorder(stl.pen2Point())).setFixedHeight(10)))
                    .pageFooter(Templates.footerComponent)
                    .setDataSource(createDataSource(tableRowsList))
                    .show(false);
            //

        } catch (DRException e) {

            e.printStackTrace();

        }

    }

    private JRDataSource createDataSource(ArrayList<InvertTableRow> tableRowsList) {
        //
        DRDataSource dataSource = new DRDataSource(COLUMNS);
        //
        for (int x = 0; x < tableRowsList.get(0).getValues().size(); x++) {
            //
            Object[] values = new String[tableRowsList.size()];
            //
            for (int i = 0; i < tableRowsList.size(); i++) {
                InvertTableRow row = tableRowsList.get(i);
                try {
                    values[i] = row.getValue(x);
                } catch (Exception ex) {
                    return dataSource;
                }
            }
            //
            dataSource.add(values);
            //
        }

        return dataSource;

    }

    private JRDataSource createDataSource_(ArrayList<InvertTableRow> tableRowsList) {

        DRDataSource dataSource = new DRDataSource("item", "orderdate", "quantity", "unitprice");

        dataSource.add("Notebook", new Date(), 1, new BigDecimal(500));

        dataSource.add("Book", new Date(), 4, new BigDecimal(25));

        dataSource.add("PDA", new Date(), 2, new BigDecimal(120));

        return dataSource;

    }
}