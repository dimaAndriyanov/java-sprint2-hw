// Класс для хранения данных месячных отчетов

import java.util.HashMap;
import java.util.List;

public class MonthlyReport {

    HashMap<String, ItemData> monthlyReport = new HashMap<>(); // String - название товара, ItemData - данные товара

    // Конструктор класса, заполняет данные объекта из списка строк monthlyReportFileContent
    // Каждая строка имеет формат itemName,isExpense,quantity,pricePerPiece.
    // Например Воздушные шарики,true,5000,5
    public MonthlyReport(List<String> monthlyReportFileContent) {
        for (String line : monthlyReportFileContent) {
            String[] partsOfLine = line.split(",");
            String itemName = partsOfLine[0];
            boolean itemIsExpense = Boolean.parseBoolean(partsOfLine[1]);
            int itemQuantity = Integer.parseInt(partsOfLine[2]);
            int itemPricePerPiece = Integer.parseInt(partsOfLine[3]);
            ItemData itemData = new ItemData(itemIsExpense, itemQuantity, itemPricePerPiece);
            this.monthlyReport.put(itemName, itemData);
        }
    }

    // Возвращает объект класса ItemData по имени itemName
    public ItemData getItemData(String itemName) {
        if (this.monthlyReport.containsKey(itemName)) {
            return this.monthlyReport.get(itemName);
        } else {
            return null;
        }
    }

    // Возвращает доход по товару itemName
    public int getEarning(String itemName) {
        ItemData itemData = this.getItemData(itemName);
        if (itemData != null) {
            if (!itemData.isExpense()) {
                return itemData.getQuantity() * itemData.getPricePerItem();
            } else {
                System.out.println("Данный товар не принес дохода в этом месяце");
                return -1;
            }
        }else {
            System.out.println("Данный товар не найден в месячном отчете");
            return -1;
        }
    }

    // Возвращает затраты по товару itemName
    public int getSpending(String itemName) {
        ItemData itemData = this.getItemData(itemName);
        if (itemData != null) {
            if (itemData.isExpense()) {
                return itemData.getQuantity() * itemData.getPricePerItem();
            } else {
                System.out.println("Данный товар не продавался в этом месяце");
                return -1;
            }
        }else {
            System.out.println("Данный товар не найден в месячном отчете");
            return -1;
        }
    }

    // Возвращает название товара, который принес самый большй доход за месяц
    public String getBiggestEarningName() {
        String biggestEarningName = null;
        int biggestEarning = 0;
        for (String itemName : this.monthlyReport.keySet()) {
            ItemData itemData = this.monthlyReport.get(itemName);
            if (!itemData.isExpense()) {
                if (biggestEarning < itemData.getQuantity() * itemData.getPricePerItem()) {
                    biggestEarning = itemData.getQuantity() * itemData.getPricePerItem();
                    biggestEarningName = itemName;
                }
            }
        }
        return biggestEarningName;
    }

    // Возвращает название товара с самым большим расходом за месяц
    public String getBiggestSpendingName() {
        String biggestSpendingName = null;
        int biggestSpending = 0;
        for (String itemName : this.monthlyReport.keySet()) {
            ItemData itemData = this.monthlyReport.get(itemName);
            if (itemData.isExpense()) {
                if (biggestSpending < itemData.getQuantity() * itemData.getPricePerItem()) {
                    biggestSpendingName = itemName;
                    biggestSpending = itemData.getQuantity() * itemData.getPricePerItem();
                }
            }
        }
        return biggestSpendingName;
    }

    // Возвращает суммарные траты за месяц по всем товарам
    public int getSpendingsSum() {
        int spendingsSum = 0;
        for (String itemName : this.monthlyReport.keySet()) {
            ItemData itemData = this.monthlyReport.get(itemName);
            if (itemData.isExpense()) {
                spendingsSum += itemData.getQuantity() * itemData.getPricePerItem();
            }
        }
        return spendingsSum;
    }

    // Возвращает суммарный доход за месяц по всем товарам
    public int getEarningsSum() {
        int earningsSum = 0;
        for (String itemName : this.monthlyReport.keySet()) {
            ItemData itemData = this.monthlyReport.get(itemName);
            if (!itemData.isExpense()) {
                earningsSum += itemData.getQuantity() * itemData.getPricePerItem();
            }
        }
        return earningsSum;
    }
}
