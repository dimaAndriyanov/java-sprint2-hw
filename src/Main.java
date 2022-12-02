import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.HashMap;

// Основной класс приложения. Отвечает за взаимодействие с пользователем
public class Main {

    public static void main(String[] args) {

        int userInputForMenu;
        int userInput;
        int year;
        String message;
        Scanner scanner = new Scanner(System.in);
        ReportManager reportManager = new ReportManager();
        HashMap<Integer, MonthlyReport[]> monthlyReportsByYear = new HashMap<>();
        HashMap<Integer, YearlyReport> yearlyReportsByYear = new HashMap<>();

        do {
            printMenu();
            message = "Выберите номер команды";
            userInputForMenu = getUserInputInt(scanner, message);
            if (userInputForMenu == 1) {
                message = "Введите год, в котором вы хотите загрузить месячные отчеты";
                year = readNumberInRange(scanner, message, 2007, 2022);
                if (monthlyReportsByYear.containsKey(year)) {
                    message = "За этот год отчеты уже загружены. Хотите загрузить отчеты заново?\n" +
                            "1 - да, 0 - нет";
                    userInput = readNumberInRange(scanner, message, 0, 1);
                    if (userInput == 1) {
                        readMonthlyReportsByYear(scanner, monthlyReportsByYear, year);
                    }
                } else {
                    readMonthlyReportsByYear(scanner, monthlyReportsByYear, year);
                }
            } else if (userInputForMenu == 2) {
                message = "Введите год, за который вы хотите загрузить отчет";
                year = readNumberInRange(scanner, message, 2007, 2022);
                if (yearlyReportsByYear.containsKey(year)) {
                    message = "За этот год отчет уже загружен. Хотите загрузить отчет заново?\n" +
                            "1 - да, 0 - нет";
                    userInput = readNumberInRange(scanner, message, 0, 1);
                    if (userInput == 1) {
                        readYearlyReportByYear(yearlyReportsByYear, year);
                    }
                } else {
                    readYearlyReportByYear(yearlyReportsByYear, year);
                }
            } else if (userInputForMenu == 3) {
                message = "Введите год, за который вы хотитет сверить загруженные отчеты";
                year = readNumberInRange(scanner, message, 2007, 2022);
                if (monthlyReportsByYear.containsKey(year)) {
                    if (yearlyReportsByYear.containsKey(year)) {
                        reportManager.checkReports(monthlyReportsByYear.get(year),
                                yearlyReportsByYear.get(year));
                    } else {
                        System.out.println("За указанный год не загружен годовой отчет");
                    }
                } else if (yearlyReportsByYear.containsKey(year)){
                    System.out.println("За указанный год не загружено ни одного месячного отчета");
                } else {
                    System.out.println("За указанный год не загружено ни одного отчета");
                }
            } else if (userInputForMenu == 4) {
                message = "Введите год, за который вы хотитет полчить информацию о всех месячных отчетах";
                year = readNumberInRange(scanner, message, 2007, 2022);
                if (monthlyReportsByYear.containsKey(year)) {
                    reportManager.showMonthlyReportsStatistics(monthlyReportsByYear.get(year), year);
                } else {
                    System.out.println("За указанный год не загружено ни одного месячного отчета");
                }
            } else if (userInputForMenu == 5) {
                message = "Введите год, за который вы хотите получить информацию о годовом отчете";
                year = readNumberInRange(scanner, message, 2007, 2022);
                if (yearlyReportsByYear.containsKey(year)) {
                    reportManager.showYearlyReportStatistics(yearlyReportsByYear.get(year), year);
                } else {
                    System.out.println("За указанный год годовой отчет не загружен");
                }
            } else if (userInputForMenu != 0) System.out.println("Неверный ввод: такого пункта в меню нет. Попробуйте снова");
        } while (userInputForMenu !=0);
    }

    // Выводит на экран меню
    public static void printMenu() {
        System.out.println("МЕНЮ");
        System.out.println("1. Считать месячные отчеты");
        System.out.println("2. Считать годовой отчет");
        System.out.println("3. Сверить отчеты");
        System.out.println("4. Вывести информацию о всех загруженных месячных отчетах");
        System.out.println("5. Вывести информацию о загруженном годовом отчете");
        System.out.println("0. Выход");
    }

