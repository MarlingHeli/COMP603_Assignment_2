/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Question;

/**
 *
 * @author hmarl
 */
import java.util.*;

public class QuestionPool {
    //hashMap to keep track of question index and question object
    private Map<Integer, Question> questionPool;
//    private Random random = new Random();

    public QuestionPool() {
        questionPool = new HashMap<>();
        loadQuestions();
    }

    //return question pool if needed
    public Map<Integer, Question> getQuestionPool() {
        return questionPool;
    }

    // get random questions based on numQuestions size
    public List<Question> getRandomQuestions(int numQuestions) {
    List<Question> allQuestions = new ArrayList<>(questionPool.values());
    //randomise question order
    Collections.shuffle(allQuestions);
    //get list of first numQuestions of questions
    return allQuestions.subList(0, numQuestions);
    }

    public Question getQuestionByID(int id) {
        return questionPool.get(id);
    }
    
    //method to insert questions into questionPool map
    //note: question attribute format (questionText, explanation, answer, id)
    
    //Week 1: Java Recap
    private void loadQuestions() {
        questionPool.put(1, new MultiChoiceQuestion(
            """
            Student student1;
            What is the term called for creating this object?
            1) Declare
            2) Define
            3) Initialise
            """,
            """
            Declaring an object or variable does not assign a value to it yet. 
            It introduces this object to the program and the value is assigned 
            later.
            """,
            1, 1));

        questionPool.put(2, new MultiChoiceQuestion(
            """
            What is encapsulation?
            1) Passing on code from a parent to child class for reusability
            2) Restricting access to fields in a class. Access the fields 
            through public methods
            3) An object being able to take on multiple forms, such as a parent 
            class being used to refer to a child class object
            """,
            """
            One of the reasons encapsulation is used is for making sure data 
            does not get altered as easily, especially if it is important. 
            Example: a medicine dosage is accidentally changed from 10 to 100mg 
            in another part of the program.
            """,
            2, 2));

        questionPool.put(3, new MultiChoiceQuestion(
            """
            When a field is set to default accessibility (no accessibility 
            defined), how can the field be accessed?
            1) Only methods in the same class can access the field
            2) Any method from any class can access the field
            3) Only methods in the same package can access the field
            """,
            """
            Default accessibility is similar to protected accessibility, except 
            that protected accepts subclasses, even if they are in different 
            packages. Default is limited to classes and methods the same package
            only.
            """,
            3, 3));

        //Week 2: Java collections, File IO
        questionPool.put(4, new MultiChoiceQuestion(
            """
            Which collection interface has no order and no duplicate elements?
            1) List
            2) Map
            3) Set
            """,
            """
            Lists allow duplicate elements and indexing (order). Sets are good
            for storing unique elements and when indexing does not matter. Maps
            link keys to values like a dictionary, but there cannot be duplicate
            keys.
            """,
            3, 4));

        questionPool.put(5, new MultiChoiceQuestion(
            """
            animals.put('cat', 'meow');
            Which collection interface works with this command?
            1) Set
            2) Map
            3) List
            """,
            """
            To insert an element to a collection, sets and lists use add() while
            maps use put(). The parameters for put() are (key, value).
            """,
            2, 5));

        questionPool.put(6, new MultiChoiceQuestion(
            """
            myList.get(3);
            What does this command do?
            1) Get the element at index 3 in the list
            2) Get the element 3 in the list
            3) Get the first 3 elements in the list
            """,
            """
            The parameter for get() is index of integer type (index: integer). 
            It retrieves the element at that index.
            """,
            1, 6));

        questionPool.put(7, new MultiChoiceQuestion(
            """
            Which command is the proper way to create a generic ArrayList 
            collection?
            1) ArrayList<Integer> list = new ArrayList<>();
            2) ArrayList list<Integer> = new ArrayList();
            3) ArrayList list = new ArrayList();
            """,
            """
            A generic collection requires specifying an Object data type 
            (reference type). In this case, it is Integer, which means that the
            ArrayList can only hold Integers. The full command for creating the
            ArrayList is actually: 
            ArrayList<Integer> list = new ArrayList<Integer>();
            However, Java developers are lazy, so the second diamond operator 
            (<>) can be left empty. The program already knows what the data type
            will be based on the assignment on the left side. This is known as 
            type inference.
            """,
            1, 7));

        questionPool.put(8, new MultiChoiceQuestion(
            """
            What is buffering in File Input/Output (File I/O)?
            1) Each byte of information is read from disk or written to disk as 
            soon as possible.
            2) Each byte is stored until the storage that holds these bytes is 
            full, then it is read from or written to disk.
            3) Each byte is read from disk or written to disk after a 10 
            milisecond delay.
            """,
            """
            A buffer is a block of RAM that stores these bytes. It causes 
            reading and writing in batches. Buffering is used to reduce the 
            overhead (resources, processing power, and time required) of the 
            disk operation. Buffering is more efficient than no buffering, the 
            first option in this question.
            """,
            2, 8));

        questionPool.put(9, new MultiChoiceQuestion(
            """
            pw = new PrintWriter(new FileOutputStream('out.txt', true));
            What happens to the file when pw is used to write to it?
            1) pw appends text to the beginning of out.txt
            2) pw overwrites the old text in out.txt
            3) pw appends text to the end of out.txt
            """,
            """
            'true' is an optional argument used to enable appending. Without it,
            pw will overwrite the existing text in out.txt. Appending occurs at
            the end of the existing text.
            """,
            3, 9));
        
        questionPool.put(10, new MultiChoiceQuestion(
            """
            read()
            What does this method do for a BufferedReader?
            1) Read file line-by-line
            2) Read file character-by-character 
            3) Read file character-by-character through their ASCII code
            """,
            """
            readLine() is used to read text line-by-line into a String. read() 
            is used to read each character into its ASCII code (integer). It 
            returns -1 if it has reached the end of the file.
            """,
            3, 10));
                
        //Week 3: modern programming languages (AI prompting), Apache projects
        questionPool.put(11, new MultiChoiceQuestion(
            """
            Autonomous AI that sets its own goals and decisions with little to 
            no guidance.
            Which term suits this definition?
            1) Generative AI
            2) Agentic AI
            3) AI agent
            """,
            """
            An Agentic AI is an advanced system that can identify opportunities
            in its environment without much prompting. It is good at adapting to
            new situations and is equipped with appropriate tools to help 
            accomplish tasks. It is sometimes used interchangably with AI agent,
            but AI agents make up the agentic AI system. AI agents have less
            autonomy and are designed for less complex tasks. 
            """,
            2, 11));
        
        questionPool.put(12, new MultiChoiceQuestion(
            """
            In structured prompting's essential elements, what does System 
            Context refer to?
            1) Broadening the AI's context by describing what the output is 
            being applied towards in real life
            2) Giving context on what programming language and framework the AI
            should use
            3) Describing restrictions and limitations for the AI's output
            """,
            """
            System Context is one of the elements used to make the AI's output
            cater more towards your situation, rather than a generic answer. An
            example of system context is informing the AI that they are writing
            code to make a university enrollment system. 
            """,
            1, 12));

        questionPool.put(13, new MultiChoiceQuestion(
            """
            What type of prompting involves providing at least one example to 
            guide the AI on what the output should look like?
            1) Few-shot prompting
            2) Problem decomposition
            3) Zero-shot prompting
            """,
            """
            Few-shot prompting is used when AI is expected to follow a strict 
            format, including a consistent coding style. It is often used for 
            large projects.
            """,
            1, 13));

        questionPool.put(14, new MultiChoiceQuestion(
            """
            Which Apache Project is used to manage dependencies easier?
            1) Tika
            2) Maven
            3) Ant
            """,
            """
            Apache Maven Project was created to resolve the difficulty of 
            managing dependencies in Java projects. Dependencies are external
            libraries and frameworks, and they can have their own dependencies.
            """,
            2, 14));

        questionPool.put(15, new MultiChoiceQuestion(
            """
            Which Apache project is best for using Tika?
            1) Tika
            2) Ant
            3) Maven
            """,
            """
            There is no Tika project because it is a toolkit. Tika can be used
            through an Ant or Maven project, but it should be used through Maven
            because it has lots of dependencies.
            """,
            3, 15));

        //Week 4: OOAD, SOLID design
        questionPool.put(16, new MultiChoiceQuestion(
            """
            Which of these diagrams is a structure diagram?
            1) Activity diagram
            2) Use case diagram
            3) Class diagram
            """,
            """
            Structure diagrams describe static aspects of the system (parts of
            the system that do not change during runtime) and its software
            architecture. Class diagram is a structure diagram. The other
            diagrams mentioned are behaviour diagrams.
            """,
            3, 16));
        
        questionPool.put(17, new MultiChoiceQuestion(
            """
            In PlantUML, for class diagrams, what does this arrow represent?
            <|..
            1) Association
            2) Inheritance
            3) Implementation
            """,
            """
            As defined by UML rules, implementation (also known as realisation)
            is represented by a hollow arrowhead with broken line. Use this
            arrow between an interface or abstract class and their subclasses.
            """,
            3, 17));
       
        questionPool.put(18, new MultiChoiceQuestion(
            """
            In PlantUML, for use case diagrams, what does this arrow represent?
            -->
            1) Include
            2) Association
            3) Exclude
            """,
            """
            In PlantUML, association is represented by a solid arrow. The number
            of hyphens in the arrow increases the arrow's length in the diagram.
            Use this arrow to show communication between an actor and usecase.
            """,
            2, 18));
        
        questionPool.put(19, new MultiChoiceQuestion(
            """
            In SOLID design principles, what does the I stand for?
            1) Interface Segregation Principle
            2) Inheritance Separation Principle
            3) Independent Segregation Principle
            """,
            """
            For good design of code, one of the principles to follow is 
            Interface Segregation Principle (ISP). It states that 'A client
            should never be forced to implement an interface that it doesn't
            use or clients shouldn't be forced to depend on methods they don't
            use'.
            """,
            1, 19));
       
        questionPool.put(20, new MultiChoiceQuestion(
            """
            In SOLID design, which principle is this?
            'Subclasses should behave nicely when used in place of their base
            class'
            1) Liskov Substitution Principle
            2) Dependency Inversion Principle
            3) Open/Closed Principle
            """,
            """
            Liskov Substitution Principle (LSP) requires that objects in
            subclasses can replace their parent class without breaking
            the program.
            """,
            1, 20));
        
        questionPool.put(21, new MultiChoiceQuestion(
            """
            In SOLID design principles, what does the S stand for?
            1) Simple Responsibility Principle
            2) Single Responsibility Principle
            3) Single Substitution Principle
            """,
            """
            Single Responsibility Principle (SRP) states that 'A class should
            have only one reason to change'. It means that each class should
            not have to juggle multiple responsibilities because it could lead
            to messier code.
            """,
            2, 21));
       
        //Week 5: multi-threading
        questionPool.put(22, new MultiChoiceQuestion(
            """
            What is one of the libraries used for multi-threading?
            1) java.util.Thread
            2) java.io.Thread
            3) java.lang.Thread
            """,
            """
            Threads are the smallest unit of execution in a process (actively
            running program). Multi-threading is when a process runs multiple
            threads simultaneously. java.lang.Thread and java.lang.Runnable do
            not have to be manually imported because java.lang library is 
            automatically included in every Java file.
            """,
            3, 22));
        
        questionPool.put(23, new MultiChoiceQuestion(
            """
            Which one of these methods is not part of the Thread library?
            1) start()
            2) yield()
            3) stop()
            """,
            """
            stop() does not exist in the Thread library. A thread stops when it
            finishes running its code or gets blocked (paused). start() is used
            to run a thread. yield() is used to block a thread to let other
            threads execute. The thread scheduler will decide when the yielded
            thread runs again.
            """,
            3, 23));
        
        questionPool.put(24, new MultiChoiceQuestion(
            """
            What is the synchronized keyword used for with threads?
            1) Allow multiple threads to access a class or method at the same
            time.
            2) Block other threads from accessing a class or method while one
            thread is accessing it.
            3) Make multiple threads execute directly after each other in a
            sequence.
            """,
            """
            Multiple threads can already access the same class or method. That
            is why synchronized keyword (synchronisation) is used to block other
            threads out so that race conditions do not occur. Race conditions
            occur when multiple threads try to update information at the same
            time, causing unexpected program results.
            """,
            2, 24));
        
        //Week 6: git
        questionPool.put(25, new MultiChoiceQuestion(
            """
            What is a 'branch' in Git?
            1) A synonym for another contributor's channel in your project
            2) A separate workspace for you to make changes to master code
            3) The location of the current folder is called the 'root' files.
            Within the folder are called 'branches'
            """,
            """
            Branches allow you to edit and test various parts of your code, such
            as new features, or bug fixes, without affecting the main code which
            is in a working state. This is helpful because you have freedom to
            experiment; a key part of programming.
            """,
            2, 25));
        
        questionPool.put(26, new MultiChoiceQuestion(
            """
            What does the command <git status> do in GitCMD?
            1) Check the branch you are currently using, along with if you need
            to commit anything, and your working tree
            2) Check the log of commits so far, lists each commit's description
            and who did it
            3) Check the status of other contributors for your project
            """,
            """
            <git status> gives an overall idea of your current workspace, which
            is your branch, pending commits, and the state of your working tree. 
            """,
            1, 26));
        
        questionPool.put(27, new MultiChoiceQuestion(
            """
            Which command is used to create a new folder/directory in GitCMD?
            1) cd
            2) dir/a
            3) md
            """,
            """
            Md, or 'Make Directory' is the command to make a new directory in
            Git CMD (Git Command Prompt)
            """,
            3, 27));
    
        //Week 7: GUI
        questionPool.put(28, new MultiChoiceQuestion(
            """
            What is an event object in a GUI?
            1) An object that represents a user action
            2) An object that listens for an event triggered by the user
            3) An object that starts an event for the user to interact with 
            """,
            """
            When a user interacts with the GUI, such as clicking a button, Java 
            creates an event object, which stores data about the action, such as
            the action source and time that the action occurred. A listener 
            object is what listens for and responds to events. It is inactive 
            until an event object gets sent to it.
            """,
            1, 28));
        
        questionPool.put(29, new MultiChoiceQuestion(
            """
            What method of adding ActionListener is this?
            
            myButton.addActionListener( new ActionListener() { 
              public void actionPerformed (ActionEvent e) {
                …
               }
            }
            1) Inner class listener
            2) Anonymous inner listener
            3) ActionLister implementing JFrame
            """,
            """
            In a Graphical User Interface (GUI), an anonymous inner listener 
            directly uses the actionPerformed method when adding the 
            ActionListener to a component. There is no need to make a separate 
            class and refer to a class name.
            """,
            2, 29));
        
        questionPool.put(30, new MultiChoiceQuestion(
            """
            Which kind of GUI layout involves arranging components in 
            fixed-length rows and columns?
            1) GridBagLayout
            2) CardLayout
            3) GridLayout
            """,
            """
            GridLayout allows components to be arranged in fixed-length rows 
            and columns. GridBagLayout is similar to this, except that it allows
            components to be different lengths.
            """,
            3, 30));
                
        questionPool.put(31, new MultiChoiceQuestion(
            """
            Which GUI component is used to let users select multiple options?
            1) Combo boxes
            2) Radio Buttons
            3) Checkboxes
            """,
            """
            Checkboxes allow multiple selections while Combo boxes and Radio 
            buttons allow one. Make a Checkbox using this command:
            JCheckBox checkbox = new JCheckBox('Option text');
            """,
            3, 31));
                 
        //No Week 8 materials
        
        //Week 9: JDBC
        questionPool.put(32, new MultiChoiceQuestion(
            """
            What does SQL stand for?
            1) Standard Query Language
            2) Structured Query Language
            3) Statement Query Language
            """,
            """
            In databases, Structured Query Language (SQL) is used to create 
            table structures, insert, modify, and delete data from tables, and 
            retrieve data through queries.
            """,
            2, 32));
                                
        questionPool.put(33, new MultiChoiceQuestion(
            """
            What is one thing that JDBC does not standardise?
            1) SQL syntax
            2) Data structure of query results
            3) Ways to connect to a database
            """,
            """
            Java Database Connectivity (JDBC) is an Application Programming 
            Interface (API) that connects programmers to databases. It does not 
            standardise SQL syntax because each Database Management System 
            (DBMS) has their own syntax, like different dialects. Examples of 
            DBMS are MySQL, Apache Derby, Oracle.
            """,
            1, 33));
                                        
        questionPool.put(34, new MultiChoiceQuestion(
            """
            What parameter is omitted when connecting to an embedded database?
            1) Database network address
            2) Database name
            3) Driver type
            """,
            """
            Since the database is embedded, it is directly integrated into the 
            Java project folder. There is no need to provide the network 
            address, so the url for connection will look like this:
            String url = 'jdbc:derby:DBname; create=true';
            """,
            1, 34));
                                                
        questionPool.put(35, new MultiChoiceQuestion(
            """
            In databases, when calling next() on a ResultSet object for the 
            first time, where is the cursor placed?
            1) Second row in table
            2) First row in table
            3) Second to last row in table
            """,
            """
            ResultSet acts as an iterator (cursor) that goes through a table 
            row-by-row. It goes forward by default (top to bottom). Initially, 
            the cursor is placed before the first row. Call next() before 
            trying to access the first row's data.
            """,
            2, 35));
                              
        //Week 10: Design patterns
        questionPool.put(36, new MultiChoiceQuestion(
            """
            At what level of development do idiom patterns occur?
            1) System Level
            2) Subsystem Level
            3) Code level
            """,
            """
            Idioms in programming occur at code level. Idioms are a common or 
            popular way to express a task. Example: using an iterator instead 
            of a for loop to go through elements in a collection.
            """,
            3, 36));
        
        questionPool.put(37, new MultiChoiceQuestion(
            """
            What type of design pattern is the Factory pattern?
            1) Structural
            2) Behavioural
            3) Creational
            """,
            """
            The Factory pattern defines creating an interface with concrete 
            subclasses, which allows the right subclass object to be returned 
            based on a user's input. Example: returning bananas out of many 
            fruit objects. This comes under the Creational design pattern, 
            which involves common ways to create objects and instantiate 
            classes.
            """,
            3, 37));
        
        questionPool.put(38, new MultiChoiceQuestion(
            """
            Which pattern has one class that handles interactions between other 
            classes?
            1) Singleton
            2) Mediator
            3) Adapter
            """,
            """
            The Mediator pattern is used to reduce coupling (over-reliance) of 
            classes. Instead of many classes interacting with each other (which 
            gets hard to manage), they communicate through the mediator. The 
            classes can focus on their responsibilities without worrying about 
            the responsibilities of others (separation of concerns).
            """,
            2, 38));
        
        questionPool.put(39, new MultiChoiceQuestion(
            """
            In the Model View Controller (MVC) pattern, which component 
            manipulates or updates the Model?
            1) Controller
            2) View
            3) Model
            """,
            """
            View displays visual elements to the user in a GUI. When the View 
            receives a user action, such as clicking a button, it generates an 
            event that triggers the corresponding method in the Controller. The 
            Controller updates the Model, which holds the business logic. 
            Business logic is rules that control how data is accessed and 
            updated.
            """,
            1, 39));
        
        //Week 11: validation and verification, JUnit framework
        questionPool.put(40, new MultiChoiceQuestion(
            """
            A human action that causes an incorrect result is known as...
            1) Mistake
            2) Fault
            3) Failure
            """,
            """
            Faults are an incorrect step in a computer program. Failures are 
            incorrect results or outputs. Since computers do not make mistakes, 
            human error leads to faults then failures.
            """,
            1, 40));
        
        questionPool.put(41, new MultiChoiceQuestion(
            """
            In High Level Testing, which kind of testing checks the stability 
            of a program and how well it handles unexpected values?
            1) Usability testing
            2) Stress testing
            3) System Function testing
            """,
            """
            Stress testing gives the program extreme values, often to breaking 
            points to study the outcome. Usability testing involves getting 
            users to try the program to observe ease of use. System Function 
            testing checks the overall program and sees if the program matches 
            its functional specifications.
            """,
            2, 41));
        
        questionPool.put(42, new MultiChoiceQuestion(
            """
            Regarding the Unit Testing Framework, which is false about a unit?
            1) A unit is the smallest testable component in a program
            2) A unit is usually a method
            3) A unit depends on other components
            """,
            """
            Units should not depend on other components (other classes, 
            methods, database, etc.) for the sake of testing the unit in 
            isolation. The point of unit testing is to ensure that a particular 
            unit component is passing test cases and operating as expected.
            """,
            3, 42));
        
        questionPool.put(43, new MultiChoiceQuestion(
            """
            What is the verdict of this JUnit test case? The value calculated 
            by Multiply class is 15.0
            
            @Test
            public void test() {
              Multiply m = new Multiply();
              m.calculate(2.3, 6.54);
              double actual = m.getResult();
              double expected = 15.042;
              Assert.assertEquals(actual, expected, 0.01);
            }
            1) Pass
            2) Error
            3) Fail
            """,
            """
            The delta value (0.01) is the acceptable range for rounding in a 
            float or double. The test case results in a fail because the 
            difference between the expected and actual value is outside delta's 
            range (15.032 to 15.052). Check if the actual value falls within 
            delta's range using Math.abs(expected - actual) <= delta
            = |15.042 - 15.0| = 0.042 
            0.042 > 0.01 = Fail
            """,
            3, 43));
        
        //Week 12: Code smells
        questionPool.put(44, new MultiChoiceQuestion(
            """
            What is Bad Code Smells?
            1) Code that contains bugs or errors which is considered smelly
            2) Signs of bad design or implementation choices
            3) Bad code that is hard to read
            """,
            """
            Smelly code does not cause the program to crash, but it can reduce 
            readability and maintainability of the code. It is called 'Bad Code 
            Smells' because a surface stench signals a deeper problem, like a 
            stinky house from leaking pipes. Smelly code hints that there may 
            be design choices that leads to technical debt (making the cheapest 
            change in the present that leads to higher cost in the future).
            """,
            2, 44));
        
        questionPool.put(45, new MultiChoiceQuestion(
            """
            What is the correct definition for Bloaters in Bad Code Smells?
            1) Classes and methods that have grown too large 
            2) Classes and methods with high coupling
            3) Classes and methods that use Object-Oriented principles 
            incorrectly
            """,
            """
            Bloaters are large classes and methods. They are harder to 
            understand and maintain due to the long length, and they typically 
            accumulate overtime when no effort is made to refactor 
            (shorten/split) them. Methods longer than 10 lines of code might be 
            at risk of becoming a bloater.
            """,
            1, 45));
        
        questionPool.put(46, new MultiChoiceQuestion(
            """
            Which kind of code smell includes Shotgun Surgery?
            1) Change Preventers
            2) Bloaters
            3) Object-Orientation Abusers
            """,
            """
            Change Preventers occurs when classes and methods have high 
            coupling (high dependency) with each other. Changing one part of 
            the code can lead to changing many other parts. Shotgun Surgery is 
            specifically when a modification requires changing multiple classes.
            """,
            1, 46));
        
        questionPool.put(47, new MultiChoiceQuestion(
            """
            What does it mean when a class has low cohesion?
            1) The class depends less on other classes or methods
            2) The class juggles too many different tasks 
            3) The class is focused on its task
            """,
            """
            Low cohesion is when a class is unfocused on its actual job due to 
            handling a variety of tasks. The aim for a program is to have high 
            cohesion, where the class is focused on its job. High cohesion 
            follows Single Responsibility Principle (SRP).
            """,
            2, 47));

    }
}