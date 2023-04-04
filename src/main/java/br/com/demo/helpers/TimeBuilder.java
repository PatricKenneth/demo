package br.com.demo.helpers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class TimeBuilder {
  public final static ZoneId DEFAULT_ZONE_ID = ZoneId.of("America/Sao_Paulo");

  public static LocalDateTime getDateTime() {
    return LocalDateTime.now(DEFAULT_ZONE_ID);
  }

  public static LocalDate getDate() {
    return LocalDate.now(DEFAULT_ZONE_ID);
  }

}
