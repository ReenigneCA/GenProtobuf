package GenProtobuf;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.Messages;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;


public class ConfigGenProtoBufPanel {
    private JPanel panel1;
    private JTextField protocTextField;
    private JCheckBox pythonCheckBox;
    private JTextField pythonTextField;
    private JCheckBox cppCheckBox;
    private JCheckBox cSharpCheckBox;
    private JCheckBox javaCheckBox;
    private JCheckBox javaNanoCheckBox;
    private JCheckBox javaScriptCheckBox;
    private JCheckBox objCCheckBox;
    private JCheckBox rubyCheckBox;
    private JTextField cppTextField;
    private JTextField cSharpTextField;
    private JTextField javaTextField;
    private JTextField javaNanoTextField;
    private JTextField javaScriptTextField;
    private JTextField objCTextField;
    private JTextField rubyTextField;
    private JButton protocBrowse;
    private JButton pythonBrowse;
    private JButton cppBrowse;
    private JButton cSharpBrowse;
    private JButton javaBrowse;
    private JButton javaNanoBrowse;
    private JButton javaScriptBrowse;
    private JButton objCBrowse;
    private JButton rubyBrowse;
    private JComboBox quickGenCombo;
    private JButton addCustomButton;
    private JCheckBox customCheckBox;
    private JComboBox customCombo;
    private JButton customBrowse;
    private JTextField customTextField;
    private JLabel customLabel;
    private final String projectPath;
    private final DialogWrapper dialog;
    private final Project project;



    public ConfigGenProtoBufPanel(DialogWrapper Dialog, Project Project) {
        project = Project;
        projectPath = project.getBasePath();
        dialog = Dialog;
        //Messages.showMessageDialog(project,"project:"+project.toString(),"Information",Messages.getInformationIcon());

        addEnableListener(pythonCheckBox, pythonTextField, pythonBrowse);
        addEnableListener(cppCheckBox, cppTextField, cppBrowse);
        addEnableListener(cSharpCheckBox, cSharpTextField, cSharpBrowse);
        addEnableListener(javaCheckBox, javaTextField, javaBrowse);
        addEnableListener(javaNanoCheckBox, javaNanoTextField, javaNanoBrowse);
        addEnableListener(javaScriptCheckBox, javaScriptTextField, javaScriptBrowse);
        addEnableListener(objCCheckBox, objCTextField, objCBrowse);
        addEnableListener(rubyCheckBox, rubyTextField, rubyBrowse);

        protocBrowse.addActionListener(actionEvent -> {
            final JFileChooser fc = new JFileChooser();
            fc.setCurrentDirectory(new File("/"));
            fc.setFileSelectionMode(JFileChooser.FILES_ONLY);

            if (fc.showOpenDialog(dialog.getContentPane()) == JFileChooser.APPROVE_OPTION) {
                protocTextField.setText(fc.getSelectedFile().getAbsolutePath());
            }
        });

        createBrowseListener(pythonBrowse, pythonTextField);
        createBrowseListener(cppBrowse, cppTextField);
        createBrowseListener(cSharpBrowse, cSharpTextField);
        createBrowseListener(javaBrowse, javaTextField);
        createBrowseListener(javaNanoBrowse, javaNanoTextField);
        createBrowseListener(javaScriptBrowse, javaScriptTextField);
        createBrowseListener(objCBrowse, objCTextField);
        createBrowseListener(rubyBrowse, rubyTextField);

        String protoCVal = PropertiesComponent.getInstance().getValue("GenProtobuf.protocLoc");
        //Messages.showMessageDialog(project,"loaded protoCVal:"+protoCVal,"Information",Messages.getInformationIcon());
        if (protoCVal != null) protocTextField.setText(protoCVal);
        else {
            protocTextField.setText("protoc");
        }
        setupFromVar(pythonCheckBox, pythonTextField, pythonBrowse, "GenProtobuf.pythonLoc");
        setupFromVar(cppCheckBox, cppTextField, cppBrowse, "GenProtobuf.cppLoc");
        setupFromVar(cSharpCheckBox, cSharpTextField, cSharpBrowse, "GenProtobuf.cSharpLoc");
        setupFromVar(javaCheckBox, javaTextField, javaBrowse, "GenProtobuf.javaLoc");
        setupFromVar(javaNanoCheckBox, javaNanoTextField, javaNanoBrowse, "GenProtobuf.javaNanoLoc");
        setupFromVar(javaScriptCheckBox, javaScriptTextField, javaScriptBrowse, "GenProtobuf.javaScriptLoc");
        setupFromVar(objCCheckBox, objCTextField, objCBrowse, "GenProtobuf.objCLoc");
        setupFromVar(rubyCheckBox, rubyTextField, rubyBrowse, "GenProtobuf.rubyLoc");
        //TODO need to set value of combo box based on saved value
        String quickGenValue = PropertiesComponent.getInstance().getValue("GenProtobuf.quickGen");
        String quickGenProjValue = PropertiesComponent.getInstance(project).getValue("GenProtobuf.quickGen");
        if (quickGenValue != null) {
            switch (quickGenValue) {
                case "C++":
                    break;
                case "Python":
                    break;
                case "C#":
                    break;
                case "Java":
                    break;
                case "Java Nano":
                    break;
                case "JavaScript":
                    break;
                case "Obj C":
                    break;
                case "Ruby":
                    break;
                default:
                    quickGenValue = null;
            }
        }
        if (quickGenProjValue != null) {
            switch (quickGenProjValue) {
                case "C++":
                case "Python":
                case "C#":
                case "Java":
                case "Java Nano":
                case "JavaScript":
                case "Obj C":
                case "Ruby":
                    quickGenValue = quickGenProjValue;
                    break;
                default:
                    quickGenValue = null;
            }
        }

        if (quickGenValue == null) quickGenValue = "Python";
        quickGenCombo.setSelectedItem(quickGenValue);
        customCombo.addItem("Dart");
        customCombo.addItem("GO");
        customCheckBox.setText((String)customCombo.getSelectedItem());

        customCombo.addActionListener(actionEvent -> {
           customCheckBox.setText((String)customCombo.getSelectedItem());
        });
    }

