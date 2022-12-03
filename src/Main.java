import java.util.Scanner;

// Отвечает за взаимодействие с пользователем
public class Main {

    public static void main(String[] args) {

        int userInputForMenu;
        int userInput;
        int year;
        String message;
        Scanner scanner = new Scanner(System.in);
        ReportManager reportManager = new ReportManager();

        do {
            printMenu();
            message = "Выберите номер команды";
            userInputForMenu = getUserInputInt(scanner, message);
            if (userInputForMenu == 1) {
                message = "Введите год, в котором вы хотите загрузить месячные отчеты";
                year = readNumberInRange(scanner, message, 2007, 2022);
                if (reportManager.containsMonthlyReportsByYear(year)) {
                    message = "За этот год отчеты уже загружены. Хотите загрузить отчеты заново?\n" +
                            "1 - да, 0 - нет";
                    userInput = readNumberInRange(scanner, message, 0, 1);
                    if (userInput == 1) {
                        readMonthlyReportsByYear(scanner, reportManager, year);
                    }
                } else {
                    readMonthlyReportsByYear(scanner, reportManager, year);
                }
            } else if (userInputForMenu == 2) {
                message = "Введите год, за который вы хотите загрузить отчет";
                year = readNumberInRange(scanner, message, 2007, 2022);
                if (reportManager.containsYearlyReportByYear(year)) {
                    message = "За этот год отчет уже загружен. Хотите загрузить отчет заново?\n" +
                            "1 - да, 0 - нет";
                    userInput = readNumberInRange(scanner, message, 0, 1);
                    if (userInput == 1) {
                        reportManager.readYearlyReportByYear(year);
                    }
                } else {
                    reportManager.readYearlyReportByYear(year);
                }
            } else if (userInputForMenu == 3) {
                message = "Введите год, за который вы хотитет сверить загруженные отчеты";
                year = readNumberInRange(scanner, message, 2007, 2022);
                reportManager.checkReportsByYear(year);
            } else if (userInputForMenu == 4) {
                message = "Введите год, за который вы хотитет полчить информацию о всех месячных отчетах";
                year = readNumberInRange(scanner, message, 2007, 2022);
                reportManager.showMonthlyReportsStatisticsByYear(year);
            } else if (userInputForMenu == 5) {
                message = "Введите год, за который вы хотите получить информацию о годовом отчете";
                year = readNumberInRange(scanner, message, 2007, 2022);
                reportManager.showYearlyReportStatisticsByYear(year);
            } else if (userInputForMenu != 0)
                System.out.println("Неверный ввод: такого пункта в меню нет. Попробуйте снова");
        } while (userInputForMenu != 0);
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

    // Считывает введенные пользователем границы интервала месячных отчетов и вызывает метод объекта
    // ReportManager для считывания файлов из этого интервала
    public static void readMonthlyReportsByYear(Scanner scanner, ReportManager reportManager,
                                                int year) {
        String message = "Введите номер первого месяца, за который вы хотите зарузить отчет\n" +
                "1 - Январь, 2 - Февраль,.. 12 - Декабрь";
        int firstMonthNumber = readNumberInRange(scanner, message, 1, 12);
        message = "Введите номер последнего месяца, за который вы хотите зарузить отчет\n" +
                "1 - Январь, 2 - Февраль,.. 12 - Декабрь";
        int lastMonthNumber = readNumberInRange(scanner, message, firstMonthNumber, 12);
        reportManager.readMonthlyReportsByYear(year, firstMonthNumber, lastMonthNumber);
    }
}