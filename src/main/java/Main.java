import facade.AppFacade;
import model.Application;
import utils.CommandProcessor;
import utils.InputReader;
import view.ConsolePresenter;
import view.ConsoleUI;

public class Main {
    public static void main(String[] args){
        AppFacade facade = new AppFacade();
        CommandProcessor processor = new CommandProcessor();
        InputReader reader = new InputReader();
        ConsoleUI ui = new ConsoleUI();
        ConsolePresenter presenter = new ConsolePresenter(facade, ui);

        Application app = new Application(facade, processor, reader, ui);

        ui.showMessage("Добро пожаловать в финансовый менеджер!");
        /*ui.showCommands(UserState.Unauthorized);*/
        app.start();
    }
}
