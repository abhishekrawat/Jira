import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.project.UpdateProjectParameters
import org.apache.log4j.Level
import org.apache.log4j.Logger

// Importing your csv file
// First column: old project name
// Second column: new project name

File file = new File("/opt/jira/home/scripts/file.csv") 


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
