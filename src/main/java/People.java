import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class Main {
    public static void main(String[] args) {
        People people = new People();
        Person person1 = new Person(4, "Ivan", "Smirnov", 10);
        people.save(person1);
        System.out.println(people.index().toString());

    }
}

public class People {
    private static final String URL = "jdbc:postgresql://localhost:5432/first_db"; //URL адрес базы данных //Эти данные по правильному должны храниться в файле Properties
    private static final String USERNAME = "postgres"; //Эти данные по правильному должны храниться в файле Properties
    private static final String PASSWORD = "XABP123890"; //Эти данные по правильному должны храниться в файле Properties
    private static Connection connection; //Соединение с помощью JDBC к базе данных

    static { //статический блок
        try {
            Class.forName("org.postgresql.Driver");//подгрузка класса с JDBC драйвером
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);//в connection помещаем то что вернет getConnection, т.е. подлючимся к БД
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //метод для считывания из БД
    public List<Person> index() {
        List<Person> people = new ArrayList<>();//сюда будем класть то что взяли из БД
        try {
            Statement statement = connection.createStatement();//statement - объект который содержит в себе SQL запрос к базе данных. На соединении connection создаем запрос к базе данных
            String SQL = "SELECT * FROM person1";//сам SQL запрос, который вернет все строки из БД
            ResultSet resultSet = statement.executeQuery(SQL);//на объекте statement выполняем SQL запрос (метод Query получает данные) и передаем их в объект класса ResultSet, который инкапсулирует результат запроса к базе данных

            while (resultSet.next()) { //проходимся по строкам и каждую строку переводим в java-объект
                Person person = new Person();
                person.setId(resultSet.getInt("id"));//из resultSet получаем значение колонки с названием id для текущей строки на которой мы находимся и это значение помещаем в person с помощью setId
                person.setName(resultSet.getString("Name"));
                person.setLastName(resultSet.getString("lastName"));
                person.setAge(resultSet.getInt("age"));
                people.add(person);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return people;
    }

    //метод для записи в БД
    public void save(Person person) {
        try {
            Statement statement = connection.createStatement();
            String SQL = "INSERT INTO person1 VALUES(" + person.getId() + ",'" + person.getName() + "'," + person.getLastName() + ",'" + person.getAge() + "')";//сам SQL запрос, который помещает строку в БД
            statement.executeUpdate(SQL);//метод Update передает данные
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
