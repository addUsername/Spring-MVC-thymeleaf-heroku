import time
from selenium import webdriver
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.common.action_chains import ActionChains
from selenium.webdriver.support.ui import Select
from json import dump

print("Inicio")
driver = webdriver.Firefox(executable_path='geckodriver.exe')
time.sleep(2)
URL = "https://opendata.aemet.es/centrodedescargas/productosAEMET?"
driver.get(URL)

myDict = {}
select = Select(driver.find_element_by_id("obs2"))
time.sleep(1)
actions = ActionChains(driver)
time.sleep(1)
actions = actions.send_keys(Keys.TAB)
time.sleep(1)
actions = actions.send_keys(Keys.TAB)
time.sleep(1)
actions = actions.send_keys(Keys.TAB)
time.sleep(1)
actions = actions.send_keys(Keys.TAB)
time.sleep(1)
actions = actions.send_keys(Keys.TAB)
time.sleep(1)
actions = actions.send_keys(Keys.TAB)
time.sleep(1)
actions = actions.send_keys(Keys.TAB)
time.sleep(1)
actions = actions.send_keys(Keys.TAB)

time.sleep(1)
actions.perform()

for option in select.options:
        print(option.text, option.get_attribute('value'))
        time.sleep(1)
        actions = ActionChains(driver)
        time.sleep(1)
        actions = actions.send_keys(Keys.ENTER)
        time.sleep(1)
        actions = actions.send_keys(option.text)
        time.sleep(1)
        actions = actions.send_keys(Keys.ENTER)
        actions.perform()
        time.sleep(1)
        select2 = Select(driver.find_element_by_id("obs22"))
        myDict[option.text] = [option2.text for option2 in select2.options]      
        print(myDict[option.text])


time.sleep(1)
with open("output.json", "w") as file:
            dump(myDict, file)
print("finish")
