package pe.com.gp.util;

import java.math.BigInteger;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTHMerge;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSpacing;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTbl;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTVMerge;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STBorder;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STLineSpacingRule;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;

/**
 * Clase con Snippets utiles para el desarrollo con Java utilizando la libreria
 * Apache POI
 *
 * @author: Alex D. Cabello.
 * @version: 27/09/2017
 */
public final class UtilPOI {

    public static void poiWordSaltoLinea(XWPFDocument document, int numLineas) {
        XWPFParagraph parrafo = document.createParagraph();
        poiWordQuitaEspaciosParrafo(parrafo);
        XWPFRun parrafoRun = parrafo.createRun();
        for (int i = 1; i < numLineas; i++) { // inicia en 1
            parrafoRun.addBreak();
        }
    }

    public static void poiWordQuitaEspaciosParrafo(XWPFParagraph parrafo) {
        /*parrafo.setSpacingAfter(0);
        parrafo.setSpacingAfterLines(0);
        parrafo.setSpacingBefore(0);
        parrafo.setSpacingBeforeLines(0);
        parrafo.setSpacingBetween(0);
        parrafo.setSpacingLineRule(LineSpacingRule.EXACT);*/
        CTPPr ppr = parrafo.getCTP().getPPr();
        if (ppr == null) {
            ppr = parrafo.getCTP().addNewPPr();
        }
        CTSpacing spacing = ppr.isSetSpacing() ? ppr.getSpacing() : ppr.addNewSpacing();
        spacing.setAfter(BigInteger.valueOf(0));
        spacing.setAfterLines(BigInteger.valueOf(0));
        spacing.setBefore(BigInteger.valueOf(0));
        spacing.setBeforeLines(BigInteger.valueOf(0));
        spacing.setLineRule(STLineSpacingRule.AUTO);
        //spacing.setLine(BigInteger.valueOf(240));
    }

