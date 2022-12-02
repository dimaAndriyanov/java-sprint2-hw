// Хранит данные по конкретному товару из месячного отчета
public class ItemData {

    boolean isExpense; // true - товар является тратой, false - доходом
    int quantity; // количество товара
    int pricePerItem; // цена единицы товара

    public ItemData(boolean isExpense, int quantity, int pricePerItem) {
        this.isExpense = isExpense;
        this.quantity = quantity;
        this.pricePerItem = pricePerItem;
    }

    public boolean isExpense() {
        return this.isExpense;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public int getPricePerItem() {
        return this.pricePerItem;
    }
}
