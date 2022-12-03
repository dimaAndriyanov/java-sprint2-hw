// Хранит данные по конкретному товару из месячного отчета
public class MonthRecord {

    String name; // наименование товара
    int quantity; // количество товара
    int pricePerItem; // цена единицы товара

    public MonthRecord(String name, int quantity, int pricePerItem) {
        this.name = name;
        this.quantity = quantity;
        this.pricePerItem = pricePerItem;
    }

    public String getName() {
        return this.name;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public int getPricePerItem() {
        return this.pricePerItem;
    }

    public int getPrice() {
        return this.getQuantity() * this.getPricePerItem();
    }
}