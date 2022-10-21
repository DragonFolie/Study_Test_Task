P.S run test individually in PersonRepositoryTest

Потрібно робити SpringBoot application, в якому буде всього один REST endpoint, 
який повертає json з ім'ям, прізвищем та віком людини за id. У додатку має бути 
один RestController, Service та Repository. У базі даних зберігатимуться дані 
про людей: ім'я, прізвище та дата народження. У сервісі вважатиметься вік людини.
Для роботи програми слід вибрати будь-яку embedded базу даних. Для роботи з базою 
даних використовувати Spring Data. Вона має стартувати зі стартом програми. Слід
написати тести (unit + e2e). Складальник будь-який на ваш розсуд. Можна 
використовувати spring initializr https://start.spring.io/. Програма має 
успішно білдитися, а тести проходити. Версія джави та всіх залежностей будь-які.
Код слід запушити в окремий гіт репозиторій, а за виконанням надати посилання.

е2e - це наскрізне тестування усієї логіки програми. Воно не обов'язково має 
задіяти фронт, а може, якщо він є. Але ми просто REST без фронту. Ми тестуємо 
програму шляхом відправки різних запитів на REST контролер і очікуємо отримати
відповідні відповіді. Для таких тестів слід підняти embedded database. 
