package GenProtobuf;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.Messages;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class ConfigureGenProtobufDialog extends DialogWrapper {
    private final ConfigGenProtoBufPanel panel;

    ConfigureGenProtobufDialog(@Nullable Project project){
        super(project);
        if (project == null) {
            Messages.showMessageDialog(project, "Project still loading please re-run with an active project", "Information", Messages.getInformationIcon());
        }
        panel = new ConfigGenProtoBufPanel(this, project);
        setTitle("Configure Gen Protobuf Plugin");
        init();


    }


    public void saveResults(){
        panel.saveResults();
    }


    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return panel.getMainPanel();
    }
}
