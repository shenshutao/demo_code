<p>Spring boot with the web module</p>

<p>For my understanding, Spring boot is "based on different requirement, summarize serveral modules with default configurations inside." With those default configurations, we can build a website or start a restful resource in serverl minutes, speed up java development. <p>
<br />
Steps:
<ul>
<li>Create project using SPRING INITIALIZR, URL: http://start.spring.io/ (Alternatively, you can use the IntelliJ New->Project->Spring Initializr).</li>
For this demo, I just use the module 'web'.

<li>Import this project into IntelliJ as maven project.</li>
We can see this project's structure is based on the maven's best practice. src/main/java, src/main/resources and src/test/java.

<li>Add HelloController.java into src/main/java.</li>

<li>Run the application directly.</li>

<li>Open browser and enter url: http://localhost:8080/hello.</li>

<li>Write the Junit HelloControllerTests.java and execute.</li>

