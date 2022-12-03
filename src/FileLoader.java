import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

//Класс для считывания данных из файлов
public class FileLoader {

    // Считывает содержимое файла построчно в список, состоящий из строк
    // path - путь к файлу
    public List<String> readFileContents(String path) {
        try {
            return Files.readAllLines(Path.of(path));
        } catch (IOException e) {
            System.out.println("Невозможно прочитать файл с отчётом " + path +
                    "\nВозможно файл не находится в нужной директории.");
            return Collections.emptyList();
        }
    }

    // Создает объект класса MonthlyReport из данных файла
    // path - путь к файлу
    // Возвращает объект класса MonthlyReport
    public MonthlyReport fillMonthlyReportFromFile (String path) {
        List<String> monthlyReportFileContent = this.readFileContents(path);
        if (monthlyReportFileContent.isEmpty()) {
            return null;
        }
        monthlyReportFileContent.remove("item_name,is_expense,quantity,sum_of_one");
        return new MonthlyReport(monthlyReportFileContent);

    }

    // Создает объект класса YearlyReport из данных файла
    // path - путь к файлу
    // Возвращает объект класса YearlyReport
    public YearlyReport fillYearlyReportFromFile (String path) {
        List<String> yearlyReportFileContent = this.readFileContents(path);
        if (yearlyReportFileContent.isEmpty()) {
            return null;
        }
        yearlyReportFileContent.remove("month,amount,is_expense");
        YearlyReport yearlyReport = new YearlyReport();
        yearlyReport.addData(yearlyReportFileContent);
        return yearlyReport;
    }
}