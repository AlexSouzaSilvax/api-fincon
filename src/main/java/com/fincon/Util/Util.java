package com.fincon.Util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.TextStyle;
import java.util.Date;
import java.util.Locale;

public class Util {
    public static Date dataAtual() {
        return Date.from(LocalDateTime.now().toInstant(ZoneOffset.of("-03:00")));
    }

    public static Date LocalDateTimeForDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static int getAnoAtual() {
        LocalDate hoje = LocalDate.now();
        return hoje.getYear();
    }

    public static int getMesAtual() {
        LocalDate hoje = LocalDate.now();
        return hoje.getMonthValue();
    }

    public static String getMesAtualExtenso() {
        LocalDate hoje = LocalDate.now();

        String pMesAtualExtenso = hoje.getMonth()
                .getDisplayName(TextStyle.FULL, new Locale("pt", "BR"));
        pMesAtualExtenso = pMesAtualExtenso.substring(0, 1).toUpperCase() + pMesAtualExtenso.substring(1);

        return pMesAtualExtenso;
    }

}
