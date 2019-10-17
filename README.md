[![Build Status](https://travis-ci.org/Miguelos/SalesTaxes.svg?branch=master)](https://travis-ci.org/Miguelos/SalesTaxes) [![codecov](https://codecov.io/gh/Miguelos/SalesTaxes/branch/master/graph/badge.svg?token=uSnwph6Ahk)](https://codecov.io/gh/Miguelos/SalesTaxes)

# Sales Taxes

Basic sales tax is applicable at a rate of 10% on all goods, except books, food, and medical products that are exempt. Import duty is an additional sales tax applicable on all imported goods at a rate of 5%, with no exemptions.

When I purchase items I receive a receipt which lists the name of all the items and their price (including tax), finishing with the total cost of the items, and the total amounts of sales taxes paid. The rounding rules for sales tax are that for a tax rate of n%, a shelf price of p contains (np/100 rounded up to the nearest 0.05) amount of sales tax.

Write an application that prints out the receipt details for these shopping baskets...

## Project Structure

The structure is simple. The model is just the class *Item* which contains immutable data representing a product.
The class *TaxCalculator* contains the domain of the program. It has two main methods, one to add items to the cart and other to get the receipt as a String.
Both classes have their tests associated.

To build the project and run the tests use Gradle.

## Notes

* Sales taxes are composed by _Basic sales taxes_ and _Import taxes_. Therefore an imported item should have applied both if applicable.
* Round rule is applied separately to each tax increment.
* The data source is out of the scope and it is the tests that use files to load the test data into the program.
