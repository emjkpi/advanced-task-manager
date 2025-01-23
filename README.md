Домашний бухгалтер
Описание:
Домашний бухгалтер используется для того, чтобы отслеживать финансовые растраты и вести их учет.
Примеры:
•	https://www.cashorganizer.com/rus/?deeplink=adwords_opt_domashnyaya_buhalteriya_3&gclid=CjwKCAjw5p_8BRBUEiwAPpJO6ygnbAtRMiTfybhAsL0TRXcOBa-sEYiKyPUdZSQ-vPh0LpkhmqRFBxoCjK8QAvD_BwE
•	https://equity.today/luchshie-programmy-dlya-domashnej-buxgalterii.html

Функциональные требования:
1.	Я как пользователь хочу залогиниться в систему использую свою почту и пароль
2.	Я как пользователь хочу оставить заметку о потраченной сумме, например:
Money: 200 UAH
Category: Food
Date: 15-10-2020
3.	Я как пользователь хочу видеть всю историю расходов (табличка, лента, тому подобное)
4.	Я как пользователь хочу иметь возможность получить агрегацию и посмотреть итоговую сумму потраченную за день/месяц/год. Иметь возможность отфильтровать по категории, дате
5.	Я как пользователь хочу иметь возможность выгрузить отчет в PDF формате
6.	Дополнительные функции можно добавлять по усмотрению

Не функциональные требования:
1.	UI/frontend часть не требуется разрабатывать, только API
2.	API должен быть написан на Java, Spring Boot, Spring Data, Gradle, Hibernate, PostgreSQL, Docker, Flyway, Lombok, Mapstruct
3.	API тестировать с помощью Postman
4.	PostgreSQL развернуть в Docker: 
docker run -p 5432:5432 -e POSTGRES_USER=s1mple -e POSTGRES_PASSWORD=s1mple_passsword -e POSTGRES_DB=home_accountant -d postgres:13.12
5.	Среда разработки – Intellij IDEA
6.	API должен быть разработан в 3x уровней архитектуре: Controller -> Service -> Repository, CRUD
7.	В Intellij IDEA установить плагин SonarLint
8.	Должно храниться на Github, public repo
9.	Должно запускаться “без приседаний”, я должен иметь возможность выкачать проект из Github и одной командой запустить его (сам запуск)
10.	Код должно быть покрыт тестами.
