package pe.com.gp.util;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

/**
 * Clase para la exportacion a Microsoft Excel.
 *
 * @author: Alex D. Cabello.
 * @version: 13/03/2018 *
 */
public final class ExportaExcel {

    /**
     * Exporta un Map a Excel.
     *
     * @param titulo De tipo String. Ejemplo: "REPORTE DE INVENTARIO"
     * @param rpteHoja De tipo String. Ejemplo: "Hoja1"
     * @param pie De tipo boolean.
     * @param cabeceras De tipo String. Ejemplo String[] rpteCabeceras =
     * {"NRO.","FECHA","LOCAL"};
     * @param data De tipo Map.
     * @return De tipo InputStream.
     * @throws java.lang.Exception
     */
    public static ByteArrayOutputStream prepara(
            String titulo,
            String rpteHoja,
            boolean pie,
            String[] cabeceras,
            Map<Integer, Object[]> data) throws Exception {

        ByteArrayOutputStream baos = null;
        HSSFWorkbook wb = null;
        try {
            wb = new HSSFWorkbook();
            HSSFSheet sheet = wb.createSheet(rpteHoja);

            // Titulo
            HSSFCellStyle tit_style = wb.createCellStyle();
            HSSFFont tit_font = wb.createFont();
            tit_font.setFontName("ARIAL");
            tit_style.setFont(tit_font);
            tit_font.setFontHeightInPoints((short) 11);
            tit_font.setBold(true); //tit_font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            tit_style.setAlignment(HorizontalAlignment.CENTER); //tit_style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, cabeceras.length - 1));

            HSSFRow tit_row = sheet.createRow(0);
            HSSFCell tit_cell = tit_row.createCell(0);
            tit_cell.setCellValue(titulo);
            tit_cell.setCellStyle(tit_style);

            // Cabeceras    
            HSSFRow cab_row = sheet.createRow(2);
            HSSFCellStyle cab_style = wb.createCellStyle();
            HSSFFont cab_font = wb.createFont();
            cab_font.setFontName("ARIAL");
            cab_style.setFont(cab_font);
            cab_font.setFontHeightInPoints((short) 8);
            cab_font.setBold(true); //cab_font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            cab_style.setBorderBottom(BorderStyle.MEDIUM); // cab_style.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);            
            cab_style.setAlignment(HorizontalAlignment.CENTER); // cab_style.setAlignment(HSSFCellStyle.ALIGN_CENTER);

            for (int i = 0; i < cabeceras.length; i++) {
                HSSFCell cab_cell = cab_row.createCell(i);
                cab_cell.setCellValue(cabeceras[i]);
                cab_cell.setCellStyle(cab_style);
                sheet.autoSizeColumn(i);
            }

            // Cuerpo      
            HSSFCellStyle body_style = wb.createCellStyle();    // Crear el objeto Estilo de Celda
            HSSFFont body_font = wb.createFont();               // Crear el objeto Fuente    
            body_font.setFontName("ARIAL");
            int rownum = 3;
            int c = 0;
            Set<Integer> keyset = data.keySet();
            for (Integer key : keyset) {
                HSSFRow row = sheet.createRow(rownum++);
                //HSSFCellStyle body_style = wb.createCellStyle();
                //HSSFFont body_font = wb.createFont();
                body_style.setFont(body_font);
                body_font.setFontHeightInPoints((short) 8);
                Object[] objArr = data.get(key);
                int cellnum = 0;
                for (Object obj : objArr) {
                    HSSFCell cell = row.createCell(cellnum++);
                    cell.setCellStyle(body_style);
                    if (obj instanceof Date) {
                        cell.setCellValue((Date) obj);
                    } else if (obj instanceof Boolean) {
                        cell.setCellValue((Boolean) obj);
                    } else if (obj instanceof String) {
                        cell.setCellValue((String) obj);
                    } else if (obj instanceof Double) {
                        cell.setCellValue((Double) obj);
                    } else if (obj instanceof Long) {
                        cell.setCellValue((Long) obj);
                    } else if (obj instanceof Integer) {
                        cell.setCellValue((Integer) obj);
                    }
                    sheet.autoSizeColumn(c++);
                }
            }

            // Pie   
            if (pie) {
                HSSFCell pie_cell;
                HSSFCellStyle pie_style = wb.createCellStyle();
                HSSFFont pie_font = wb.createFont();
                pie_font.setFontName("ARIAL");
                pie_style.setFont(pie_font);
                pie_font.setFontHeightInPoints((short) 8);
                pie_font.setColor(IndexedColors.GREY_25_PERCENT.getIndex()); // pie_font.setColor(HSSFColor.GREY_25_PERCENT.index);
                pie_style.setAlignment(HorizontalAlignment.CENTER); // pie_style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                sheet.addMergedRegion(new CellRangeAddress(rownum + 1, rownum + 1, 0, cabeceras.length - 1));
                HSSFRow pie_row = sheet.createRow(rownum + 1);
                pie_cell = pie_row.createCell(0);
                pie_cell.setCellValue("Copyright " + Calendar.getInstance().get(Calendar.YEAR) + " © Grupo Pana S.A. Todos los derechos reservados.");
                pie_cell.setCellStyle(pie_style);
            }

            baos = new ByteArrayOutputStream();
            wb.write(baos);

        } catch (Exception e) {
            throw new Exception("GP.ERROR: " + e);
        } finally {
            try {
                if (baos != null) {
                    baos.close();
                }
                if (wb != null) {
                    wb.close();
                }
            } catch (Exception e) {
                throw new Exception("GP.ERROR: " + e);
            }
        }

//        baos = new ByteArrayOutputStream();
//        //ByteArrayInputStream bais = null;
//        try {
//            wb.write(baos);
//            //bais = new ByteArrayInputStream(baos.toByteArray());
//        } catch (IOException e) {
//            throw new Exception("GP.ERROR: " + e);
//        } finally {
//            try {
//                if (baos != null) {
//                    baos.close();
//                }
//            } catch (Exception e) {
//                throw new Exception("GP.ERROR: " + e);
//            }
//
//            /*try {
//                if (bais != null) {
//                    bais.close();
//                }
//            } catch (Exception e) {
//                throw new Exception("GP.ERROR: " + e);
//            }*/
//        }
        return baos;
    }

