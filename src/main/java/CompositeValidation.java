import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.Validation;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.lang.annotation.*;

public class CompositeValidation {
    public static void main(String[] args) {
        System.out.println(CompositeValidation.class.getSimpleName());

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

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @Documented
    @Constraint(validatedBy = {})
    @ReportAsSingleViolation
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
    @ReportAsSingleViolation
    @Min(value = 0, message = "{value}以上にしてください!!")
    @Max(value = 59, message = "{value}以下にしてください!!")
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

        @Hour(message = "不正な時間の入力です")
        private int hour;

        @Minute(message = "不正な分の入力です")
        private int minute;
    }
}