    // Считывает целое число, введенное пользователем в консоли
    public static int getUserInputInt(Scanner scanner, String message) {
        System.out.print(message + " ");
        return scanner.nextInt();
    }

    // Считывает целое число, введенное пользователем в консоли, в интервале от leftBorder до rightBorder включительно
    public static int readNumberInRange(Scanner scanner, String message, int leftBorder, int rightBorder) {
        int input;
        do {
            input = getUserInputInt(scanner, message);
            if (input < leftBorder) {
                System.out.println("Неверный ввод: введенное число не должно быть меньше " + leftBorder +
                        ". Попробуйте снова");
            }
            if (input > rightBorder) {
                System.out.println("Неверный ввод: введенное число не должно превышать " + rightBorder +
                        ". Попробуйте снова");
            }
        } while ((input < leftBorder) || input > rightBorder);
        return input;
    }

    // Считывает содержимое файла построчно в список, состоящий из строк
    // path - путь к файлу
    public static List<String> readFileContents(String path) {
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
    public static MonthlyReport fillMonthlyReportFromFile (String path) {
        List<String> monthlyReportFileContent = readFileContents(path);
        if (monthlyReportFileContent.isEmpty()) {
            return null;
        } else {
            monthlyReportFileContent.remove("item_name,is_expense,quantity,sum_of_one");
            return new MonthlyReport(monthlyReportFileContent);
        }
    }

    // Последовательно считывает данные из файлов, содержащих месячные отчеты за год year в интервале,
    // выбираемом пользователем. Добавляет данные в объекты приложения
    public static void readMonthlyReportsByYear(Scanner scanner, HashMap<Integer, MonthlyReport[]> monthlyReportsByYear,
                                                int year) {
        String message = "Введите номер первого месяца, за который вы хотите зарузить отчет\n" +
                "1 - Январь, 2 - Февраль,.. 12 - Декабрь";
        int firstMonthNumber = readNumberInRange(scanner, message, 1, 12);
        message = "Введите номер последнего месяца, за который вы хотите зарузить отчет\n" +
                "1 - Январь, 2 - Февраль,.. 12 - Декабрь";
        int lastMonthNumber = readNumberInRange(scanner, message, firstMonthNumber, 12);
        MonthlyReport[] monthlyReports = new MonthlyReport[12];
        boolean flag = true;
        String path;
        for (int i = firstMonthNumber; (i <= lastMonthNumber) && (i <= 9); i++) {
            path = "resources/m." + year + "0" + i + ".csv";
            monthlyReports[i - 1] = fillMonthlyReportFromFile(path);
            if (monthlyReports[i - 1] == null) {
                flag = false;
            }
        }
        for (int i = Integer.max(firstMonthNumber, 10); i <= lastMonthNumber; i++) {
            path = ("resources/m." + year) + i + ".csv";
            monthlyReports[i - 1] = fillMonthlyReportFromFile(path);
            if (monthlyReports[i - 1] == null) {
                flag = false;
            }
        }
        if (flag) {
            monthlyReportsByYear.put(year, monthlyReports);
            System.out.println("Отчеты загружены");
        } else {
            System.out.println("Отчеты не загружены");
        }
    }

    // Считывает данные из файла, содержащего годовой отчет за год year. Добавляет данные в объекты приложения
    public static void readYearlyReportByYear(HashMap<Integer, YearlyReport> yearlyReportsByYear, int year) {
        String path = "resources/y." + year + ".csv";
        List<String> yearlyReportFileContent = readFileContents(path);
        if (!yearlyReportFileContent.isEmpty()) {
            yearlyReportFileContent.remove("month,amount,is_expense");
            YearlyReport yearlyReport = new YearlyReport();
            yearlyReport.addData(yearlyReportFileContent);
            yearlyReportsByYear.put(year, yearlyReport);
            System.out.println("Отчет загружен");
        } else {
            System.out.println("Отчет не загружен");
        }
    }
}

