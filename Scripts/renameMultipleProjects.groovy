import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.project.UpdateProjectParameters
import org.apache.log4j.Level
import org.apache.log4j.Logger

// Create a csv file
// First column: old project name
// Second column: new project name
// Upload it to your server and add path as per your installation

File file = new File("C:/Program Files/Atlassian/Application Data/JIRA/scripts/rename.csv")


def log = Logger.getLogger("dev.rawat")
log.setLevel(Level.DEBUG)
def projectManager = ComponentAccessor.getProjectManager()

// Reading each row from csv first column and renaming it as per second column

file.eachLine { line ->
    def columns = line.split(",")
    
	def project = projectManager.getProjectObjByName(columns[0])

	if(project) {
	    def updateProjectParameters = UpdateProjectParameters.forProject(project.id).name(columns[1])
	    projectManager.updateProject(updateProjectParameters)
	    log.debug("Project renamed")
	}
	else {
	    log.debug("Oops, project is unavailable")
	}
}
