package GenProtobuf;

import com.intellij.execution.ui.ConsoleView;
import com.intellij.execution.ui.ConsoleViewContentType;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ContentIterator;
import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

public class GenAllProtobufs extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = ConfigureGenProtobuf.getProject(e);
        genAllProtoBufs(project);
    }

    void genAllProtoBufs(Project project) {
        if (project == null) return;

        ConsoleView console = GenProtobufUtils.getConsole(project);
        String[] langs = GenProtobufUtils.getProjectLangs(project);
        if(langs == null){
            Messages.showMessageDialog(project,"No languages setup, run Tools->Configure GenProtobuf","Information",Messages.getInformationIcon());
            return;
        }

        ProjectFileIndex.SERVICE.getInstance(project).iterateContent(new ContentIterator() {
            @Override
            public boolean processFile(@NotNull VirtualFile virtualFile) {
                String fileName = virtualFile.getName();
                if(fileName.length() < 6) return true;
                if(fileName.substring(fileName.length()-6).equals(".proto") == false) return true;
                GenProtobufUtils.processFile(virtualFile,console,langs);
                console.print(virtualFile.getName(), ConsoleViewContentType.NORMAL_OUTPUT);
                return true;
            }
        });
        GenProtobufUtils.syncEntireProject(project);
    }
}
