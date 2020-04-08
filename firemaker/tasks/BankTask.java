package firemaker.tasks;

import firemaker.Firemaker;
import net.runelite.api.coords.WorldPoint;
import simple.hooks.filters.SimpleBank;
import simple.hooks.scripts.task.Task;
import simple.hooks.wrappers.SimpleNpc;
import simple.robot.api.ClientContext;

public class BankTask extends Task {

    private final WorldPoint BANK_TILE = new WorldPoint(3167, 3489, 0); // The bank tile we want to step to for banking

    public BankTask(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean condition() {
        return ctx.inventory.populate().filter(Firemaker.logtype).population() <= 0;
    }

    @Override
    public void run() {
        if(!ctx.bank.bankOpen()) {
            SimpleNpc banker = ctx.npcs.populate().filter("Banker").nearest().next();
            if(banker != null) {
                if(banker.validateInteractable()) {
                    if(banker.click("Bank")) {
                        ctx.onCondition(() -> ctx.bank.bankOpen(), 250, 10);
                    }
                }
            }
        } else {
            if(ctx.inventory.populate().filter("Tinderbox").population() <= 0){
                if(ctx.bank.withdraw("Tinderbox", SimpleBank.Amount.ONE)) {
                    ctx.onCondition(() -> ctx.inventory.populate().filter("Tinderbox").population() > 0, 250, 10);
                }
            }
            if(ctx.bank.withdraw(Firemaker.logtype, SimpleBank.Amount.ALL)) {
                ctx.onCondition(() -> ctx.inventory.populate().filter(Firemaker.logtype).population() > 0, 250, 10);
                ctx.bank.closeBank();
            }
        }
    }


    @Override
    public String status() {
        return "Banking logs";
    }
}