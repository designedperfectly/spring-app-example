
	README: instructions to build and run or to test the project

	Build instructions (using an Eclipse Maven project):

		1. Run As / Maven clean
		2. Run As / Maven build... use package goal
		3. Run As / Maven install

	If preferred, equivalent mvn command can be used on the command line instead:

		1. mvn clean package install

	After building, Eclipse can either run as Java Application or JUnit test.
	After installing, run as:

		1. java -jar target\spring-app-example-0.1.0-SNAPSHOT.jar

	If running the application (Eclipse or using java -jar), test instructions are:

		Option 1. Open localhost:8080/passwd-app/ or localhost:8080/passwd-app/index.html and try various passwords.
		Option 2. Use curl for localhost:8080/passwd-app/passwd, including JSON object:

			curl -H "Content-Type: application/json" -d "{\"passwd\": \"<passwdToTest>\"}" localhost:8080/passwd

		For example:

			curl -H "Content-Type: application/json" -d "{\"passwd\": \"abc123\"}" localhost:8080/passwd

	Questions can be sent to: ResumeRequest@DesignedPerfectly.com
