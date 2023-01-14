
import java.util.*;

public class Dept {
	
	// Creating all instance variables for Dept class
	ArrayList<Academics> academics;
	ArrayList<Administrator> administrator;
	ArrayList<Faculty> faculty;
	ArrayList<UGrad> uGrad;
	List<Grad> grad;
	List<ProgramDirector> programDirector;
	ChairPerson chairperson;
	int index = 0;
	static final int maxStudents = 8;
	static final int maxTA = 5;
	

	/**
	 * Creating Dept default constructor to initialize arraylists.
	 */
	public Dept() {
		academics = new ArrayList<Academics>();
		administrator = new ArrayList<Administrator>();
		faculty = new ArrayList<Faculty>();
		uGrad = new ArrayList<UGrad>();
		grad = new ArrayList<Grad>();
		programDirector = new ArrayList<ProgramDirector>();

	}

	/**
	 * Creating Dept overloaded constructor to create a chairperson.
	 * @param chair is the ChairPerson assigned.
	 */
	public Dept(ChairPerson chair) {
		this();
		this.chairperson = chair; 
	}
	
	/**
	 * 
	 * @return returns the chairperson
	 */
	public ChairPerson getChairPerson() {
		return this.chairperson;
	}
	
	/**
	 *  HireFaculty() hires faculty and adds it to the faculty ArrayList if it already doesnt exist.
	 * @param f is the faculty to be added
	 * @throws NoSpaceException is the exception that checks that the max limit of faculty is not exceeded.
	 */
	public void HireFaculty(Faculty f) throws NoSpaceException{
		if(this.faculty.size() == 70) {
			throw new NoSpaceException();
		}else {
			if(!(faculty.contains(f))){
				faculty.add(f); //Adds faculty only if there is less than 70 members and it is not a duplicate.
			}
		}
	}
	
	/**
	 * 
	 * @return it returns the faculty ArrayList.
	 */
	public ArrayList<Faculty> getFaculty() {
		return faculty;
	}

	/**
	 * 
	 * @return it returns the number of faculty in the university.
	 */
	public int getNumOfFaculty() {
		return faculty.size();
	}
	
	
	/**
	 * It adds a student to the faculty which has space. It is on a first come
	 * first serve basis.
	 * @param student is the undergrad student that gets assigned to a faculty.
	 */
	public void AdmitStudent(UGrad student) {
		int n = index;
		if(this.faculty.get(n).getNumOfAdvisingUGrads() < 8) {
			addStudent(student, n); //Adds student only if there is less than 8 advisees for the faculty.
		}
		else {
			n += 1;
			index = n;
			addStudent(student, n); //Adds student to the next faculty available.
		}
	}
	
	/**
	 * addStudent() assigns a student a faculty and a faculty the student.
	 * The function also sorts based on alphabetical order.
	 * @param ugrad is the student needed to be assigned
	 * @param n is the location in the arrayList that designates which faculty to add to.
	 */
	public void addStudent(UGrad ugrad, int n) {
		if(this.uGrad.size() <= 500) {
			if(!(this.uGrad.contains(ugrad))){
				this.uGrad.add(ugrad); // Adds uGrad student if its not a duplicate.
				Collections.sort(uGrad); //Sorts based on name alphabetically
				ugrad.setFaculty(this.faculty.get(n)); //Adds student objects faculty in Student class.
				faculty.get(n).setAdvising(ugrad);//Adds student to faculty arrayList in Faculty class.
			}
		}
	}

	/**
	 * 
	 * @return it returns the number of undergrad students;
	 */
	public int getNumOfUGradStudents() {
		return uGrad.size();
	}
	
	/**
	 * 
	 * @return it returns the number of graduate students;
	 */
	public int getNumOfGradStudents() {
		return grad.size();
	}
	
	/**
	 * HireTA hires the added student as a TA if there is space in the faculty.
	 * @param s is the graduate student to be assigned as a TA.
	 * @throws NoSpaceException is the exception thrown if all faculties are full.
	 */
	public void HireTA(Grad s) throws NoSpaceException {
		if(this.grad.size() <= 150) {
		int n =	index;
		if(this.faculty.get(n).getTAs().size() < 5) {
			addTA(s, n); //Adds TA to faculty n if there is space.
		}
		else if(this.faculty.get(n+1).getTAs().size() < 5){
			n += 1;
			index = n;
			addTA(s, n); // adds TA to next location.
		}
		}
		else {
			throw new NoSpaceException();
		}
	}
	
