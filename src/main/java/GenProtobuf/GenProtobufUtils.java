package GenProtobuf;

import com.intellij.execution.filters.TextConsoleBuilderFactory;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.execution.ui.ConsoleViewContentType;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowAnchor;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.ui.content.Content;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
/*
* All the deprecated usages are in this file
* need to replace ToolWindowManager.registerToolWindow with ToolWindowFactory and toolWindow extension point... sigh
*
*
*
 */
public class GenProtobufUtils {
    public static final String toolWindowID = "GenProtobuf";

    static ConsoleView getConsole(Project project) {
       ToolWindow toolWindow = null;
        ConsoleView consoleView = null;
        String[] ids = ToolWindowManager.getInstance(project).getToolWindowIds();
        boolean idIsRegistered = false;
        for (int i = 0; i < ids.length; i++) {
            if (toolWindowID.equals(ids[i])) {
                idIsRegistered = true;
                break;
            }
        }
        if (idIsRegistered == false) {
            com.intellij.openapi.ui.Messages.showMessageDialog(project, "No console found", "Information", Messages.getInformationIcon());
            return null;
        }
        //ToolWindowManager.getInstance(project).registerToolWindow(toolWindowID, true, ToolWindowAnchor.BOTTOM)
        toolWindow = ToolWindowManager.getInstance(project).getToolWindow(toolWindowID);
        toolWindow.activate(null);
        consoleView = TextConsoleBuilderFactory.getInstance().createBuilder(project).getConsole();
        toolWindow.getContentManager().removeAllContents(true);
        Content content = toolWindow.getContentManager().getFactory().createContent(consoleView.getComponent(), "run ouput", false);
        content.setPinned(true);
        toolWindow.getContentManager().addContent(content);

        return consoleView;
    }

    static String langToSwitch(String lang){
        switch(lang){
            case "C++":
                return "--cpp_out=";
            case "Python":
                return "--python_out=";
            case "C#":
                return "--csharp_out=";
            case "Java":
                return "--java_out=";
            case "Java Nano":
                return "--javanano_out=";
            case "JavaScript":
                return "--js_out=";
            case "Obj C":
                return "--objc_out=";
            case "Ruby":
                return "--ruby_out=";

        }
        return "--python_out=";
    }

    static String[] getProjectLangs(Project project){
        List<String> retLangs = new ArrayList<String>();

        String [] langNames = {"GenProtobuf.cppLoc",
        "GenProtobuf.cSharpLoc",
        "GenProtobuf.javaLoc",
        "GenProtobuf.objCLoc",
        "GenProtobuf.rubyLoc",
        "GenProtobuf.javaNanoLoc",
        "GenProtobuf.javaScriptLoc",
        "GenProtobuf.pythonLoc"};
        String newLangString = null;
        for (String langName: langNames) {
            newLangString = langStringFromValueName(project, langName);
            if (newLangString != null)
                retLangs.add(newLangString);
        }
        if(retLangs.size() < 1) return null;
        String [] retArray = new String[retLangs.size()];
        retArray = retLangs.toArray(retArray);
        return retArray;
    }

    static void syncEntireProject(Project project){
        VirtualFile sourceDirs[] = ModuleRootManager.getInstance(ModuleManager.getInstance(project).getModules()[0]).getSourceRoots();
        for(VirtualFile srcDir: sourceDirs) {
            srcDir.refresh(true, true);
        }
    }
    
    private static String langStringFromValueName(Project project, String valName){
        PropertiesComponent props = PropertiesComponent.getInstance(project);

        String val = props.getValue(valName);
        if(val == null) return null;
        if(val.length() < 1) return null;

        String switchString = "";
        switch(valName){
            case "GenProtobuf.cppLoc":
                switchString = "--cpp_out=";
                break;
            case "GenProtobuf.pythonLoc":
                switchString = "--python_out=";
                break;
            case "GenProtobuf.cSharpLoc":
                switchString = "--csharp_out=";
                break;
            case "GenProtobuf.javaLoc":
                switchString = "--java_out=";
                break;
            case "GenProtobuf.javaNanoLoc":
                switchString = "--javanano_out=";
                break;
            case "GenProtobuf.javaScriptLoc":
                switchString = "--js_out=";
                break;
            case "GenProtobuf.objCLoc":
                switchString = "--objc_out=";
                break;
            case "GenProtobuf.rubyLoc":
                switchString = "--ruby_out=";
                break;
        }

        return switchString+val;

    } 

    static void processFile(VirtualFile file, ConsoleView console, String[] langs){
        String fileName = file.getName();
        String path = file.getParent().getCanonicalPath();
        List<String> commandList = new ArrayList<String>();

        if(fileName.length() < 6) {
            console.print(fileName + " is not a .proto file", ConsoleViewContentType.ERROR_OUTPUT);
            return;
        }
        if(fileName.toLowerCase().substring(fileName.length()-6).equals(".proto") == false) {
            console.print(fileName + " is not a .proto file\n", ConsoleViewContentType.ERROR_OUTPUT);
            return;
        }
        String protocPath = ConfigureGenProtobuf.getProtocPath();
        commandList.add(protocPath);
        for (int i=0;i<langs.length;i++){
            commandList.add(langs[i]);
        }
        commandList.add(fileName);
        String command = "";
        for (int i=0;i< commandList.size();i++){
            command += commandList.get(i) +" ";
        }

        console.print("working directory:"+path+"\n", ConsoleViewContentType.NORMAL_OUTPUT);
        console.print(command+"\n", ConsoleViewContentType.NORMAL_OUTPUT);
        ProcessBuilder pb = new ProcessBuilder(commandList);
        pb.directory(new File(path));
        try {
            Process p = pb.start();
            InputStream is = p.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine()) != null){
                console.print("    "+line,ConsoleViewContentType.NORMAL_OUTPUT);
            }
            is = p.getErrorStream();
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);
            while ((line = br.readLine()) != null){
                console.print("    "+line,ConsoleViewContentType.NORMAL_OUTPUT);
            }


        } catch (Exception e) {
            console.print("Exception running protoc",ConsoleViewContentType.ERROR_OUTPUT);
            e.printStackTrace();
        }
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        console.print("\ncompleted:"+file.getCanonicalPath()+" at: "+ dtf.format(LocalDateTime.now()),ConsoleViewContentType.NORMAL_OUTPUT);
    }

}


