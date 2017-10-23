/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MyObjectTable;

import MyObjectTableInvert.JComboBoxInvert;
import supplementary.HelpA;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Iterator;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

/**
 *
 * @author KOCMOC
 */
public class TableRow extends JPanel implements MouseListener, AncestorListener, ComponentListener {

    private final Border INITIAL_BORDER;
    public int ROW_NR;
    private int NR_COLUMNS;
    private String DATABASE_ID;
    private final LayoutManager LAYOUT;
    public static int FLOW_LAYOUT = 1;
    public static int GRID_LAYOUT = 2;
    protected int COLUMN_COUNT = 0;
    private final Table TABLE;
    public RowData ROW_COLUMN_DATA;

    public TableRow(RowData rowColumnObjects, String database_id, int row_nr, int layout, Table table) {
        //
        if (layout == FLOW_LAYOUT) {
            this.setLayout(new FlowLayout(FlowLayout.LEFT));
        } else if (layout == GRID_LAYOUT) {
            this.setLayout(new GridLayout(1, rowColumnObjects.size()));
        }
        //
        this.TABLE = table;
        this.LAYOUT = getLayout();
        this.DATABASE_ID = database_id;
        this.ROW_NR = row_nr + 1;
        this.NR_COLUMNS = rowColumnObjects.size();
        //
        ROW_COLUMN_DATA = rowColumnObjects;
        addData(rowColumnObjects);
        //
        this.setBorder(BorderFactory.createRaisedBevelBorder());
        INITIAL_BORDER = getBorder();
        //
        this.addComponentListener(this);
        this.addAncestorListener(this);
        gridLayoutFix();
    }
    
    

    private void gridLayoutFix() {
        if (this.getLayout() instanceof GridLayout) {
            GridLayout gridLayout = (GridLayout) this.getLayout();
            gridLayout.setHgap(5);
        }
    }

    private void addData(RowData rowColumnObjects) {
        for (Object object : rowColumnObjects) {
            addColumn(object);
        }

    }

    protected void addColumn(Object obj) {
        Component add_component = null;
        //
        if (obj instanceof String) {
            JLabel label = new JLabel("<html><p style='margin-left:5px'>" + (String) obj + "</p></html>");//(String) obj
            label.setBorder(BorderFactory.createRaisedBevelBorder());
            add_component = label;
            addComponent(add_component);
        } else if (obj instanceof JButton) {
            add_component = (Component) obj;
            addComponent(add_component);
        } else if (obj instanceof JComboBox) {
            add_component = (Component) obj;
            addComponent(add_component);
        } else if (obj instanceof SelectRowButton) {
            add_component = (Component) obj;
            addComponent(add_component);
            //
            //OBS! OBS!
            SelectRowButton srb = (SelectRowButton) obj;
            srb.setSelectedRow(ROW_NR);
            srb.setSelectedDatabaseId(DATABASE_ID);
            srb.addSelectRowBtnPressedListener(getTable());
        }
        //
        if (add_component != null) {
            add_component.addMouseListener(this);
            //
            JComponent component = (JComponent) add_component;
            component.addAncestorListener(this);
        }
        //
        getTable().row_col_object__column_count__map.put(add_component, COLUMN_COUNT);
        //        
        COLUMN_COUNT++;
    }

    protected void addComponent(Component componentToAdd) {
        this.add(componentToAdd);
    }
    
    public void setValueAt(int column_index,Object value){
        Component c = this.getComponent(column_index);
        //
        if (c instanceof JTextField) {
            JTextField jtf = (JTextField) c;
            jtf.setText(value.toString());
        } else if(c instanceof JComboBox){
            JComboBox jcb = (JComboBox)c;
            jcb.setSelectedItem(value);
        }
    }
    
    public Object getComponentAt(int column_index){
        return this.getComponent(column_index);
    }

    public String getValueAt(int column_index) {
        Component c = this.getComponent(column_index);
        //
        if (c instanceof JLabel) {
            JLabel label = (JLabel) c;
            return label.getText();
        } else if (c instanceof JComboBoxInvert) {
            JComboBoxInvert comboBox = (JComboBoxInvert) c;
//            return HelpA.getComboBoxSelectedValue(comboBox);
            return comboBox.getComboBoxSelectedValue();
        } else if (c instanceof JTextField) {
            JTextField jtf = (JTextField) c;
            return (String) jtf.getText();
        } else {
            return null;
        }
    }

    public RowData getRowConfig() {
//        Component[] c_arr = this.getComponents();
//        RowData rowColumnData = new RowData();
//
//        for (int i = 0; i < c_arr.length; i++) {
//            Component c = c_arr[i];
//            if (c instanceof JLabel) {
//                JLabel label = (JLabel) c;
//                String value = label.getText();
//                rowColumnData.addRowColumnData(value);
//            } else if (c instanceof JComboBox) {
//                JComboBox comboBox = (JComboBox) c;
//                String value = (String) comboBox.getSelectedItem();
//                rowColumnData.addRowColumnData(value);
//            }
//        }
//        return rowColumnData;
        return ROW_COLUMN_DATA;
    }

    public int getRowNr() {
        return ROW_NR;
    }

