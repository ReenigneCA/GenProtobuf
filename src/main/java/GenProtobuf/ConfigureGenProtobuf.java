package GenProtobuf;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.Messages;
import org.jetbrains.annotations.NotNull;



public class ConfigureGenProtobuf extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        Project project = getProject(event);
        if (project == null) return;

        ConfigureGenProtobufDialog dialog = new ConfigureGenProtobufDialog(project);
        dialog.show();
        if (dialog.getExitCode() == DialogWrapper.OK_EXIT_CODE)
            dialog.saveResults();
    }

    public static Project getProject(AnActionEvent e){
        Project project = e.getProject();

        if (project == null)
            project = ProjectManager.getInstance().getOpenProjects()[0];

        if (project == null) {
            Messages.showMessageDialog(project, "Project still loading please re-run with an active project", "Information", Messages.getInformationIcon());

        }
        return project;
    }

    public static String getQuickLang(Project project){
        String quickLang = "Python";
        String tester = null;

        PropertiesComponent props = PropertiesComponent.getInstance();
        tester = props.getValue("GenProtobuf.quickGen");
        if (tester != null){
            if(tester.length() > 0) quickLang = tester;
        }
        if(project != null) {
            props = PropertiesComponent.getInstance(project);
            tester = props.getValue("GenProtobuf.quickGen");
            if (tester != null) {
                if (tester.length() > 0) quickLang = tester;
            }
        }
        return quickLang;
    }

    public static String getProtocPath(){
        String tester;
        PropertiesComponent props = PropertiesComponent.getInstance();
        tester = props.getValue("GenProtobuf.protocLoc");
        if(tester == null) return "protoc";
        if(tester.length() == 0) return "protoc";
        return tester;
    }
}
