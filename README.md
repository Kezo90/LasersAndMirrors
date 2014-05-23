Installation:
======================================================
 * Require Apache Maven 3.0+

 * (Optional) MySQL database:

   * Version 5.5 recommended.

   * Execute SQL queries contained in <project directory>/sql/mysql_*.sql files.

   * Set DB.MYSQL value to true in the source.


Run:
======================================================
 * From terminal/command line (choose one):

   * mvn process-classes exec:exec

   * mvn package && java -jar ./target/lasers-and-mirrors-0.1.jar

     * Not works with packaged SQLite database.


Control:
======================================================
 * Select an object: click on it.

 * Rotate selected object: click outer of the object and turn around the mouse pointer while holding down the mouse button.

 * Drag selected object: click on it again and drag it.

 * Keybindings:

    * Escape: go back to parent menu.
