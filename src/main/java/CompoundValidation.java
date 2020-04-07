import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.Validation;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.lang.annotation.*;

public class CompoundValidation {
    public static void main(String[] args) {
        System.out.println(CompoundValidation.class.getSimpleName());

        Time t = new Time(15, 30);
        Validation.buildDefaultValidatorFactory().getValidator().validate(t).forEach(vio -> {
            System.out.println(vio.getMessage());
        });

        Time tb2 = new Time(26, 30);
        Validation.buildDefaultValidatorFactory().getValidator().validate(tb2).forEach(vio -> {
            System.out.println(vio.getPropertyPath());
            System.out.println(vio.getMessage());
        });
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @Documented
    @Constraint(validatedBy = {})
    @Min(0)
    @Max(23)
    @interface Hour {
        String message() default "";
        Class<?>[] groups() default {};
        Class<? extends Payload>[] payload() default {};
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @Documented
    @Constraint(validatedBy = {})
    @Min(0)
    @Max(59)
    @interface Minute {
        String message() default "";
        Class<?>[] groups() default {};
        Class<? extends Payload>[] payload() default {};
    }

    static class Time {
        public Time(int hour, int minute) {
            this.hour = hour;
            this.minute = minute;
        }

        @Hour
        private int hour;

        @Minute
        private int minute;
    }
}
