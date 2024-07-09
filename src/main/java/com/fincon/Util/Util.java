package com.fincon.Util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;

public class Util {
    public static Date dataAtual() {
        return Date.from(LocalDateTime.now().toInstant(ZoneOffset.of("-03:00")));
    }
    
    public static Date LocalDateTimeForDate(LocalDateTime localDateTime) {        
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