	/**
	 * addTA adds the TA to the faculty arrayList and assigns the graduate student a faculty.
	 * @param s is the graduate student to be assigned as a TA.
	 * @param n is the location in the arrayList to designate which faculty should the student 
	 * be assigned a TA to.
	 */
	public void addTA(Grad s, int n) {
		if(!(this.grad.contains(s))){
			this.grad.add(s); // Adds student to grad array only if its not a duplicate.
			Collections.sort(grad);
			s.setFaculty(this.faculty.get(n)); //Set student's faculty in Student class
			faculty.get(n).setTA(s); //Add student to TA array in Faculty class.
		}
		if(!(this.faculty.get(n).TA.isEmpty())) {
			this.faculty.get(n).populated = true; // If there exists a TA in current faculty object populated = true
		}
	}

	/**
	 * AlumnusUGrad is a function that removes all links between an undergraduate
	 * student and a faculty after they have graduated.
	 * @param student is the undergraduate student that is graduating.
	 */
	public void AlumnusUGrad(Student student) {
		this.faculty.get(index).advising.remove(student); //Remove student from advising array in Faculty class.
		this.uGrad.remove(student);//Remove student from uGrad array.
	}

	/**
	 * AlumnusGrad is the function that removed all links between a graduate and their
	 * faculty after graduating.
	 * @param student is the student that is graduating
	 * @throws NoTAException is thrown when a faculty's last TA graduate leaving it empty.
	 */
	public void AlumnusGrad(Grad student) throws NoTAException {
		this.faculty.get(index).TA.remove(student);
		this.grad.remove(student); // Removed connections between grad and faculty.
		if(this.faculty.get(index).TA.isEmpty()){
			this.faculty.get(index).populated = false; // Populated becomes false if the last TA is removed from faculty.
		}
		if(this.faculty.get(index).populated == false) {// Checks if the faculty has its last TA removed
			for(int i = 0; i < this.faculty.size(); i ++) {
				if(this.faculty.get(i).TA.isEmpty()) { 
					throw new NoTAException();
				}
			}
		}
		
		
	}

	/**
	 * 
	 * @return it returns an arrayList of all graduate students.
	 */
	public List<Grad> ExtractAllGradDetails() {
		return this.grad;
	}

	/**
	 * 
	 * @return it returns an arrayList of all undergraduate students.
	 */
	public List<UGrad> ExtractAllUGradDetails() {
		return this.uGrad;
	}

	/**
	 * 
	 * @return it returns all faculty details after sorting them alphabetically by name.
	 */
	public List<Faculty> ExtractAllFacultyDetails() {
		Collections.sort(faculty);//Sort faculty array alphabetically.
		return this.faculty;
	}

	/**
	 * Extracts all faculty details based on program.
	 * @param string is the program to search for.
	 * @return it returns all the faculty details of the required program in alphabetical oder by name.
	 */
	public List<Faculty> ExtractFacultyDetails(String string) {
		List<Faculty> fcopy = new ArrayList<Faculty>();
		for(int  i = 0; i < this.faculty.size(); i++){
			if(this.faculty.get(i).program.equals(string)) {
				fcopy.add(faculty.get(i)); // Add all faculty that are in the required program
			}
		}
		Collections.sort(fcopy); //sort array alphabetically
		return fcopy;
	}

	/**
	 * 
	 * @param f is the faculty who's advisees we wish to extract
	 * @return it returns all undergraduate students advised by faculty f in alphabetical order by name.
	 */
	public ArrayList<Student> ExtractAdviseesDetails(Faculty f) {
		Collections.sort(f.advising); //Sort advising array based on name;
		return f.advising;
	}

	/**
	 * 
	 * @param f is the faculty who's TA's we wish to extract.
	 * @return it returns all graduate students who are TA's in faculty f, sorted by alphabetical.
	 */
	public ArrayList<Student> ExtractTAsDetails(Faculty f) {
		Collections.sort(f.TA); //Sort TA array based on name
		return f.TA;
	}

	/**
	 * 
	 * @param p1 p1 adds a program director.
	 * @throws NoSpaceException is thrown if more than 3 programDirectors are added. 
	 */
	public void addProgramDirector(ProgramDirector p1) throws NoSpaceException{
		if(programDirector.size() <= 3) {
			this.programDirector.add(p1); 
		}
		else {
			throw new NoSpaceException();
		}
	}

