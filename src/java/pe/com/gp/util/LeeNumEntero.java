package pe.com.gp.util;

public final class LeeNumEntero {

    // NO USAR ESTA CLASE, SERA ELIMINADA
    // NO USAR ESTA CLASE, SERA ELIMINADA
    // NO USAR ESTA CLASE, SERA ELIMINADA
    // NO USAR ESTA CLASE, SERA ELIMINADA
    // NO USAR ESTA CLASE, SERA ELIMINADA
    // NO USAR ESTA CLASE, SERA ELIMINADA
    // NO USAR ESTA CLASE, SERA ELIMINADA
    // NO USAR ESTA CLASE, SERA ELIMINADA
    // NO USAR ESTA CLASE, SERA ELIMINADA
    // NO USAR ESTA CLASE, SERA ELIMINADA
    // NO USAR ESTA CLASE, SERA ELIMINADA
    // NO USAR ESTA CLASE, SERA ELIMINADA
    // NO USAR ESTA CLASE, SERA ELIMINADA
    // NO USAR ESTA CLASE, SERA ELIMINADA
    // NO USAR ESTA CLASE, SERA ELIMINADA
    // NO USAR ESTA CLASE, SERA ELIMINADA
    // NO USAR ESTA CLASE, SERA ELIMINADA
    // NO USAR ESTA CLASE, SERA ELIMINADA
    // NO USAR ESTA CLASE, SERA ELIMINADA
    // NO USAR ESTA CLASE, SERA ELIMINADA
    // NO USAR ESTA CLASE, SERA ELIMINADA
    // NO USAR ESTA CLASE, SERA ELIMINADA
    // NO USAR ESTA CLASE, SERA ELIMINADA
    // NO USAR ESTA CLASE, SERA ELIMINADA
    // NO USAR ESTA CLASE, SERA ELIMINADA
    // NO USAR ESTA CLASE, SERA ELIMINADA
    // NO USAR ESTA CLASE, SERA ELIMINADA
    // NO USAR ESTA CLASE, SERA ELIMINADA
    // NO USAR ESTA CLASE, SERA ELIMINADA
    // NO USAR ESTA CLASE, SERA ELIMINADA
    // NO USAR ESTA CLASE, SERA ELIMINADA
    // NO USAR ESTA CLASE, SERA ELIMINADA
    // NO USAR ESTA CLASE, SERA ELIMINADA
    // NO USAR ESTA CLASE, SERA ELIMINADA
    // NO USAR ESTA CLASE, SERA ELIMINADA
    // NO USAR ESTA CLASE, SERA ELIMINADA
    // NO USAR ESTA CLASE, SERA ELIMINADA
    // NO USAR ESTA CLASE, SERA ELIMINADA
    // NO USAR ESTA CLASE, SERA ELIMINADA
    // NO USAR ESTA CLASE, SERA ELIMINADA
    // NO USAR ESTA CLASE, SERA ELIMINADA
    // NO USAR ESTA CLASE, SERA ELIMINADA
    // NO USAR ESTA CLASE, SERA ELIMINADA
    // NO USAR ESTA CLASE, SERA ELIMINADA
    // NO USAR ESTA CLASE, SERA ELIMINADA
    // NO USAR ESTA CLASE, SERA ELIMINADA
    // NO USAR ESTA CLASE, SERA ELIMINADA
    // NO USAR ESTA CLASE, SERA ELIMINADA
    // NO USAR ESTA CLASE, SERA ELIMINADA
    // NO USAR ESTA CLASE, SERA ELIMINADA
    // NO USAR ESTA CLASE, SERA ELIMINADA
    // NO USAR ESTA CLASE, SERA ELIMINADA
    // NO USAR ESTA CLASE, SERA ELIMINADA
    // NO USAR ESTA CLASE, SERA ELIMINADA
    // NO USAR ESTA CLASE, SERA ELIMINADA
    // NO USAR ESTA CLASE, SERA ELIMINADA
    // NO USAR ESTA CLASE, SERA ELIMINADA
    // NO USAR ESTA CLASE, SERA ELIMINADA
    // NO USAR ESTA CLASE, SERA ELIMINADA
    // NO USAR ESTA CLASE, SERA ELIMINADA
    // NO USAR ESTA CLASE, SERA ELIMINADA
    // NO USAR ESTA CLASE, SERA ELIMINADA
    // NO USAR ESTA CLASE, SERA ELIMINADA
    // NO USAR ESTA CLASE, SERA ELIMINADA
    // NO USAR ESTA CLASE, SERA ELIMINADA
    // NO USAR ESTA CLASE, SERA ELIMINADA
    // NO USAR ESTA CLASE, SERA ELIMINADA
    // NO USAR ESTA CLASE, SERA ELIMINADA
    // NO USAR ESTA CLASE, SERA ELIMINADA
    // NO USAR ESTA CLASE, SERA ELIMINADA
    private static final String[] UNIDADES = {"", "UN ", "DOS ", "TRES ",
        "CUATRO ", "CINCO ", "SEIS ", "SIETE ", "OCHO ", "NUEVE ", "DIEZ ",
        "ONCE ", "DOCE ", "TRECE ", "CATORCE ", "QUINCE ", "DIECISEIS",
        "DIECISIETE", "DIECIOCHO", "DIECINUEVE", "VEINTE"};

