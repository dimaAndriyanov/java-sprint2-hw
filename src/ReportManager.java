import java.util.HashMap;


// Основной класс приложения. Отвечает за работу с отчетами
public class ReportManager {

    String[] months = {"Январь", "Февраль", "Март", "Апрель", "Май", "Июнь",
            "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"};
    HashMap<Integer, MonthlyReport[]> monthlyReportsByYear = new HashMap<>();
    HashMap<Integer, YearlyReport> yearlyReportsByYear = new HashMap<>();
    FileLoader fileLoader = new FileLoader();
    ReportChecker reportChecker = new ReportChecker();

    // Возвращает true, если месячные отчеты в году year были считаны, false - в противном случае
    public boolean containsMonthlyReportsByYear(int year) {
        return this.monthlyReportsByYear.containsKey(year);
    }

    // Последовательно считывает данные из файлов, содержащих месячные отчеты за год year
    // в интервале firstMonthNumber, lastMonthNumber включительно. Добавляет данные в объекты приложения
    public void readMonthlyReportsByYear(int year, int firstMonthNumber, int lastMonthNumber) {
        MonthlyReport[] monthlyReports = new MonthlyReport[12];
        boolean flag = true;
        String path;
        for (int i = firstMonthNumber; (i <= lastMonthNumber) && (i <= 9); i++) {
            path = "resources/m." + year + "0" + i + ".csv";
            monthlyReports[i - 1] = this.fileLoader.fillMonthlyReportFromFile(path);
            if (monthlyReports[i - 1] == null) {
                flag = false;
            }
        }
        for (int i = Integer.max(firstMonthNumber, 10); i <= lastMonthNumber; i++) {
            path = ("resources/m." + year) + i + ".csv";
            monthlyReports[i - 1] = this.fileLoader.fillMonthlyReportFromFile(path);
            if (monthlyReports[i - 1] == null) {
                flag = false;
            }
        }
        if (flag) {
            this.monthlyReportsByYear.put(year, monthlyReports);
            System.out.println("Отчеты загружены");
        } else {
            System.out.println("Отчеты не загружены");
        }
    }

    // Возвращает true, если годовой отчет в году year был считан, false - в противном случае
    public boolean containsYearlyReportByYear(int year) {
        return this.yearlyReportsByYear.containsKey(year);
    }

    // Считывает данные из файла, содержащего годовой отчет за год year. Добавляет данные в объекты приложения
    public void readYearlyReportByYear(int year) {
        String path = "resources/y." + year + ".csv";
        YearlyReport yearlyReport = this.fileLoader.fillYearlyReportFromFile(path);
        if (yearlyReport != null) {
            this.yearlyReportsByYear.put(year, yearlyReport);
        } else {
            System.out.println("Отчет не загружен");
        }
    }

    // Выводит информацио о загруженных месячных отчетах за год year
    public void showMonthlyReportsStatisticsByYear(int year) {
        if (this.containsMonthlyReportsByYear(year)) {
            MonthlyReport[] monthlyReports = this.monthlyReportsByYear.get(year);
            System.out.println(year + " год");
            for (int i = 0; i < monthlyReports.length; i++) {
                if (monthlyReports[i] != null) {
                    System.out.println(this.months[i]);
                    MonthRecord monthRecord = monthlyReports[i].getBiggestEarningMonthRecord();
                    if (monthRecord != null) {
                        System.out.println("Больше всего заработано на - " + monthRecord.getName() +
                                ": доход составил " + monthRecord.getPrice());
                    } else {
                        System.out.println("В этом месяце нет доходов");
                    }
                    monthRecord = monthlyReports[i].getBiggestSpendingMonthRecord();
                    if (monthRecord != null) {
                        System.out.println("Больше всего потрачено на - " + monthRecord.getName() +
                                ": расход составил " + monthRecord.getPrice());
                    } else {
                        System.out.println("В этом месяце нет расходов");
                    }
                }
            }
        } else {
            System.out.println("За указанный год не загружено ни одного месячного отчета");
        }
    }

    // Выводит информацию о загруженном годовом отчете за год year
    public void showYearlyReportStatisticsByYear(int year) {
        if (this.containsYearlyReportByYear(year)) {
            YearlyReport yearlyReport = this.yearlyReportsByYear.get(year);
            System.out.println(year + " год");
            int amountOfMonthlyReports = yearlyReport.getAmountOfMonthlyReports();
            if (amountOfMonthlyReports > 0) {
                System.out.println("Прибыль по каждому месяцу:");
                yearlyReport.showProfitsByMonths(this.months);
                System.out.println("Средний расход за все месяцы в году - " +
                        yearlyReport.getAverageSpending());
                System.out.println("Средний доход за все месяцы в году - " +
                        yearlyReport.getAverageEarning());
            } else {
                System.out.println("Годовой отчет составлен некорректно");
            }
        } else {
            System.out.println("За указанный год годовой отчет не загружен");
        }
    }

    // Сверяет загруженные месячные и годовой отчеты, основываясь на балансах
    public void checkReportsByYear(int year) {
        if (this.containsMonthlyReportsByYear(year)) {
            if (this.containsYearlyReportByYear(year)) {
                this.reportChecker.checkReports(this.monthlyReportsByYear.get(year),
                        this.yearlyReportsByYear.get(year), this.months);
            } else {
                System.out.println("За указанный год не загружен годовой отчет");
            }
        } else if (this.yearlyReportsByYear.containsKey(year)){
            System.out.println("За указанный год не загружено ни одного месячного отчета");
        } else {
            System.out.println("За указанный год не загружено ни одного отчета");
        }
    }
}