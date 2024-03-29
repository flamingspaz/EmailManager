/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emailmanager;

/**
 *
 * @author yousef
 */
// most code from http://fahdshariff.blogspot.co.uk/2010/02/display-any-resultset-in-jtable.html

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ResultSetTable extends JTable {

    private static DefaultTableModel dataModel;

    public ResultSetTable(ResultSet rs)
            throws SQLException {

        super();
        dataModel = new DefaultTableModel();
        setModel(dataModel);

        try {
            //create an array of column names
            ResultSetMetaData mdata = rs.getMetaData();
            int colCount = mdata.getColumnCount();
            String[] colNames = new String[colCount];
            for (int i = 1; i <= colCount; i++) {
                colNames[i - 1] = mdata.getColumnName(i);
            }
            dataModel.setColumnIdentifiers(colNames);

            //now populate the data
            while (rs.next()) {
                String[] rowData = new String[colCount];
                for (int i = 1; i <= colCount; i++) {
                    rowData[i - 1] = rs.getString(i);
                }
                dataModel.addRow(rowData);
            }
        } finally {
            try {
                rs.close();
            } catch (SQLException ignore) {
            }
        }
        // make it pretty for viewing
        getColumnModel().getColumn(0).setMaxWidth(30);
        getColumnModel().getColumn(0).setMinWidth(20);
        getColumnModel().getColumn(6).setMaxWidth(70);
        getColumnModel().getColumn(6).setMinWidth(70);
        removeColumn(getColumnModel().getColumn(4));
        setShowGrid(false);
    }
}
