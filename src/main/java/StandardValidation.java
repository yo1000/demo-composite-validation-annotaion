import javax.validation.Validation;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class StandardValidation {
    public static void main(String[] args) {
        System.out.println(StandardValidation.class.getSimpleName());

        Time t1 = new Time(15, 30);
        Validation.buildDefaultValidatorFactory().getValidator().validate(t1).forEach(vio -> {
            System.out.println(vio.getMessage());
        });

        Time t2 = new Time(26, -1);
        Validation.buildDefaultValidatorFactory().getValidator().validate(t2).forEach(vio -> {
            System.out.println(vio.getPropertyPath());
            System.out.println(vio.getMessage());
        });
    }

    static class Time {
        public Time(int hour, int minute) {
            this.hour = hour;
            this.minute = minute;
        }

        @Min(0)
        @Max(23)
        private int hour;

        @Min(0)
        @Max(59)
        private int minute;
    }
}
