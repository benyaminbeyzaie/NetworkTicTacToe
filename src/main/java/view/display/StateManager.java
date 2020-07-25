package view.display;

import view.state.MenuState;
import view.state.State;
import view.state.StateContainer;

import java.io.IOException;

public class StateManager {
    private  Display display;
    private StateContainer stateContainer;
    public StateManager(Display display, StateContainer stateContainer){
        this.display = display;
        this.stateContainer = stateContainer;
    }

    public void setCurrentState(State currentState) throws IOException {
        if (currentState instanceof MenuState) ((MenuState) currentState).setSignedPlayerInfo();
        display.setContentPane(currentState);
        display.revalidate();
    }

    public StateContainer getStateContainer() {
        return stateContainer;
    }

    public void setStateContainer(StateContainer stateContainer) {
        this.stateContainer = stateContainer;
    }
}