	/**
	 * Retires the faculty and then assigns the remaining advisees and TA's to the next available 
	 * faculty.
	 * @param f is the faculty that is retiring.
	 * @throws NoSpecialtyException is thrown if there is no other faculty with the required skills.
	 * @throws NoSpaceException is thrown if there is no space to reassign students.
	 */
	public void RetireFaculty(Faculty f) throws NoSpecialtyException, NoSpaceException {
		boolean a = false;
		ArrayList<Student> Grad = new ArrayList<Student>();
		
		for(int i = 0; i < f.TA.size(); i++) {//Adding all TA into local arrayList
					Grad.add(f.TA.get(i));
		}
		this.faculty.remove(f); //Deleting faculty
		
		for(int i = 0; i < this.faculty.size(); i++) {
			
			if(this.faculty.get(i).TA.size() == 5) {
				a = true; // If all faculty have max number of TA's
			}
		}
		
		if(a == true) {
			throw new NoSpecialtyException();
		}
		else{
			for(int i = 0; i < grad.size(); i++) {
				this.HireTA(grad.get(i)); //Add TA's to remaining Faculties.
			}
		}
	}
}




class Person{
	
	//Setting up variables that all subclasses will share
	protected String firstName;
	protected String lastName;
	protected String fullName;
	protected int age;
	protected String gender;
	protected String address;
	
	
	/**
	 * 
	 * @return the firstName of the person object
	 */
	public String getFirstName() {
		return this.firstName;
	}
	
	/**
	 * 
	 * @param firstName is used to set the firstName to the person object.
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	/**
	 * 
	 * @return it returns the lastName of the person object.
	 */
	public String getLastName() {
		return this.lastName;
	}
	
	/**
	 * 
	 * @param lastName is used to set the lastName of the person object.
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	/**
	 * 
	 * @return it returns the age of the person object.
	 */
	public int getAge() {
		return this.age;
	}
	
	/**
	 * 
	 * @param age sets the age of the person object.
	 */
	public void setAge(int age) {
		this.age = age;
	}
	
	/**
	 * 
	 * @return it returns the gender of the person object.
	 */
	public String getGender() {
		return this.gender;
	}
	
	/**
	 * 
	 * @param gender is used to set the gender of the person object.
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	/**
	 * 
	 * @return it returns the address of the person object.
	 */
	public String getAddress() {
		return this.address;
	}
	
	/**
	 * 
	 * @param address is used to set the address of the person object.
	 */
	public void setAddress(String address) {
		this.address = address;
	}
}



class Academics extends Person{
	
	//Setting up instance variables that all subclasses will share.
	protected int employeeID = 98;
	protected static int uniqueID = 1;
	protected double salary;
	boolean a = false;
	protected String program;

	
	/**
	 * 
	 * @return it returns the employeeID of the object.
	 */
	public int getEmployeeID(){
		return employeeID;
	}
	
	/**
	 * 
	 * @param academics is the object who's employeeID needs to be set.
	 */
	public void setEmployeeID(Academics academics) {
		employeeID += uniqueID;
	}
	
	/**
	 * 
	 * @return returns the salary of the object.
	 */
	public double getsalary() {
		return this.salary;
	}
	
	/**
	 * 
	 * @param salary is used to set the salary of the object.
	 */
	public void setSalary(double salary) {
		this.salary = salary;
	}
	
}




class Administrator extends Academics{
	
}




class ProgramDirector extends Administrator{
	
	/**
	 * Overloaded constructor to set up the ProgramDirector
	 * @param firstName is the first name Of the programDirector
	 * @param lastName is the last name Of the programDirector
	 * @param age is the age Of the programDirector
	 * @param gender is the gender Of the programDirector
	 * @param address is the address Of the programDirector
	 */
	public ProgramDirector(String firstName, String lastName, int age, String gender, String address) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.gender = gender;
		this.address = address;
	}

	/**
	 * 
	 * @param program sets the program for the programDirector 
	 */
	public void setProgram(String program) {
		this.program = program;		
	}
}


class ChairPerson extends Administrator{
	
	/**
	 * Overloaded constructor for the ChairPerson
	 * @param firstName is the first name of the chairPerson
	 * @param lastName is the last name of the chairPerson
	 * @param age
	 * @param gender
	 * @param address
	 */
	public ChairPerson(String firstName, String lastName, int age, String gender, String address) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.gender = gender;
		this.address = address;
		uniqueID++;
		setEmployeeID(this);
		this.employeeID = getEmployeeID();

	}
	
	/**
	 * 
	 * @return is returns the chairPerson
	 */
	public ChairPerson getChairPerson() {
		return this;
	}
	
	
	/**
	 * @return returns the required string that shows information for the chairperson.
	 */
	@Override
	public String toString() {
		return "Chair Person [[[" + this.employeeID + ", " + this.salary + "[" + this.firstName +", " + this.lastName + 
				", " + this.age + ", " + this.gender + ", " + this.address + "]]]]";
	}
}


