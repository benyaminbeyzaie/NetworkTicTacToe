package view.display;

import view.state.State;
import view.state.StateHandler;

public class StateManager {
    private  Display display;
    private StateHandler stateHandler;
    public StateManager(Display display, StateHandler stateHandler){
        this.display = display;
        this.stateHandler = stateHandler;
    }

    public void setCurrentState(State currentState){
        display.setContentPane(currentState);
        display.revalidate();
    }

    public StateHandler getStateHandler() {
        return stateHandler;
    }

    public void setStateHandler(StateHandler stateHandler) {
        this.stateHandler = stateHandler;
    }
}