    public static XWPFTableCell poiWordCreaCell(XWPFTable table, XWPFTableRow row, int cell, Integer size) {
        XWPFTableCell tableCell = row.getCell(cell);
        tableCell.removeParagraph(0);
        if (size != null && size > 0) {
            tableCell.getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf(size));
        }
        return tableCell;
    }

    public static XWPFRun poiWordCreaRun(
            XWPFParagraph parrafo, String fontFamily, String fontColor,
            int fontSize, boolean negritas, UnderlinePatterns subrayado, String texto) {
        XWPFRun parrafoRun = parrafo.createRun();
        parrafoRun.setColor(fontColor);
        parrafoRun.setBold(negritas);
        parrafoRun.setFontFamily(fontFamily);
        parrafoRun.setFontSize(fontSize);
        parrafoRun.setUnderline(subrayado);
        parrafoRun.setText(texto);
        return parrafoRun;
    }

    public static void poiWordUneCeldasVertical(XWPFTable table, int col, int fromRow, int toRow) {
        for (int rowIndex = fromRow; rowIndex <= toRow; rowIndex++) {
            CTVMerge vmerge = CTVMerge.Factory.newInstance();
            if (rowIndex == fromRow) {
                // The first merged cell is set with RESTART merge value
                vmerge.setVal(STMerge.RESTART);
            } else {
                // Cells which join (merge) the first one, are set with CONTINUE
                vmerge.setVal(STMerge.CONTINUE);
            }
            XWPFTableCell cell = table.getRow(rowIndex).getCell(col);
            // Try getting the TcPr. Not simply setting an new one every time.
            CTTcPr tcPr = cell.getCTTc().getTcPr();
            if (tcPr != null) {
                tcPr.setVMerge(vmerge);
            } else {
                // only set an new TcPr if there is not one already
                tcPr = CTTcPr.Factory.newInstance();
                tcPr.setVMerge(vmerge);
                cell.getCTTc().setTcPr(tcPr);
            }
        }
    }

    public static void poiWordUneCeldasHorizontal(XWPFTable table, int row, int fromCol, int toCol) {
        for (int colIndex = fromCol; colIndex <= toCol; colIndex++) {
            CTHMerge hmerge = CTHMerge.Factory.newInstance();
            if (colIndex == fromCol) {
                // The first merged cell is set with RESTART merge value
                hmerge.setVal(STMerge.RESTART);
            } else {
                // Cells which join (merge) the first one, are set with CONTINUE
                hmerge.setVal(STMerge.CONTINUE);
            }
            XWPFTableCell cell = table.getRow(row).getCell(colIndex);
            // Try getting the TcPr. Not simply setting an new one every time.
            CTTcPr tcPr = cell.getCTTc().getTcPr();
            if (tcPr != null) {
                tcPr.setHMerge(hmerge);
            } else {
                // only set an new TcPr if there is not one already
                tcPr = CTTcPr.Factory.newInstance();
                tcPr.setHMerge(hmerge);
                cell.getCTTc().setTcPr(tcPr);
            }
        }
    }

    // Recorrer toda la tabla y aplica formato a las celdas vacias
    public static void poiWordFormateaTabla(XWPFTable table, String fontFamily, String fontColor, int fontSize, boolean negritas) {
        for (int i = 0; i < table.getNumberOfRows(); i++) {
            XWPFTableRow row = table.getRow(i);
            int numberOfCell = row.getTableCells().size();
            for (int y = 0; y < numberOfCell; y++) {
                XWPFTableCell cell = row.getCell(y);
                cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
                if (cell.getText() != null && cell.getText().trim().length() > 0) {
                    XWPFParagraph parrafo = cell.getParagraphArray(0);
                    UtilPOI.poiWordQuitaEspaciosParrafo(parrafo);
                }
                if (cell.getText() == null || cell.getText().trim().length() == 0) {
                    cell.removeParagraph(0);
                    XWPFParagraph parrafo = cell.addParagraph();
                    UtilPOI.poiWordQuitaEspaciosParrafo(parrafo);
                    XWPFRun parrafoRun = parrafo.createRun();
                    parrafoRun.setColor(fontColor);
                    parrafoRun.setBold(negritas);
                    parrafoRun.setFontFamily(fontFamily);
                    parrafoRun.setFontSize(fontSize);
                    parrafoRun.setText("");
                }
            }
        }
    }

    public static void poiWordBordesTabla(XWPFTable table, STBorder.Enum borderType, int size, int space, String hexColor) {
        table.getCTTbl().getTblPr().getTblBorders().getBottom().setColor(hexColor);
        table.getCTTbl().getTblPr().getTblBorders().getTop().setColor(hexColor);
        table.getCTTbl().getTblPr().getTblBorders().getLeft().setColor(hexColor);
        table.getCTTbl().getTblPr().getTblBorders().getRight().setColor(hexColor);
        table.getCTTbl().getTblPr().getTblBorders().getInsideH().setColor(hexColor);
        table.getCTTbl().getTblPr().getTblBorders().getInsideV().setColor(hexColor);

        table.getCTTbl().getTblPr().getTblBorders().getRight().setSz(BigInteger.valueOf(size));
        table.getCTTbl().getTblPr().getTblBorders().getTop().setSz(BigInteger.valueOf(size));
        table.getCTTbl().getTblPr().getTblBorders().getLeft().setSz(BigInteger.valueOf(size));
        table.getCTTbl().getTblPr().getTblBorders().getBottom().setSz(BigInteger.valueOf(size));
        table.getCTTbl().getTblPr().getTblBorders().getInsideH().setSz(BigInteger.valueOf(size));
        table.getCTTbl().getTblPr().getTblBorders().getInsideV().setSz(BigInteger.valueOf(size));

        table.getCTTbl().getTblPr().getTblBorders().getBottom().setVal(borderType);
        table.getCTTbl().getTblPr().getTblBorders().getTop().setVal(borderType);
        table.getCTTbl().getTblPr().getTblBorders().getLeft().setVal(borderType);
        table.getCTTbl().getTblPr().getTblBorders().getRight().setVal(borderType);
        table.getCTTbl().getTblPr().getTblBorders().getInsideH().setVal(borderType);
        table.getCTTbl().getTblPr().getTblBorders().getInsideV().setVal(borderType);
    }

    // Anchos de las columnas para OpenOffice
    public static void poiWordAnchoColsOpenOffice(XWPFTable table, Integer... anchos) {
        for (Integer w : anchos) {
            table.getCTTbl().addNewTblGrid().addNewGridCol().setW(BigInteger.valueOf(w));
        }
        for (int i = 0; i < anchos.length; i++) {
            CTTblWidth tblWidth = table.getRow(0).getCell(i).getCTTc().addNewTcPr().addNewTcW();
            tblWidth.setW(BigInteger.valueOf(anchos[i]));
            tblWidth.setType(STTblWidth.DXA);
        }
    }

    // Alineacion de tabla
    public static void poiWordAlineaTabla(XWPFTable table, STJc.Enum alineacion) {
        CTTbl tablex = table.getCTTbl();
        CTTblPr pr = tablex.getTblPr();
        CTTblWidth tblW = pr.getTblW();
        //tblW.setW(BigInteger.valueOf(5000));
        tblW.setType(STTblWidth.PCT);
        pr.setTblW(tblW);
        tablex.setTblPr(pr);
        CTJc jc = pr.addNewJc();
        jc.setVal(alineacion); // Solo funciona CENTER, RIGHT, LEFT       
        pr.setJc(jc);
    }

    // Referencias:
    // https://stackoverflow.com/questions/27634991/how-to-format-the-text-in-a-xwpftable-in-apache-poi
    // https://stackoverflow.com/questions/27541560/how-to-format-cell-in-xwpftable-in-apache-poi
    // https://stackoverflow.com/questions/27439453/how-to-set-the-global-font-of-word-file-via-apache-poi
    // https://stackoverflow.com/questions/42251975/how-to-increase-width-of-column-of-table-in-word-file-by-java
}