class Faculty extends Academics implements Comparable<Object>{
	
	//All instance variables for Faculty class
	protected ArrayList<Student> advising;
	protected ArrayList<Student> TA;
	protected boolean populated = false; 
	
	/**
	 * Overloaded Faculty constructor that sets up the faculty. The employeeID is also set
	 * and the arraylist for advising and TA is initialised.
	 * @param firstName is the first name of the faculty object.
	 * @param lastName is the last name of the faculty object.
	 * @param age is the age of the faculty object.
	 * @param gender is the gender of the faculty object.
	 * @param address is the address of the faculty object.
	 */
	public Faculty(String firstName, String lastName, int age, String gender, String address) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.fullName = this.firstName + this.lastName;
		this.age = age;
		this.gender = gender;
		this.address = address;
		advising = new ArrayList<Student>();
		TA = new ArrayList<Student>();
		uniqueID++;
		setEmployeeID(this);
		this.employeeID = getEmployeeID();
	}
	
	/**
	 * 
	 * @param program is the program that is set for the faculty object.
	 */
	public void setProgram(String program) {
		this.program = program;
	}
	
	/**
	 * 
	 * @return it returns the program of the faculty object.
	 */
	public String getProgram() {
		return this.program;
	}
	
	/**
	 * @return returns the required string to view the Faculty information.
	 */
	@Override
	public String toString() {
		return "Faculty " + this.program + "[[" + this.employeeID + ", " + this.salary + "[" + this.firstName +", " + this.lastName + 
		                                     ", " + this.age + ", " + this.gender + ", " + this.address + "]]]";
	}
	
	/**
	 * 
	 * @param student is used to add the student to the current faculty object.
	 */
	public void setAdvising(Student student) {
		advising.add(student);
	}
	
	/**
	 * 
	 * @return returns the advising arrayList that the faculty has.
	 */
	public ArrayList<Student> getAdvisingUgrads() {
		return advising;
	}

	/**
	 * 
	 * @return returns the TA arrayList that the faculty has.
	 */
	public ArrayList<Student> getTAs() {
		return TA;
	}
	
	/**
	 * 
	 * @param student is the graduate student that is added as a TA for the current faculty.
	 */
	public void setTA(Student student) {
		TA.add(student);
	}

	/**
	 * 
	 * @return returns the number of advisees the current faculty has.
	 */
	public int getNumOfAdvisingUGrads() {
		return advising.size();
	}

	/**
	 * 
	 * @return returns the number of TA's the current faculty has.
	 */
	public int getNumOfTAs() {
		return TA.size();
	}
	
	/**
	 * Two objects are equal if they have the same employeeID, first name, last name, age, gender and address.
	 * @param object is the object to check if it is equal with.
	 * @return returns whether the current object and object are equal.
	 */
	@Override
	public boolean equals(Object object) {
		//Grad grad = (Grad) object;
		Faculty faculty = (Faculty) object;
			if(this.employeeID == faculty.employeeID && this.firstName == faculty.firstName 
					&& this.lastName == faculty.lastName && this.age == faculty.age
					&& this.gender == faculty.gender && this.address == faculty.address) {
				return true;
			}
			else {
				return false;
			}
	}

	/**
	 * compareTo checks if the fullName is the same or which string is greater than the other.
	 * @param o is the object to be compared to.
	 * @return returns an integer based on comparing the fullName of the object.
	 */
	@Override
	public int compareTo(Object o) {
		Faculty faculty = (Faculty) o;
		
		if(this.fullName == faculty.fullName) {
			return 0;
		}
		else if(this.fullName.compareTo(faculty.fullName) < 0){
			return -1;
		}
		else {
			return 1;
		}	
	}
}


class Student extends Person implements Comparable<Object>{
	
	//All instance variables of the student class
	protected static int uniqueID = 1;
	protected int studentID = 998;
	ArrayList<Student> students = new ArrayList<Student>();;
	Faculty faculty;
	
	
	/**
	 * 
	 * @return it returns the studentId of the current Object
	 */
	public int getStudentID(){
		return studentID;
	}
	
	/**
	 * 
	 * @param student is used to set the StudentID of the current Object
	 */
	public void setStudentID(Student student) {
		studentID += uniqueID;
	}
	
