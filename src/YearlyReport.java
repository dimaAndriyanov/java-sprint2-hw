// Класс для хранения данных годовых отчетов
import java.util.List;

public class YearlyReport {

    int[][] yearlyReport = new int[12][2]; // yearlyReport[i] - месяц
                                            // yearlyReport[i][0] - траты за месяц
                                            // yearlyReport[i][1] - доход за месяц

    // Заполняет все траты и доходы значением -1. -1 означает, что данные не загружены
    public YearlyReport() {
        for (int i = 0; i < this.yearlyReport.length; i++) {
            for (int j = 0; j < this.yearlyReport[i].length; j++) {
                this.yearlyReport[i][j] = -1;
            }
        }
    }

    // Заполняет поле yearlyReport данными из списка строк yearlyReportFileContent
    // Каждая строка имеет формат month,amount,isExpense.
    // Например 01,100000,true
    public void addData(List<String> yearlyReportFileContent) {
        for (String line : yearlyReportFileContent) {
            String[] partsOfLine = line.split(",");
            if (Boolean.parseBoolean(partsOfLine[2])) {
                this.yearlyReport[Integer.parseInt(partsOfLine[0]) - 1][0] = Integer.parseInt(partsOfLine[1]);
            } else {
                this.yearlyReport[Integer.parseInt(partsOfLine[0]) - 1][1] = Integer.parseInt(partsOfLine[1]);
            }
        }
    }

    // Возвращает траты за месяц monthNumber, где 0 - Январь, 1 - Февраль,.. 11 - Декабрь
    public int getSpendingByMonth(int monthNumber) {
        return this.yearlyReport[monthNumber][0];
    }

    // Возвращает доход за месяц monthNumber, где 0 - Январь, 1 - Февраль,.. 11 - Декабрь
    public int getEarningByMonth(int monthNumber) {
        return this.yearlyReport[monthNumber][1];
    }

    // Возвращает true, если данные по доходам и расходам за месяц monthNumber были загружены.
    // false -  в противном случае. 0 - Январь, 1 - Февраль,.. 11 - Декабрь
    public boolean isEmptyMonthReport(int monthNumber) {
        return (this.yearlyReport[monthNumber][0] == -1) || (this.yearlyReport[monthNumber][1] == -1);
    }

    // Возвращает сумму всех трат за год
    public int getSpendingsSum() {
        int spendingsSum = 0;
        for (int i = 0; i < this.yearlyReport.length; i++) {
            if (!this.isEmptyMonthReport(i)) {
                spendingsSum += this.getSpendingByMonth(i);
            }
        }
        return spendingsSum;
    }

    // Возвращает сумму всех доходов за год
    public int getEarningsSum() {
        int earningsSum = 0;
        for (int i = 0; i < this.yearlyReport.length; i++) {
            if (!this.isEmptyMonthReport(i)) {
                earningsSum += this.getEarningByMonth(i);
            }
        }
        return earningsSum;
    }

    // Возвращает количество месяцев в году, данные по которым были загружены
    public int getAmountOfMonthlyReports() {
        int amount = 0;
        for (int i = 0; i < this.yearlyReport.length; i++) {
            if (!this.isEmptyMonthReport(i)) amount++;
        }
        return amount;
    }

    // Возвращает прибыль (доходы - расходы) за каждый месяц.
    // months - массив, содержащий названия месяцев
    public void showProfitsByMonths(String[] months) {
        for (int i = 0; i < this.yearlyReport.length; i++) {
            if (!this.isEmptyMonthReport(i)) {
                System.out.println(months[i] + " - " + (this.getEarningByMonth(i) - this.getSpendingByMonth(i)));
            }
        }
    }

    // Возвращает среднюю трату по месяцам за год
    public double getAverageSpending() {
        return ((double) this.getSpendingsSum()) / this.getAmountOfMonthlyReports();
    }

    // Возвращает средний доход по месяцам за год
    public double getAverageEarning() {
        return ((double) this.getEarningsSum()) / this.getAmountOfMonthlyReports();
    }
}
