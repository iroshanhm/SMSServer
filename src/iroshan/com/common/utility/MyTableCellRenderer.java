/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package iroshan.com.common.utility;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.tree.DefaultTreeCellRenderer;

/**
 *
 * @author IroSHAN // extends JLabel
 */
public class MyTableCellRenderer extends JLabel implements TableCellRenderer{
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        DefaultTableCellRenderer dfTblCelRend = (DefaultTableCellRenderer)table.getCellRenderer(row, column);
        Component comp = dfTblCelRend.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        comp.setBackground(Color.red);
        return this;

        //throw new UnsupportedOperationException("Not supported yet.");
    }

}
