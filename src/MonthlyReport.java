import java.util.ArrayList;
import java.util.List;

// Класс для хранения данных месячных отчетов
public class MonthlyReport {

    ArrayList<MonthRecord> spendings = new ArrayList<>(); // список трат за месяц
    ArrayList<MonthRecord> earnings = new ArrayList<>(); // список доходов за месяц

    // Конструктор класса, заполняет данные объекта из списка строк monthlyReportFileContent
    // Каждая строка имеет формат itemName,isExpense,quantity,pricePerPiece.
    // Например Воздушные шарики,true,5000,5
    public MonthlyReport(List<String> monthlyReportFileContent) {
        for (String line : monthlyReportFileContent) {
            String[] partsOfLine = line.split(",");
            String itemName = partsOfLine[0];
            boolean isExpense = Boolean.parseBoolean(partsOfLine[1]);
            int itemQuantity = Integer.parseInt(partsOfLine[2]);
            int itemPricePerPiece = Integer.parseInt(partsOfLine[3]);
            MonthRecord monthRecord = new MonthRecord(itemName, itemQuantity, itemPricePerPiece);
            if (isExpense) {
                this.spendings.add(monthRecord);
            } else {
                this.earnings.add(monthRecord);
            }
        }
    }

    // Возвращает объект класса MonthRecord с максимальной тратой
    public MonthRecord getBiggestSpendingMonthRecord() {
        int spending = 0;
        MonthRecord biggestSpendingMonthRecord = null;
        for (MonthRecord monthRecord : this.spendings) {
            if (spending < monthRecord.getPrice()) {
                spending = monthRecord.getPrice();
                biggestSpendingMonthRecord = monthRecord;
            }
        }
        return biggestSpendingMonthRecord;
    }

    // Возвращает объект класса MonthRecord с максимальным доходом
    public MonthRecord getBiggestEarningMonthRecord() {
        int earning = 0;
        MonthRecord biggestEarningMonthRecord = null;
        for (MonthRecord monthRecord : this.earnings) {
            if (earning < monthRecord.getPrice()) {
                earning = monthRecord.getPrice();
                biggestEarningMonthRecord = monthRecord;
            }
        }
        return biggestEarningMonthRecord;
    }

    // Возвращает суммарные траты за месяц по всем товарам
    public int getSpendingsSum() {
        int spendingsSum = 0;
        for (MonthRecord monthRecord : this.spendings) {
            spendingsSum += monthRecord.getPrice();
        }
        return spendingsSum;
    }

    // Возвращает суммарный доход за месяц по всем товарам
    public int getEarningsSum() {
        int earningsSum = 0;
        for (MonthRecord monthRecord : this.earnings) {
            earningsSum += monthRecord.getPrice();
        }
        return earningsSum;
    }
}