package firemaker;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import firemaker.tasks.BankTask;
import firemaker.tasks.FiremakeTask;
import simple.hooks.scripts.Category;
import simple.hooks.scripts.ScriptManifest;
import simple.hooks.scripts.task.Task;
import simple.hooks.scripts.task.TaskScript;
import simple.hooks.simplebot.ChatMessage;
import javax.swing.*;

@ScriptManifest(author = "Andrew w", category = Category.FIREMAKING, description = "Burns logs, start at the Grand Exchange.",
        discord = "Andrew w#0376", name = "Afiremaker", servers = { "Zenyte" }, version = "0.1")


public class Firemaker extends TaskScript {

    private final JFrame frame = new JFrame("Afiremaker");

    public static String logtype;

    private List<Task> tasks = new ArrayList<Task>();

    @Override
    public void onExecute() {

        FlowLayout flow = new FlowLayout();

        JPanel panel = new JPanel();

        panel.setLayout(flow);
        flow.setAlignment(FlowLayout.CENTER);

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(150,90);
        String[] week= {
                "Logs",
                "Oak logs",
                "Willow logs",
                "Teak logs",
                "Maple logs",
                "Yew logs",
                "Magic logs",
                "Redwood logs"
        };

        //create list
        JComboBox options = new JComboBox(week);
        JButton button = new JButton("Start");
        button.addActionListener(e -> {
            logtype = options.getSelectedItem().toString();
            tasks.addAll(Arrays.asList(
                    new BankTask(ctx),
                    new FiremakeTask(ctx)
            ));
            System.out.println(logtype);

            frame.dispose();
        });
        panel.add(options);
        panel.add(button);
        frame.getContentPane().add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        System.out.println("Started Afiremaker!");
    }

    @Override
    public List<Task> tasks() {
        return tasks;// Tells our TaskScript these are the tasks we want executed
    }

    @Override
    public boolean prioritizeTasks() {
        return true;// Will prioritize tasks in order added in our {tasks} List
    }

    // This method is not needed as the TaskScript class will call it, itself
    @Override
    public void onProcess() {
        // Can add anything here before tasks have been ran
        super.onProcess();// Needed for the TaskScript to process the tasks
        //Can add anything here after tasks have been ran
    }

    @Override
    public void onTerminate() {
        frame.dispose();
    }

    @Override public void onChatMessage(ChatMessage e) {}

    @Override
    public void paint(Graphics g) {
    }

}