# This is Python Selenium code

from selenium import webdriver
from selenium.webdriver.support.ui import WebDriverWait as wait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.common.by import By
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.common.action_chains import ActionChains as AC
from selenium.webdriver.support.ui import Select as select
import datetime, time

timeout = 30
mlc_url = "https://www.mlcinsurance.com.au/"
ato_url = "https://www.ato.gov.au/Calculators-and-tools/Host/?anchor=STC&anchor=STC#STC/questions"

chrome_driver_path = 'C:/drivers/chromedriver.exe'
options = webdriver.ChromeOptions()
# options.add_argument('headless') 
options.add_argument("--start-maximized")
prefs = {"profile.default_content_setting_values.notifications" : 2}
options.add_experimental_option("prefs",prefs)

def requestForLifeViewDemo(driver, str):
    
    # wait for page to load
    xpath_str = "//h1[text()='Life Insurance']"
    wait(driver, timeout).until(EC.presence_of_element_located((By.XPATH, xpath_str)))
    print("MLC Insurance page loaded")
    
    # search for lifeview
    elem_xpath = "//nav[@id='nav-onscreen']//button"
    scrollViewToElementAndClick(driver, elem_xpath)
    print("Search clicked")
    
    # enter search string
    elem_xpath = "//input[@id='q'][@type='search']"
    scrollViewToElementAndInputText(driver, elem_xpath, str, 3)
    print("Entered  Lifeview")
    
    elem_xpath = "//a[@role='button']/span[@class='autocomplete-results-item-title']/strong[text()='LifeView']"
    scrollViewToElementAndClick(driver, elem_xpath)
    print("Selected  Lifeview")

    # verify breadcrumbs Home, Partnering with us, Superannuation funds, LifeView
    xpath_str = "//ul[@itemprop='breadcrumb']"
    wait(driver, timeout).until(EC.presence_of_element_located((By.XPATH, xpath_str)))
    print("Breadcrumbs verified")
    
    # click 'Request a demo' button
    request_demo_button_elem = "//span[@class='cta'][text()='Request a demo']"
    scrollViewToElementAndClick(driver, request_demo_button_elem)
    print("clicked 'Request a demo'")
    
    elem_xpath = "//h1[text()='Request a LifeView demo']"
    wait(driver, timeout).until(EC.presence_of_element_located((By.XPATH, elem_xpath)))
    print("'Request a demo' form loaded")
    
    # enter relevant data in form
    elem_xpath = "//input[contains(@class,'text-box single-line')]"
    wait(driver, timeout).until(EC.presence_of_element_located((By.XPATH, elem_xpath)))
    elem = driver.find_elements_by_xpath(elem_xpath)

    elem[0].clear()
    elem[0].send_keys('Test name')
    
    elem[1].clear()
    elem[1].send_keys('Test company')
    
    elem[2].clear()
    elem[2].send_keys('test@company.com')
    
    elem[3].clear()
    elem[3].send_keys('0452291888')
    
    elem[4].clear()
    elem[4].send_keys('15-May-2020')
    
    elem_xpath = "//input[@type='radio'][@value='PM']"
    scrollViewToElementAndClick(driver, elem_xpath)
    
    elem_xpath = "//textarea[@class='form-control']"
    scrollViewToElementAndInputText(driver, elem_xpath, 'Entering test details')
    
    print("Entered relevant data..")
    
    book_a_demo_button_elem = "//input[@type='submit'][@value='Book a demo']"
#     scrollViewToElementAndClick(driver, book_a_demo_button_elem)

def verifyCalculatedTax(driver):
    
    # wait for page to load       
    xpath_str = "//h1[text()='Simple tax calculator']"
    wait(driver, timeout).until(EC.presence_of_element_located((By.XPATH, xpath_str)))
    print('ATO Calculator page loaded')

    # Select an income year*
    income_yr = '2019'
    xpath_str = "//select[@id='ddl-financialYear']/option[@value='"+ income_yr +"']"
    elem = wait(driver, timeout).until(EC.presence_of_element_located((By.XPATH, xpath_str)))
    elem.click()
    print('Income year : {}'.format(income_yr))

    # Enter your taxable income - enter dollars only*
    income_amount = '120000'
    elem = wait(driver, timeout).until(EC.presence_of_element_located((By.ID, "texttaxIncomeAmt")))
    elem.send_keys(income_amount)
    print('Income amount : ${}'.format(income_amount))

    # Select your residency status*
    status = ['Resident for full year','Non-resident for full year','Part-year resident']
    res_status = "//span[text()='Resident for full year']"
    scrollViewToElementAndClick(driver, res_status)
    print('Residency status : {}'.format(status[0]))
    
    submit_button_elem = "//button[text()='Submit']"
    scrollViewToElementAndClick(driver, submit_button_elem)
    wait(driver, timeout).until(EC.presence_of_element_located((By.XPATH, "//div[@class='white-block']")))
    
    estimated_tax_elem = "//div[@class='white-block']//span"
    estimated_tax = wait(driver, timeout).until(EC.presence_of_element_located((By.XPATH, estimated_tax_elem)))
    print('Estimated tax {}'.format(estimated_tax.get_attribute('innerText')))

def scrollViewToElement(driver, elem_xpath, waittime=0, count=1):
    '''
    Scroll view to the element
    @param elem_xpath: element to scrollview to 
    '''
    scroll = 0
    elem = wait(driver, timeout).until(EC.presence_of_element_located((By.XPATH, elem_xpath)))
    while scroll < count: # scroll count times
        driver.execute_script('arguments[0].scrollIntoView({behavior: "instant", block: "center"});', elem)
        scroll += 1
    time.sleep(waittime)

def scrollViewToElementAndClick(driver, elem_xpath, waittime=0, count=1):
    '''
    Scroll view to the element
    @param elem: element to scrollview to 
    '''
    scroll = 0
    elem = wait(driver, timeout).until(EC.presence_of_element_located((By.XPATH, elem_xpath)))
    while scroll < count: # scroll count times
        driver.execute_script('arguments[0].scrollIntoView({behavior: "instant", block: "center"});', elem)
        scroll += 1
    elem = wait(driver, timeout).until(EC.presence_of_element_located((By.XPATH, elem_xpath)))
    elem.click()
    time.sleep(waittime)

def scrollViewToElementAndInputText(driver, elem_xpath, txt, waittime=0, count=1):
    '''
    Scroll view to the element
    @param elem: element to scrollview to 
    '''
    scroll = 0
    elem = wait(driver, timeout).until(EC.presence_of_element_located((By.XPATH, elem_xpath)))
    while scroll < count: # scroll count times
        driver.execute_script('arguments[0].scrollIntoView({behavior: "instant", block: "center"});', elem)
        scroll += 1
    elem = wait(driver, timeout).until(EC.presence_of_element_located((By.XPATH, elem_xpath)))
    elem.clear()
    elem.send_keys(txt)
    time.sleep(waittime)

def exitBrowser(driver):
    driver.close()
    driver.quit()
    print("Browser exit")


if __name__ == "__main__":

    driver=webdriver.Chrome(options=options, executable_path=chrome_driver_path)
    start_time = datetime.datetime.now().replace(microsecond=0)
    
    # MLC Test Execution
    driver.get(mlc_url)
    requestForLifeViewDemo(driver, 'Lifeview')

    # ATO Calculator Test Execution
    driver.get(ato_url)
    verifyCalculatedTax(driver)

    exitBrowser(driver)
    end_time = datetime.datetime.now().replace(microsecond=0)
    print("Total time elapsed (HH:MM:SS): %s" % str(end_time - start_time))
