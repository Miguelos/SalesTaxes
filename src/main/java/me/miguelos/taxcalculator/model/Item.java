package me.miguelos.taxcalculator.model;

import java.math.BigDecimal;


public class Item {

  private final boolean imported;
  private final boolean basic;
  private final String description;
  private final BigDecimal price;

  /**
   * Constructor for the item model. It save the necessary for the domain to calculate the taxes and
   * print the receipt.
   */
  public Item(boolean imported, boolean basic, String description, BigDecimal price) {
    this.imported = imported;
    this.basic = basic;
    this.description = description;
    this.price = price;
  }

  public boolean isImportTaxApplicable() {
    return imported;
  }

  public boolean isBasicSalesTaxApplicable() {
    return basic;
  }

  public BigDecimal getPrice() {
    return price;
  }

  @Override
  public String toString() {
    return description;
  }
}
