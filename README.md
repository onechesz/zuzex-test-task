<h1>Веб-приложение, реализованное в процессе выполнение тестового задания на позицию Junior Java-разработчика в компанию https://www.zuzex.com/.</h1>

<h2>О проекте</h2>

<h3>Описание</h3>

<p>Проект представляет собой RESTful-приложение, которое позволяет пользователям контролировать свои дома, наполняя их жильцами.</p>

<h2>Разработка</h2>

<h3>Технологии</h3>

<p>Согласно техническому заданию, необходимо было использовать обязательно такие вещи как PostgreSQL, Spring Boot (REST, JPA), Liquibase, Maven, Java (11 версии и выше).</p>
<p>Для того чтобы разработка была более удобной я принял решение дополнить этот стек парочкой полезных компонентов фреймворка (Security и Validation).</p>
<p>Список технологий, использованных в процессе разработки:</p>
<ul>
    <li>Java (corretto-18.0.2)</li>
    <li>Spring (Security, Validation, Web, PostgreSQL Driver, Data JPA, Liquibase, Maven, JWT)</li>
    <li>PostgreSQL</li>
</ul>

<h3>Процесс</h3>

<p>Приложение разрабатывалось мной в IntelliJ IDEA. Я коммитил каждый этап, а также трекал время за работой.</p>

<h2>Результаты</h2>

<h3>Сводка</h3>

<p>Ссылки:</p>
<ul>
    <li><a href="https://drive.google.com/file/d/1mTtIUZRNaReI-N-Wyy79mkwgOywzYMIm/view?usp=sharing">этапы разработки проекта (трекинг времени)</a></li>
    <li><a href="https://drive.google.com/file/d/1k6xvB2UGxxQj7lVzyVU1Y3yBp08TdDSK/view?usp=sharing">оригинальное ТЗ</a></li>
</ul>

<h3>Запуск</h3>
<p>Порядок действий для запуска приложения:</p>
<ol>
    <li>Скачать проект и распаковать архив</li>
    <li>Создать базу данных в PostgreSQL и изменить (при необходимости) свойства в файле <strong>application.properties</strong></li>
    <li>Запустить программу при помощи функции main, находящейся в классе <strong>ZuzexTestTaskApplication</strong></li>
</ol>

<p>Для перехода в Swagger-UI, откройте в браузере: <code>корневая-ссылка-проекта/swagger-ui.html</code></p>