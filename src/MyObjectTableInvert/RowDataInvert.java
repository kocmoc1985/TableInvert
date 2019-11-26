/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyObjectTableInvert;

import MyObjectTable.RowData;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import sql.SqlBasicLocal;
import supplementary.GP;
import supplementary.HelpA;

/**
 *
 * @author mcab
 */
public class RowDataInvert extends RowData {
    //

    private TableRowInvert PARENT;
    //
    public static final int TYPE_COMMON = 0;
    public static final int TYPE_JCOMBOBOX = 1;
    public static final int TYPE_JBUTTON = 2;
    public static final int TYPE_JLABEL = 3;
    //
    private int type = TYPE_COMMON;
    private String additionalInfo;
    private SqlBasicLocal sql;
    private String updateOtherTablesBefore = "";
    //
    private final String TableName;
    private final String primaryOrForeignKey;
    private final String fieldOrigName;
    private final String fieldNickName;
    private final Object unitOrObject;
    private final boolean visible;
    private final boolean important;
    private final boolean isString;
    private final boolean keyIsString;
    //
    private boolean editable = true;
    private boolean JTextFieldToolTipText = false;
    private boolean comboBoxMultipleValue = false;
    private boolean fakeValue = false;
    private boolean comboBoxFixedValue;

    /**
     *
     * @param tableName
     * @param primaryOrForeignKey = this key shall be present in the query with
     * the same name as in the original table to make it work
     * @param keyIsString
     * @param field_original_name = it's very important that it's the original
     * name, be careful when using "AS" in query
     * @param field_nick_name
     * @param unitOrObject
     * @param string
     * @param visible
     * @param important
     */
    public RowDataInvert(
            String tableName,
            String primaryOrForeignKey,
            boolean keyIsString,
            String field_original_name,
            String field_nick_name,
            Object unitOrObject,
            boolean string,
            boolean visible,
            boolean important) {
        this.TableName = tableName;
        this.primaryOrForeignKey = primaryOrForeignKey;
        this.keyIsString = keyIsString;
        this.fieldOrigName = field_original_name;
        this.fieldNickName = field_nick_name;
        this.unitOrObject = unitOrObject;
        this.isString = string;
        this.visible = visible;
        this.important = important;
    }

    /**
     * Extended constructor for Objects other then "String", for example
     * JComboBoxes or...
     *
     * @param type - type is an "int" which defines the Component type to be
     * used
     * @param additionalInfo - in case of JComboBox it's a sql query
     * @param sql
     * @param updateOtherTablesBefore - you shall pass a command like
     * "sql_cmd_1", which will mean that other tables must be updated before you
     * update your primary table
     */
    public RowDataInvert(
            int type,
            String additionalInfo,
            SqlBasicLocal sql,
            String updateOtherTablesBefore,
            String tableName,
            String primaryOrForeignKey,
            boolean keyIsString,
            String field_original_name,
            String field_nick_name,
            Object unit,
            boolean string,
            boolean visible,
            boolean important) {
        //
        this.type = type;
        this.additionalInfo = additionalInfo;
        this.sql = sql;
        this.updateOtherTablesBefore = updateOtherTablesBefore;
        //
        this.TableName = tableName;
        this.primaryOrForeignKey = primaryOrForeignKey;
        this.keyIsString = keyIsString;
        this.fieldOrigName = field_original_name;
        this.fieldNickName = field_nick_name;
        this.unitOrObject = unit;
        this.isString = string;
        this.visible = visible;
        this.important = important;
    }

      public void enableFixedValues() {
        comboBoxFixedValue = true;
    }

    public void enableFakeValue() {
        fakeValue = true;
    }

    public boolean fakeValueEnabled() {
        return fakeValue;
    }

    public void enableComboBoxMultipleValue() {
        comboBoxMultipleValue = true;
    }

    public boolean comboBoxMultipleValueEnabled() {
        return comboBoxMultipleValue;
    }

    public void enableToolTipTextJTextField() {
        JTextFieldToolTipText = true;
    }

    public boolean toolTipTextEnabled() {
        return JTextFieldToolTipText;
    }

    public void setUneditable() {
        editable = false;
    }

    public boolean isEditable() {
        return editable;
    }

    public String getUpdateOtherTablesBefore() {
        return updateOtherTablesBefore;
    }

    public int getType() {
        return type;
    }

    public Object getSpecialComponent(Object value) {
        //
        if (type == TYPE_JCOMBOBOX) {
            JComboBoxInvert jcb = new JComboBoxInvert(GP.TRACKING_TOOL_TIP);

            HelpA.addMouseListenerJComboBox(jcb, new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent me) {
                    System.out.println("YEEEE");
                }
            });

            if (comboBoxMultipleValue) {
                jcb.fillComboBox(sql, jcb, additionalInfo, value, true, false);
            } else if (fakeValue) {
                jcb.fillComboBox(sql, jcb, additionalInfo, value, false, true);
            }else if (comboBoxFixedValue) {
                String comboboxValues[] = HelpA.extract_comma_separated_values(additionalInfo);
                jcb = (JComboBoxInvert) HelpA.fillComboBox(jcb, comboboxValues, value);
            } else {
                jcb.fillComboBox(sql, jcb, additionalInfo, value, false, false);
            }
            //
            return (JComboBoxInvert)jcb;
            //
        } else if (type == TYPE_JBUTTON) {
        } else if (type == TYPE_JLABEL) {
        }
        //
        return null;
    }

    public String getFieldNickName() {
        return fieldNickName;
    }

    public String getFieldOriginalName() {
        return fieldOrigName;
    }

    public Object getUnit() {
        return unitOrObject;
    }

    public boolean getVisible() {
        return visible;
    }

    public boolean getImportant() {
        return important;
    }

    public boolean isString() {
        return isString;
    }

    public String getTableName() {
        return TableName;
    }

    public String getPrimaryOrForeignKeyName() {
        return primaryOrForeignKey;
    }

    public boolean getKeyIsString() {
        return keyIsString;
    }

    @Override
    public String toString() {
        return fieldOrigName + ", " + fieldNickName + ", " + unitOrObject + ", " + visible;
    }
}
