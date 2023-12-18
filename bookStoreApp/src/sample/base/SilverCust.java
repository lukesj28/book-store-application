package sample.base;

public class SilverCust extends State {

    public SilverCust(Cust C) {
        C.setStatus("SILVER");
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
