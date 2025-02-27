package GenProtobuf;

import com.intellij.execution.ui.ConsoleView;
import com.intellij.execution.ui.ConsoleViewContentType;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;

public class QuickGen extends AnAction {
    private ConsoleView console = null;
    private final String toolWindowID = "GenProtobuf-Console";

    @Override
    public void actionPerformed(AnActionEvent e) {
        VirtualFile [] files = e.getData(LangDataKeys.VIRTUAL_FILE_ARRAY);


        Project project = ConfigureGenProtobuf.getProject(e);

        if (project == null) return;

        String quickLang = ConfigureGenProtobuf.getQuickLang(project);
        console = GenProtobufUtils.getConsole(project);
        String [] langs = new String[1];
        langs[0] = GenProtobufUtils.langToSwitch(quickLang)+".";
        //console.print(protocPath+" "+GenProtobufUtils.langToSwitch(quickLang), ConsoleViewContentType.NORMAL_OUTPUT);
        //Messages.showMessageDialog(e.getProject(), "lang:"+quickLang, "Information", Messages.getInformationIcon());
        for (int i =0; i<files.length;i++){
            GenProtobufUtils.processFile(files[i],console, langs);
            files[i].getParent().refresh(true,false);
        }
    }
}
