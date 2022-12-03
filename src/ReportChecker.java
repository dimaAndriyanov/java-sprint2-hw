// Класс для сверки месячных и годового отчетов
public class ReportChecker {

    // Производит сверку месячных отчетов monthlyReports и годового отчета yearlyReport
    // months содержит названия всех месяцев
    public void checkReports(MonthlyReport[] monthlyReports, YearlyReport yearlyReport, String[] months) {
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