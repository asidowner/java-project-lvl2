package hexlet.code;

import picocli.CommandLine;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.Command;

import java.util.concurrent.Callable;

@Command(name = "gendiff", mixinStandardHelpOptions = true, version = "app 1.0",
        description = "Compares two configuration files and shows a difference.")
public class App implements Callable<Integer> {
    @Parameters(index = "0", description = "Path to first file")
    private String filepath1;

    @Parameters(index = "1", description = "Path to second file")
    private String filepath2;

    @Option(names = {"-f", "--format"}, arity = "0..1", defaultValue = "stylish",
            description = "Output format [default: stylish]")
    private String format;

    /**
     * Метод вызывает метод сравнения файлов generate в классе Differ.
     * Параметры передается в командной строке при вызове приложения
     */
    @Override
    public Integer call() {
        String result;
        try {
            result = Differ.generate(filepath1, filepath2, format);
        } catch (Exception e) {
            System.out.printf("""
                    Oops, something went wrong. Try again with different params.
                    Problem in: %s
                    """, e.getMessage());
            return 1;
        }
        System.out.println(result);
        return 0;
    }

    public static void main(String... args) {
        new CommandLine(new App()).execute(args);
    }
}
