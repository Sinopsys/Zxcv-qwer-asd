import requests
from bs4 import BeautifulSoup
import re
import json
import codecs
import time
import smtplib


class Item:

    def __init__(self, category, img_url, old_price, new_price, name, discount, date, condition):
        self.old_price = old_price
        self.new_price = new_price
        self.category = category
        self.date = date
        self.name = name
        self.discount = discount
        self.img_url = img_url
        self.condition = condition


items = []
url_core = 'https://dixy.ru/promo/week'


def get_max_pages(url):
    source_code = requests.get(url)
    source_code.encoding = 'utf-8'
    soup = BeautifulSoup(source_code.text, "html.parser")
    res = 0
    for link in soup.find_all('a'):
        if '?PAGEN' in link.get('href'):
            res += 1
    return res


max_pages = get_max_pages(url_core)


def get_max_items(max_pages):
    current_page = 1
    count = 0
    while current_page <= max_pages:
        count += len(BeautifulSoup(
            requests.get(url_core + '?PAGEN_1=' +
                         str(current_page)).text, "html.parser"
        ).find_all('div', {'class': "product"}))
        current_page += 1
    return count


# important to execute first
def init_items_categs(max_pages):
    current_page = 1
    while current_page <= max_pages:
        url = url_core + '?PAGEN_1=' + str(current_page)
        source_code = requests.get(url)
        source_code.encoding = 'utf-8'
        soup = BeautifulSoup(source_code.text, "html.parser")
        for link in soup.find_all('div', {'class': "product-category"}):
            items.append(Item(link.text, '', '', '', '', '', '', ''))
        current_page += 1


def init_items_imgs(max_pages):
    current_page = 1
    img_urls = []
    while current_page <= max_pages:
        url = url_core + '?PAGEN_1=' + str(current_page)
        source_code = requests.get(url)
        source_code.encoding = 'utf-8'
        soup = BeautifulSoup(source_code.text, "html.parser")
        products = soup.find_all('div', {'class': 'product-image'})
        for i in range(len(products)):
            img_urls.append('https://dixy.ru' +
                            products[i].contents[1].get('src'))
        current_page += 1
    for i in range(len(img_urls)):
        items[i].img_url = img_urls[i]


def init_items_old_price(max_pages):
    current_page = 1
    old_prices = []
    while current_page <= max_pages:
        url = url_core + '?PAGEN_1=' + str(current_page)
        source_code = requests.get(url)
        source_code.encoding = 'utf-8'
        soup = BeautifulSoup(source_code.text, "html.parser")
        products = soup.find_all('div', {'class': 'price2'})
        for p in products:
            try:
                raw_old_price = p.contents[3].text
                old_price = str(raw_old_price[:len(raw_old_price) - 3]) \
                    + '.' + str(raw_old_price[-3:-1])
                old_prices.append(old_price)
            except:
                old_prices.append("NO_OLD_PRICE_INFO")
        current_page += 1
    for i in range(len(old_prices)):
        items[i].old_price = old_prices[i]


def init_items_new_prices(max_pages):
    current_page = 1
    prices_rub = []
    prices_cop = []
    while current_page <= max_pages:
        url = url_core + '?PAGEN_1=' + str(current_page)
        page_code = requests.get(url)
        page_code.encoding = 'utf-8'
        source_code = page_code.text
        soup = BeautifulSoup(source_code, "html.parser")
        for link in soup.find_all('div', {'class': 'price'}):
            price = link.string
            prices_rub.append(price)
        for link in soup.find_all('div', {'class': 'fract'}):
            cop = link.string
            prices_cop.append(cop)
        current_page += 1
    for i in range(len(prices_rub)):
        items[i].new_price = (str(prices_rub[i]) + '.' + str(prices_cop[i]))


def init_items_discounts(max_pages):
    current_page = 1
    discounts = []
    while current_page <= max_pages:
        url = url_core + '?PAGEN_1=' + str(current_page)
        source_code = requests.get(url)
        source_code.encoding = 'utf-8'
        soup = BeautifulSoup(source_code.text, "html.parser")
        products = soup.find_all('div', {'class': "product-price-container"})
        for p in products:
            try:
                discounts.append(re.findall(
                    r'\d+', p.contents[3].text)[0] + '%')
            except:
                discounts.append("NO_DISCOUNT_INFO")
        current_page += 1
    for i in range(len(discounts)):
        items[i].discount = discounts[i]


