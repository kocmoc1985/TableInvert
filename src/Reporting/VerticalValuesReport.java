///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package Reporting;
//
//import static net.sf.dynamicreports.report.builder.DynamicReports.*;
//
//import java.math.BigDecimal;
//import java.util.Date;
//import net.sf.dynamicreports.examples.Templates;
//import net.sf.dynamicreports.report.builder.FieldBuilder;
//import net.sf.dynamicreports.report.builder.column.ComponentColumnBuilder;
//import net.sf.dynamicreports.report.builder.component.VerticalListBuilder;
//import net.sf.dynamicreports.report.builder.style.StyleBuilder;
//import net.sf.dynamicreports.report.builder.subtotal.AggregationSubtotalBuilder;
//import net.sf.dynamicreports.report.constant.HorizontalAlignment;
//import net.sf.dynamicreports.report.constant.PageType;
//import net.sf.dynamicreports.report.constant.VerticalAlignment;
//import net.sf.dynamicreports.report.datasource.DRDataSource;
//import net.sf.dynamicreports.report.exception.DRException;
//import net.sf.jasperreports.engine.JRDataSource;
//
///**
// * @author Ricardo Mariaca (r.mariaca@dynamicreports.org)
// */
//public class VerticalValuesReport {
//
//    public VerticalValuesReport() {
//        build();
//    }
//    
//   
//    private void build() {
//        StyleBuilder nameStyle = stl.style().bold();
//        StyleBuilder valueStyle = stl.style().setHorizontalAlignment(HorizontalAlignment.LEFT);
//        FieldBuilder<String> itemField = field("item", type.stringType());
//        FieldBuilder<Integer> quantityField = field("quantity", type.integerType());
//        FieldBuilder<BigDecimal> unitPriceField = field("unitprice", type.bigDecimalType());
//        FieldBuilder<Date> orderDateField = field("orderdate", type.dateType());
//
//        
//        VerticalListBuilder nameList = cmp.verticalList(
//                cmp.text("Item:").setStyle(nameStyle),
//                cmp.text("Quantity:").setStyle(nameStyle),
//                cmp.text("Unit price:").setStyle(nameStyle),
//                cmp.text("Order date:").setStyle(nameStyle));
//
//        VerticalListBuilder valueList = cmp.verticalList(
//                cmp.text(itemField).setStyle(valueStyle),
//                cmp.text(quantityField).setStyle(valueStyle),
//                cmp.text(unitPriceField).setStyle(valueStyle),
//                cmp.text(orderDateField).setStyle(valueStyle));
//
//        ComponentColumnBuilder nameColumn = col.componentColumn("Name", nameList);
//
//        ComponentColumnBuilder valueColumn = col.componentColumn("Value", valueList);
//        
////        AggregationSubtotalBuilder<BigDecimal> unitPriceSum = sbt.sum(unitPriceField, valueColumn)
////                .setLabel("Unit price sum =");
//
//        StyleBuilder titleStyle = stl.style()
//                .setVerticalAlignment(VerticalAlignment.TOP)
//                .setFontSize(15);
//
//        try {
//
//            report()
//                    .setTemplate(Templates.reportTemplate)
//                    .setPageFormat(PageType.A4)
//                    .fields(itemField, quantityField, unitPriceField, orderDateField)
//                    .columns(nameColumn, valueColumn)
//                    //                    .subtotalsAtSummary(unitPriceSum)
//                    .title(//shows report title
//                    cmp.horizontalList()
//                    .add(
////                    cmp.image(getClass().getResourceAsStream("../images/dynamicreports.png")).setFixedDimension(80, 80),
//                    cmp.text("MCRecipe").setStyle(titleStyle).setHorizontalAlignment(HorizontalAlignment.LEFT),
//                    cmp.text("Getting started").setStyle(titleStyle).setHorizontalAlignment(HorizontalAlignment.RIGHT))
//                    .newRow()
//                    .add(cmp.filler().setStyle(stl.style().setTopBorder(stl.pen2Point())).setFixedHeight(10)))
//                    .pageFooter(Templates.footerComponent)
//                    .setDataSource(createDataSource())
//                    .show();
//
//
//
//        } catch (DRException e) {
//
//            e.printStackTrace();
//
//        }
//
//    }
//
//    private JRDataSource createDataSource() {
//        DRDataSource dataSource = new DRDataSource("item", "orderdate", "quantity", "unitprice");
//        dataSource.add("Notebook", new Date(), 1, new BigDecimal(500));
//        dataSource.add("Book", new Date(), 4, new BigDecimal(25));
//        dataSource.add("PDA", new Date(), 2, new BigDecimal(120));
//
//        return dataSource;
//
//    }
//
//    public static void main(String[] args) {
//
//        new VerticalValuesReport();
//
//    }
//}