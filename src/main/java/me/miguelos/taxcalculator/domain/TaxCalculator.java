package me.miguelos.taxcalculator.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedHashMap;
import java.util.Map;
import me.miguelos.taxcalculator.domain.model.Item;


class TaxCalculator {

  private final LinkedHashMap<Item, Integer> cart;
  private final BigDecimal ROUND_DIVISOR = BigDecimal.valueOf(0.05);
  private final BigDecimal BASIC_TAX_RATE = BigDecimal.valueOf(0.10);
  private final BigDecimal IMPORT_TAX_RATE = BigDecimal.valueOf(0.05);


  TaxCalculator() {
    cart = new LinkedHashMap<>();
  }

  public void addItem(int quantity, Item item) {
    if (quantity > 0) {
      cart.put(item, quantity);
    }
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

    if (item.isBasicSalesTaxApplicable()) {
      basicTax = getTax(item.getPrice(), BASIC_TAX_RATE);
    }
    if (item.isImportTaxApplicable()) {
      importTax = getTax(item.getPrice(), IMPORT_TAX_RATE);
    }
    return importTax.add(basicTax).setScale(2, RoundingMode.FLOOR);
  }

  private BigDecimal getTax(BigDecimal price, BigDecimal taxRate) {
    return new BigDecimal(taxRate.toString())
        .multiply(price)
        .divide(ROUND_DIVISOR, 0, RoundingMode.UP)
        .multiply(ROUND_DIVISOR);
  }

  /**
   * @param quantity Number of the products.
   * @param price Price without taxes applied.
   * @return Price after taxes for the item passed as parameter.
   */
  private BigDecimal getPriceAfterTaxes(int quantity, BigDecimal price, BigDecimal salesTax) {
    return new BigDecimal(price.toString())
        .multiply(BigDecimal.valueOf(quantity))
        .add(salesTax)
        .setScale(2, BigDecimal.ROUND_HALF_UP);
  }

  @Override
  public String toString() {
    return receipt();
  }

  String receipt() {
    StringBuilder sb = new StringBuilder();
    BigDecimal price = BigDecimal.valueOf(0);
    BigDecimal salesTaxes = BigDecimal.valueOf(0);
    for (Map.Entry<Item, Integer> cartItem : cart.entrySet()) {
      int quantity = cartItem.getValue();
      BigDecimal itemTaxes = getSalesTax(cartItem.getKey());
      BigDecimal priceAfterTaxes =
          getPriceAfterTaxes(quantity, cartItem.getKey().getPrice(), itemTaxes);
      price = price.add(priceAfterTaxes);
      salesTaxes = salesTaxes.add(itemTaxes);
      sb.append(quantity)
          .append(" ")
          .append(cartItem.getKey().toString())
          .append(": ")
          .append(priceAfterTaxes)
          .append("\n");
    }
    sb.append("Sales Taxes: ")
        .append(salesTaxes)
        .append("\n")
        .append("Total: ")
        .append(price);
    return sb.toString();
  }
}
