@AusPostAPI
Feature: Calculate international parcel postage cost
Scenario: Calculate international shipping parcel cost
 Given I execute Get Country Codes "GetCountryCodes" api
 Then I execute Get Services "GetServices" api

Scenario Outline: Calculate shipping cost
 And I execute "GetDeliveryPrice" and calculate shipping cost <country_code> <weight> <service_code>
 Examples:
     | country_code | weight | service_code                 |
     | NZ           | 1.5    | INT_PARCEL_STD_OWN_PACKAGING |
     | GB           | 5.0    | Non-resident for full year   |
     | MY           | 2.0    | Part-year resident           |
