package hexlet.code;

import picocli.CommandLine;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.Command;

@Command(name = "gendiff", mixinStandardHelpOptions = true, version = "app 1.0",
        description = "Compares two configuration files and shows a difference.")
public class App implements Runnable {
    @Parameters(index = "0", description = "Path to first file")
    private String filepath1;

    @Parameters(index = "1", description = "Path to second file")
    private String filepath2;

    @Option(names = {"-f", "--format"}, arity = "0..1", defaultValue = "stylish",
            description = "Output format [default: stylish]")
    private String format = "stylish";

    /**
     * Метод вызывает метод сравнения файлов generate в классе Differ.
     * Параметры передается в командной строке при вызове приложения
     */
    public void run() {
        var result = Differ.generate(filepath1, filepath2, format);
        System.out.println(result);
    }

    public static void main(String... args) {
        new CommandLine(new App()).execute(args);
    }
}
