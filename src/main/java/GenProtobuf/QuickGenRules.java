package GenProtobuf;

import com.intellij.execution.ui.ConsoleView;
import com.intellij.execution.ui.ConsoleViewContentType;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;


public class QuickGenRules extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        VirtualFile [] files = e.getData(LangDataKeys.VIRTUAL_FILE_ARRAY);
        Project project = ConfigureGenProtobuf.getProject(e);
        if (project == null) return;

        genProtoBuf(project, files);
    }

    void genProtoBuf(Project project, VirtualFile [] files) {
        if (project == null) return;

        ConsoleView console = GenProtobufUtils.getConsole(project);
        String[] langs = GenProtobufUtils.getProjectLangs(project);
        if(langs == null){
            Messages.showMessageDialog(project,"No languages setup, run Tools->Configure GenProtobuf","Information\n",Messages.getInformationIcon());
            return;
        }

        for(VirtualFile virtualFile:files){

                String fileName = virtualFile.getName();
                if(fileName.length() < 6){
                    console.print("\nfile:"+fileName+" is not a protobuf skipping\n",ConsoleViewContentType.NORMAL_OUTPUT);
                    continue;
                }
                if(fileName.substring(fileName.length()-6).equals(".proto") == false){
                    console.print("\nfile:"+fileName+" is not a protobuf skipping\n",ConsoleViewContentType.NORMAL_OUTPUT);
                    continue;
                }
                GenProtobufUtils.processFile(virtualFile,console,langs);
                //console.print(virtualFile.getName(), ConsoleViewContentType.NORMAL_OUTPUT);
            }

        GenProtobufUtils.syncEntireProject(project);
    }

}
