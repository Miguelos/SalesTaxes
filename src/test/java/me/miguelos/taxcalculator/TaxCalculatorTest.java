package me.miguelos.taxcalculator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Scanner;
import me.miguelos.taxcalculator.model.Item;
import org.junit.jupiter.api.Test;

class TaxCalculatorTest {

  @Test
  void taxCalculatorTest1() {
    taxCalculatorTestFromFile("input1.txt", "output1.txt");
  }

  @Test
  void taxCalculatorTest2() {
    taxCalculatorTestFromFile("input2.txt", "output2.txt");
  }

  @Test
  void taxCalculatorTest3() {
    taxCalculatorTestFromFile("input3.txt", "output3.txt");
  }

  @Test
  void taxCalculatorTest4() {
    taxCalculatorTestFromFile("input4.txt", "output4.txt");
  }

  @Test
  void taxCalculatorTest5() {
    taxCalculatorTestFromFile("input5.txt", "output5.txt");
  }

  void taxCalculatorTestFromFile(String inputFile, String expectedOutputFile) {
    TaxCalculator tc = new TaxCalculator();
    BufferedReader reader;
    try {
      reader = new BufferedReader(new FileReader("src/test/resources/" + inputFile));
      String line;
      while ((line = reader.readLine()) != null && !line.isEmpty()) {
        String[] split = line.split(" at ");
        BigDecimal price = BigDecimal.valueOf(Double.parseDouble(split[1]));
        int firstSpace = split[0].indexOf(' ');
        int quantity = Integer.parseInt(split[0].substring(0, firstSpace));
        String description = split[0].substring(firstSpace + 1);
        Item it = new Item(
            description.contains("imported"),
            !(description.contains("book") || description.contains("chocolate") || description
                .contains("pills")),
            description,
            price
        );
        tc.addItem(quantity, it);
      }
      reader.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    assertResultFile(tc.receipt(), expectedOutputFile);
  }

  private void assertResultFile(String receipt, String expectedOutputFile) {
    try {
      String content = new Scanner(new File("src/test/resources/" + expectedOutputFile))
          .useDelimiter("\\Z")
          .next();
      assertEquals(content, receipt);
    } catch (FileNotFoundException ex) {
      ex.printStackTrace();
    }
  }
}