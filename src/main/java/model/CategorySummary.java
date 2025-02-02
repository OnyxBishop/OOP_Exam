package model;

public class CategorySummary {
    private final double totalAmount;
    private final int count;
    private final TransactionType type;

    public CategorySummary(double totalAmount, int count, TransactionType type) {
        this.totalAmount = totalAmount;
        this.count = count;
        this.type = type;
    }

    public double getTotalAmount() { return totalAmount; }
    public int getCount() { return count; }
    public TransactionType getType() { return type; }
}
