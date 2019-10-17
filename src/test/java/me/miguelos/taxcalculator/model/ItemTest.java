package me.miguelos.taxcalculator.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.Random;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;


class ItemTest {


  @DisplayName("Random item Test")
  @RepeatedTest(value = 10, name = "{displayName} -> {currentRepetition}")
  void testRandomProduct() {
    Random r = new Random();

    boolean imported = r.nextBoolean();
    boolean basic = r.nextBoolean();
    double price = r.nextDouble();
    String desc = "chocolate bar";

    Item item = new Item(imported, basic, desc, new BigDecimal(price));

    assertAll(
        () -> assertEquals(imported, item.isImportTaxApplicable(),
            "Doesn't save import property properly"),
        () -> assertEquals(basic, item.isBasicSalesTaxApplicable(),
            "Doesn't save basic tax properly"),
        () -> assertEquals(item.toString(), (desc))
    );
  }

  @Test
  void isImportTaxApplicable() {
    Item item = new Item(true, false, "book", BigDecimal.valueOf(0.85));
    assertTrue(item.isImportTaxApplicable());
  }

  @Test
  void isBasicSalesTaxApplicable() {
    Item item = new Item(true, false, "book", BigDecimal.valueOf(0.85));
    assertFalse(item.isBasicSalesTaxApplicable());
  }

  @Test
  void getPrice() {
    Item item = new Item(true, false, "book", BigDecimal.valueOf(0.85));
    assertEquals(item.getPrice(), BigDecimal.valueOf(0.85));
  }

  @Test
  void testToString() {
    Item item =
        new Item(true, false, "imported book", BigDecimal.valueOf(0.85));
    assertEquals("imported book", item.toString());
  }
}