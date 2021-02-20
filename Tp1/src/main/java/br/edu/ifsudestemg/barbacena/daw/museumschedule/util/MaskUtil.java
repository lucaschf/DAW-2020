package br.edu.ifsudestemg.barbacena.daw.museumschedule.util;


@SuppressWarnings("unused")
public abstract class MaskUtil {

    public static final String CPF = "###.###.###-##";
    public static final String CNPJ = "##.###.###/####-##";
    public static final String CELL_PHONE = "## #####-####";
    public static final String ZIP_CODE = "#####-###";
    public static final String DATE = "##/##/####";
    public static final String CARD = "#### #### #### ####";
    public static final String CARD_EXPIRATION = "##/##";
    private static final char[] signs = new char[]{'.', '-', '/', '(', ')', ',', ' '};

    @SuppressWarnings("WeakerAccess")
    public static String unmask(final String s) {
        if (s == null || s.isEmpty())
            return s;

        String unMasked = s;

        for (char c : signs) {
            unMasked = unMasked.replaceAll("[" + c + "]", "");
        }

        return unMasked;
    }

    private static boolean isASign(char c) {
        for (char character : signs) {
            if (c == character)
                return true;
        }

        return false;
    }
}