def init_items_names(max_pages):
    current_page = 1
    names = []
    while current_page <= max_pages:
        url = url_core + '?PAGEN_1=' + str(current_page)
        source_code = requests.get(url)
        source_code.encoding = 'utf-8'
        soup = BeautifulSoup(source_code.text, "html.parser")
        products = soup.find_all('div', {'class': "product-name"})
        for p in products:
            names.append(p.text.strip())
        current_page += 1
    for i in range(len(names)):
        items[i].name = names[i]


def init_items_dates(max_pages):
    current_page = 1
    dates = []
    while current_page <= max_pages:
        url = url_core + '?PAGEN_1=' + str(current_page)
        source_code = requests.get(url)
        source_code.encoding = 'utf-8'
        soup = BeautifulSoup(source_code.text, "html.parser")
        products = soup.find_all('div', {'class': "product-day"})
        for p in products:
            dates.append(p.string)
        current_page += 1
    for i in range(len(dates)):
        items[i].date = dates[i]


def init_items_conditions(max_pages):
    current_page = 1
    conditions = []
    while current_page <= max_pages:
        url = url_core + '?PAGEN_1=' + str(current_page)
        source_code = requests.get(url)
        source_code.encoding = 'utf-8'
        soup = BeautifulSoup(source_code.text, "html.parser")
        products = soup.find_all('div', {'class': "product-info-container"})
        for p in products:
            try:
                if "product-title" in str(p.contents[5]):
                    conditions.append(p.contents[5].text)
                else:
                    raise Exception
            except:
                conditions.append('NO_CONDITION_INFO')
        current_page += 1
    for i in range(len(conditions)):
        items[i].condition = conditions[i]


def start_crawling():
    # get maximum number of pages
    max_pages = get_max_pages(url_core)
    init_items_categs(max_pages)
    init_items_imgs(max_pages)
    init_items_old_price(max_pages)
    init_items_new_prices(max_pages)
    init_items_discounts(max_pages)
    init_items_names(max_pages)
    init_items_dates(max_pages)
    init_items_conditions(max_pages)


def write_results():
    csv = codecs.open(r'dixy_items.csv', 'w', 'utf-8')
    header = '"Name";"Category";"Old price";' + \
             '"New price";"Dates";"Img ref";"Discount";"Special conditions"\n'
    csv.write(header)
    for i in items:
        csv.write('"' + i.name + '"' + ';' + '"' + i.category +
                  '"' + ';' + '"' + i.old_price + '"' + ';' + '"' + i.new_price +
                  '"' + ';' + '"' + i.date + '"' + ';' + '"' + i.img_url + '"' + ';' +
                  '"' + i.discount + '"' + ';' + '"' + i.condition + '"' + '\n')
    csv.close()


def notify_via_email(msg):
    server = smtplib.SMTP('smtp.gmail.com', 587)
    server.starttls()
    server.login("immediate.tm@gmail.com", "fucksociety")

    server.sendmail("immediate.tm@gmail.com",
                    "immediate.tm@gmail.com", msg)
    server.quit()


# will run permanently on server
def track_changes():
    max_pages = get_max_pages(url_core)
    current_number = get_max_items(max_pages)
    while True:
        # print("..........tracking changes..........")
        time.sleep(60)
        max_pages = get_max_pages(url_core)
        new_number = get_max_items(max_pages)
        if new_number != current_number:
            # print("..........changes occurred. restarting a crawler..........")
            start_crawling()
            write_results()
            notify_via_email("Some changes happened to dixy catalogue:\n"
                             "There were {} items, and now there are {} of them!"
                             .format(current_number, new_number))
            # print("..........notified via email..........")
            current_number = new_number
            time.sleep(1800)


# a single run
# start_crawling()
# write_results()

# wil run on server
track_changes()

# EOF
