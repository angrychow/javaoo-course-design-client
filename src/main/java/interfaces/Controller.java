package interfaces;

import clientEnum.Event;

public interface Controller {
    public void handleEvent(Event event);
    public void handleMessage(String text);
}
