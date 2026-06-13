/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistence;

import Question.MultiChoiceQuestion;
import Question.Question;
import Question.QuestionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hmarl
 */
public class DatabaseQuestions extends DatabaseDAO{
    
    QuestionFactory factory;
            
    public DatabaseQuestions(Connection connection) {
        super(connection);
        factory = new QuestionFactory();
        this.createTable();
    }
    
    public List<Question> getQuestionsFromIDs(String questionIDs) {

        List<Question> questions = new ArrayList<>();

        String[] ids = questionIDs.split(",");

        for (String id : ids) {
            Question question = getQuestionByID(Integer.parseInt(id.trim()));

            if (question != null) {
                questions.add(question);
            }
        }

        return questions;
    }
    
    public Question getQuestionByID(int id) {

        String sql =
            """
            SELECT *
            FROM QUESTIONS
            WHERE ID = ?
            """;

        try (
            PreparedStatement stmt =
                connection.prepareStatement(sql)
        ) {

            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                return factory.createMultiChoiceQuestion(
                    rs.getInt("ID"),
                    rs.getString("QUESTIONTEXT"),
                    rs.getString("ANSWER1"),
                    rs.getString("ANSWER2"),
                    rs.getString("ANSWER3"),
                    rs.getInt("CORRECTANSWER"),
                    rs.getString("EXPLANATION")
                );
            }
        }
        catch (SQLException ex) {
            System.out.println("Failed to load question");
        }

