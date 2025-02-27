package GenProtobuf;

import com.intellij.execution.filters.TextConsoleBuilderFactory;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.execution.ui.ConsoleViewContentType;
import com.intellij.openapi.wm.ToolWindow;

import javax.swing.*;

public class GenProtobufConsole {
    JPanel empty;
    public GenProtobufConsole(ToolWindow toolWindow) {
        //console = TextConsoleBuilderFactory.getInstance().createBuilder(project).getConsole();
        //console.print("testing", ConsoleViewContentType.NORMAL_OUTPUT);
        empty = new JPanel();
    }

    public JComponent getContent() {
        return empty;
    }
}
