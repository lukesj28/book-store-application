package sample.base;

public class GoldCust extends State {

    public GoldCust(Cust C) {
        C.setStatus("GOLD");
    }

    @Override
    public void UpdateStatus(Cust C) {
        if (C.getPoints() >= 1000) {
            C.setState(new GoldCust(C));
        } else if (C.getPoints() >= 0) {
            C.setState(new SilverCust(C));
        }
    }
}
