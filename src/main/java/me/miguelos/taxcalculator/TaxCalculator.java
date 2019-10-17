package me.miguelos.taxcalculator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedHashMap;
import me.miguelos.taxcalculator.model.Item;

class TaxCalculator {

  private final LinkedHashMap<Item, Integer> cart;
  private BigDecimal price;
  private BigDecimal salesTaxes;

  TaxCalculator() {
    cart = new LinkedHashMap<>();
    price = BigDecimal.valueOf(0);
    salesTaxes = BigDecimal.valueOf(0);
  }

  public void addItem(int quantity, Item item) {
    if (quantity > 0) {
      cart.put(item, quantity);
      updatePrice(quantity, item);
    }
  }

  private void updatePrice(int quantity, Item item) {
    price = price.add(getPriceAfterTaxes(quantity, item));
    salesTaxes = salesTaxes.add(getSalesTax(item));
  }

  /**
   * The rounding rules for sales tax are that for a tax rate of n%, a shelf price of p contains
   * (np/100 rounded up to the nearest 0.05) amount of sales tax.
   *
   * @param item Product to calculate the tax.
   * @return Tax applied to the item passed as parameter.
   */
  private BigDecimal getSalesTax(final Item item) {
    BigDecimal basicTax = BigDecimal.valueOf(0);
    BigDecimal importTax = BigDecimal.valueOf(0);
    BigDecimal divisor = BigDecimal.valueOf(0.05);
    if (item.isBasicSalesTaxApplicable()) {
      basicTax = BigDecimal.valueOf(0.10)
          .multiply(item.getPrice())
          .divide(divisor, 0, RoundingMode.UP)
          .multiply(divisor);
    }
    if (item.isImportTaxApplicable()) {
      importTax = BigDecimal.valueOf(0.05)
          .multiply(item.getPrice())
          .divide(divisor, 0, RoundingMode.UP)
          .multiply(divisor);
    }
    return importTax.add(basicTax).setScale(2, RoundingMode.FLOOR);
  }

  /**
   * @param quantity Number of the products.
   * @param item Product to calculate the price after taxes.
   * @return Price after taxes for the item passed as parameter.
   */
  private BigDecimal getPriceAfterTaxes(int quantity, final Item item) {
    return item.getPrice().multiply(BigDecimal.valueOf(quantity)).add(getSalesTax(item))
        .setScale(2, RoundingMode.FLOOR);
  }

  @Override
  public String toString() {
    return receipt();
  }

  String receipt() {
    StringBuilder sb = new StringBuilder();
    for (Item i : cart.keySet()) {
      int quantity = cart.get(i);
      sb.append(quantity)
          .append(" ")
          .append(i)
          .append(": ")
          .append(getPriceAfterTaxes(quantity, i))
          .append("\n");
    }
    sb.append("Sales Taxes: ")
        .append(salesTaxes)
        .append("\n");
    sb.append("Total: ")
        .append(price);
    return sb.toString();
  }
}