    private void addEnableListener(final JCheckBox cb, final JTextField tf, final JButton browseButton) {
        cb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (cb.isSelected() == false) {
                    tf.setEnabled(false);
                    browseButton.setEnabled(false);
                } else {
                    tf.setEnabled(true);
                    if (tf.getText() == null)
                        tf.setText("." + File.separatorChar);
                    if (tf.getText().length() == 0)
                        tf.setText("." + File.separatorChar);
                    browseButton.setEnabled(true);
                }
            }
        });

    }

    public void saveResults() {
        //Messages.showMessageDialog(project,"project:"+project.toString()+" props:"+PropertiesComponent.getInstance(project),"Information",Messages.getInformationIcon());

        PropertiesComponent props = PropertiesComponent.getInstance();

        props.setValue("GenProtobuf.protocLoc", protocTextField.getText());
        props.setValue("GenProtobuf.quickGen", (String) quickGenCombo.getSelectedItem());


        props = PropertiesComponent.getInstance(project);

        props.setValue("GenProtobuf.quickGen", (String) quickGenCombo.getSelectedItem());
        props.setValue("GenProtobuf.pythonLoc", getResult(pythonCheckBox, pythonTextField));
        props.setValue("GenProtobuf.cppLoc", getResult(cppCheckBox, cppTextField));
        props.setValue("GenProtobuf.cSharpLoc", getResult(cSharpCheckBox, cSharpTextField));
        props.setValue("GenProtobuf.javaLoc", getResult(javaCheckBox, javaTextField));
        props.setValue("GenProtobuf.javaNanoLoc", getResult(javaNanoCheckBox, javaNanoTextField));
        props.setValue("GenProtobuf.javaScriptLoc", getResult(javaScriptCheckBox, javaScriptTextField));
        props.setValue("GenProtobuf.objCLoc", getResult(objCCheckBox, objCTextField));
        props.setValue("GenProtobuf.rubyLoc", getResult(rubyCheckBox, rubyTextField));


    }


    private void setupFromVar(JCheckBox cb, JTextField tf, JButton button, String valName) {

        if (valName == null) return;
        PropertiesComponent props = PropertiesComponent.getInstance(project);
        String val = null;
        try {
            val = props.getValue(valName);
        } catch (Exception e) {
            Messages.showMessageDialog(project, "couldn't get instance for:" + valName, "Information", Messages.getInformationIcon());
        }
       /* if (valName == "GenProtobuf.rubyLoc") {
            Messages.showMessageDialog(project, "loading:" + valName + " val:\"" + val + "\"", "Information", Messages.getInformationIcon());
            if(val == ""){
                Messages.showMessageDialog(project, "nothing", "Information", Messages.getInformationIcon());
            }
        }*/
        if (val == null) {
            cb.setSelected(false);
            button.setEnabled(false);
            tf.setEnabled(false);
        } else {
            cb.setSelected(true);
            tf.setEnabled(true);
            button.setEnabled(true);
            if (val != "")
                tf.setText(val);
            else {
                tf.setText("." + File.separatorChar);
            }

        }

    }


    private String getResult(JCheckBox cb, JTextField tf) {
        if (cb.isSelected() == false)
            return null;
        else
            return tf.getText();
    }

    JPanel getMainPanel() {
        return panel1;
    }

    void createBrowseListener(JButton browse, JTextField textField) {
        browse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                final JFileChooser fc = new JFileChooser();
                fc.setCurrentDirectory(new java.io.File(projectPath));
                fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

                if (fc.showOpenDialog(dialog.getContentPane()) == JFileChooser.APPROVE_OPTION) {
                    textField.setText(fc.getSelectedFile().getPath());
                }
            }
        });
    }
}
