package interfaces;

import entity.Msg;

import java.util.ArrayList;
import java.util.HashMap;

public interface RecordIO {
    public void saveData(ArrayList<Msg> msgs,int uid);

    public HashMap<Integer, ArrayList<Msg>> getRecord();
}
