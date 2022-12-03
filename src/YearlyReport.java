// Класс для хранения данных годовых отчетов
import java.util.List;

public class YearlyReport {

    int monthsAmount = 12;
    Integer[] spendings = new Integer[monthsAmount]; // содержит траты за каждый месяц
    Integer[] earnings = new Integer[monthsAmount]; // содержит доход за каждый месяц

    // Заполняет поля объекта YearlyReport данными из списка строк yearlyReportFileContent
    // Каждая строка имеет формат month,amount,isExpense.
    // Например 01,100000,true
    public void addData(List<String> yearlyReportFileContent) {
        for (String line : yearlyReportFileContent) {
            String[] partsOfLine = line.split(",");
            int monthNumber = Integer.parseInt(partsOfLine[0]) - 1;
            int amount = Integer.parseInt(partsOfLine[1]);
            boolean isExpense = Boolean.parseBoolean(partsOfLine[2]);
            if (isExpense) {
                this.spendings[monthNumber] = amount;
            } else {
                this.earnings[monthNumber] = amount;
            }
        }
    }

    // Возвращает траты за месяц monthNumber, где 0 - Январь, 1 - Февраль,.. 11 - Декабрь
    public int getSpendingByMonth(int monthNumber) {
        return this.spendings[monthNumber];
    }

    // Возвращает доход за месяц monthNumber, где 0 - Январь, 1 - Февраль,.. 11 - Декабрь
    public int getEarningByMonth(int monthNumber) {
        return this.earnings[monthNumber];
    }

    // Возвращает true, если данные по доходам и расходам за месяц monthNumber были загружены.
    // false -  в противном случае. 0 - Январь, 1 - Февраль,.. 11 - Декабрь
    public boolean isEmptyMonthReport(int monthNumber) {
        return (this.spendings[monthNumber] == null) || (this.earnings[monthNumber] == null);
    }

    // Возвращает сумму всех трат за год
    public int getSpendingsSum() {
        int spendingsSum = 0;
        for (int i = 0; i < this.monthsAmount; i++) {
            if (!this.isEmptyMonthReport(i)) {
                spendingsSum += this.getSpendingByMonth(i);
            }
        }
        return spendingsSum;
    }

    // Возвращает сумму всех доходов за год
    public int getEarningsSum() {
        int earningsSum = 0;
        for (int i = 0; i < this.monthsAmount; i++) {
            if (!this.isEmptyMonthReport(i)) {
                earningsSum += this.getEarningByMonth(i);
            }
        }
        return earningsSum;
    }

    // Возвращает количество месяцев в году, данные по которым были загружены
    public int getAmountOfMonthlyReports() {
        int amount = 0;
        for (int i = 0; i < this.monthsAmount; i++) {
            if (!this.isEmptyMonthReport(i)) amount++;
        }
        return amount;
    }

    // Возвращает прибыль (доходы - расходы) за каждый месяц.
    // months - массив, содержащий названия месяцев
    public void showProfitsByMonths(String[] months) {
        for (int i = 0; i < this.monthsAmount; i++) {
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