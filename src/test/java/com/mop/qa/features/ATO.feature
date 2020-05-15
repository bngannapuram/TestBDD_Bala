Feature: Verify Calculated Tax on ATO

@ATOCalculator
Scenario: Verify calculated tax
 Given I Launch ATO website

Scenario Outline: Verify calculated tax
 And I Calculate tax <income_year> <income_amount> <residancy_status>
 Examples:
     | income_year | income_amount | residancy_status           |
     | 2017        | 120000        | Resident for full year     |
     | 2018        | 130000        | Non-resident for full year |
     | 2019        | 140000        | Part-year resident         |