        return null;
    }
    
    @Override
    public void createTable() {

        //ID auto increments for each new record
        String sql =
            """
            CREATE TABLE QUESTIONS
            (
                ID INT NOT NULL GENERATED ALWAYS AS IDENTITY
                    (START WITH 1, INCREMENT BY 1) PRIMARY KEY,
                QUESTIONTEXT VARCHAR(1000),
                ANSWER1 VARCHAR(500),
                ANSWER2 VARCHAR(500),
                ANSWER3 VARCHAR(500),
                CORRECTANSWER INT,
                EXPLANATION VARCHAR(5000)
            )
            """;

        try (Statement statement = connection.createStatement()) {

            if (!checkTableExists("QUESTIONS")) {
                statement.executeUpdate(sql);
                System.out.println("QUESTIONS table created");
                //add insert questions into table
                this.addQuestions();
            }
            else {
                System.out.println("QUESTIONS table already exists");
            }
        }
        catch (SQLException ex) {
            System.out.println("Failed to create QUESTIONS table");
        }
    }
    
    public List<Question> getQuestions() {

        List<Question> questions = new ArrayList<>();

        String sql =
            """
            SELECT *
            FROM QUESTIONS
            ORDER BY ID
            """;

        try (
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql)
        ) {

            while (rs.next()) {

                Question question = factory.createMultiChoiceQuestion(
                    rs.getInt("ID"),
                    rs.getString("QUESTIONTEXT"),
                    rs.getString("ANSWER1"),
                    rs.getString("ANSWER2"),
                    rs.getString("ANSWER3"),
                    rs.getInt("CORRECTANSWER"),
                    rs.getString("EXPLANATION")
                );

                questions.add(question);
            }
        }
        catch (SQLException ex) {
            System.out.println("Failed to load questions");
        }

        return questions;
    }
    
    public void addQuestions() {

        String sql =
            """
            INSERT INTO QUESTIONS
            (
                QUESTIONTEXT,
                ANSWER1,
                ANSWER2,
                ANSWER3,
                CORRECTANSWER,
                EXPLANATION
            )
            VALUES (?, ?, ?, ?, ?, ?)
            """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            //args: prepared statement question text, answer 1, answer 2, 
            //      answer 3, answer (int), explanation
            //Week 1: Java Recap
            addQuestion( 
                stmt,
                "Student student1; \n" +
                "What is the term called for creating this object?",
                "Declare",
                "Define",
                "Initialise",
                1,
                "Declaring an object or variable does not assign a value to it yet. " +
                "It introduces this object to the program and the value is assigned " +
                "later."
            );
            
            addQuestion( 
                stmt,
                "What is encapsulation?",
                "Passing on code from a parent to child class for reusability",
                "Restricting access to fields in a class. Access the fields " +
                "through public methods",
                "An object being able to take on multiple forms, such as a parent " +
                "class being used to refer to a child class object",
                2,
                "One of the reasons encapsulation is used is for making sure data " +
                "does not get altered as easily, especially if it is important. " +
                "Example: a medicine dosage is accidentally changed from 10 to 100mg " +
                "in another part of the program."
            );
            
            addQuestion( 
                stmt,
                "When a field is set to default accessibility (no accessibility " +
                "defined), how can the field be accessed?",
                "Only methods in the same class can access the field",
                "Any method from any class can access the field",
                "Only methods in the same package can access the field",
                3,
                "Default accessibility is similar to protected accessibility, except " +
                "that protected accepts subclasses, even if they are in different " +
                "packages. Default is limited to classes and methods the same package " +
                "only"
            );
            
            //Week 2: Java collections, File IO
            addQuestion( 
                stmt,
                "Which collection interface has no order and no duplicate elements?",
                "List",
                "Map",
                "Set",
                3,
                "Lists allow duplicate elements and indexing (order). Sets are good " +
                "for storing unique elements and when indexing does not matter. Maps " +
                "link keys to values like a dictionary, but there cannot be duplicate " +
                "keys."
            );
            
            addQuestion( 
                stmt,
                "animals.put('cat', 'meow');\n" +
                "Which collection interface works with this command?",
                "Set",
                "Map",
                "List",
                2,
                "To insert an element to a collection, sets and lists use add() while " +
                "maps use put(). The parameters for put() are (key, value)."
            );
            
            addQuestion( 
                stmt,
                "myList.get(3);\n" +
                "What does this command do?",
                "Get the element at index 3 in the list",
                "Get the element 3 in the list",
                "Get the first 3 elements in the list",
                1,
                "The parameter for get() is index of integer type (index: integer). " +
                "It retrieves the element at that index."
            );
            
            addQuestion( 
                stmt,
                "Which command is the proper way to create a generic ArrayList " +
                "collection?",
                "ArrayList<Integer> list = new ArrayList<>();",
                "ArrayList list<Integer> = new ArrayList();",
                "ArrayList list = new ArrayList();",
                1,
                "A generic collection requires specifying an Object data type " +
                "(reference type). In this case, it is Integer, which means that the " +
                "ArrayList can only hold Integers. The full command for creating the " +
                "ArrayList is actually: " +
                "ArrayList<Integer> list = new ArrayList<Integer>();" +
                "However, Java developers are lazy, so the second diamond operator " +
                "(<>) can be left empty. The program already knows what the data type " +
                "will be based on the assignment on the left side. This is known as " +
                "type inference."
            );
            
            addQuestion( 
                stmt,
                "What is buffering in File Input/Output (File I/O)?",
                "Each byte of information is read from disk or written to disk as " +
                "soon as possible.",
                "Each byte is stored until the storage that holds these bytes is " +
                "full, then it is read from or written to disk.",
                "Each byte is read from disk or written to disk after a 10 " +
                "milisecond delay.",
                2,
                "A buffer is a block of RAM that stores these bytes. It causes " +
                "reading and writing in batches. Buffering is used to reduce the " +
                "overhead (resources, processing power, and time required) of the " +
                "disk operation. Buffering is more efficient than no buffering, the " +
                "first option in this question."
            );
            
            addQuestion( 
                stmt,
                "pw = new PrintWriter(new FileOutputStream('out.txt', true));\n " +
                "What happens to the file when pw is used to write to it?",
                "pw appends text to the beginning of out.txt",
                "pw overwrites the old text in out.txt",
                "pw appends text to the end of out.txt",
                3,
                "'true' is an optional argument used to enable appending. Without it, " +
                "pw will overwrite the existing text in out.txt. Appending occurs at " +
                "the end of the existing text."
            );
            
            addQuestion( 
                stmt,
                "read() \n" +
                "What does this method do for a BufferedReader?",
                "Read file line-by-line",
                "Read file character-by-character ",
                "Read file character-by-character through their ASCII code",
                3,
                "readLine() is used to read text line-by-line into a String. read() " +
                "is used to read each character into its ASCII code (integer). It " +
                "returns -1 if it has reached the end of the file."
            );
            
            //Week 3: modern programming languages (AI prompting), Apache projects
            addQuestion( 
                stmt,
                "Autonomous AI that sets its own goals and decisions with little to " +
                "no guidance. \n" + 
                "Which term suits this definition?",
                "Generative AI",
                "Agentic AI",
                "AI agent",
                2,
                "An Agentic AI is an advanced system that can identify opportunities" +
                "in its environment without much prompting. It is good at adapting to" +
                "new situations and is equipped with appropriate tools to help " +
                "accomplish tasks. It is sometimes used interchangably with AI agent, " +
                "but AI agents make up the agentic AI system. AI agents have less" +
                "autonomy and are designed for less complex tasks."
            );

            // Question 12
            addQuestion(
                stmt,
                "In structured prompting's essential elements, what does System " +
                "Context refer to?",
                "Broadening the AI's context by describing what the output is being " +
                "applied towards in real life",
                "Giving context on what programming language and framework the AI " +
                "should use",
                "Describing restrictions and limitations for the AI's output",
                1,
                "System Context is one of the elements used to make the AI's output " +
                "cater more towards your situation, rather than a generic answer. An " +
                "example of system context is informing the AI that they are writing " +
                "code to make a university enrollment system."
            );

            // Question 13
            addQuestion(
                stmt,
                "What type of prompting involves providing at least one example to " +
                "guide the AI on what the output should look like?",
                "Few-shot prompting",
                "Problem decomposition",
                "Zero-shot prompting",
                1,
                "Few-shot prompting is used when AI is expected to follow a strict " +
                "format, including a consistent coding style. It is often used for " +
                "large projects."
            );

            // Question 14
            addQuestion(
                stmt,
                "Which Apache Project is used to manage dependencies easier?",
                "Tika",
                "Maven",
                "Ant",
                2,
                "Apache Maven Project was created to resolve the difficulty of " +
                "managing dependencies in Java projects. Dependencies are external " +
                "libraries and frameworks, and they can have their own dependencies."
            );

            // Question 15
            addQuestion(
                stmt,
                "Which Apache project is best for using Tika?",
                "Tika",
                "Ant",
                "Maven",
                3,
                "There is no Tika project because it is a toolkit. Tika can be used " +
                "through an Ant or Maven project, but it should be used through Maven " +
                "because it has lots of dependencies."
            );

            // Week 4: OOAD, SOLID Design

            // Question 16
            addQuestion(
                stmt,
                "Which of these diagrams is a structure diagram?",
                "Activity diagram",
                "Use case diagram",
                "Class diagram",
                3,
                "Structure diagrams describe static aspects of the system (parts of " +
                "the system that do not change during runtime) and its software " +
                "architecture. Class diagram is a structure diagram. The other " +
                "diagrams mentioned are behaviour diagrams."
            );

            // Question 17
            addQuestion(
                stmt,
                "In PlantUML, for class diagrams, what does this arrow represent?\n" +
                "<|..",
                "Association",
                "Inheritance",
                "Implementation",
                3,
                "As defined by UML rules, implementation (also known as realisation) " +
                "is represented by a hollow arrowhead with broken line. Use this " +
                "arrow between an interface or abstract class and their subclasses."
            );

            // Question 18
            addQuestion(
                stmt,
                "In PlantUML, for use case diagrams, what does this arrow represent?\n" +
                "-->",
                "Include",
                "Association",
                "Exclude",
                2,
                "In PlantUML, association is represented by a solid arrow. The number " +
                "of hyphens in the arrow increases the arrow's length in the diagram. " +
                "Use this arrow to show communication between an actor and use case."
            );

            // Question 19
            addQuestion(
                stmt,
                "In SOLID design principles, what does the I stand for?",
                "Interface Segregation Principle",
                "Inheritance Separation Principle",
                "Independent Segregation Principle",
                1,
                "For good design of code, one of the principles to follow is " +
                "Interface Segregation Principle (ISP). It states that 'A client " +
                "should never be forced to implement an interface that it doesn't " +
                "use or clients shouldn't be forced to depend on methods they don't " +
                "use'."
            );

            // Question 20
            addQuestion(
                stmt,
                "In SOLID design, which principle is this?\n" +
                "'Subclasses should behave nicely when used in place of their base class'",
                "Liskov Substitution Principle",
                "Dependency Inversion Principle",
                "Open/Closed Principle",
                1,
                "Liskov Substitution Principle (LSP) requires that objects in " +
                "subclasses can replace their parent class without breaking " +
                "the program."
            );
            
            addQuestion(
                stmt,
                "In SOLID design principles, what does the S stand for?",
                "Simple Responsibility Principle",
                "Single Responsibility Principle",
                "Single Substitution Principle",
                2,
                "Single Responsibility Principle (SRP) states that 'A class should " +
                "have only one reason to change'. It means that each class should " +
                "not have to juggle multiple responsibilities because it could lead " +
                "to messier code."
            );

            addQuestion(
                stmt,
                "What is one of the libraries used for multi-threading?",
                "java.util.Thread",
                "java.io.Thread",
                "java.lang.Thread",
                3,
                "Threads are the smallest unit of execution in a process (actively " +
                "running program). Multi-threading is when a process runs multiple " +
                "threads simultaneously. java.lang.Thread and java.lang.Runnable do " +
                "not have to be manually imported because java.lang library is " +
                "automatically included in every Java file."
            );

            addQuestion(
                stmt,
                "Which one of these methods is not part of the Thread library?",
                "start()",
                "yield()",
                "stop()",
                3,
                "stop() does not exist in the Thread library. A thread stops when it " +
                "finishes running its code or gets blocked (paused). start() is used " +
                "to run a thread. yield() is used to block a thread to let other " +
                "threads execute. The thread scheduler will decide when the yielded " +
                "thread runs again."
            );

            addQuestion(
                stmt,
                "What is the synchronized keyword used for with threads?",
                "Allow multiple threads to access a class or method at the same time.",
                "Block other threads from accessing a class or method while one " +
                "thread is accessing it.",
                "Make multiple threads execute directly after each other in a sequence.",
                2,
                "Multiple threads can already access the same class or method. That " +
                "is why synchronized keyword (synchronisation) is used to block other " +
                "threads out so that race conditions do not occur. Race conditions " +
                "occur when multiple threads try to update information at the same " +
                "time, causing unexpected program results."
            );

            //Week 6: git
            addQuestion(
                stmt,
                "What is a 'branch' in Git?",
                "A synonym for another contributor's channel in your project",
                "A separate workspace for you to make changes to master code",
                "The location of the current folder is called the 'root' files. " +
                "Within the folder are called 'branches'",
                2,
                "Branches allow you to edit and test various parts of your code, such " +
                "as new features, or bug fixes, without affecting the main code which " +
                "is in a working state. This is helpful because you have freedom to " +
                "experiment; a key part of programming."
            );

            addQuestion(
                stmt,
                "What does the command <git status> do in GitCMD?",
                "Check the branch you are currently using, along with if you need " +
                "to commit anything, and your working tree",
                "Check the log of commits so far, lists each commit's description " +
                "and who did it",
                "Check the status of other contributors for your project",
                1,
                "<git status> gives an overall idea of your current workspace, which " +
                "is your branch, pending commits, and the state of your working tree."
            );

            addQuestion(
                stmt,
                "Which command is used to create a new folder/directory in GitCMD?",
                "cd",
                "dir/a",
                "md",
                3,
                "Md, or 'Make Directory' is the command to make a new directory in " +
                "Git CMD (Git Command Prompt)."
            );

            //Week 7: GUI
            addQuestion(
                stmt,
                "What is an event object in a GUI?",
                "An object that represents a user action",
                "An object that listens for an event triggered by the user",
                "An object that starts an event for the user to interact with",
                1,
                "When a user interacts with the GUI, such as clicking a button, Java " +
                "creates an event object, which stores data about the action, such as " +
                "the action source and time that the action occurred. A listener " +
                "object is what listens for and responds to events. It is inactive " +
                "until an event object gets sent to it."
            );

            addQuestion(
                stmt,
                "What method of adding ActionListener is this?\n\n" +
                "myButton.addActionListener(new ActionListener() {\n" +
                "    public void actionPerformed(ActionEvent e) {\n" +
                "        ...\n" +
                "    }\n" +
                "});",
                "Inner class listener",
                "Anonymous inner listener",
                "ActionListener implementing JFrame",
                2,
                "In a Graphical User Interface (GUI), an anonymous inner listener " +
                "directly uses the actionPerformed method when adding the " +
                "ActionListener to a component. There is no need to make a separate " +
                "class and refer to a class name."
            );

            //Question 30
            addQuestion(
                stmt,
                "Which kind of GUI layout involves arranging components in " +
                "fixed-length rows and columns?",
                "GridBagLayout",
                "CardLayout",
                "GridLayout",
                3,
                "GridLayout allows components to be arranged in fixed-length rows " +
                "and columns. GridBagLayout is similar to this, except that it allows " +
                "components to be different lengths."
            );
            
            addQuestion(
                stmt,
                "Which GUI component is used to let users select multiple options?",
                "Combo boxes",
                "Radio Buttons",
                "Checkboxes",
                3,
                "Checkboxes allow multiple selections while Combo boxes and Radio " +
                "buttons allow one. Make a Checkbox using this command:\n" +
                "JCheckBox checkbox = new JCheckBox('Option text');"
            );

            //No Week 8 materials
        
            //Week 9: JDBC
            addQuestion(
                stmt,
                "What does SQL stand for?",
                "Standard Query Language",
                "Structured Query Language",
                "Statement Query Language",
                2,
                "In databases, Structured Query Language (SQL) is used to create " +
                "table structures, insert, modify, and delete data from tables, and " +
                "retrieve data through queries."
            );

            addQuestion(
                stmt,
                "What is one thing that JDBC does not standardise?",
                "SQL syntax",
                "Data structure of query results",
                "Ways to connect to a database",
                1,
                "Java Database Connectivity (JDBC) is an Application Programming " +
                "Interface (API) that connects programmers to databases. It does not " +
                "standardise SQL syntax because each Database Management System " +
                "(DBMS) has its own syntax, like different dialects. Examples of " +
                "DBMS are MySQL, Apache Derby, and Oracle."
            );

            addQuestion(
                stmt,
                "What parameter is omitted when connecting to an embedded database?",
                "Database network address",
                "Database name",
                "Driver type",
                1,
                "Since the database is embedded, it is directly integrated into the " +
                "Java project folder. There is no need to provide the network " +
                "address, so the URL for connection will look like this:\n" +
                "String url = 'jdbc:derby:DBname;create=true';"
            );

            addQuestion(
                stmt,
                "In databases, when calling next() on a ResultSet object for the " +
                "first time, where is the cursor placed?",
                "Second row in table",
                "First row in table",
                "Second to last row in table",
                2,
                "ResultSet acts as an iterator (cursor) that goes through a table " +
                "row-by-row. It goes forward by default (top to bottom). Initially, " +
                "the cursor is placed before the first row. Call next() before " +
                "trying to access the first row's data."
            );

            //Week 10: Design patterns
            addQuestion(
                stmt,
                "At what level of development do idiom patterns occur?",
                "System Level",
                "Subsystem Level",
                "Code level",
                3,
                "Idioms in programming occur at code level. Idioms are a common or " +
                "popular way to express a task. Example: using an iterator instead " +
                "of a for loop to go through elements in a collection."
            );

            addQuestion(
                stmt,
                "What type of design pattern is the Factory pattern?",
                "Structural",
                "Behavioural",
                "Creational",
                3,
                "The Factory pattern defines creating an interface with concrete " +
                "subclasses, which allows the right subclass object to be returned " +
                "based on a user's input. Example: returning bananas out of many " +
                "fruit objects. This comes under the Creational design pattern, " +
                "which involves common ways to create objects and instantiate " +
                "classes."
            );

            addQuestion(
                stmt,
                "Which pattern has one class that handles interactions between other " +
                "classes?",
                "Singleton",
                "Mediator",
                "Adapter",
                2,
                "The Mediator pattern is used to reduce coupling (over-reliance) of " +
                "classes. Instead of many classes interacting with each other (which " +
                "gets hard to manage), they communicate through the mediator. The " +
                "classes can focus on their responsibilities without worrying about " +
                "the responsibilities of others (separation of concerns)."
            );

            addQuestion(
                stmt,
                "In the Model View Controller (MVC) pattern, which component " +
                "manipulates or updates the Model?",
                "Controller",
                "View",
                "Model",
                1,
                "View displays visual elements to the user in a GUI. When the View " +
                "receives a user action, such as clicking a button, it generates an " +
                "event that triggers the corresponding method in the Controller. The " +
                "Controller updates the Model, which holds the business logic. " +
                "Business logic is rules that control how data is accessed and " +
                "updated."
            );

            //Week 11: validation and verification, JUnit framework
            //Question 40
            addQuestion(
                stmt,
                "A human action that causes an incorrect result is known as...",
                "Mistake",
                "Fault",
                "Failure",
                1,
                "Faults are an incorrect step in a computer program. Failures are " +
                "incorrect results or outputs. Since computers do not make mistakes, " +
                "human error leads to faults then failures."
            );
            
            addQuestion(
                stmt,
                "In High Level Testing, which kind of testing checks the stability " +
                "of a program and how well it handles unexpected values?",
                "Usability testing",
                "Stress testing",
                "System Function testing",
                2,
                "Stress testing gives the program extreme values, often to breaking " +
                "points to study the outcome. Usability testing involves getting " +
                "users to try the program to observe ease of use. System Function " +
                "testing checks the overall program and sees if the program matches " +
                "its functional specifications."
            );

            addQuestion(
                stmt,
                "Regarding the Unit Testing Framework, which is false about a unit?",
                "A unit is the smallest testable component in a program",
                "A unit is usually a method",
                "A unit depends on other components",
                3,
                "Units should not depend on other components (other classes, methods, " +
                "database, etc.) for the sake of testing the unit in isolation. The " +
                "point of unit testing is to ensure that a particular unit component " +
                "is passing test cases and operating as expected."
            );

            addQuestion(
                stmt,
                "What is the verdict of this JUnit test case? The value calculated by " +
                "Multiply class is 15.0\n\n" +
                "@Test\n" +
                "public void test() {\n" +
                "  Multiply m = new Multiply();\n" +
                "  m.calculate(2.3, 6.54);\n" +
                "  double actual = m.getResult();\n" +
                "  double expected = 15.042;\n" +
                "  Assert.assertEquals(actual, expected, 0.01);\n" +
                "}",
                "Pass",
                "Error",
                "Fail",
                3,
                "The delta value (0.01) is the acceptable range for rounding in a " +
                "float or double. The test case results in a fail because the " +
                "difference between the expected and actual value is outside delta's " +
                "range (15.032 to 15.052). Check if the actual value falls within " +
                "delta's range using Math.abs(expected - actual) <= delta. " +
                "|15.042 - 15.0| = 0.042. Since 0.042 > 0.01, the test fails."
            );

            //Week 12: Code smells
            addQuestion(
                stmt,
                "What is Bad Code Smells?",
                "Code that contains bugs or errors which is considered smelly",
                "Signs of bad design or implementation choices",
                "Bad code that is hard to read",
                2,
                "Smelly code does not cause the program to crash, but it can reduce " +
                "readability and maintainability of the code. It is called 'Bad Code " +
                "Smells' because a surface stench signals a deeper problem, like a " +
                "stinky house from leaking pipes. Smelly code hints that there may " +
                "be design choices that lead to technical debt."
            );

            addQuestion(
                stmt,
                "What is the correct definition for Bloaters in Bad Code Smells?",
                "Classes and methods that have grown too large",
                "Classes and methods with high coupling",
                "Classes and methods that use Object-Oriented principles incorrectly",
                1,
                "Bloaters are large classes and methods. They are harder to understand " +
                "and maintain due to their length, and they typically accumulate over " +
                "time when no effort is made to refactor them. Methods longer than " +
                "10 lines of code might be at risk of becoming a bloater."
            );

            addQuestion(
                stmt,
                "Which kind of code smell includes Shotgun Surgery?",
                "Change Preventers",
                "Bloaters",
                "Object-Orientation Abusers",
                1,
                "Change Preventers occur when classes and methods have high coupling " +
                "(high dependency) with each other. Changing one part of the code can " +
                "lead to changing many other parts. Shotgun Surgery is specifically " +
                "when a modification requires changing multiple classes."
            );

            addQuestion(
                stmt,
                "What does it mean when a class has low cohesion?",
                "The class depends less on other classes or methods",
                "The class juggles too many different tasks",
                "The class is focused on its task",
                2,
                "Low cohesion is when a class is unfocused on its actual job due to " +
                "handling a variety of tasks. The aim for a program is to have high " +
                "cohesion, where the class is focused on its job. High cohesion " +
                "follows Single Responsibility Principle (SRP)."
            );     
 
            stmt.executeBatch();

            System.out.println("Questions inserted");
        }
        catch (SQLException ex) {
            System.out.println("Failed to insert questions");
            }   
    }
    
    //helper method to add individual questions
    private void addQuestion(
    PreparedStatement stmt, String questionText, String answer1, String answer2,
    String answer3, int correctAnswer, String explanation) {
        //Prepared statement: column position, value
        try {
            stmt.setString(1, questionText);
            stmt.setString(2, answer1);
            stmt.setString(3, answer2);
            stmt.setString(4, answer3);
            stmt.setInt(5, correctAnswer);
            stmt.setString(6, explanation);
            stmt.addBatch();
            
        } catch (SQLException ex) {
            System.out.println("Failed to add question");
        }
    }
    
}
