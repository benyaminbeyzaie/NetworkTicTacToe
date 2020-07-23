package view.display;

import view.state.State;
import view.state.StateContainer;

public class StateManager {
    private  Display display;
    private StateContainer stateContainer;
    public StateManager(Display display, StateContainer stateContainer){
        this.display = display;
        this.stateContainer = stateContainer;
    }

    public void setCurrentState(State currentState){
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