    private static final String[] DECENAS = {"VENTI", "TREINTA ", "CUARENTA ",
        "CINCUENTA ", "SESENTA ", "SETENTA ", "OCHENTA ", "NOVENTA ",
        "CIEN "};

    private static final String[] CENTENAS = {"CIENTO ", "DOSCIENTOS ",
        "TRESCIENTOS ", "CUATROCIENTOS ", "QUINIENTOS ", "SEISCIENTOS ",
        "SETECIENTOS ", "OCHOCIENTOS ", "NOVECIENTOS "};

    public static String lee(String number)
            throws NumberFormatException {
        return lee(Double.parseDouble(number));
    }

    public static String lee(double doubleNumber)
            throws NumberFormatException {

        StringBuilder converted = new StringBuilder();

        // Validamos que sea un numero legal
        if (doubleNumber > 999999999) {
            throw new NumberFormatException(
                    "El numero es mayor de 999'999.999, "
                    + "no es posible convertirlo");
        }

        if (doubleNumber < 0) {
            throw new NumberFormatException("El numero debe ser positivo");
        }

        String splitNumber[] = String.valueOf(doubleNumber).replace('.', '#')
                .split("#");

        // Descompone el trio de millones
        int millon = Integer.parseInt(String.valueOf(getDigitAt(splitNumber[0],
                8))
                + String.valueOf(getDigitAt(splitNumber[0], 7))
                + String.valueOf(getDigitAt(splitNumber[0], 6)));
        if (millon == 1) {
            converted.append("UN MILLON ");
        } else if (millon > 1) {
            converted.append("").append(convertNumber(String.valueOf(millon))).append("MILLONES ");
        }

        // Descompone el trio de miles
        int miles = Integer.parseInt(String.valueOf(getDigitAt(splitNumber[0],
                5))
                + String.valueOf(getDigitAt(splitNumber[0], 4))
                + String.valueOf(getDigitAt(splitNumber[0], 3)));
        if (millon >= 1) {
            if (miles == 1) {
                converted.append(convertNumber(String.valueOf(miles))).append("MIL ");
            } else if (miles > 1) {
                converted.append(convertNumber(String.valueOf(miles))).append("MIL ");
            }
        } else {
            if (miles == 1) {
                converted.append("UN MIL ");
            }
            if (miles > 1) {
                converted.append("").append(convertNumber(String.valueOf(miles))).append("MIL ");
            }
        }

        // Descompone el ultimo trio de unidades
        int cientos = Integer.parseInt(String.valueOf(getDigitAt(
                splitNumber[0], 2))
                + String.valueOf(getDigitAt(splitNumber[0], 1))
                + String.valueOf(getDigitAt(splitNumber[0], 0)));
        if (miles >= 1 || millon >= 1) {
            if (cientos >= 1) {
                converted.append(convertNumber(String.valueOf(cientos)));
            }
        } else {
            if (cientos == 1) {
                converted.append("UN ");
            }
            if (cientos > 1) {
                converted.append("").append(convertNumber(String.valueOf(cientos)));
            }
        }

        if (millon + miles + cientos == 0) {
            converted.append("CERO ");
        }

        return converted.toString();
    }

    private static String convertNumber(String number) {

        if (number.length() > 3) {
            throw new NumberFormatException(
                    "La longitud maxima debe ser 3 digitos");
        }

        // Caso especial con el 100
        if (number.equals("100")) {
            return "CIEN ";
        }

        StringBuilder output = new StringBuilder();
        if (getDigitAt(number, 2) != 0) {
            output.append(CENTENAS[getDigitAt(number, 2) - 1]);
        }

        int k = Integer.parseInt(String.valueOf(getDigitAt(number, 1))
                + String.valueOf(getDigitAt(number, 0)));

        if (k <= 20) {
            output.append(UNIDADES[k]);
        } else if (k > 30 && getDigitAt(number, 0) != 0) {
            output.append(DECENAS[getDigitAt(number, 1) - 2]).append("Y ").append(UNIDADES[getDigitAt(number, 0)]);
        } else {
            output.append(DECENAS[getDigitAt(number, 1) - 2]).append(UNIDADES[getDigitAt(number, 0)]);
        }

        return output.toString();
    }

    private static int getDigitAt(String origin, int position) {
        if (origin.length() > position && position >= 0) {
            return origin.charAt(origin.length() - position - 1) - 48;
        }
        return 0;
    }
}
