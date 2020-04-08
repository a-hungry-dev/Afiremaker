package firemaker.tasks;

import firemaker.Firemaker;
import net.runelite.api.coords.WorldPoint;
import simple.hooks.filters.SimpleSkills;
import simple.hooks.scripts.task.Task;
import simple.robot.api.ClientContext;

public class FiremakeTask extends Task {

    private int runs = 0;

    public FiremakeTask(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean condition() {
        return ctx.inventory.populate().filter(Firemaker.logtype).population() >= 1;
    }

    @Override
    public void run() {

        int xpBeforeAction = ctx.skills.experience(SimpleSkills.Skills.FIREMAKING);
        if(ctx.inventory.inventoryFull()){
            if(ctx.players.getLocal().getLocation().distanceTo(new WorldPoint(3196, 3491 - runs, 0)) >= 1) {
                ctx.pathing.step(new WorldPoint(3196, 3491 - runs, 0));
            } else {
                ctx.inventory.populate().filter("Tinderbox").next().click(0);
                ctx.inventory.populate().filter(Firemaker.logtype).next().click(0);
                ctx.onCondition(() -> xpBeforeAction < ctx.skills.experience(SimpleSkills.Skills.FIREMAKING), 1, 20);
            }
        } else {
            if(ctx.players.getLocal().getAnimation() == -1){
                ctx.inventory.populate().filter("Tinderbox").next().click(0);
                ctx.inventory.populate().filter(Firemaker.logtype).next().click(0);
                ctx.onCondition(() -> xpBeforeAction < ctx.skills.experience(SimpleSkills.Skills.FIREMAKING), 1, 20);
            }
        }
        if(ctx.inventory.isEmpty()){
            runs++;
            if(runs == 4){
                runs = 0;
            }
        }



    }


    @Override
    public String status() {
        return "Banking logs";
    }
}