    public int getColumnCount() {
        return NR_COLUMNS;
    }

    public String getDatabaseId() {
        return DATABASE_ID;
    }

    public Table getTable() {
//        return (Table) this.getParent();
        return TABLE;
    }

    public void setRowNr(int nr) {
        this.ROW_NR = nr;
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        current_row_highlight(me.getSource());
        set_current_row__and__database_id();
    }

    private void set_current_row__and__database_id() {
        Table t = getTable();
        t.setCurrentRow(ROW_NR);
        t.setCurrentDatabaseId(DATABASE_ID);
        System.out.println("selected_row: " + t.getCurrentRow() + " / database_id: " + t.getCurrentDatabaseId());
    }

    public void current_row_highlight(Object source) {
        Component c = (Component) source;
        TableRow tr = (TableRow) c.getParent();
        //
        if (getTable().getSelectedRow() != tr.ROW_NR) {
            tr.setBorder(BorderFactory.createLoweredBevelBorder());
        }
    }

    @Override
    public void mouseExited(MouseEvent me) {
        row_unhighlight(me.getSource());
    }

    private void row_unhighlight(Object source) {
        Component c = (Component) source;
        TableRow tr = (TableRow) c.getParent();
        //
        if (getTable().getSelectedRow() != tr.ROW_NR) {
            tr.setBorder(INITIAL_BORDER);
        }

    }

    @Override
    public void mouseClicked(MouseEvent me) {
        if (me.getSource() instanceof SelectRowButton) {
            selectRow(me);
        }
    }

    private void selectRow(MouseEvent me) {
        SelectRowButton srb = (SelectRowButton) me.getSource();
        if (srb.isSelected()) {
            Table table = getTable();
            //
            unselectPreviousSelectedRow();
            //
            table.setSelectRowBtn(srb);
            table.setSelectedRow(ROW_NR);
            table.setSelectedDatabaseId(DATABASE_ID);
            //
//            table.getRow(ROW_NR).setBorder(new LineBorder(Color.green)); // Don't have it enabled
            //
            System.out.println("column_2_value: " + table.getValueAt(table.getSelectedRow(), 3));
        } else {
            unselectPreviousSelectedRow();
            //
            //
            RowData rcd = getTable().getRow(getTable().getSelectedRow()).getRowConfig();
        }
    }

    private void unselectPreviousSelectedRow() {
        Table table = getTable();
        SelectRowButton previous_srb = table.getSelectRowBtn();
        if (previous_srb != null) {
            TableRow prev_selected_row = table.getRow(table.getSelectedRow());
            prev_selected_row.setBorder(INITIAL_BORDER);
            previous_srb.setSelected(false);
        }
    }

    @Override
    public void mousePressed(MouseEvent me) {
    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void ancestorAdded(AncestorEvent ae) {
        //This must be done after the parent component JPanel is drawn otherwise the getWidth() will return 0
        resize_row_or_column(ae);
    }

    private void resize_row_or_column(AncestorEvent ae) {
        if (ae.getSource() instanceof JPanel) {
            JPanel row_panel = (JPanel) ae.getSource();
            //
            row_panel.setPreferredSize(new Dimension(TABLE.ROW_WIDTH_INITIAL, TABLE.ROW_HEIGHT));
            //
        } else {
            if (LAYOUT instanceof GridLayout) {
                this.updateUI();
                return;
            }
            JComponent c = (JComponent) ae.getSource();
            //
            if(getTable().row_col_object__column_count__map.get(c) == null){
                return;
            }
            //
            if(getWidth() == 0){
                this.updateUI();
                return;
            }
            //
            int column_nr = getTable().row_col_object__column_count__map.get(c);
            int w = calculateWidth(TABLE.COLUMN_WIDTHS_PERCENT[column_nr]);
            c.setPreferredSize(new Dimension(w, TABLE.ROW_HEIGHT - 10));
            this.updateUI();
        }
    }

    private int calculateWidth(int percent) {
        double p = ((double) percent / (double) 100);
        int x = (int) (getWidth() * p);
        return x;
    }

    @Override
    public void ancestorRemoved(AncestorEvent ae) {
        //
    }

    @Override
    public void ancestorMoved(AncestorEvent ae) {
        //
    }

    @Override
    public void componentResized(ComponentEvent ce) {
        resizeRows();
    }

    private void resizeRows() {
        if (LAYOUT instanceof GridLayout) {
            return;
        }
        Set set = getTable().row_col_object__column_count__map.keySet();
        Iterator it = set.iterator();
        while (it.hasNext()) {
            Component column_obj = (Component) it.next();
            int column = (Integer) getTable().row_col_object__column_count__map.get(column_obj);
            int w = calculateWidth(TABLE.COLUMN_WIDTHS_PERCENT[column]);
            column_obj.setPreferredSize(new Dimension(w, TABLE.ROW_HEIGHT - 10));
        }
        updateUI();
    }

    @Override
    public void componentMoved(ComponentEvent ce) {

    }

    @Override
    public void componentShown(ComponentEvent ce) {

    }

    @Override
    public void componentHidden(ComponentEvent ce) {

    }

}
