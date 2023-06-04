/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.com.gp.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import pe.com.gp.util.Util;
import com.linuxense.javadbf.DBFWriter;
import com.linuxense.javadbf.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * @author Administrador
 */
public class grabardbf extends DispatchAction {

    private static final Logger LOGGER = LogManager.getLogger();

    public ActionForward inicializa(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        LOGGER.info("<==== Inicio Metodo: inicializa ====>");
        String mappingFindForward;
        if (Util.sesionEstaActiva(request)) {
            HttpSession session = request.getSession();
            mappingFindForward = "inicializa";
        } else {
            mappingFindForward = "logout";
        }
        //DBFWriter palomino = new DBFWriter(new File() );
        //palomino.
        //palomino.write();
        return mapping.findForward(mappingFindForward);
    }

    public String LeerCampos() {
        DBFReader reader = null;
        try {

            // create a DBFReader object
            reader = new DBFReader(new FileInputStream("D:\\libro1.dbf"));

            // get the field count if you want for some reasons like the following
            int numberOfFields = reader.getFieldCount();

            // use this count to fetch all field information
            // if required
            for (int i = 0; i < numberOfFields; i++) {

                DBFField field = reader.getField(i);

                // do something with it if you want
                // refer the JavaDoc API reference for more details
                //
                System.out.println(field.getName());
            }

            // Now, lets us start reading the rows
            Object[] rowObjects;

            while ((rowObjects = reader.nextRecord()) != null) {

                for (int i = 0; i < rowObjects.length; i++) {
                    System.out.println(rowObjects[i]);
                }
            }

            // By now, we have iterated through all of the rows
        } catch (DBFException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
           // DBFUtils.close(reader);
        }
        return "";
    }

    public String leer() {
        DBFReader reader = null;
        try {

            // create a DBFReader object
            reader = new DBFReader(new FileInputStream("D\\libro1.dbf"));

            // get the field count if you want for some reasons like the following
            int numberOfFields = reader.getFieldCount();

            // use this count to fetch all field information
            // if required
            for (int i = 0; i < numberOfFields; i++) {

                DBFField field = reader.getField(i);

                // do something with it if you want
                // refer the JavaDoc API reference for more details
                //
                System.out.println(field.getName());
            }

            // Now, lets us start reading the rows
//            DBFRow row;
//
//            while ((row = reader.nextRow()) != null) {
//                System.out.println(row.getString("PHONE"));
//            }

            // By now, we have iterated through all of the rows
        } catch (DBFException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //DBFUtils.close(reader);
        }
        return "";
    }

//    public String grabar() throws FileNotFoundException{
//        //https://github.com/albfernandez/javadbf
//        
//        try{
//            DBFWriter writer = new DBFWriter(new FileOutputStream("D:\\libro1.dbf"));
//        DBFField[] fields = new DBFField[2];
//		fields[0] = new DBFField();
//		fields[0].setName("codigo");
//		fields[0].setType(DBFDataType.CHARACTER);
//		fields[0].setLength(10);
//
//		fields[1] = new DBFField();
//		fields[1].setName("nombre");
//		fields[1].setType(DBFDataType.CHARACTER);
//		fields[1].setLength(20);
//
////		fields[2] = new DBFField();
////		fields[2].setName("salary");
////		fields[2].setType(DBFDataType.NUMERIC);
////		fields[2].setLength(12);
////		fields[2].setDecimalCount(2);
//
//		
//		writer.setFields(fields);
//
//		// now populate DBFWriter
//
//		Object rowData[] = new Object[2];
//		rowData[0] = "2000";
//		rowData[1] = "LUZ";
//		//rowData[2] = new Double(5000.00);
//
//		writer.addRecord(rowData);
//
//		rowData = new Object[2];
//		rowData[0] = "300";
//		rowData[1] = "JOSE";
//		//rowData[2] = new Double(3400.00);
//
//		writer.addRecord(rowData);
//
////		rowData = new Object[3];
////		rowData[0] = "1002";
////		rowData[1] = "Rohit";
////		//rowData[2] = new Double(7350.00);
////
////		writer.addRecord(rowData);
//
//		// write to file
//		writer.close();
//        // write to file
//        }catch (DBFException e) {
//            e.printStackTrace();
//        }
//        catch (FileNotFoundException e) {
//            e.printStackTrace();
//             
//        }  finally {
//           //writer.close();
//        }
//        
//        return "";
//    }

}