    /**
     * Exporta un Map a Excel, se usa para gran cantidad de registros, este
     * metodo no autoajusta las celdas jusamente porque se elaboro para no
     * afectar la performance.
     *
     * @param titulo De tipo String. Ejemplo: "REPORTE DE INVENTARIO"
     * @param rpteHoja De tipo String. Ejemplo: "Hoja1"
     * @param pie De tipo boolean.
     * @param cabeceras De tipo String. Ejemplo String[] rpteCabeceras =
     * {"NRO.","FECHA","LOCAL"};
     * @param data De tipo Map.
     * @return De tipo InputStream.
     * @throws java.lang.Exception
     */
    public static ByteArrayOutputStream preparaStreaming(
            String titulo, String rpteHoja, boolean pie,
            String[] cabeceras, Map<Integer, Object[]> data
    ) throws Exception {

        ByteArrayOutputStream baos = null;
        SXSSFWorkbook wb = null;
        try {
            wb = new SXSSFWorkbook(100);
            Sheet sheet = wb.createSheet(rpteHoja);

            // Titulo
            CellStyle tit_style = wb.createCellStyle();
            Font tit_font = wb.createFont();
            tit_font.setFontName("ARIAL");
            tit_style.setFont(tit_font);
            tit_font.setFontHeightInPoints((short) 11);
            tit_font.setBold(true); //tit_font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            tit_style.setAlignment(HorizontalAlignment.CENTER); //tit_style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, cabeceras.length - 1));

            Row tit_row = sheet.createRow(0);
            Cell tit_cell = tit_row.createCell(0);
            tit_cell.setCellValue(titulo);
            tit_cell.setCellStyle(tit_style);

            // Cabeceras    
            Row cab_row = sheet.createRow(2);
            CellStyle cab_style = wb.createCellStyle();
            Font cab_font = wb.createFont();
            cab_font.setFontName("ARIAL");
            cab_style.setFont(cab_font);
            cab_font.setFontHeightInPoints((short) 8);
            cab_font.setBold(true); //cab_font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            cab_style.setBorderBottom(BorderStyle.MEDIUM); // cab_style.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);            
            cab_style.setAlignment(HorizontalAlignment.CENTER); // cab_style.setAlignment(HSSFCellStyle.ALIGN_CENTER);

            for (int i = 0; i < cabeceras.length; i++) {
                Cell cab_cell = cab_row.createCell(i);
                cab_cell.setCellValue(cabeceras[i]);
                cab_cell.setCellStyle(cab_style);
            }

            // Cuerpo      
            CellStyle body_style = wb.createCellStyle();    // Crear el objeto Estilo de Celda
            Font body_font = wb.createFont();               // Crear el objeto Fuente  
            body_font.setFontName("ARIAL");
            int rownum = 3;
            Set<Integer> keyset = data.keySet();
            for (Integer key : keyset) {
                Row row = sheet.createRow(rownum++);
                body_style.setFont(body_font);
                body_font.setFontHeightInPoints((short) 8);
                Object[] objArr = data.get(key);
                int cellnum = 0;
                for (Object obj : objArr) {
                    Cell cell = row.createCell(cellnum++);
                    cell.setCellStyle(body_style);
                    if (obj instanceof Date) {
                        cell.setCellValue((Date) obj);
                    } else if (obj instanceof Boolean) {
                        cell.setCellValue((Boolean) obj);
                    } else if (obj instanceof String) {
                        cell.setCellValue((String) obj);
                    } else if (obj instanceof Double) {
                        cell.setCellValue((Double) obj);
                    } else if (obj instanceof Long) {
                        cell.setCellValue((Long) obj);
                    } else if (obj instanceof Integer) {
                        cell.setCellValue((Integer) obj);
                    }
                }
            }

            // Pie   
            if (pie) {
                Cell pie_cell;
                CellStyle pie_style = wb.createCellStyle();
                Font pie_font = wb.createFont();
                pie_font.setFontName("ARIAL");
                pie_style.setFont(pie_font);
                pie_font.setFontHeightInPoints((short) 8);
                pie_font.setColor(IndexedColors.GREY_25_PERCENT.getIndex()); // pie_font.setColor(HSSFColor.GREY_25_PERCENT.index);
                pie_style.setAlignment(HorizontalAlignment.CENTER); // pie_style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                sheet.addMergedRegion(new CellRangeAddress(rownum + 1, rownum + 1, 0, cabeceras.length - 1));
                Row pie_row = sheet.createRow(rownum + 1);
                pie_cell = pie_row.createCell(0);
                pie_cell.setCellValue("Copyright " + Calendar.getInstance().get(Calendar.YEAR) + " © Grupo Pana S.A. Todos los derechos reservados.");
                pie_cell.setCellStyle(pie_style);
            }

            baos = new ByteArrayOutputStream();
            wb.write(baos);
        } catch (Exception e) {
            throw new Exception("GP.ERROR: " + e);
        } finally {
            try {
                if (baos != null) {
                    baos.close();
                }
                if (wb != null) {
                    wb.dispose();
                    wb.close();
                }
            } catch (Exception e) {
                throw new Exception("GP.ERROR: " + e);
            }
        }
        return baos;
    }
}