	/**
	 * 
	 * @param faculty is used to set the faculty of the current student object.
	 */
	public void setFaculty(Faculty faculty) {
		this.faculty = faculty;
	}
	
	/**
	 * 
	 * @return it returns the faculty the student belongs to.
	 */
	public Faculty getAdvisor() {
		return this.faculty;
	}
	
	/**
	 * 
	 * @param student is the student who's faculty should be removed.
	 */
	public void removeFaculty(Student student) {
		student.faculty = null;
	}
	
	/**
	 * @param object is the object to check if it is equal with.
	 * @return returns whether the current object and object are equal.	 
	 */
	@Override
	public boolean equals(Object object) {
		//Grad grad = (Grad) object;
		Student grad = (Student) object;
		Student t = (Student) this;
			if(this.studentID == grad.studentID && this.firstName == grad.firstName 
					&& this.lastName == grad.lastName && this.age == grad.age
					&& this.gender == grad.gender && this.address == grad.address) {
				return true;
			}
			else {
				return false;
			}
}
	
	/**
	 * compareTo checks if the fullName is the same or which string is greater than the other.
	 * @param o is the object to be compared to.
	 * @return returns an integer based on comparing the fullName of the object.
	 */
	@Override
	public int compareTo(Object o) {
		Student grad = (Student) o;
		
		if(this.fullName == grad.fullName) {
			return 0;
		}
		else if(this.fullName.compareTo(grad.fullName) < 0){
			return -1;
		}
		else {
			return 1;
		}
		
	}
}


class UGrad extends Student{
	
	/**
	 * Overloaded constructor that sets the information for undergrad student.
	 * It also sets the StudentID of the undergrad student.
	 * @param firstName is the first name of the undergrad student object.
	 * @param lastName is the last name of the undergrad student object.
	 * @param age is the age of the undergrad student object.
	 * @param gender is the gender of the undergrad student object.
	 * @param address is the address of the undergrad student object.
	 */
	public UGrad(String firstName, String lastName, int age, String gender, String address) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.fullName = this.firstName + this.lastName;
		this.age = age;
		this.gender = gender;
		this.address = address;
		uniqueID++;
		setStudentID(this);
		this.studentID = getStudentID();
	}
	
	/**
	 * @return returns the required string that shows information for the undergraduate.
	 */
	public String toString() {
		return "Undergraduate [" + this.studentID + "[[" + this.firstName + ", " + this.lastName + 
				", " + this.age + ", " + this.gender + ", " + this.address + "]]]";
	}
}


class Grad extends Student{
	
	/**
	 * Overloaded constructor to set the attributes of the graduate class.
	 * It also sets the StudentID of the graduate student.
	 * @param firstName is the firstName of the graduate student.
	 * @param lastName is the lastName of the graduate student.
	 * @param age is the age of the graduate student.
	 * @param gender is the gender of the graduate student.
	 * @param address is the address of the graduate student.
	 */
	public Grad(String firstName, String lastName, int age, String gender, String address) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.fullName = this.firstName + this.lastName;
		this.age = age;
		this.gender = gender;
		this.address = address;
		uniqueID++;
		setStudentID(this);
		this.studentID = getStudentID();
	}
	

	/**
	 * @return returns the required string that shows information for the graduate student.
	 */
	public String toString() {
		return "Graduate [" + this.studentID + "[[" + this.firstName + ", " + this.lastName + 
				", " + this.age + ", " + this.gender + ", " + this.address + "]]]";
	}
}





class NoSpaceException extends Exception{ // Exception for when there is no space.
	
	/**
	 * constructor for user created exception.
	 */
	public NoSpaceException() {
		super();
	}
	
	/**
	 * overloaded Constructor for the user created exception to display message  .
	 */
	public NoSpaceException(String message) {
		super(message);
	}
}

class NoTAException extends Exception{ // Exception for when there is no TA in a faculty.
	
	/**
	 * Constructor for the user created exception.
	 */
	public NoTAException() {
		super();
	}
	
	/**
	 * overloaded Constructor for the user created exception to display message  .
	 */
	public NoTAException(String message) {
		super(message);
	}
}

class NoSpecialtyException extends Exception{ // Exception for when there is no faculty member for the given program.
	
	/**
	 * Constructor for the user created exception.
	 */
	public NoSpecialtyException() {
		super();
	}
	
	/**
	 * overloaded Constructor for the user created exception to display message  .
	 */
	public NoSpecialtyException(String message) {
		super(message);
	}
}
