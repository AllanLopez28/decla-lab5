package org.example;

import alice.tuprolog.Prolog;
import alice.tuprolog.SolveInfo;
import alice.tuprolog.Theory;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Set;

/** Carga las reglas y envia los dos operandos al motor Prolog. */
public class Connection {
    private final Prolog engine = new Prolog();
    private static final Set<String> OPERACIONES =
            Set.of("suma", "resta", "multiplicacion", "division");

    public Connection() throws Exception {
        try (InputStream in = getClass().getResourceAsStream("/calculo.pl")) {
            if (in == null) throw new IllegalStateException("No se encontró calculo.pl");
            engine.setTheory(new Theory(in));
        }
    }

    public String consultar(String predicado, String primero, String segundo) throws Exception {
        if (!OPERACIONES.contains(predicado)) {
            throw new IllegalArgumentException("Operación no válida.");
        }
        String a = validarNumero(primero);
        String b = validarNumero(segundo);
        if (predicado.equals("division") && new BigDecimal(b).signum() == 0) {
            throw new IllegalArgumentException("No se puede dividir entre cero.");
        }
        SolveInfo info = engine.solve(predicado + "(" + a + ", " + b + ", R).");
        if (!info.isSuccess()) {
            throw new IllegalArgumentException("No se pudo realizar la operación.");
        }
        return info.getTerm("R").toString();
    }

    private static String validarNumero(String texto) {
        String numero = texto == null ? "" : texto.trim();
        if (numero.isEmpty()) {
            throw new IllegalArgumentException("Ingresá los dos números primero.");
        }
        if (!numero.matches("[+-]?(?:[0-9]+(?:[.,][0-9]+)?|[.,][0-9]+)")) {
            throw new IllegalArgumentException("Ingresá números válidos, por ejemplo: -5 o 2.5.");
        }
        BigDecimal valor = new BigDecimal(numero.replace(',', '.'));
        double aproximado = valor.doubleValue();
        if (!Double.isFinite(aproximado) || (aproximado == 0 && valor.signum() != 0)) {
            throw new IllegalArgumentException("El número está fuera del rango permitido.");
        }
        // Normaliza el texto antes de construir la consulta Prolog.
        String normalizado = valor.stripTrailingZeros().toPlainString();
        try {
            Long.parseLong(normalizado);
            return normalizado;
        } catch (NumberFormatException ex) {
            return normalizado.contains(".") ? normalizado : normalizado + ".0";
        }
    }
}
