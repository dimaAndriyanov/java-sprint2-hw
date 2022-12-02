// Класс отвечает за работу с отчетами
public class ReportManager {

    String[] months = {"Январь", "Февраль", "Март", "Апрель", "Май", "Июнь",
            "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"};

    // Выводит информацио о загруженных месячных отчетах за год year
    public void showMonthlyReportsStatistics(MonthlyReport[] monthlyReports, int year) {
        System.out.println(year + " год");
        for (int i = 0; i < monthlyReports.length; i++) {
            if (monthlyReports[i] != null) {
                System.out.println(months[i]);
                String nameOfBiggestEarning = monthlyReports[i].getBiggestEarningName();
                if (nameOfBiggestEarning != null) {
                    System.out.println("Больше всего заработано на - " + nameOfBiggestEarning +
                            ": доход составил " + monthlyReports[i].getEarning(nameOfBiggestEarning));
                } else {
                    System.out.println("В этом месяце нет доходов");
                }
                String nameOfBiggestSpending = monthlyReports[i].getBiggestSpendingName();
                if (nameOfBiggestSpending != null) {
                    System.out.println("Больше всего потрачено на - " + nameOfBiggestSpending +
                            ": расход составил " + monthlyReports[i].getSpending(nameOfBiggestSpending));
                } else {
                    System.out.println("В этом месяце нет расходов");
                }
            }
        }
    }

    // Выводит информацию о загруженном годовом отчете за год year
    public void showYearlyReportStatistics(YearlyReport yearlyReport, int year) {
        System.out.println(year + " год");
        int amountOfMonthlyReports = yearlyReport.getAmountOfMonthlyReports();
        if (amountOfMonthlyReports > 0) {
            System.out.println("Прибыль по каждому месяцу:");
            yearlyReport.showProfitsByMonths(months);
            System.out.println("Средний расход за все месяцы в году - " +
                    yearlyReport.getAverageSpending());
            System.out.println("Средний доход за все месяцы в году - " +
                    yearlyReport.getAverageEarning());
        } else {
            System.out.println("Годовой отчет составлен некорректно");
        }
    }

    // Сверяет загруженные месячные и годовой отчеты, основываясь на балансах
    public void checkReports(MonthlyReport[] monthlyReports, YearlyReport yearlyReport) {
        boolean flag = true;
        for (int i = 0; i < monthlyReports.length; i++) {
            if (monthlyReports[i] != null) {
                if (!yearlyReport.isEmptyMonthReport(i)) {
                    if (monthlyReports[i].getSpendingsSum() != yearlyReport.getSpendingByMonth(i)) {
                        System.out.println("Есть расхождения в доходах за " + months[i]);
                        flag = false;
                    }
                    if (monthlyReports[i].getEarningsSum() != yearlyReport.getEarningByMonth(i)) {
                        System.out.println("Есть расхождения в расходах за " + months[i]);
                        flag = false;
                    }
                } else {
                    System.out.println("В годовом отчете отсутствует информация за " + months[i]);
                    flag = false;
                }
            } else if (!yearlyReport.isEmptyMonthReport(i)) {
                System.out.println("Отсутствует месячный отчет за " + months[i]);
                flag = false;
            }
        }
        if (flag) {
            System.out.println("Отчеты успешно сверены. Расхождений не обнаружено");
        }
    }
}
