package javax0.dospexml.support;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@ArgumentsSource(CommandTestFilesProvider.class)
@ParameterizedTest(name = "{1}")
public @interface TestCommands {
    String value() default "";

    String only() default ".*";
}
