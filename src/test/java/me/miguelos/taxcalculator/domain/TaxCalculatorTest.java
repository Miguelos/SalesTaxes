package me.miguelos.taxcalculator.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import me.miguelos.taxcalculator.domain.model.Item;
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
    Scanner scanner;
    try {
      scanner = new Scanner(
          new File("src/test/resources/" + inputFile),
          "UTF-8"
      );

      while (scanner.hasNext()) {
        String line = scanner.nextLine();
        Pattern linePattern = Pattern.compile("(\\d+) (.*) at (\\d+\\.\\d{2})");

        Matcher m = linePattern.matcher(line);
        if (m.find()) {
          BigDecimal price = BigDecimal.valueOf(Double.parseDouble(m.group(3)));
          int quantity = Integer.parseInt(m.group(1));
          String description = m.group(2);

          Item it = new Item(
              description.contains("imported"),
              !(description.contains("book") || description.contains("chocolate") || description
                  .contains("pills")),
              description,
              price
          );
          tc.addItem(quantity, it);
        } else {
          throw new IllegalArgumentException("Wrong input file format");
        }
      }
      scanner.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    assertResultFile(tc.receipt(), expectedOutputFile);
  }

  private void assertResultFile(String receipt, String expectedOutputFile) {
    try {
      String content = new Scanner(
          new File("src/test/resources/" + expectedOutputFile),
          "UTF-8"
      ).useDelimiter("\\Z").next();
      assertEquals(content, receipt);
    } catch (FileNotFoundException ex) {
      ex.printStackTrace();
    }
  }
}