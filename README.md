## Simple Table

### Task description

<ul>

Assemble: **Gradle**

Run: **Spring Boot**

Frameworks: **Vaadin**, **jooq**

Database: **SQLite** or **mySql**.

</ul>

Server Application that listens port (default 8080) and returns page with table when requested.
<br>Table has next columns: 'Name', 'Comment', 'Amount', 'Created', 'Updated'.
<br>Page contains also buttons `Create record` and `Download data`.

<ul>

`Create record` adds new record to a table.

`Download data` downloads all database records in .json format

</ul>

Table raws clickable as well and opens modal window with record edit options.

<br><br>
### Solution

Created database connection and prepared transactions.
<br>
Created simplest view, but it took time.
<br>
Next step is making service and DAO
<br>
Single page is http://127.0.0.1:8080/table-aphrodite
