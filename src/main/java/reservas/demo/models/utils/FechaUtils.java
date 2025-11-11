package reservas.demo.models.utils;

import java.time.LocalDate;

public class FechaUtils {

    public static int anioActual() {
        return LocalDate.now().getYear();
    }

    public static int mesActual() {
        return LocalDate.now().getMonthValue();
    }
}
